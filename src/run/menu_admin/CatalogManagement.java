//package run.menu_admin;
//
//import designImpl.ProductDesignImpl;
//import designImpl.CatalogDesignImpl;
//import entity.Catalog;
//import util.IOFile;
//import util.Inputmethods;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//
//public class CatalogManagement {
//    private CatalogDesignImpl catalogDesignImpl = new CatalogDesignImpl();
//
//    public void CatalogManagement(CatalogDesignImpl catalogDesign) {
//        this.catalogDesignImpl=catalogDesign;
//
//        while (true) {
//            System.out.println("╔════════════════════════════════════╗");
//            System.out.println("║            Manage Catalog          ║");
//            System.out.println("╠════════════════════════════════════╣");
//            System.out.println("║  1. Hiển thị danh sách danh mục    ║");
//            System.out.println("║  2. Thêm danh mục mới              ║");
//            System.out.println("║  3. Tìm catalog bằng id            ║");
//            System.out.println("║  4. Cập nhật danh mục              ║");
//            System.out.println("║  5. Xóa danh mục                   ║");
//            System.out.println("║  6. Thoát                          ║");
//            System.out.println("╚════════════════════════════════════╝");
//            byte choice = Inputmethods.getByte();
//            switch (choice) {
//                case 1:
//                    displayAllCatalogs();
//                    break;
//                case 2:
//                    addNewCatalog();
//                    break;
//                case 3:
////                    findById();
//                    break;
//                case 4:
//                    updateCatalog();
//                    break;
//                case 5:
//                    deleteCatalog();
//                    break;
//                case 6:
//                    MenuAdmin.menuAdmin();
//                    break;
//                default:
//                    System.err.println("Không đúng lựa chọn");
//            }
//            if (choice == 6) {
//                break;
//            }
//        }
//    }
//
//    public void addNewCatalog() {
//        System.out.print("Bạn muốn nhập vào nhiêu danh mục: ");
//        int n = Inputmethods.getInteger();
//        for (int i = 0; i < n; i++) {
//            System.out.println("Danh mục thứ " + (i + 1));
//            Catalog catalog=new Catalog();
//            catalog.setCatalogId(catalogDesignImpl.getNewId());
//            catalog.inputData();
//            catalogDesignImpl.save(catalog);
//        }
//        System.out.println("Thêm mới catalog thành công");
//    }
//
//    public void displayAllCatalogs() {
//        if (catalogDesignImpl.getAll().isEmpty()) {
//            System.err.println("Chưa có danh mục nào");
//            return;
//        }
//        IOFile.readFromFile(IOFile.CATALOG_PATH);
//        for (Catalog c : catalogDesignImpl.getAll()) {
//            System.out.println(c);
//        }
//    }
//
//    public void updateCatalog() {
//        System.out.print("Nhập mã danh mục cần : ");
//        int id = Inputmethods.getInteger();
//        Catalog catalog = catalogDesignImpl.findById(id);
//        if (catalog == null) {
//            System.err.println("Không tồn tại danh mục này");
//            return;
//        }
//        Catalog newCatalog = new Catalog();
//        newCatalog.setCatalogId(catalog.getCatalogId());
//        newCatalog.inputData();
//        catalogDesignImpl.save(newCatalog);
//    }
//
////    public void deleteCatalog() {
////        System.out.print("Nhập mã danh mục cần xóa: ");
////        int id = Inputmethods.getInteger();
////        Catalog catalog = catalogDesignImpl.findById(id);
////        if (catalog != null) {
////            if(ProductDesignImpl.getProductList().stream().noneMatch(product -> product.getCatalogId() == id)){
////                catalogDesignImpl.delete(id);
////                System.out.println("Xóa danh mục thành công.");
////            } else {
////                System.err.println("Không thể xóa danh mục đang có sản phẩm.");
////            }
////        }else {
////            System.err.println("Không tìm thấy danh mục.");
////        }
////    }
//public void deleteCatalog() {
//    System.out.print("Nhập mã danh mục cần xóa: ");
//    int id = Inputmethods.getInteger();
//    Catalog catalog = catalogDesignImpl.findById(id);
//    if (catalog != null) {
//        // Kiểm tra nếu có sản phẩm thuộc danh mục này
//        if (ProductDesignImpl.getProductList().stream().noneMatch(product -> product.getCatalogId() == id)) {
//            // Đánh dấu danh mục là không hoạt động
//            catalog.setStatus(false);
//            // Lưu danh mục bị xóa vào file DELETED_CATALOG_PATH
//            saveDeletedCatalog(catalog);
//            // Cập nhật lại danh sách danh mục
//            catalogDesignImpl.save(catalog);
//            System.out.println("Xóa danh mục thành công.");
//        } else {
//            System.err.println("Không thể xóa danh mục đang có sản phẩm.");
//        }
//    } else {
//        System.err.println("Không tìm thấy danh mục.");
//    }
//}private void saveDeletedCatalog(Catalog catalog) {
//        // Đọc danh sách danh mục đã bị xóa từ file
//        List<Catalog> deletedCatalogs = IOFile.readFromFile(IOFile.DELETED_CATALOG_PATH);
//
//        // Nếu danh sách danh mục đã bị xóa chưa được khởi tạo, tạo danh sách mới
//        if (deletedCatalogs == null) {
//            deletedCatalogs = new ArrayList<>();
//        }
//
//        // Thêm danh mục bị xóa vào danh sách
//        deletedCatalogs.add(catalog);
//
//        // Ghi danh sách danh mục đã bị xóa vào file
//        IOFile.writeToFile(deletedCatalogs, IOFile.DELETED_CATALOG_PATH);
//    }
//}

