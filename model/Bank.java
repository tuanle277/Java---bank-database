package model;

import model.*;
import java.util.Collection;

import dao.*;

public class Bank {
    private BankDao dao;
    
    private int BankID;
    private String branchName;
    private String location;

    private Collection<Account> accounts;
    private Collection<ATM> atms;

    public Bank(BankDao dao, int BankID, String branchName, String location)
    {
        this.dao = dao;
        this.BankID = BankID;
        this.branchName = branchName;
        this.location = location;
    }

    public int getBankID(){
        return BankID;
    }

    public String getName(){ 
        return branchName;
    }

    public String getLocation(){ 
        return location;
    }

    public void setbranchName(String branchName){
        this.branchName = branchName;
        dao.changeBranchName(BankID, branchName); 
    }

    public String location(){ 
        return location;
    }
    public void setLocation(String location){ 
        this.location = location;
        dao.changeLocation(BankID, location);
    }

    public Collection<Account> getAccounts() {
		if (accounts == null) {
			accounts = dao.getAccounts(BankID);
		}
		return accounts;
	}

    public Collection<ATM> getATMs() {
		if (atms == null) {
			atms = dao.getATMs(BankID);
		}
		return atms;
	}

}