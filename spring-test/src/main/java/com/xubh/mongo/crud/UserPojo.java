package com.xubh.mongo.crud;

import java.util.Date;

public class UserPojo {

    private String _id;

    private String _name;

    private int _age;

    private long _timestamp;

    private Date _date;

    private String _create_time;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public long get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(long _timestamp) {
        this._timestamp = _timestamp;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public String get_create_time() {
        return _create_time;
    }

    public void set_create_time(String _create_time) {
        this._create_time = _create_time;
    }
}
