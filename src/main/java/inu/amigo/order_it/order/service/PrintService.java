//package inu.amigo.order_it.order.service;
//
//import inu.amigo.order_it.item.repository.ItemRepository;
//import inu.amigo.order_it.order.dto.ReceiptDetailDto;
//import inu.amigo.order_it.order.dto.ReceiptDto;
//import inu.amigo.order_it.order.entity.Detail;
//import inu.amigo.order_it.order.entity.Order;
//import inu.amigo.order_it.order.printer.SewooPrinter;
//import inu.amigo.order_it.order.repository.OrderRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class PrintService {
//
//    private final SewooPrinter sewooPrinter;
//
//    public PrintService(SewooPrinter sewooPrinter) {
//        this.sewooPrinter = sewooPrinter;
//    }
//
//    public void print(Order order) {
//        ReceiptDto receiptDto = new ReceiptDto();
//        List<ReceiptDetailDto> receiptDtoList = new ArrayList<>();
//        int totalPrice = 0;
//
//        for (Detail detail : order.getDetails()) {
//            ReceiptDetailDto receiptDetail = new ReceiptDetailDto();
//            receiptDetail.setName(detail.getItem().getName());
//            receiptDetail.setQuantity(detail.getQuantity());
//            receiptDetail.setPrice(receiptDetail.getQuantity() * detail.getItem().getPrice());
//            totalPrice += receiptDetail.getPrice();
//
//            receiptDtoList.add(receiptDetail);
//        }
//        receiptDto.setReceiptDtoList(receiptDtoList);
//
//        receiptDto.setOrderType(order.getOrderType());
//        receiptDto.setTotalPrice(totalPrice);
//
//        sewooPrinter.print(receiptDto);
//    }
//}
