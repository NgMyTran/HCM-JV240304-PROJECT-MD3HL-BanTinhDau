package designImpl;

import design.IDesign;
import entity.CartItem;
import entity.Product;
import util.IOFile; // Make sure to import IOFile

import java.util.ArrayList;
import java.util.List;

public class CartDesignImpl implements IDesign<CartItem, Integer> {
    private static List<CartItem> carts;

    public CartDesignImpl() {
        carts = IOFile.readFromFile(IOFile.CART_PATH);
    }

    @Override
    public List<CartItem> getAll() {
        return carts;
    }

    @Override
    public void save(CartItem cartItem) {
        CartItem existingCartItem = findById(cartItem.getCartItemId());
        if (existingCartItem == null) {
            cartItem.setCartItemId(getNewCartItemId());
            carts.add(cartItem);
        } else {
            int index = carts.indexOf(existingCartItem);
            if (index != -1) {
                carts.set(index, cartItem);
            }
        }
        IOFile.writeToFile(carts, IOFile.CART_PATH);
    }

    @Override
    public CartItem findById(Integer id) {
        return carts.stream()
                .filter(ci -> ci.getCartItemId() == id)
                .findFirst()
                .orElse(null);
    }

    public CartItem findByProductId(Integer id) {
        return carts.stream()
                .filter(ci -> ci.getProductId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Integer id) {
        CartItem cartItem = findById(id);
        if (cartItem != null) {
            carts.remove(cartItem);
            IOFile.writeToFile(carts, IOFile.CART_PATH);
            System.out.println("Xóa thành công");
        } else {
            System.err.println("Không tồn tại id");
        }
    }

    public int getNewCartItemId() {
        return carts.stream()
                .mapToInt(CartItem::getCartItemId)
                .max()
                .orElse(0) + 1;
    }

    public void changeQuantity(CartItem cartItem) {
        int index = carts.indexOf(findById(cartItem.getCartItemId()));
        if (index != -1) {
            carts.set(index, cartItem);
            IOFile.writeToFile(carts, IOFile.CART_PATH);
        }
    }

    public void updateCartIds() {
        List<CartItem> updatedCartItemList = IOFile.readFromFile(IOFile.CART_PATH);
        int newId = 1; // Starting ID

        for (CartItem product : carts) {
            product.setCartItemId(newId++);
            updatedCartItemList.add(product);
        }

        carts = updatedCartItemList;
        IOFile.writeToFile(carts, IOFile.PRODUCT_PATH);
    }
}