package run.menu_admin;

import designImpl.CatalogDesignImpl;
import designImpl.ProductDesignImpl;
import entity.Catalog;
import util.IOFile;
import util.Inputmethods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CatalogManagement {
    private static final CatalogDesignImpl catalogDesignImpl = new CatalogDesignImpl();
    public static void catalogManagementMenu() {
        while (true) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║            Manage Catalog          ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị danh sách danh mục    ║");
            System.out.println("║  2. Thêm danh mục mới              ║");
            System.out.println("║  3. Tìm catalog bằng id            ║");
            System.out.println("║  4. Cập nhật danh mục              ║");
            System.out.println("║  5. Xóa danh mục                   ║");
            System.out.println("║  6. Thoát                          ║");
            System.out.println("╚════════════════════════════════════╝");
            byte choice = Inputmethods.getByte();
            switch (choice) {
                case 1:
                    displayAllCatalogs();
                    break;
                case 2:
                    addNewCatalog();
                    break;
                case 3:
                    findCatalogById();
                    break;
                case 4:
                    updateCatalog();
                    break;
                case 5:
                    deleteCatalog();
                    break;
                case 6:
                    // Quay lại menu chính hoặc thoát
                    MenuAdmin.menuAdmin();
                    return; // Thoát khỏi vòng lặp
                default:
                    System.err.println("Không đúng lựa chọn");
            }
        }
    }

    private static void displayAllCatalogs() {
        List<Catalog> catalogs = catalogDesignImpl.getAll();
        if (catalogs.isEmpty()) {
            System.err.println("Chưa có danh mục nào");
            return;
        }
        catalogs.sort(Comparator.comparingInt(Catalog::getCatalogId));
        for (Catalog catalog : catalogs) {
            System.out.println(catalog);
        }
    }

    private static void addNewCatalog() {
        System.out.print("Bạn muốn nhập vào nhiêu danh mục: ");
        int n = Inputmethods.getInteger();
        for (int i = 0; i < n; i++) {
            System.out.println("Danh mục thứ " + (i + 1));
            Catalog catalog = new Catalog();
            catalog.setCatalogId(catalogDesignImpl.getNewId());
            catalog.inputData();
            catalogDesignImpl.save(catalog);
            IOFile.writeToFile(catalogDesignImpl.getAll(), IOFile.CATALOG_PATH);
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

    private static void findCatalogById() {
        System.out.print("Nhập ID danh mục: ");
        int id = Inputmethods.getInteger();
        Catalog catalog = catalogDesignImpl.findById(id);
        if (catalog != null) {
            System.out.println(catalog);
        } else {
            System.out.println("Không tìm thấy danh mục với ID này.");
        }
    }

    private static void deleteCatalog() {
        System.out.print("Nhập mã danh mục cần xóa: ");
        int id = Inputmethods.getInteger();
        Catalog catalog = catalogDesignImpl.findById(id);
        if (catalog != null) {

            // Kiểm tra nếu có sản phẩm thuộc danh mục này
            if (ProductDesignImpl.getProductList().stream().noneMatch(product -> product.getCatalogId() == id)) {
                // Đánh dấu danh mục là không hoạt động
                catalog.setStatus(false);

                // Lưu danh mục bị xóa vào file DELETED_CATALOG_PATH
                saveDeletedCatalog(catalog);

                // Cập nhật lại danh sách danh mục
                catalogDesignImpl.save(catalog);
                System.out.println("Xóa danh mục thành công.");
            } else {
                System.err.println("Không thể xóa danh mục đang có sản phẩm.");
            }
        } else {
            System.err.println("Không tìm thấy danh mục.");
        }
    }

    private static void saveDeletedCatalog(Catalog catalog) {
        // Đọc danh sách danh mục đã bị xóa từ file
        List<Catalog> deletedCatalogs = IOFile.readFromFile(IOFile.DELETED_CATALOG_PATH);

        // Nếu danh sách danh mục đã bị xóa chưa được khởi tạo, tạo danh sách mới
        if (deletedCatalogs == null) {
            deletedCatalogs = new ArrayList<>();
        }

        // Thêm danh mục bị xóa vào danh sách
        deletedCatalogs.add(catalog);

        // Ghi danh sách danh mục đã bị xóa vào file
        IOFile.writeToFile(deletedCatalogs, IOFile.DELETED_CATALOG_PATH);
    }
}
