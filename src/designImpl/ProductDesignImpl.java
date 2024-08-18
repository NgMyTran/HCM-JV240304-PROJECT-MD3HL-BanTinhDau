package designImpl;

import design.IDesign;
import entity.Product;
import util.IOFile;

import java.util.ArrayList;
import java.util.List;

public class ProductDesignImpl implements IDesign<Product, Integer> {
    private static List<Product> productList;

    public ProductDesignImpl() {
        productList = IOFile.readFromFile(IOFile.PRODUCT_PATH);
    }

    public static List<Product> getProductList() {
        return productList;
    }

    @Override
    public List<Product> getAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        if (product.getStatus()) {
            Product existingProduct = findById(product.getProductId());
            if (existingProduct == null) {
                product.setProductId(getNewId());
                productList.add(product);
            } else {
                int index = productList.indexOf(existingProduct);
                if (index != -1) {
                    productList.set(index, product);
                }
            }
            IOFile.writeToFile(productList, IOFile.PRODUCT_PATH);
        }
    }

    @Override
    public Product findById(Integer id) {
        return productList.stream()
                .filter(product -> product.getProductId()==id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Integer id) {
        Product product = findById(id);
        if (product != null) {
            productList.remove(product);
            IOFile.writeToFile(productList, IOFile.PRODUCT_PATH);

            List<Product> deletedProducts = IOFile.readFromFile(IOFile.DELETED_PRODUCT_PATH);
            deletedProducts.add(product);
            IOFile.writeToFile(deletedProducts, IOFile.DELETED_PRODUCT_PATH);
        } else {
            System.err.println("Không có sản phẩm này");
        }
    }

    public int getNewId() {
        return productList.stream()
                .mapToInt(Product::getProductId)
                .max()
                .orElse(0) + 1;
    }

    public void updateProductIds() {
        List<Product> updatedProductList = new ArrayList<>();
        int newId = 1; // Starting ID

        for (Product product : productList) {
            product.setProductId(newId++);
            updatedProductList.add(product);
        }

        productList = updatedProductList;
        IOFile.writeToFile(productList, IOFile.PRODUCT_PATH); // Save updated list to file
    }
}
