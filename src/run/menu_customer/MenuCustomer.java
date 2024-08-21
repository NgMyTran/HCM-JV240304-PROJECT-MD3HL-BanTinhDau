package run.menu_customer;

import designImpl.*;
import entity.*;
import run.menu_admin.MenuAdmin;
import run.menu_admin.OrderManagement;
import util.IOFile;
import util.Inputmethods;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
public class MenuCustomer {
    private static CustomerDesignImpl customerDesign = new CustomerDesignImpl();
    private static ProductDesignImpl productDesign = new ProductDesignImpl();
    private static CartDesignImpl cartDesign = new CartDesignImpl();
    private static CatalogDesignImpl catalogDesign = new CatalogDesignImpl();

    private static Scanner scanner = new Scanner(System.in);

    //---------MAIN MENU--------
    public static void menuCustomer() {
//        logout();

        String customerName = IOFile.readCusName();
        if (customerName == null || customerName.equals("admin")) {
            customerName = "BẠN";
        }

        while (true) {
            boolean isLoggedIn = IOFile.readCusName() != null;

            System.out.println("╔══════════════════════════════════════════════════════════════════╗");
            System.out.println("  Chào mừng " + "( " + customerName + " )" + " đến cửa hàng để mua sắm ");
            System.out.println("╠══════════════════════════════════════════════════════════════════╣");

            printMenuItem(1, "Xem tất cả mặt hàng");
            printMenuItem(2, "Xem mặt hàng theo danh mục");
            printMenuItem(3, "Tìm mặt hàng theo tên");

            if (!isLoggedIn ) {
                printMenuItem(4, "Đăng ký");
                printMenuItem(5, "Đăng nhập");
            }

            if (isLoggedIn ) {
                System.out.println("╠                                                                  ╣");
                printMenuItem(6, "Thêm mặt hàng vào giỏ");
                printMenuItem(7, "Xem giỏ hàng (giỏ hàng đang có: " + IOFile.readFromFile(IOFile.CART_PATH).size() + " sản phẩm)");
                printMenuItem(8, "Thay đổi số lượng sản phẩm trong giỏ hàng");
                printMenuItem(9, "Xóa mặt hàng khỏi giỏ");
                printMenuItem(10, "Hiển thị thông tin cá nhân");
                printMenuItem(11, "Sửa thông tin cá nhân");
                printMenuItem(12, "Đổi mật khẩu đăng nhập");
                printMenuItem(13, "Thanh toán");
                printMenuItem(14, "Xem đơn hàng đã đặt");
                printMenuItem(15, "Đăng xuất");
            }
            System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            System.out.print("Nhập lựa chọn của bạn: ");
            byte choice = Inputmethods.getByte();
            if (!isLoggedIn) {
                switch (choice) {
                    case 1:
                        displayProductForCus();
                        break;
                    case 2:
                        displayProductsByCatalog();
                        break;
                    case 3:
                        findProductByName();
                        break;
                    case 4:
                        register();
                        break;
                    case 5:
                        login();
                        break;

                    default:
                        System.err.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                        break;
                }
            } else {
                switch (choice) {
                    case 1:
                        displayProductForCus();
                        break;
                    case 2:
                        displayProductsByCatalog();
                        break;
                    case 3:
                        findProductByName();
                        break;
                    case 6:
                        addToCart();
                        break;
                    case 7:
                        showCart();
                        break;
                    case 8:
                        changeCartQuantity();
                        break;
                    case 9:
                        deleteCartItem();
                        break;
                    case 10:
                        displayCusInfo();
                        break;
                    case 11:
                        updateCusInfo();
                        break;
                    case 12:
                        changePassword();
                        break;
                    case 13:
                        checkout();
                        break;
                    case 14:
                        showOrders();
                        break;
                    case 15:
                        logout();
                        break;
                }
            }


        }
    }
    //---------END MAIN MENU--------

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

