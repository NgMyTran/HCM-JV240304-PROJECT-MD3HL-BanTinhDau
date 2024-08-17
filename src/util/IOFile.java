
package util;

import entity.Customer;
import entity.RoleName;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOFile <T>{
    public static final String CATALOG_PATH = "src/data/catalog.txt";
    public static final String PRODUCT_PATH = "src/data/product.txt";
    public static final String CUSTOMER_PATH = "src/data/users.txt";
    public static final String CUSTOMERLOGIN_PATH = "src/data/usersLogin.txt";
    public static final String CART_PATH = "src/data/cart.txt";
    public static final String DELETED_PRODUCT_PATH = "src/data/deleted-product.txt";
    public static final String DELETED_CATALOG_PATH = "src/data/deleted-catalog.txt";


public static <T> List<T> readFromFile(String fileName) {
    List<T> list = new ArrayList<>();
    File file = new File(fileName);

    if (!file.exists()) {
        System.out.println("Tệp tin không tồn tại: " + fileName);
        return list;
    }

    try (FileInputStream fis = new FileInputStream(fileName);
         ObjectInputStream ois = new ObjectInputStream(fis)) {
        Object obj = ois.readObject();
        if (obj instanceof List<?>) {
            list = (List<T>) obj;
        }
        System.out.println("Dữ liệu đã được đọc từ tệp tin: " + fileName);
    } catch (ClassNotFoundException | IOException e) {
        e.printStackTrace();
    }

    return list;
}
    public static <T> void writeToFile(List<T> list, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
            oos.flush();
            System.out.println("Dữ liệu đã được ghi vào tệp tin: " + fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    public static <T> List<T> readFromFile(String fileName) {
//        List<T> list = new ArrayList<>();
//        File file = new File(fileName);
//        if (file.exists() && file.length() > 0) { // Check if file exists and is not empty
//            try (FileInputStream fis = new FileInputStream(fileName);
//                 ObjectInputStream ois = new ObjectInputStream(fis)) {
//                Object obj = ois.readObject();
//                if (obj instanceof List<?>) {
//                    list = (List<T>) obj;
//                }
//            } catch (ClassNotFoundException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return list;
//    };
//
//    public static <T> void writeToFile(List<T> list, String fileName) {
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(list);
//            oos.flush();
//            System.out.println("Dữ liệu đã được ghi vào tệp tin: " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static Customer readCustomerLogin() {
        File file = new File(CUSTOMERLOGIN_PATH);
        if (!file.exists()) {
            System.err.println("File CustomerLogin không tồn tại");
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (Customer) ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("File không có giá trị");
        } catch (EOFException e) {
            System.err.println("File không có dữ liệu");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteCustomerLogin() {
        File file = new File(CUSTOMERLOGIN_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void writeCustomerLogin(Customer userLogin) {
        try (FileOutputStream fos = new FileOutputStream(CUSTOMERLOGIN_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readCusName() {
        File file = new File(CUSTOMERLOGIN_PATH);
        if (!file.exists()) {
            System.err.println("File không tồn tại");
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Customer customer = (Customer) ois.readObject();
            return customer.getCustomerName();
        } catch (FileNotFoundException e) {
            System.err.println("File không có giá trị");
        } catch (EOFException e) {
            System.err.println("File không có dữ liệu");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

