package run.menu_admin;

import entity.Order;
import entity.OrderDetail;
import entity.OrderStatus;
import util.IOFile;
import util.Inputmethods;

import java.util.List;
import java.util.stream.Collectors;

public class OrderManagement {

    public static void orderManagementMenu() {
        while (true) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║            MANAGE PRODUCT          ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị đơn chưa xác nhận     ║");
            System.out.println("║  2. Hiển thị đơn đã xác nhận       ║");
            System.out.println("║  3. Thoát                          ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Mời bạn lựa chọn: ");
            int choose = Inputmethods.getInteger();
            switch (choose) {
                case 1:
                    showWaitingOrders();
                    break;
                case 2:
                    showConfirmOrders();
                    break;
                case 3:
                    MenuAdmin.menuAdmin();
                    break;
                default:
                    System.err.println("Không đúng lựa chọn");
                    break;
            }
        }
    }
    public static void showWaitingOrders() {
        List<Order> orders = IOFile.readFromFile(IOFile.ORDER_PATH);
        List<Order> waitingOrders = orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.WAITING)
                .collect(Collectors.toList());

        if (waitingOrders.isEmpty()) {
            System.out.println("Không có đơn hàng nào chờ xử lý.");
            return;
        }
        waitingOrders.forEach(OrderManagement::displayOrderDetails);
    }

    public static void showConfirmOrders() {
        // Đọc tất cả đơn hàng từ tập tin
        List<Order> orders = IOFile.readFromFile(IOFile.ORDER_PATH);

        // Lọc các đơn hàng có trạng thái WAITING
        List<Order> waitingOrders = orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.CONFIRM)
                .collect(Collectors.toList());

        if (waitingOrders.isEmpty()) {
            System.out.println("Không có đơn hàng đã xử lý nào.");
            return;
        }

        waitingOrders.forEach(OrderManagement::displayOrderDetails);
    }

    public static void displayOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("                    CHI TIẾT ĐƠN HÀNG\n");
        sb.append("==================================================\n");
        sb.append("Mã Đơn Hàng: ").append(order.getOrderId()).append("\n");
        sb.append("Ngày Đặt: ").append(order.getOrderAt()).append("\n");
        sb.append("Ngày Giao Dự Kiến: ").append(order.getDeliverAt()).append("\n");
        sb.append("Tình Trạng: ").append(order.getOrderStatus()).append("\n");
        sb.append("Người Nhận: ").append(order.getReceiverName()).append("\n");
        sb.append("Số Điện Thoại: ").append(order.getPhoneNumber()).append("\n");
        sb.append("Địa Chỉ: ").append(order.getAddress()).append("\n");
        sb.append("Sản Phẩm:\n");

        // Tính tổng tiền của từng sản phẩm
        double totalAmount = 0;
        for (OrderDetail detail : order.getOrderDetails()) {
            double productTotal = detail.getUnitPrice() * detail.getQuantity();
            totalAmount += productTotal;
            sb.append("  - ").append(detail.getName())
                    .append(" (").append(detail.getQuantity()).append(" x ")
                    .append(String.format("%.2f", detail.getUnitPrice())).append(") = ")
                    .append(String.format("%.2f", productTotal)).append("\n");
        }

        sb.append("Tổng Tiền: ").append(String.format("%.2f", totalAmount)).append("\n");
        sb.append("==================================================\n");

        System.out.println(sb.toString());
    }
}