    //---------LIST PRODUCT--------
    private static void displayProductForCus() {
        List<Product> productList = productDesign.getAll();
        if (productList == null || productList.isEmpty()) {
            System.err.println("Chưa có sản phẩm nào.");
            System.out.println();
            return;
        }

        List<Product> activeProducts = productList.stream()
                .filter(Product::getStatus)
                .sorted(Comparator.comparingInt(Product::getProductId).reversed())
                .collect(Collectors.toList());

        if (activeProducts.isEmpty()) {
            System.out.println("Không có sản phẩm nào hoạt động.");
            System.out.println("");
            return;
        }
        System.out.println("Danh sách sản phẩm:");
        for (Product product : activeProducts) {
            String formattedPrice = String.format("%,.0f VNĐ", product.getProductPrice());
            System.out.printf("STT: %d, Tên sản phẩm: %s, Giá: %s%n", product.getProductId(), product.getProductName(), formattedPrice);
        }
        System.out.print("Nếu muốn xem chi tiết sản phẩm nào hãy nhập STT của sản phẩm đó (hoặc 0 để quay lại): ");
        int productId = Inputmethods.getInteger();
        if (productId != 0) {
            showProductDetails(productId);
        }
    }
    private static void showProductDetails(int productId) {
        Product product = productDesign.findById(productId);
        if (product != null) {
            System.out.println("Chi tiết sản phẩm:");
            System.out.println(product); // Sử dụng toString() của Product để hiển thị thông tin chi tiết
        } else {
            System.out.println("Sản phẩm với STT " + productId + " không tồn tại.");
        }
    }

    //---------HIỂN THỊ DANH SÁCH SẢN PHẨM THEO CATALOG--------
    public static void displayProductsByCatalog() {
        // Đọc danh sách sản phẩm từ file
        List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);
        if (products == null) {
            System.err.println("Danh sách sản phẩm không hợp lệ.");
            return;
        }

        // Hiển thị danh sách danh mục cho người dùng chọn
        List<Catalog> catalogs = catalogDesign.getAll();
        if (catalogs.isEmpty()) {
            System.err.println("Chưa có danh mục nào");
            return;
        }

        System.out.println("Danh sách danh mục:");
        for (Catalog catalog : catalogs) {
            long productCount = products.stream()
                    .filter(p -> p.getCatalogId() == catalog.getCatalogId())
                    .count();
            System.out.println(catalog + " | Total Products: " + productCount);
        }

        // Người dùng chọn danh mục
        System.out.print("Nhập ID danh mục để xem sản phẩm: ");

        int catalogId = Inputmethods.getInteger();

        // Tìm danh mục theo ID
        Catalog selectedCatalog = catalogDesign.findById(catalogId);
        if (selectedCatalog == null) {
            System.err.println("Danh mục không tồn tại.");
            return;
        }

        // Hiển thị sản phẩm thuộc danh mục đã chọn
        System.out.println("");
        System.out.println("Sản phẩm thuộc danh mục: " + selectedCatalog.getCatalogName());
        products.stream()
                .filter(p -> p.getCatalogId() == catalogId)
                .forEach(System.out::println);

