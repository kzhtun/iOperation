package com.info121.model;

/**
 * Created by archi on 11/4/2016.
 */

public class EventCalendarModel {

    String jobdate,
            jobtime,
            description,
            eventid,
            mins,title,locaton;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocaton() {
        return locaton;
    }

    public void setLocaton(String locaton) {
        this.locaton = locaton;
    }

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
    }

    public String getJobtime() {
        return jobtime;
    }

    public void setJobtime(String jobtime) {
        this.jobtime = jobtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }
}
