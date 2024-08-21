package run.menu_admin;

import designImpl.CatalogDesignImpl;
import designImpl.ProductDesignImpl;
import entity.Catalog;
import entity.Product;
import util.IOFile;
import util.Inputmethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMangement {
    private static ProductDesignImpl productDesign=new ProductDesignImpl();
    private static CatalogDesignImpl catalogDesign= new CatalogDesignImpl();

    public static void productManagementMenu() {
        while (true) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║            MANAGE PRODUCT          ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị danh sách sản phẩm    ║");
            System.out.println("║  2. Thêm sản phẩm mới              ║");
            System.out.println("║  3. Tìm product bằng id            ║");
            System.out.println("║  4. Cập nhật sản phẩm              ║");
            System.out.println("║  5. Xóa sản phẩm                   ║");
            System.out.println("║  6. Thay đổi thông tin theo id     ║");
            System.out.println("║  7. Thoát                          ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Mời bạn lựa chọn: ");
            int choose = Inputmethods.getInteger();
            switch (choose) {
                case 1:
                    showListProduct();
                    break;
                case 2:
                    addNewProduct();
                    break;
                case 3:
                    findProductById();
                    break;
                case 4:
                   updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    updateProduct();
                    break;
                case 7:
                    MenuAdmin.menuAdmin();
                    break;
                default:
                    System.err.println("Không đúng lựa chọn");
                    break;
            }
        }
    }

    private static void showListProduct() {
        List<Product> productList = productDesign.getAll();
        if (productList.isEmpty()) {
            System.err.println("Chưa có danh mục nào");
            return;
        }
        productList.sort(Comparator.comparingInt(Product::getProductId).reversed());
        for (Product pro : productList) {
                System.out.println(pro.toStringForAdmin());
        }
    }

    private static void addNewProduct() {
        if (CatalogDesignImpl.getCatalogList().isEmpty()) {
            System.err.println("Không có danh mục nào để thêm sản phẩm. Vui lòng tạo ít nhất một danh mục trước.");
            System.out.println("");
            CatalogManagement.catalogManagementMenu();
           // Không thực hiện thêm sản phẩm nếu không có danh mục
        }

        System.out.print("Bạn muốn nhập vào nhiêu sản phẩm: ");
        int n = Inputmethods.getInteger();
//        for (int i = 0; i < n; i++) {
//            System.out.println("Sản phẩm thứ " + (i + 1));
//            Product product = new Product();
//            product.inputData();// nhiều sản phẩm thêm vào cùng ngày thì sắp xếp theo bảng chữ cái alphabet
//            productDesign.save(product);
//            IOFile.writeToFile(ProductDesignImpl.getProductList(),IOFile.PRODUCT_PATH);
//        }
        List<Product> newProducts = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i + 1));
            Product product = new Product();
            product.inputData();
            newProducts.add(product);
        }
        List<Product> sortedProducts = newProducts.stream()
                .sorted(Comparator.comparing(Product::getCreatedAt)
                        .thenComparing(Product::getProductName))
                .collect(Collectors.toList());
        for (Product product : sortedProducts) {
            productDesign.save(product);
        }
        IOFile.writeToFile(ProductDesignImpl.getProductList(), IOFile.PRODUCT_PATH);
        System.out.println("Thêm mới product thành công.");
    }

    private static void findProductById() {
        System.out.print("Nhập ID product: ");
        int id = Inputmethods.getInteger();
        Product product = productDesign.findById(id);
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Không tìm thấy danh mục với ID này.");
        }
    }

    private static void deleteProduct() {
        System.out.print("Nhập vào Mã sản phẩm: ");
        int id = Inputmethods.getInteger();
        Product product = productDesign.findById(id);
        if (product!=null){
        productDesign.delete(id);
        productDesign.updateProductIds();

//            System.out.println("Sản  " + product.getProductId() + " được đánh dấu là không hoạt động và lưu vào file.");
        }
        else {
            System.out.println("");
            System.err.println("Không tìm thấy sản muốn xóa.");
        }
    }

    private static void updateProduct() {
        System.out.print("Nhập vào Mã id sản phẩm: ");
        int id = Inputmethods.getInteger();
        Product product = productDesign.findById(id);
        if (product == null) {
            System.err.println("Không có sản phẩm bạn muốn tìm");
            return;
        }
        Product newProduct = new Product();
        newProduct.setProductId(product.getProductId());
        newProduct.inputData();
        productDesign.save(newProduct);
        System.out.println("Cập nhật sản phẩm thành công.");
    }
}
