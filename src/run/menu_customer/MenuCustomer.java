package run.menu_customer;

import designImpl.CustomerDesignImpl;
import entity.Customer;
import entity.RoleName;
import run.menu_admin.MenuAdmin;
import util.IOFile;
import util.Inputmethods;

import java.util.List;

public class MenuCustomer {
    static CustomerDesignImpl customerDesign = new CustomerDesignImpl();
//    private static boolean isLoggedIn = false;

    public static void menuCustomer() {

        String customerName = IOFile.readCusName();
        if (customerName == null) {
            customerName = "BẠN";
        }

        while (true) {
            boolean isLoggedIn = IOFile.readCusName() != null;


            System.out.println("╔══════════════════════════════════════════════════════════════════╗");
            System.out.println("  Chào mừng " + "( " + customerName + " )" + " đến cửa hàng để mua sắm ");
            System.out.println("╠══════════════════════════════════════════════════════════════════╣");

            printMenuItem(1, "Xem tất cả mặt hàng");
            printMenuItem(2, "Xem mặt hàng theo danh mục");
            printMenuItem(3, "Thêm mặt hàng vào giỏ");
            printMenuItem(4, "Xem giỏ hàng");
            printMenuItem(5, "Xóa mặt hàng khỏi giỏ");
            printMenuItem(6, "Hiển thị thông tin cá nhân");
            printMenuItem(7, "Sửa thông tin cá nhân");
            printMenuItem(8, "Đổi mật khẩu đăng nhập");

            if (isLoggedIn) {
                printMenuItem(11, "Đăng xuất");
            } else {
                printMenuItem(9, "Đăng ký");
                printMenuItem(10, "Đăng nhập");
            }

            printMenuItem(12, "Thanh toán");
            System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn của bạn: ");
            byte choice = Inputmethods.getByte();
            switch (choice) {
                case 1:
//                ProductManager.showProductList();
                    break;
                case 2:
                    //displayProductByCategory(scanner);
                    break;
                case 3:
                    //addProductToCart(user);
                case 4:
                    //showCart(user);
                    break;
                case 5:
                    //removeProductFromCart();
                    break;
                case 6:
                    displayCusInfo();
                    break;
                case 7:
//                updateUserInfo();
                    break;
                case 8:
//                changePassword();
                    break;
                case 9:
                    register();
                    break;
                case 10:
                    login();
                    break;
                case 11:
                    logout();
                    break;
                case 12:

                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                    break;
            }
        }
    }

    private static void printMenuItem(int number, String text) {
        String format = String.format("║  %2d. %-15s", number, text);
        int paddingLength = 67 - format.length();
        String padding = repeatString(" ", paddingLength);
        System.out.println(format + padding + "║");
    }

