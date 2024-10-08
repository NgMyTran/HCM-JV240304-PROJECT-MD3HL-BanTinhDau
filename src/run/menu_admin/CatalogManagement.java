package run.menu_admin;

import designImpl.CatalogDesignImpl;
import designImpl.ProductDesignImpl;
import entity.Catalog;
import entity.Product;
import util.IOFile;
import util.Inputmethods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CatalogManagement {
    private static final CatalogDesignImpl catalogDesignImpl = new CatalogDesignImpl();

    public static void catalogManagementMenu() {
        while (true) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║            Manage Catalog          ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị danh sách danh mục    ║");
            System.out.println("║  2. Thêm danh mục mới              ║");
            System.out.println("║  3. Tìm catalog bằng tên           ║");
            System.out.println("║  4. Cập nhật danh mục              ║");
            System.out.println("║  5. Xóa danh mục                   ║");
            System.out.println("║  6. Hiển thị list danh mục đã xóa  ║");
//            System.out.println("║  7. Xem sản phẩm theo danh mục     ║");
            System.out.println("║  8. Thoát                          ║");
            System.out.println("╚════════════════════════════════════╝");

            System.out.print("Mời bạn lựa chọn: ");
            byte choice = Inputmethods.getByte();
            switch (choice) {
                case 1:
                    displayAllCatalogs();
                    break;
                case 2:
                    addNewCatalog();
                    break;
                case 3:
                    findCatalogByName();
                    break;
                case 4:
                    updateCatalog();
                    break;
                case 5:
                    deleteCatalog();
                    break;
                case 6:
                    displayDeletedCatalogs();
                    break;
//                case 7:
//                    displayProductsByCatalog();
//                    break;
                case 8:
                    MenuAdmin.menuAdmin();
                    return; // Thoát khỏi vòng lặp
                default:
                    System.err.println("Không đúng lựa chọn");
            }
        }
    }

    private static void displayAllCatalogs() {
        List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);
        List<Catalog> catalogs = catalogDesignImpl.getAll();

        if (products == null) {
            System.err.println("Danh sách sản phẩm không hợp lệ.");
            return;
        }

        if (catalogs.isEmpty()) {
            System.err.println("Chưa có danh mục nào");
            return;
        }

        // Hiển thị tất cả các danh mục và số lượng sản phẩm trong từng danh mục
        catalogs.sort(Comparator.comparingInt(Catalog::getCatalogId));
        for (Catalog catalog : catalogs) {
            long productCount = products.stream()
                    .filter(p -> p.getCatalogId() == catalog.getCatalogId())
                    .count();
            System.out.println(catalog + " | Tổng số sản phẩm: " + productCount);
        }

        // Hỏi người dùng có muốn xem sản phẩm theo danh mục không
        System.out.print("Bạn có muốn xem danh sách sản phẩm của một danh mục cụ thể không? (y/n): ");
        String response = Inputmethods.getString().trim().toLowerCase();

        if (response.equals("y")) {
            // Hiển thị danh sách danh mục để người dùng chọn
            System.out.print("Nhập ID danh mục bạn muốn xem sản phẩm: ");
            int catalogId = Inputmethods.getInteger();

            // Tìm danh mục theo ID
            Catalog selectedCatalog = catalogDesignImpl.findById(catalogId);
            if (selectedCatalog == null) {
                System.err.println("Danh mục không tồn tại.");
                return;
            }

            // Hiển thị sản phẩm thuộc danh mục đã chọn
            System.out.println("Sản phẩm thuộc danh mục: " + selectedCatalog.getCatalogName());
            List<Product> filteredProducts = products.stream()
                    .filter(p -> p.getCatalogId() == catalogId)
                    .collect(Collectors.toList());

            if (filteredProducts.isEmpty()) {
                System.out.println("Không có sản phẩm nào trong danh mục này.");
            } else {
                filteredProducts.forEach(System.out::println);
            }
        }
    }

    private static void addNewCatalog() {
    System.out.print("Bạn muốn nhập vào bao nhiêu danh mục: ");
    int n = Inputmethods.getInteger();
    for (int i = 0; i < n; i++) {
        System.out.println("Danh mục thứ " + (i + 1));
        Catalog catalog = new Catalog();
        catalog.inputData();

        // Khôi phục danh mục nếu tên trùng với danh mục đã xóa
        catalogDesignImpl.restoreCatalogIfExists(catalog.getCatalogName());

        // Thêm danh mục mới
        catalog.setCatalogId(catalogDesignImpl.getNewId());
        catalog.setStatus(true); // Đảm bảo rằng danh mục mới được kích hoạt
        catalogDesignImpl.save(catalog);
        IOFile.writeToFile(CatalogDesignImpl.getCatalogList(), IOFile.CATALOG_PATH);
    }
    System.out.println("Thêm mới catalog thành công.");
}

    private static void updateCatalog() {
        System.out.print("Nhập mã danh mục cần cập nhật: ");
        int id = Inputmethods.getInteger();
        Catalog catalog = catalogDesignImpl.findById(id);
        if (catalog == null) {
            System.err.println("Không tồn tại danh mục này");
            return;
        }
        Catalog newCatalog = new Catalog();
        newCatalog.setCatalogId(catalog.getCatalogId());
        newCatalog.inputData();
        catalogDesignImpl.save(newCatalog);
        System.out.println("Cập nhật danh mục thành công.");
    }

    private static void findCatalogByName() {
        System.out.print("Nhập name danh mục: ");
        String name = Inputmethods.getString();
        Catalog catalog = catalogDesignImpl.findByName(name);
        if (catalog != null) {
            System.out.println(catalog);
        } else {
            System.out.println("Không tìm thấy danh mục với ID này.");
        }
    }

    public static void displayDeletedCatalogs() {
        List<Catalog> deletedCatalogs = IOFile.readFromFile(IOFile.DELETED_CATALOG_PATH);
        if (deletedCatalogs == null || deletedCatalogs.isEmpty()) {
            System.out.println("Chưa có catalog nào bị xóa.");
        } else {
            for (Catalog customer : deletedCatalogs) {
                System.out.println(customer);
            }
        }
    }

    private static void deleteCatalog() {
        System.out.print("Nhập mã danh mục cần xóa: ");
        int id = Inputmethods.getInteger();
        Catalog catalog = catalogDesignImpl.findById(id);
        if (catalog != null) {
            catalogDesignImpl.deleteAndSave(id);
            // Update IDs of remaining catalogs
            catalogDesignImpl.updateCatalogIds();
//            System.out.println("Danh mục " + catalog.getCatalogId() + " được đánh dấu là không hoạt động và lưu vào file.");
        } else {
            System.err.println("Không tìm thấy danh mục.");
        }
    }

}