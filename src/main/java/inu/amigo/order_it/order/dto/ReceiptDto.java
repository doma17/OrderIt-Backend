package inu.amigo.order_it.order.dto;

import java.util.List;

public class ReceiptDto {
    private List<ReceiptDetailDto> receiptDtoList;
    private OrderType orderType;
    private int totalPrice;

    public List<ReceiptDetailDto> getReceiptDtoList() {
        return receiptDtoList;
    }

    public void setReceiptDtoList(List<ReceiptDetailDto> receiptDtoList) {
        this.receiptDtoList = receiptDtoList;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
