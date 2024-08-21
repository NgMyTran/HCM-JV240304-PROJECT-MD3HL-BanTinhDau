package designImpl;
import entity.Order;
import util.IOFile;
import util.Inputmethods;

import java.util.*;

public class OrderDesignImpl {
    private static List<Order> orderList;

    public static Scanner scanner = new Scanner(System.in);

    private static Set<Integer> usedOrderIds = new HashSet<>();
    private static Random random = new Random();

    public static List<Order> getOrderList() {
        return orderList;
    }

    public OrderDesignImpl() {
        orderList = IOFile.readFromFile(IOFile.ORDER_PATH);
        if (orderList == null) orderList = new ArrayList<>();
        System.out.println("du lieu trong file order  ");
        System.out.println();
    };

    public static void create(Order order){
//        getOrderId();
        orderList.add(order);
        IOFile.writeToFile(orderList, IOFile.ORDER_PATH);
    }

    public static int getOrderId() {
        int newId;
        do {
            newId = random.nextInt(100000) + 1;
        } while (usedOrderIds.contains(newId));
        usedOrderIds.add(newId);
        return newId;
    }

    public static int getNewId() {
        if (orderList == null) {
            // Initialize the list if it is null, or handle the null case
            orderList = new ArrayList<>();
        }
        return orderList.stream()
                .mapToInt(Order::getOrderId)
                .max()
                .orElse(0) + 1;
    }

}
