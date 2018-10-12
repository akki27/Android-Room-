package com.akki.androidroompoc.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by SadyAkki on 11/26/2017.
 */

public class NotificationViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private final LiveData<List<NotificationItemModel>> notificationItemList;

    public NotificationViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDataBase(this.getApplication());
        notificationItemList = appDatabase.notificationDataModel().getAllNotificationItems();
    }


    public void addNotification(final NotificationItemModel notificationItemModel) {
        new NotificationViewModel.addAsyncTask(appDatabase).execute(notificationItemModel);
    }

    private static class addAsyncTask extends AsyncTask<NotificationItemModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final NotificationItemModel... params) {
            db.notificationDataModel().addNotification(params[0]);
            return null;
        }

    }

    public LiveData<List<NotificationItemModel>> getNotificationDataList() {
        return notificationItemList;
    }

    public void deleteNotification(NotificationItemModel notificationModel) {
        new NotificationViewModel.deleteAsyncTask(appDatabase).execute(notificationModel);
    }

    private static class deleteAsyncTask extends AsyncTask<NotificationItemModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final NotificationItemModel... params) {
            db.notificationDataModel().deleteNotification(params[0]);
            return null;
        }

    }
}
