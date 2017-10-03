package com.info121.model;

import java.io.Serializable;

public class iOP_Model implements Serializable {

    public String JobNo;
    public String BookNo;
    public String Date;
    public String Type;
    public String Name;
    public String A;
    public String C;
    public String I;
    public String Flight;
    public String FlightTime;
    public String PickupTime;
    public String Hotel;
    public String TPTRemarks;
    public String BookingRemarks;
    public String ArrDep;
    public String Agent;
    public String Driver;
    public String Select;
    public String Status;
    public String recordscount;
    // new added archirayan
    public String client;
    public String alight;
    public String price;
    public String phone;
    public String description;
    public String pickup;
    public String claimAmount;
    public String claimRemarks;
    public String ClaimStatus;
    public String welcometext1;
    public String welcometext2;

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getAmount() {
        return price;
    }

    public void setAmount(String amount) {
        this.price = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAlight() {
        return alight;
    }

    public void setAlight(String alight) {
        this.alight = alight;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public String getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getClaimRemarks() {
        return claimRemarks;
    }

    public void setClaimRemarks(String claimRemarks) {
        this.claimRemarks = claimRemarks;
    }

    public String getClaimStatus() {
        return ClaimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        ClaimStatus = claimStatus;
    }

    public boolean isContainSearchFor(String str) {

        if (checkThisVariable(JobNo, str))
            return true;

        else if (checkThisVariable(Date, str))
            return true;
        else if (checkThisVariable(BookNo, str))
            return true;
        else if (checkThisVariable(Type, str))
            return true;
        else if (checkThisVariable(Name, str))
            return true;
        else if (checkThisVariable(A, str))
            return true;
        else if (checkThisVariable(C, str))
            return true;
        else if (checkThisVariable(I, str))
            return true;
        else if (checkThisVariable(Flight, str))
            return true;
        else if (checkThisVariable(FlightTime, str))
            return true;
        else if (checkThisVariable(PickupTime, str))
            return true;
        else if (checkThisVariable(Hotel, str))
            return true;
        else if (checkThisVariable(TPTRemarks, str))
            return true;
        else if (checkThisVariable(BookingRemarks, str))
            return true;
        else if (checkThisVariable(ArrDep, str))
            return true;
        else if (checkThisVariable(Agent, str))
            return true;
        else if (checkThisVariable(Select, str))
            return true;
        else if (checkThisVariable(Driver, str))
            return true;
        else if (checkThisVariable(Status, str))
            return true;
        else if (checkThisVariable(recordscount, str))
            return true;
        else if (checkThisVariable(claimAmount, str))
            return true;
        else if (checkThisVariable(claimRemarks, str))
            return true;
        else if (checkThisVariable(ClaimStatus, str))
            return true;

        return false;
    }

    public boolean checkThisVariable(String str, String check) {
        try {
            if (str != null)
                return str.toLowerCase().contains(check);
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getJobNo() {
        return JobNo;
    }

    public void setJobNo(String jobNo) {
        JobNo = jobNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getI() {
        return I;
    }

    public void setI(String i) {
        I = i;
    }

    public String getFlight() {
        return Flight;
    }

    public void setFlight(String flight) {
        Flight = flight;
    }

    public String getFlightTime() {
        return FlightTime;
    }

    public void setFlightTime(String flightTime) {
        FlightTime = flightTime;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getHotel() {
        return Hotel;
    }

    public void setHotel(String hotel) {
        Hotel = hotel;
    }

    public String getTPTRemarks() {
        return TPTRemarks;
    }

    public void setTPTRemarks(String tPTRemarks) {
        TPTRemarks = tPTRemarks;
    }

    public String getBookingRemarks() {
        return BookingRemarks;
    }

    public void setBookingRemarks(String bookingRemarks) {
        BookingRemarks = bookingRemarks;
    }

    public String getArrDep() {
        return ArrDep;
    }

    public void setArrDep(String arrDep) {
        ArrDep = arrDep;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getSelect() {
        return Select;
    }

    public void setSelect(String select) {
        Select = select;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBookNo() {
        return BookNo;
    }

    public void setBookNo(String bookNo) {
        BookNo = bookNo;
    }

    public String getRecordscount() {
        return recordscount;
    }

    public void setRecordscount(String recordscount) {
        this.recordscount = recordscount;
    }

    public String getWelcometext1() {
        return welcometext1;
    }

    public void setWelcometext1(String welcometext1) {
        this.welcometext1 = welcometext1;
    }

    public String getWelcometext2() {
        return welcometext2;
    }

    public void setWelcometext2(String welcometext2) {
        this.welcometext2 = welcometext2;
    }


}
