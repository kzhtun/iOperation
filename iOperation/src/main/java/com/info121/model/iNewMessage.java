package com.info121.model;

/**
 * Created by KZHTUN on 9/26/2017.
 */

public class iNewMessage {
    private String jobNo;
    private String msgHdr;
    private String msgBody;
    private String sendBy;
    private String sendDate;

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getMsgHdr() {
        return msgHdr;
    }

    public void setMsgHdr(String msgHdr) {
        this.msgHdr = msgHdr;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
