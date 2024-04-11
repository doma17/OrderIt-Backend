package inu.amigo.order_it.order.printer;

import com.sewoo.thermal.jni.LKPOSTOT;
import com.sewoo.thermal.jni.LKPOSTOTConst;
import inu.amigo.order_it.order.dto.OrderType;
import inu.amigo.order_it.order.dto.ReceiptDetailDto;
import inu.amigo.order_it.order.dto.ReceiptDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * 윈도우 환경에서만 사용가능!!
 */
@Slf4j
@Component
public class SewooPrinter {

    private final LKPOSTOT SWLib = new LKPOSTOT();

    private String ESC    = ((char) 0x1b) + "";
    private String LF     = ((char) 0x0a) + "";
    private String SPACES = "                                                                      ";
    private String logoImagePath = "C:\\Users\\isoft\\IdeaProjects\\PrinterProject\\src\\logo.bmp";

    @Async
    public void print(ReceiptDto receiptDto) {
        SewooPortConnect portConnect = new SewooPortConnect(SWLib);

        if (!portConnect.openByUsb()) {
            log.error("[SewooPrinter] port opening is failed");
            throw new RuntimeException("port open fail");
        }
        //
        printDetails(receiptDto.getReceiptDtoList(), receiptDto.getOrderType(), receiptDto.getTotalPrice());

        if (!portConnect.close()) {
            log.error("[SewooPrinter] port closing is failed");
            throw new RuntimeException("port open fail");
        }
        log.info("[SewooPrinter] Print job completed successfully.");
    }

    public void printDetails(List<ReceiptDetailDto> receiptDetailDtoList, OrderType orderType, int totalPrice) {
        try {
            do {
                // Start Print
                SWLib.PrintStart();
                // Print Logo Image
                printLogoImage();
                // Header
                printHeader(orderType);

                // Item Details
                for (ReceiptDetailDto receiptDetailDto : receiptDetailDtoList) {
                    String itemName = receiptDetailDto.getName();
                    int quantity = receiptDetailDto.getQuantity();
                    int price = receiptDetailDto.getPrice();
                    String itemDetail = formatLine(itemName, quantity, price);
                    SWLib.PrintNormal(itemDetail + "\n");
                }

                // Total Price
                SWLib.PrintNormal(ESC + "|uC                                          \n");
                SWLib.PrintNormal(ESC + "|bC" + ESC + "|2C총 금액: " + totalPrice + "원\n\n");

                // Print Barcode
                SWLib.PrintBarCode("1234567890", 109, 40, 512, 1, 2);
                // Footer : Cutting After Barcode
                SWLib.PrintNormal(ESC + "|fP");
                // Stop Print
                SWLib.PrintStop();

            } while (false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printLogoImage() {
        String bitmapFile = logoImagePath;
        File imgFile = new File(bitmapFile);
        if( imgFile.exists() && imgFile.isFile() )
        {
            SWLib.PrintBitmap(bitmapFile, LKPOSTOTConst.LK_ALIGNMENT_CENTER, 0, 5, 0);
        }
        imgFile = null;
    }

    private void printHeader(OrderType orderType) {
        // Header
        SWLib.PrintNormal(ESC + "|rATEL (032)-123-4567\n\n");
        SWLib.PrintNormal(ESC + "|cA방문해주셔서 감사합니다!\n");
        SWLib.PrintNormal(ESC + "|cA" + LocalDate.now() + "\n");

        // OrderType
        String type = (orderType.equals(OrderType.TAKE_OUT)) ? "TAKE OUT" : "EAT IN";
        SWLib.PrintNormal(ESC + "|uC                                          \n");
        SWLib.PrintNormal(ESC + "|bC" + ESC + "|2C" + ESC + "|cA" + type + "\n");
        SWLib.PrintNormal(ESC + "|uC제품명                    개수        가격\n");
    }

    /**
     한 줄당 사용가능한 char은 총 42개이다. 한글은 2개의 char공간을 차지하고,
     나머지는 1개의 char 공간을 차지한다.
     "제품명                      개수      가격"
     개 <- 28번째, 가격 <-38번쨰
     개수의 위치가 잘 맞게 설정하는 것이 중요하다. 개수의 숫자는 28, 29번째에 들어가야한다.
     즉, 메뉴의 이름 파트는 이름을 포함하여 28개의 char 공간을 차지하고,
     String Builder를 통해서 이름을 포함하여 나머지 공간을 빈칸으로 채워야 한다.
     */
    private String formatLine(String name, int quantity, int price) {

        StringBuilder temp = new StringBuilder(name);
        int tempLength = calculatePrintWidth(temp.toString());

        if (tempLength > 27) {
            System.out.println("28글자 넘음");
            temp.substring(0, 27);
        }
        for (int i = 0; i < 27 - tempLength; i++) {
            temp.append(" ");
        }

        temp.append(quantity);
        tempLength = calculatePrintWidth(temp.toString());

        String formattedPrice = String.format("%,10d원", price);
        int priceLength = calculatePrintWidth(formattedPrice);
        for (int i = 0; i < 42 - priceLength - tempLength; i++) {
            temp.append(" ");
        }

        temp.append(formattedPrice);

        return temp.toString();
    }

    private int calculatePrintWidth(String text) {
        int width = 0;
        for (char c : text.toCharArray()) {
            if (isKorean(c)) {
                width += 2; // 한글은 2칸 사용
            } else {
                width += 1; // 그 외 문자는 1칸 사용
            }
        }
        return width;
    }

    private boolean isKorean(char c) {
        return (c >= '\uAC00' && c <= '\uD7A3') || (c >= '\u1100' && c <= '\u11FF') || (c >= '\u3130' && c <= '\u318F');
    }

}
