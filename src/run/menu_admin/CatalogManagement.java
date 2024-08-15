package run.menu_admin;

import designImpl.CatalogDesignImpl;
import entity.Catalog;
import util.Inputmethods;

import java.util.Comparator;

public class CatalogManagement {
    private static final CatalogDesignImpl catalogDesignImpl = new CatalogDesignImpl();

    public static void catalogManagementMenu() {
        while (true) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║            Manage Catalog          ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị danh sách danh mục    ║");
            System.out.println("║  2. Tìm catalog bằng id            ║");
            System.out.println("║  3. Thêm danh mục mới               ║");
            System.out.println("║  4. Cập nhật danh mục               ║");
            System.out.println("║  5. Xóa danh mục                    ║");
            System.out.println("║  6. Thoát                          ║");
            System.out.println("╚════════════════════════════════════╝");
            byte choice = Inputmethods.getByte();
            switch (choice) {
                case 1:
                    displayAllCatalogs();
                    break;
                case 2:
                    findCatalogById();
                    break;
                case 3:
                    addCatalog();
                    break;
                case 4:
                    updateCatalog();
                    break;
                case 5:
                    System.out.println("Delete catalodId");
                    Integer id=Inputmethods.getInteger();
                    deleteCatalog(id);
                    break;
                case 6:
                    MenuAdmin.menuAdmin();
                    break;
                default:
                    System.err.println("Không đúng lựa chọn");
            }
            if (choice == 6) {
                break;
            }
        }
    }

    private static void displayAllCatalogs() {
        catalogDesignImpl.getAll().stream()
                .sorted(Comparator.comparingInt(Catalog::getId))
                .forEach(System.out::println);
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

    private static void addCatalog() {
        System.out.print("Nhập tên danh mục: ");
        String name = Inputmethods.getString();
        System.out.print("Nhập trạng thái danh mục (true/false): ");
        boolean status = Inputmethods.getBoolean();

        Catalog catalog = new Catalog();
        catalog.setCatalogName(name);
        catalog.setStatus(status);

        catalogDesignImpl.save(catalog);
        System.out.println("Danh mục đã được thêm thành công.");
    }

    private static void updateCatalog() {
        System.out.print("Nhập ID danh mục cần cập nhật: ");
        int id = Inputmethods.getInteger();
        Catalog catalog = catalogDesignImpl.findById(id);

        if (catalog != null) {
            System.out.print("Nhập tên danh mục mới: ");
            String name = Inputmethods.getString();
            System.out.print("Nhập trạng thái danh mục mới (true/false): ");
            boolean status = Inputmethods.getBoolean();

            catalog.setCatalogName(name);
            catalog.setStatus(status);

            catalogDesignImpl.save(catalog);
            System.out.println("Danh mục đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy danh mục với ID này.");
        }
    }


    public static void deleteCatalog(Integer id) {
        // Tìm danh mục với ID tương ứng
        Catalog catalog = catalogDesignImpl.findById(id);

        if (catalog != null) {
            // Cập nhật trạng thái danh mục thành false để "xóa" danh mục
            catalog.setStatus(false);

            // Lưu danh mục đã cập nhật
            catalogDesignImpl.save(catalog);

            System.out.println("Danh mục với ID " + id + " đã được xóa.");
        } else {
            System.out.println("Không tìm thấy danh mục với ID này.");
        }
    }

}
