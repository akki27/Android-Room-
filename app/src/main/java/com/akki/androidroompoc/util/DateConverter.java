package com.akki.androidroompoc.util;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by v-akhilesh.chaudhary on 11/20/2017.
 * SQL cannot store data types like Date by default. So we need a way to convert it into a compatible data type to store it in the database.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
