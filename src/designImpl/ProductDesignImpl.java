package designImpl;

import design.IDesign;
import entity.Product;
import util.IOFile;

import java.util.Collections;
import java.util.List;

public class ProductDesignImpl implements IDesign<Product,Integer> {
    private static List<Product> productList;


    static {
        productList = IOFile.readFromFile(IOFile.CATALOG_PATH);
    }

    public static List<Product> getProductList() {
        return productList;
    }
    @Override
    public List<Product> getAll() {
        return productList;
    }

//    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            productList.add(product);
        } else {
            productList.set(productList.indexOf(findById(product.getCatalogId())), product);
        }
    }

    @Override
    public Product findById(Integer integer) {
        for (Product p : productList) {
            if (p.getProductId()==integer) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer integer) {
        if (findById(integer) != null) {
            productList.remove(findById(integer));

        } else {
            System.err.println("Không có mục lục này");
        }
    }


    public int getNewId() {
        int idMax = 0;
        for (Product u : productList) {
            if (u.getProductId() > idMax) {
                idMax = u.getProductId();
            }
        }
        return idMax + 1;
    }

}
