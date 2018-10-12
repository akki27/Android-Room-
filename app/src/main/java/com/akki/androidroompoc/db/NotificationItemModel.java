package com.akki.androidroompoc.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by e01106 on 11/23/2017.
 */

@Entity/*(tableName = "notification")*/
public class NotificationItemModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "validity")
    private String validity;

    @ColumnInfo(name = "promo_code")
    private String promo_code;

    @ColumnInfo(name = "image_url")
    private String image_url;

    @ColumnInfo(name = "action")
    private String action;

    @ColumnInfo(name = "push_from")
    private String push_from;

    @ColumnInfo(name = "notification_type")
    private String notification_type;

    @ColumnInfo(name = "activity_name")
    private String activity_name;

    @ColumnInfo(name = "other_params")
    private String other_params;

    @ColumnInfo(name = "isRead")
    private boolean isRead;

    @ColumnInfo(name = "time_Stamp")
    private String time_Stamp;


    public NotificationItemModel(String title, String message, String validity, String promo_code, String image_url,
                                 String action, String push_from, String notification_type, String activity_name, String other_params, boolean isRead, String time_Stamp) {
        this.title = title;
        this.message = message;
        this.validity = validity;
        this.promo_code = promo_code;
        this.image_url = image_url;
        this.action = action;
        this.push_from = push_from;
        this.notification_type = notification_type;
        this.activity_name = activity_name;
        this.other_params = other_params;
        this.isRead = isRead;
        this.time_Stamp = time_Stamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPush_from() {
        return push_from;
    }

    public void setPush_from(String push_from) {
        this.push_from = push_from;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getOther_params() {
        return other_params;
    }

    public void setOther_params(String other_params) {
        this.other_params = other_params;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getTime_Stamp() {
        return time_Stamp;
    }

    public void setTime_Stamp(String time_Stamp) {
        this.time_Stamp = time_Stamp;
    }
}
