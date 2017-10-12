package com.info121.model;

/**
 * Created by KZHTUN on 10/11/2017.
 */

public class Job {
    private String jobNo;
    private String clientname;
    private String jobtype;
    private String pax;
    private String JobDate;
    private String PickupTime;
    private String PickupPoint;
    private String AlightPoint;

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    public String getJobDate() {
        return JobDate;
    }

    public void setJobDate(String jobDate) {
        JobDate = jobDate;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getPickupPoint() {
        return PickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        PickupPoint = pickupPoint;
    }

    public String getAlightPoint() {
        return AlightPoint;
    }

    public void setAlightPoint(String alightPoint) {
        AlightPoint = alightPoint;
    }
}
