package com.akki.androidroompoc.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by v-akhilesh.chaudhary on 11/20/2017.
 */

@Dao
public interface NotificationModelDao {

    @Query("select * from NotificationItemModel")
    LiveData<List<NotificationItemModel>> getAllNotificationItems();

    @Query("select * from NotificationItemModel where id = :id")
    NotificationItemModel getNotificationById(String id);

    @Insert(onConflict = REPLACE)
    void addNotification(NotificationItemModel notificationModel);

    @Delete
    void deleteNotification(NotificationItemModel notificationModel);


}
