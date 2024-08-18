
package util;

import entity.Catalog;
import entity.Customer;
import entity.RoleName;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOFile <T>{
    public static final String CATALOG_PATH = "src/data/catalogs.txt";
    public static final String PRODUCT_PATH = "src/data/product.txt";
    public static final String CUSTOMER_PATH = "src/data/users.txt";
    public static final String CUSTOMERLOGIN_PATH = "src/data/usersLogin.txt";
    public static final String CART_PATH = "src/data/cart.txt";
    public static final String DELETED_PRODUCT_PATH = "src/data/deleted-product.txt";
    public static final String DELETED_CATALOG_PATH = "src/data/deleted-catalog.txt";

    public static <T> void writeToFile(List<T> list, String fileName){
        File file =null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            fos= new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            // ghi file
            oos.writeObject(list);
            oos.flush();
        }catch (IOException e){
            throw  new RuntimeException(e);
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

public static <T> List<T> readFromFile(String path) {
    try {
        File file = new File(path);
        // Ensure the parent directory exists and create the file if it does not exist
        if (!file.exists()) {
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            file.createNewFile();

            // If the file is the user file, initialize it with a default admin user
            if (path.equals(CUSTOMER_PATH)) {
                List<Customer> users = new ArrayList<>();
                Customer adminUser = new Customer(0, "lastname", "1name", "admin", "admin@gmail.com", "admin123", "hcm","03272729", false, RoleName.ADMIN);
                users.add(adminUser);
                writeToFile( users, CUSTOMER_PATH);
            }
        }
        // Proceed to read from the file
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<T>) ois.readObject();
        } catch (EOFException e) {
            System.err.println("File is empty.");
            return new ArrayList<>(); // Return an empty list instead of null
        }
    } catch (FileNotFoundException e) {
        System.err.println("Unexpected error: File should have been created.");
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return new ArrayList<>(); // Return an empty list instead of null to handle errors better
}

    public static <T> void writeToFileDeletedList(List<T> list, String fileName) {
        writeToFile(list, fileName);
    }


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

    public static <T> void  writeToFile(Object obj, String fileName){
        File file =null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            fos= new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            // ghi file
            oos.writeObject(obj);
            oos.flush();
        }catch (IOException e){
            throw  new RuntimeException(e);
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

//
//public static <T> List<T> readFromFile(String fileName) {
//    File file = new File(fileName);
//    FileInputStream fis = null;
//    ObjectInputStream ois = null;
//    List<T> list = new ArrayList<>();
//
//    try{
//        file=new File(fileName);
//        fis=new FileInputStream(file);
//        ois=new ObjectInputStream(fis);
//        list=(List<T>) ois.readObject();
//    } catch (ClassNotFoundException | IOException c){
//        c.printStackTrace();
//    } finally {
//        if(fis!=null){
//            try {
//                fis.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        if(ois!=null){
//            try {
//                ois.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//        return list;
//}

//    // Writes objects to a file
//    public static <T> void writeToFile( List<T> data, String path) {
//        try (FileOutputStream fos = new FileOutputStream(path);
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(data);
//            oos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

