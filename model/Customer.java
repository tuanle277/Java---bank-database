package model;

import dao.*;
import model.*;

import java.sql.Date;
import java.util.Collection;

public class Customer {
    private CustomerDAO dao;

    private int CustomerID;
    private String name;
    private String address;
    private String email;
    private String phone;

    private Collection<Account> accounts;

    public Customer(CustomerDAO dao, int CustomerID, String name, String address, String email, String phone)
    {
        this.dao = dao;
        this.CustomerID = CustomerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public int getCustomerID(){ 
        return CustomerID;
    }
    public String getName(){
        return name;
    }
    // public void setName(String name){ 
    //     this.name = name;
    //     dao.changeName(CustomerID, name); 
    // }

    public String getAddress(){ 
        return address;
    }
    // public void setAddress(String address){ 
    //     this.address = address;
    //     dao.changeAddress(CustomerID, address); 
    // }

    public String getEmail(){ 
        return email;
    }
    // public void setEmail(String email){ 
    //     this.email = email;
    //     dao.changeEmail(CustomerID, email);
    // }

    public String getPhone(){ 
        return phone;
    }
    // public void setPhone(String phone){ 
    //     this.phone = phone;
    //     dao.changePhone(CustomerID, phone);
    // }

    public void setCustomerInformation(String name, String address, String email, String phone){ 
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        dao.changeCustomerInformation(CustomerID, name, address, email, phone);
    }
}
