package designImpl;

import design.IDesign;
import entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDesignImpl implements IDesign<CartItem, Integer> {
    private List<CartItem> cart = new ArrayList<>();

    @Override
    public List<CartItem> getAll() {
        return cart;
    }

    @Override
    public void save(CartItem cartItem) {
        cart.add(cartItem);
    }

    @Override
    public CartItem findById(Integer integer) {
        return cart.stream()
                .filter(ci -> ci.getCartItemId() == integer)
                .findFirst()
                .orElse(null);
    }

    public CartItem findByProductId(Integer id) {
        return cart.stream()
                .filter(ci -> ci.getProductId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Integer integer) {
        CartItem cartItem = findById(integer);
        if (cartItem != null) {
            cart.remove(cartItem);
            System.out.println("Xóa thành công");
        } else {
            System.err.println("Không tồn tại id");
        }
    }

    public int getNewCartItemId() {
        return cart.stream()
                .mapToInt(CartItem::getCartItemId)
                .max()
                .orElse(0) + 1;
    }

    public void changeQuantity(CartItem cartItem) {
        int index = cart.indexOf(findById(cartItem.getCartItemId()));
        if (index != -1) {
            cart.set(index, cartItem);
        }
    }
}
