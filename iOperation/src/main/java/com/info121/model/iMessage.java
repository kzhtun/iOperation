package com.info121.model;

/**
 * Created by KZHTUN on 9/25/2017.
 */

public class iMessage {
    private String row;
    private String msgId;
    private String jobNo;
    private String msgHdr;
    private String sendBy;
    private String sendDate;
    private String isRead;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

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

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }


}
