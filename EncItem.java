package com.company;

import java.util.Date;

/**
 * Created by eug on 20.12.15.
 */
public class EncItem
{
    private byte[] encMsg;
    private String decMsg;
    private String encAlgo;
    private Date encDate;
    private byte[] iv;

    public EncItem(byte[] encMsg, String meth, byte[] iv) {
        this.encMsg = encMsg;
        this.encDate = new Date();
        this.encAlgo = meth;
        this.iv = iv;

    }

    public byte[] getIv() {
        return iv;
    }
    public byte[] getEncMsg() {
        return encMsg;
    }

    public String getDecMsg() {
        return decMsg;
    }

    public String getEncAlgo() {
        return encAlgo;
    }

    public Date getEncDate() {
        return encDate;
    }

    public void setDecMsg(String decMsg) {
        this.decMsg = decMsg;
    }

    public void setEncAlgo(String encAlgo) {
        this.encAlgo = encAlgo;
    }

    @Override
    public String toString() {
        return "EncItem{" +
                "encMsg='" + encMsg + '\'' +
                ", decMsg='" + decMsg + '\'' +
                ", encAlgo='" + encAlgo + '\'' +
                ", encDate=" + encDate +
                '}';
    }
}