    private static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    //---------HIỂN THỊ INFORMATION CÁ NHÂN--------
    private static void displayCusInfo() {
        Customer customer = IOFile.readCustomerLogin();
        if (customer != null) {
            System.out.println("Thông tin cá nhân:");
            System.out.println("Họ: " + customer.getLastName());
            System.out.println("Tên: " + customer.getFirstName());
            System.out.println("Tên đăng nhập: " + customer.getCustomerName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Địa chỉ: " + customer.getAddress());
            System.out.println("Số điện thoại: " + customer.getPhone());
        } else {
            System.out.println("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

    //---------DANG KY--------
    private static void register() {
        System.out.println("------------------Register----------------");
        Customer customer = new Customer();
//        // Check existed email
//        IOFile<Customer> ioFile = new IOFile<>();
        List<Customer> existingCustomers = IOFile.readFromFile(IOFile.CUSTOMER_PATH);

        while (true) {
            System.out.println("Nhập họ của bạn: ");
            String lastName = Inputmethods.getString();
            if (!lastName.isEmpty()) {
                customer.setLastName(lastName);
                break;
            } else {
                System.out.println("Không được để trống. Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.println("Nhập tên của bạn: ");
            String firstName = Inputmethods.getString();
            if (!firstName.isEmpty()) {
                customer.setFirstName(firstName);
                break;
            } else {
                System.out.println("Không được để trống. Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.println("Nhập tên đăng nhập: ");
            String name = Inputmethods.getString();
            if (!name.isEmpty()) {
                customer.setCustomerName(name);
                break;
            } else {
                System.out.println("Không được để trống. Vui lòng nhập lại.");
            }
        }
        //Nhập và kiểm tra email
        while (true) {
            System.out.println("Nhập email: ");
            String email = Inputmethods.getString();
            boolean emailExists = existingCustomers.stream()
                    .anyMatch(c -> c.getEmail().equals(email));
            if (emailExists) {
                System.out.println("Email đã được đăng ký. Vui lòng nhập email khác.");
            } else if (email.matches("^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]+$")) {
                customer.setEmail(email);
                break;
            } else {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }
        }
        //Nhập và kiểm tra mật khẩu
        while (true) {
            System.out.println("Nhập mật khẩu: ");
            String password = Inputmethods.getString();
            if (!password.isEmpty()) {
                if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{3,}$")) {
                    customer.setPassword(password);
                    break;
                } else {
                    System.out.println("Mật khẩu phải có 1 chữ viết hoa, 1 chữ viết thường, 1 số và có 3 kí tự trở lên. Vui lòng nhập lại.");
                }
            } else {
                System.out.println("Không được để trống. Vui lòng nhập lại.");
            }

        }

        // Confirm password
        int attemptCount = 0; // Counter for password confirmation attempts
        while (attemptCount < 3) {
            System.out.println("Xác nhận mật khẩu: ");
            String confirmPassword = Inputmethods.getString();

            if (confirmPassword.equals(customer.getPassword())) {
                // Nhập và kiểm tra số điện thoại
                while (true) {
                    System.out.println("Nhập số điện thoại: ");
                    String phone = Inputmethods.getString();
                    if (phone.matches("^(\\+84|0)\\d{5,9}$")) {
                        customer.setPhone(phone);
                        break;
                    } else {
                        System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                    }
                }
                // Nhập và kiểm tra địa chỉ
                while (true) {
                    System.out.println("Nhập địa chỉ: ");
                    String address = Inputmethods.getString();
                    if (!address.isEmpty()) {
                        customer.setAddress(address);
                        break;
                    } else {
                        System.out.println("Không được để trống. Vui lòng nhập lại.");
                    }
                }
                customerDesign.save(customer);
                System.out.println("Đăng ký thành công");
                login();
                return;
            } else {
                attemptCount++;
                System.out.println("Xác nhận mật khẩu không đúng. Bạn còn " + (3 - attemptCount) + " lần thử nữa.");
            }

            if (attemptCount == 3) {
                System.out.println("Bạn đã nhập sai quá 3 lần. Vui lòng thử lại sau 3 giây.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                register();
                return;
            }
        }
    }

    //---------DANG NHẬP--------
    private static void login() {
        System.out.println("=================Đăng nhập=================");
        System.out.println("Nhập email :");
        String userEmail = Inputmethods.getString();
        System.out.println("Nhập password");
        String password = Inputmethods.getString();

        List<Customer> customerList = IOFile.readFromFile(IOFile.CUSTOMER_PATH);

        // Initialize admin user
        Customer admin = new Customer();
        admin.setCustomerName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("admin123");
        admin.setRoleName(RoleName.ADMIN);
        admin.setBlocked(false);

        // Check if admin user already exists
        boolean adminExists = customerList.stream()
                .anyMatch(c -> c.getEmail().equals(admin.getEmail()));

        if (!adminExists) {
            customerList.add(admin); // Add admin if not exists
            IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH); // Save updated list to file
        }
        System.out.println(customerList);

        // Check user login
        Customer customerLogin = customerList.stream()
                .filter(c -> c.getEmail().equals(userEmail) && c.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (customerLogin == null) {
            System.err.println("Tài khoản không tồn tại. Bạn có muốn đăng ký không? (y/n)");
            String choice = Inputmethods.getString().trim().toLowerCase();
            if (choice.equals("y")) {
                register();
            } else {
                System.out.println("Đăng nhập thất bại. Tạm biệt.");
                menuCustomer();
            }
        } else {
            // Check if the user is blocked
            if (customerLogin.isBlocked()) {
                System.err.println("Tài khoản bị khóa, vui lòng liên hệ 02348219");
            } else {
                // Save login information if the user is not blocked
                IOFile.writeCustomerLogin(customerLogin);

                // Xét quyền của user
                if (customerLogin.getRoleName() == RoleName.ADMIN) {
                    MenuAdmin.menuAdmin();
                } else if (customerLogin.getRoleName() == RoleName.CUSTOMER) {
                    System.out.println("Đăng nhập thành công.");
                    menuCustomer();
                }
            }
        }
    }

    //---------DANG XUẤT--------
    public static void logout() {
        IOFile.deleteCustomerLogin();
        System.out.println("Đăng xuất thành công. Về lại trạng thái trrước khi đăng nhập.");
        menuCustomer();
    }
}
