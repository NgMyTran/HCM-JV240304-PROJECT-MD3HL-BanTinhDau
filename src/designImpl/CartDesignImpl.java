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
        for (CartItem ci: cart){
            if(ci.getCartItemId()==integer){
                return ci;
            }
        }
        return null;
    }
    public CartItem findByProductId(Integer id){
        for (CartItem ci: cart){
            if(ci.getProduct().getProductId()==id){
                return ci;
            }
        }
        return null;
    }
    @Override
    public void delete(Integer integer) {
        cart.remove(findById(integer));
        System.out.println("Xóa thanh công");
    }

    public  int getNewCartItemId(){
        int idMax=0 ;
        for (CartItem ci: cart){
            if(ci.getCartItemId()> idMax){
                idMax= ci.getCartItemId();
            }
        }
        return idMax+1;
    }
}
