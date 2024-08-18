package run.menu_customer;

import designImpl.CustomerDesignImpl;
import designImpl.ProductDesignImpl;
import entity.Customer;
import entity.Product;
import entity.RoleName;
import run.menu_admin.CatalogManagement;
import run.menu_admin.MenuAdmin;
import util.IOFile;
import util.Inputmethods;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuCustomer {
 private    static CustomerDesignImpl customerDesign = new CustomerDesignImpl();
    private static ProductDesignImpl productDesign = new ProductDesignImpl();

    private static Scanner scanner = new Scanner(System.in);

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
            System.out.println("");
            System.out.print("Nhập lựa chọn của bạn: ");
            byte choice = Inputmethods.getByte();
            switch (choice) {
                case 1:
                    showListProduct();
                    break;
                case 2:
                    CatalogManagement.displayProductsByCatalog();
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
                    updateCusInfo();
                    break;
                case 8:
                changePassword();
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

    private static void showListProduct() {
        List<Product> productList = productDesign.getAll();
        if (productList == null || productList.isEmpty()) {
            System.err.println("Chưa có sản phẩm nào.");
            return;
        }

        // Lọc các sản phẩm có status == true
        List<Product> activeProducts = productList.stream()
                .filter(Product::getStatus)
                .sorted(Comparator.comparingInt(Product::getProductId))
                .collect(Collectors.toList());

        if (activeProducts.isEmpty()) {
            System.out.println("Không có sản phẩm nào hoạt động.");
            return;
        }

        for (Product product : activeProducts) {
            System.out.println(product);
        }
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
//            System.out.println("Pass: " + customer.getPassword());
            System.out.println("Địa chỉ: " + customer.getAddress());
            System.out.println("Số điện thoại: " + customer.getPhone());
        } else {
            System.out.println("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

    //---------ĐỔI INFORMATION CÁ NHÂN--------
    private static void updateCusInfo() {
        Customer customer = IOFile.readCustomerLogin();
        if (customer != null) {
            System.out.println("Cập nhật thông tin cá nhân:");

            System.out.println("Nhập họ mới (hoặc nhấn Enter để bỏ qua):");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) {
                customer.setLastName(lastName);
            }

            System.out.println("Nhập tên mới (hoặc nhấn Enter để bỏ qua):");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                customer.setFirstName(firstName);
            }

            System.out.println("Nhập tên đăng nhập mới (hoặc nhấn Enter để bỏ qua):");
            String customerName = scanner.nextLine();
            if (!customerName.isEmpty()) {
                customer.setCustomerName(customerName);
            }

            System.out.println("Nhập địa chỉ mới (hoặc nhấn Enter để bỏ qua):");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                customer.setAddress(address);
            }

            System.out.println("Nhập số điện thoại mới (hoặc nhấn Enter để bỏ qua):");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                customer.setPhone(phone);
            }

            boolean updated = CustomerDesignImpl.updateInfo(customer);
            if (updated) {
                IOFile.writeCustomerLogin(customer);
                System.out.println("Cập nhật thông tin thành công.");
                menuCustomer();
            } else {
                System.err.println("Cập nhật thông tin thất bại.");
            }
        } else {
            System.out.println("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

    //---------ĐỔI PASS CÁ NHÂN--------
    private static void changePassword() {
        final byte maxAttempts = 3;
        Customer customer = IOFile.readCustomerLogin();
        byte attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("Nhập mật khẩu cũ: ");
            String oldPass = scanner.nextLine();

            if (customer != null && oldPass.equals(customer.getPassword())) {
                // Process new password
                String newPassword = promptNewPassword();

                if (newPassword != null) {
                    customer.setPassword(newPassword);
                    boolean updated = CustomerDesignImpl.updatePassword(customer);
                    if (updated) {
                        System.out.println("Cập nhật mật khẩu thành công.");
                    } else {
                        System.out.println("Cập nhật mật khẩu thất bại.");
                    }
                    menuCustomer();
                    return;
                } else {
                    menuCustomer();
                    return;
                }
            } else {
                attempts++;
                if (attempts < maxAttempts) {
                    System.out.println("Mật khẩu cũ sai. Còn " + (maxAttempts - attempts) + " lần thử.");
                } else {
                    customer.setBlocked(true);
                    customerDesign.save(customer);
                    IOFile.writeToFile(CustomerDesignImpl.getCustomerList(), IOFile.CUSTOMER_PATH);
                    System.out.println("Tài khoản của bạn đã bị khóa do nhập sai quá nhiều lần.");
                }
            }
        }
        logout();
    }

    private static String promptNewPassword() {
        final byte initialAttempts = 2;
        final byte additionalAttempts = 1;
        final byte totalAttempts = initialAttempts + additionalAttempts;
        int confirmAttempts = 0;
        boolean additionalAttemptsGranted = false;

        while (confirmAttempts < totalAttempts) {
            System.out.print("Nhập mật khẩu mới: ");
            String newPassword = scanner.nextLine();
            System.out.print("Xác nhận mật khẩu mới: ");
            String confirmPassword = scanner.nextLine();

            if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
                return newPassword;
            } else {
                confirmAttempts++;
                if (confirmAttempts == initialAttempts && !additionalAttemptsGranted) {
                    System.out.println("Bạn có muốn ngừng đổi mật khẩu và về trang chính không?Nếu không thì bạn còn " + additionalAttempts + " lần confirm, nếu vẫn sai tài khoản của bạn sẽ bị đóng (y/n): ");
                    String choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("y")) {
                        return null;
                    } else {
                        additionalAttemptsGranted = true;
                        System.out.println("Bạn còn " + additionalAttempts + " lần confirm.");
                    }
                } else if (confirmAttempts < totalAttempts) {
                    System.err.print("Mật khẩu xác nhận không khớp. Còn " + (totalAttempts - confirmAttempts) + " lần thử.(enter)");
                }
            }
        }

        System.out.println("Bạn đã hết số lần thử. Đang tự động đăng xuất.");
        logout();

        return null;
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
        String customerEmail = Inputmethods.getString();
        System.out.println("Nhập password");
        String password = Inputmethods.getString();

        List<Customer> customerList = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
        System.out.println(customerList);

//         Check customer login
        Customer customerLogin = customerList.stream()
                .filter(c -> c.getEmail().equals(customerEmail) && c.getPassword().equals(password))
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
            // Check if the customer is blocked
            if (customerLogin.isBlocked()) {
                System.err.println("Tài khoản bị khóa, vui lòng liên hệ 02348219");
            } else {
                // Save login information if the customer is not blocked
                IOFile.writeCustomerLogin(customerLogin);

                // Xét quyền của customer
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
