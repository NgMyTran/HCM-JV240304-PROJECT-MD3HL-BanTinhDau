package run.menu_admin;

import designImpl.CatalogDesignImpl;
import run.menu_customer.MenuCustomer;
import util.IOFile;
import util.Inputmethods;

public class MenuAdmin {
    public static void menuAdmin() {
//        CatalogManagement catalogManagement = new CatalogManagement();

        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║ -----------Chào mừng ADMIN----------        ║");
        System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ 1. Quản lý danh mục                         ║");
        System.out.println("║ 2. Quản lý sản phẩm                         ║");
        System.out.println("║ 3. Quản lý người dùng                       ║");
        System.out.println("║ 4. Đăng xuất                                ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        byte choice = Inputmethods.getByte();
        switch (choice) {
            case 1:
                CatalogManagement.catalogManagementMenu();
                break;
            case 2:
                ProductMangement.productManagementMenu();
                break;
            case 3:
                CustomerManagement.userManagementMenu();
                break;
            case 4:
                System.out.println("Đã thoát trang quản lý của ADMIN");
                logout();
            default:
                System.out.println("Lựa chọn không hợp lệ");
                break;
        }
    }
    public static void logout(){
        IOFile.deleteCustomerLogin();
        System.out.println("Đăng xuất thành công. Về lại trạng thái trrước khi đăng nhập.");
        MenuCustomer.menuCustomer();
    }
}
