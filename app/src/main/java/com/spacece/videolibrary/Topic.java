package com.spacece.videolibrary;

public class Topic {
    private String status;
    private String title;
    private String v_id;
    private String filter;
    private String length;
    private String v_URL;
    private String v_date;
    private String v_uni_no;
    private String desc;

    public Topic(String status, String title, String v_id, String filter, String length, String v_URL, String v_date, String v_uni_no, String desc) {
        this.status = status;
        this.title = title;
        this.v_id = v_id;
        this.filter = filter;
        this.length = length;
        this.v_URL = v_URL;
        this.v_date = v_date;
        this.v_uni_no = v_uni_no;
        this.desc = desc;
    }

    public String getTopic_name(){
        return title;
    }
    public String getDescription(){
        return desc;
    }
    public String getV_id() {
        return v_id;
    }

    public String getFilter() {
        return filter;
    }

    public String getLength() {
        return length;
    }

    public String getV_URL() {
        return v_URL;
    }

    public String getV_date() {
        return v_date;
    }

    public String getV_uni_no() {
        return v_uni_no;
    }

    public String getDesc() {
        return desc;
    }

}
