package model;

import dao.*;

public class ATM {
    private ATMDAO dao;

    private int ATMID;
    private String location;
    private String branch;
    private Bank bankID;

    public ATM(ATMDAO dao, int ATMID, String location, String branch)  
    {
        this.ATMID = ATMID;
        this.dao = dao ;
        this.location = location;
        this.branch = branch;
    }

    public int getATMID(){ 
        return ATMID;
    }

    public String getLocation()
    {
        return location;
    }

    public String getBranch()
    {
        return branch;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setBranch(String branch)
    {
        this.branch = branch;
    }


    

}
