package run.menu_admin;

import designImpl.CustomerDesignImpl;
import entity.Customer;
import util.IOFile;
import util.Inputmethods;

import java.util.Comparator;

public class CustomerManagement {
  private static final CustomerDesignImpl customerDesignImpl = new CustomerDesignImpl();

    public static void userManagementMenu() {
        while (true) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║            Manage Customer         ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị danh sách người dùng  ║");
            System.out.println("║  2. Tìm customer bằng id           ║");
            System.out.println("║  3. Tìm customer bằng tên          ║");
            System.out.println("║  4. Khóa tài khoản customer        ║");
            System.out.println("║  5. Mở khóa tài khoản customer     ║");
            System.out.println("║  6. Thoát                          ║");
            System.out.println("╚════════════════════════════════════╝");
            byte choice = Inputmethods.getByte();
            switch (choice) {
                case 1:
                    displayAllCustomer();
                    break;
                case 2:
                    findCustomerById();
                    break;
                case 3:
                    findCustomerByName();
                    break;
                case 4:
                    blockCustomer();
                    break;
                case 5:
                    unblockCustomer();
                    break;
                case 6:
                    MenuAdmin.menuAdmin();
                    break;
                default:
                    System.err.println("Không đúng lựa chọn");
            }
            if (choice == 3) {
                break;
            }
        }
    }

    private static void displayAllCustomer() {
        CustomerDesignImpl.getCustomerList().sort(Comparator.comparingInt(Customer::getCustomerId).reversed());
        for (Customer customer :  CustomerDesignImpl.getCustomerList()) {
            System.out.println(customer);
        }
    }

    private static void findCustomerById() {
        System.out.print("Nhập ID người dùng: ");
        int id = Inputmethods.getInteger();
        Customer user = customerDesignImpl.findById(id);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("Không tìm thấy người dùng với ID này.");
        }
    }

    private static void findCustomerByName() {
        System.out.print("Nhập tên người dùng: ");
        String name = Inputmethods.getString();
        Customer user = CustomerDesignImpl.findCusByName(name);
        if (user != null) {
            System.out.println(user);
            CustomerManagement.blockCustomer();
        } else {
            System.out.println("Không tìm thấy người dùng với tên này.");
        }
    }

    private static void blockCustomer() {
        System.out.print("Nhập ID người dùng để khóa: ");
        int id = Inputmethods.getInteger();
        boolean isBlocked = customerDesignImpl.blockCustomer(id);
        if (isBlocked) {
            System.out.println("Người dùng đã bị khóa.");
            IOFile.writeToFile(CustomerDesignImpl.getCustomerList(),IOFile.CUSTOMER_PATH);
        }
        else {
            System.out.println("Không tìm thấy người dùng với ID này.");
        }

    }

    private static void unblockCustomer() {
        System.out.print("Nhập ID người dùng để mở khóa: ");
        int id = Inputmethods.getInteger();
        boolean isUnblocked = customerDesignImpl.unblockCustomer(id);
        if (isUnblocked) {
            System.out.println("Người dùng đã được mở khóa.");
            IOFile.writeToFile(CustomerDesignImpl.getCustomerList(),IOFile.CUSTOMER_PATH);
        } else {
            System.out.println("Không tìm thấy người dùng với ID này.");
        }
    }

}
