package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Customer implements Serializable {
    private int customerId;
    private String customerName, lastName, firstName;
    private String email;
    private String password;
    private  String address;
    private String phone;
//    private LocalDate birthday;
    private boolean isBlocked=false;
    private RoleName roleName;


    public Customer(){
    };

    public Customer(int customerId, String lastName, String firstName, String customerName, String email, String password, String address, String phone , boolean isBlocked, RoleName roleName) {
        this.customerId =customerId ;
        this.lastName=lastName;
        this.firstName=firstName;
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
//        this.birthday = birthday;
        this.isBlocked = isBlocked;
        this.roleName = roleName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    //    public String toString() {
//        return String.format(
//                """
//                            User{
//                            id=%d,
//                            name='%s',
//                            email='%s',
//                            address='%s',
//                            phone='%s',
//                            cart=%s
//                        }
//                        """, customerId, customerName, email, address, phone,
////                cart
//        );
//    }

    @Override
    public String toString() {
        return "User {" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isBlock='" + isBlocked + '\'' +
                " } \n";
    }
}
