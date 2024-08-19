package entity;

import java.io.Serializable;
import entity.Product;

public class CartItem implements Serializable {
   private Product product;
    private int cartItemId ;
    private int productId;
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

    public CartItem(Product product, int cartItemId, int productId, double price, int quantity) {
        this.product = product;
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        return "Id : "+cartItemId +"| ProductName : " + product.getProductName()
                +"\n Price : "+price + "| Quantity : " +quantity + "| TotalAmout :" +quantity*price;
    }

}
