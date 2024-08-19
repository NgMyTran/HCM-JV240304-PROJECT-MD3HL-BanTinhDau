package entity;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int cartItemId;
    private int productId; // Thay đổi từ Product thành int
    private double price;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int cartItemId, int productId, double price, int quantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Id: " + cartItemId + " | ProductId: " + productId
                + "\n Price: " + price + " | Quantity: " + quantity + " | TotalAmount: " + quantity * price;
    }
}