        if (products.stream().noneMatch(p -> p.getCatalogId() == catalogId)) {
            System.out.println("Không có sản phẩm nào trong danh mục này.");
        }
    }

    //---------TÌM SP = TÊN--------
    private static void findProductByName() {
        System.out.print("Nhập tên mặt hàng: ");
        String name = scanner.nextLine();
        List<Product> products = productDesign.findProByName(name);
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                System.out.println(product);
            }
        } else {
            System.out.println("Không tìm thấy mặt hàng với tên này.");
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
                    System.err.println("Tài khoản của bạn đã bị khóa do nhập sai quá nhiều lần.");
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
            System.out.print("Nhập giới tính: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Nam") || input.equalsIgnoreCase("nam")) {
                customer.setGender(true);
                break;
            } else if (input.equalsIgnoreCase("Nữ") || input.equalsIgnoreCase("nữ")) {
                customer.setGender(false);
                break;
            } else {
                System.out.println("Vui lòng nhập 'Nam' hoặc 'Nữ'.");
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
                System.err.println("Email đã được đăng ký. Vui lòng nhập email khác.");
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
//                    e.printStackTrace();
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
//        System.out.println(customerList);

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
                // isBlocked==false
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
        menuCustomer();
    }



    //---------CART--------
    private static void addToCart() {
        System.out.println("Nhập id của sản phẩm cần mua");
        int idPro = Inputmethods.getInteger();
        Product p = productDesign.findById(idPro);
        if (p == null) {
            System.err.println("ID không tồn tại");
            return;
        }
        if (p.getStock() <= 0) {
            System.err.println("Sản phẩm hết hàng");
            return;
        }

        System.out.println("Nhập số lượng thêm mới");
        int quantity;
        while (true) {
            quantity = Inputmethods.getInteger();
            if (quantity <= p.getStock()) {
                break;
            }
            System.err.println("Số lượng trong kho chỉ còn " + p.getStock() + ", vui lòng giảm số lượng");
        }
        CartItem cartItem = cartDesign.findByProductId(idPro);
        if (cartItem == null) {
            cartDesign.save(new CartItem(cartDesign.getNewCartItemId(), idPro, p.getProductPrice(), quantity));
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartDesign.changeQuantity(cartItem);
        }
        p.setStock(p.getStock() - quantity);
        productDesign.save(p);

        // Immediately update the file
        IOFile.writeToFile(cartDesign.getAll(), IOFile.CART_PATH);
    }

    private static void showCart() {
        if (cartDesign.getAll().isEmpty()) {
            System.out.println("");
            System.err.println("Không có sản phẩm nào trong giỏ hàng");
            return;
        }
        double total = 0;
        for (CartItem ci : cartDesign.getAll()) {
            total += ci.getPrice() * ci.getQuantity();
            System.out.println(ci);
        }
        System.out.println("Total : " + total);
    }

    private static void changeCartQuantity() {
        System.out.println("Nhập vào itemId muốn thay đổi số lượng");
        int cartItemId = Inputmethods.getInteger();
        CartItem cartItem = cartDesign.findById(cartItemId);
        if (cartItem == null) {
            System.err.println("ID không tồn tại");
            return;
        }
        Product p = productDesign.findById(cartItem.getProductId());
        if (p == null) {
            System.err.println("Sản phẩm không tồn tại");
            return;
        }

        System.out.println("Nhập vào số lượng cần thay đổi");
        int newQuantity;
        while (true) {
            newQuantity = Inputmethods.getInteger();
            if (newQuantity <= cartItem.getQuantity() + p.getStock()) {
                p.setStock(p.getStock() + cartItem.getQuantity()); // Trả lại số lượng từ giỏ hàng về kho
                cartItem.setQuantity(newQuantity);
                cartDesign.changeQuantity(cartItem);
                p.setStock(p.getStock() - newQuantity); // Giảm số lượng trong kho
                productDesign.save(p);
                break;
            }
            System.err.println("Tối đa chỉ có thể mua " + (cartItem.getQuantity() + p.getStock()) + " sản phẩm");
        }
    }

    private static void deleteCartItem() {
        System.out.println("Nhập vào itemId muốn xóa");
        int cartItemId = Inputmethods.getInteger();
        CartItem cartItem = cartDesign.findById(cartItemId);
        if (cartItem == null) {
            System.err.println("ID không tồn tại");
            return;
        }
        cartDesign.delete(cartItemId);
        Product p = productDesign.findById(cartItem.getProductId());
        if (p != null) {
            p.setStock(p.getStock() + cartItem.getQuantity());
            productDesign.save(p);
        }
    }
    //---------END CART--------


    //---------ORDER--------
    public static void checkout() {
        List<CartItem> cartItems = cartDesign.getAll();
        if (cartItems.isEmpty()) {
            System.out.println("Giỏ hàng của bạn hiện tại đang trống.");
            return;
        }
        System.out.println("Bạn muốn:");
        System.out.println("1. Thanh toán tất cả sản phẩm trong giỏ hàng");
        System.out.println("2. Chọn một số sản phẩm trong cart rồi thanh toán");
        System.out.print("Nhập lựa chọn của bạn: ");
        int choice = Inputmethods.getInteger();

        switch (choice) {
            case 1:
                // Thanh toán tất cả sản phẩm trong giỏ hàng
                createOrder(cartItems);
                cartDesign.getAll().clear(); // Xóa tất cả sản phẩm trong giỏ hàng
                IOFile.writeToFile(cartItems, IOFile.CART_PATH); // Cập nhật file giỏ hàng
                break;

            case 2:
                // Thanh toán một số sản phẩm được chọn
                List<CartItem> selectedItems = selectItemsForCheckout(cartItems);
                if (!selectedItems.isEmpty()) {
                    createOrder(selectedItems);
                    // Xóa các sản phẩm đã chọn khỏi giỏ hàng
                    cartItems.removeAll(selectedItems);
                    IOFile.writeToFile(cartItems, IOFile.CART_PATH); // Cập nhật file giỏ hàng
                }
                break;

            default:
                System.out.println("Lựa chọn không hợp lệ.");
                break;
        }
    }
    private static void createOrder(List<CartItem> cartItems) {
        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Hiển thị thông tin đơn hàng
        System.out.println("");
        System.out.println("Đơn hàng mới:");
        cartItems.forEach(System.out::println);
        System.out.println("Tổng tiền: " + totalAmount);

        // Tạo danh sách OrderDetail từ CartItem
        List<OrderDetail> orderDetails = cartItems.stream()
                .map(cartItem -> {
                    Product product = productDesign.findById(cartItem.getProductId());
                    if (product != null) {
                        return new OrderDetail(
                                cartItem.getProductId(),
                                OrderDesignImpl.getNewId(), // Chưa có ID đơn hàng, bạn cần phải lấy ID mới trước khi tạo OrderDetail
                                product.getProductName(),
                                cartItem.getPrice(),
                                cartItem.getQuantity()
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Tạo đơn hàng mới
        Order newOrder = new Order(
                OrderDesignImpl.getOrderId(),
                IOFile.readCustomerLogin().getCustomerId(),
                Order.getNameFromUser(),
                Order.getPhoneFromUser(),
                Order.getAddressFromUser(),
                totalAmount,
                OrderStatus.WAITING,
                orderDetails,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        OrderDesignImpl.create(newOrder);

        IOFile.writeToFile(OrderDesignImpl.getOrderList(), IOFile.ORDER_PATH);
        System.out.println("Đơn hàng đã được tạo thành công.");

        // Cập nhật ID cho các sản phẩm trong giỏ hàng và ghi vào file
        List<CartItem> remainingCartItems = IOFile.readFromFile(IOFile.CART_PATH);
        System.out.println(remainingCartItems+"remainingCartItems");
        IOFile.writeToFile(cartDesign.getAll(), IOFile.CART_PATH);
        updateCartIds(remainingCartItems);
    }

    public static void updateCartIds(List<CartItem> cartItems) {
        List<CartItem> updatedCartItemList = new ArrayList<>();
        int newId = 1; // Starting ID

        for (CartItem cartItem : cartItems) {
            cartItem.setCartItemId(newId++);
            updatedCartItemList.add(cartItem);
        }
        // Ghi lại thông tin giỏ hàng đã cập nhật vào file
        IOFile.writeToFile(updatedCartItemList, IOFile.CART_PATH);
    }

    private static List<CartItem> selectItemsForCheckout(List<CartItem> cartItems) {
        List<CartItem> selectedItems = new ArrayList<>();
        while (true) {
            System.out.println("");
            System.out.println("Danh sách sản phẩm trong giỏ hàng:");
            cartItems.forEach(System.out::println);
            System.out.print("Nhập ID sản phẩm để chọn (hoặc 0 để kết thúc): ");
            int productId = Inputmethods.getInteger();
            if (productId == 0) {
                break;
            }

            CartItem cartItem = cartDesign.findById(productId);
            if (cartItem != null) {
                selectedItems.add(cartItem);
            } else {
                System.out.println("ID sản phẩm không hợp lệ.");
            }
        }

        if (selectedItems.isEmpty()) {
            System.out.println("Không có sản phẩm nào được chọn.");
        }

        return selectedItems;
    }
    // Display all orders
    public static void showOrders() {
        List<Order> orders =IOFile.readFromFile(IOFile.ORDER_PATH);
        if (orders == null ||  orders.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
            return;
        }

        for (Order order : orders) {
            if (order != null)showOrderSummary(order);
            else System.out.println("Có một đơn hàng không hợp lệ.");

        }
    }

    private static void showOrderSummary(Order order) {
        if (order == null) {
            System.out.println("Đơn hàng không hợp lệ.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("                    HÓA ĐƠN ĐẶT HÀNG\n");
        sb.append("==================================================\n");
        sb.append("Mã Đơn Hàng: ").append(order.getOrderId()).append("\n");
        sb.append("Ngày Đặt: ").append(order.getOrderAt()).append("\n");
        sb.append("Tình Trạng: ").append(order.getOrderStatus()).append("\n");

        int totalQuantity = order.getOrderDetails().stream()
                .mapToInt(OrderDetail::getQuantity)
                .sum();
        double totalAmount = order.getOrderDetails().stream()
                .mapToDouble(detail -> detail.getUnitPrice() * detail.getQuantity())
                .sum();

        sb.append("Tổng Số Lượng: ").append(totalQuantity).append("\n");
        sb.append("Tổng Số Tiền: ").append(String.format("%.2f", totalAmount)).append("\n");
        sb.append("==================================================\n");

        System.out.println(sb.toString());

        // Ask if user wants to see detailed information
        System.out.print("Bạn có muốn xem chi tiết đơn hàng đã đặt? (y/n): ");
        char choice = Inputmethods.getChar();
        if (choice == 'y' || choice == 'Y') {
            OrderManagement.displayOrderDetails(order);
        }
    }

    //---------END ORDER--------



}
