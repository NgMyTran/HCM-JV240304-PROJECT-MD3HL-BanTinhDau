package entity;

import util.Inputmethods;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order implements Serializable {
    private int orderId;
    private int userId;
    private String receiverName;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus;
    private List<OrderDetail> orderDetails;
    private LocalDate orderAt;
    private LocalDate deliverAt;

    // Constructor
    public Order(int orderId, int userId, String receiverName, String phoneNumber, String address,
                 double total, OrderStatus orderStatus, List<OrderDetail> orderDetails,
                 LocalDate orderAt, LocalDate deliverAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
        this.orderAt = orderAt;
        this.deliverAt = deliverAt;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public LocalDate getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDate orderAt) {
        this.orderAt = orderAt;
    }

    public LocalDate getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(LocalDate deliverAt) {
        this.deliverAt = deliverAt;
    }


    public static String getNameFromUser() {
        System.out.print("Nhập tên người nhận: ");
        return Inputmethods.getString().trim();
    }
    public static String getPhoneFromUser() {
        String phone;
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            phone = Inputmethods.getString().trim();
            if (phone.matches("^(\\+84|0)\\d{9,10}$")) { // Đã sửa điều kiện cho số điện thoại chuẩn hơn
                return phone;
            } else {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
    public static String getAddressFromUser() {
        String streetAddress, ward, district, city;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Nhập số nhà, tên đường: ");
            streetAddress = scanner.nextLine().trim();
            if (!streetAddress.isEmpty() && streetAddress.matches("^[\\p{L}0-9\\s-]{3,100}$")) {
                break;
            } else {
                System.out.println("Số nhà, tên đường chỉ chứa chữ cái, số, khoảng trắng và dấu gạch nối (-). Độ dài từ 3 đến 100 ký tự. Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.print("Nhập phường/xã: ");
            ward = scanner.nextLine().trim();
            if (!ward.isEmpty() && ward.matches("^[\\p{L}\\s]{3,50}( \\d+)?$")) {
                break;
            } else {
                System.out.println("Tên phường/xã chỉ chứa chữ cái và khoảng trắng. Có thể bao gồm số nếu là tên phường như 'Phường 12'. Độ dài từ 3 đến 50 ký tự. Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.print("Nhập quận/huyện: ");
            district = scanner.nextLine().trim();
            if (!district.isEmpty() && district.matches("^[\\p{L}0-9\\s]{3,50}$")) {
                break;
            } else {
                System.out.println("Tên quận/huyện chỉ chứa chữ cái, số và khoảng trắng. Độ dài từ 3 đến 50 ký tự. Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.print("Nhập thành phố: ");
            city = scanner.nextLine().trim();
            if (!city.isEmpty() && city.matches("^[\\p{L}\\s]{3,50}$")) {
                break;
            } else {
                System.out.println("Tên thành phố/tỉnh chỉ chứa chữ cái và khoảng trắng. Độ dài từ 3 đến 50 ký tự. Vui lòng nhập lại.");
            }
        }
        return String.format("%s, %s, %s, %s", streetAddress, ward, district, city);
    }
}
