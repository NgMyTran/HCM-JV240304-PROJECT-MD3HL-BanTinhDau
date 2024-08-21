package designImpl;

import design.IDesign;
import entity.Customer;
import entity.RoleName;
import util.IOFile;
import util.Inputmethods;

import java.util.List;

public class CustomerDesignImpl implements IDesign<Customer, Integer> {
    private static List<Customer> customerList;
    private static List<Customer> deletedCustomerList;

    public static List<Customer> getCustomerList() {
        return customerList;
    }
    public static List<Customer> getDeletedCustomerList() {
        return deletedCustomerList;
    }

    public CustomerDesignImpl() {
        customerList = IOFile.readFromFile(IOFile.CUSTOMER_PATH);
        System.out.println();
    };


//    @Override
    public void save(Customer customer) {
        if (customer.getCustomerId() == 0) {
            // New customer, assign a new ID
            customer.setCustomerId(getNewId());
            customer.setBlocked(false);
            customer.setRoleName(RoleName.CUSTOMER);
            customerList.add(customer);
        } else {
            // Existing customer, update the information
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getCustomerId()==customer.getCustomerId()) {
                    customerList.set(i, customer);
                    break;
                }
            }
        }
        IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH);
    }



    @Override
    public List<Customer> getAll() {
        return customerList;
    }

    @Override
    public Customer findById(Integer id) {
        for (Customer customer : customerList) {
            if(customer.getCustomerId()==id)
                return customer;
        }
        return null;
    }

    public static Customer findCusByName(String name) {
        for (Customer customer : customerList) {
            if (customer.getCustomerName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        customerList.removeIf(customer -> customer.getCustomerId()==id);
        IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH);
    }

    public int getNewId() {
        int idMax = 0;
        for (Customer u : customerList) {
            if (u.getCustomerId() > idMax) {
                idMax = u.getCustomerId();
            }
        }
        return idMax + 1;
    }



    public static boolean updateInfo(Customer updatedCus) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == updatedCus.getCustomerId()) {
                customer.setCustomerName(updatedCus.getCustomerName());
                customer.setAddress(updatedCus.getAddress());
                customer.setPhone(updatedCus.getPhone());
                IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH);
                return true;
            }
        }
        return false;
    }
    public static boolean updatePassword(Customer updatedCus) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == updatedCus.getCustomerId()) {
                customer.setPassword(updatedCus.getPassword());
                IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH);
                return true;
            }
        }
        return false;
    }

public boolean blockCustomer(int cusId) {
    Customer cus = findById(cusId);
    if (cus != null) {
        final String ADMIN_EMAIL = "admin@gmail.com";
        if(cus.getEmail().equals(ADMIN_EMAIL)){
            System.err.println("Admin cannot be bloked");
            return false;
        }else if(cus.isBlocked()){
            System.err.println("Customer is blocked, cannot block again");
        }else {
            cus.setBlocked(true);
            save(cus);
            IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH);
            return true;
        }
    }
    return false;
}

    public boolean unblockCustomer(int cusId) {
        Customer cus = findById(cusId);
        if (cus != null) {
            cus.setBlocked(false);
            save(cus);
            IOFile.writeToFile(customerList, IOFile.CUSTOMER_PATH);
            return true;
        }
        return false;
    }

}
