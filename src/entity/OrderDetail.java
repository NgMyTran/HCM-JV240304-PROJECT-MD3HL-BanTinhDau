package entity;

// Enum for order status

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private int productId;
    private int orderId;
    private String name;
    private double unitPrice;
    private int quantity;

    // Constructor
    public OrderDetail(int productId, int orderId, String name, double unitPrice, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productId=" + productId +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                '}';
    }
}
