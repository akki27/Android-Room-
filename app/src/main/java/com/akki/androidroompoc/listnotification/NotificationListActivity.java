package com.akki.androidroompoc.listnotification;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.akki.androidroompoc.R;
import com.akki.androidroompoc.addNotification.AddActivity;
import com.akki.androidroompoc.db.NotificationItemModel;
import com.akki.androidroompoc.db.NotificationViewModel;
import com.akki.androidroompoc.util.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private final String TAG = NotificationListActivity.class.getName();

    private NotificationViewModel viewModel;
    private NotificationViewAdapter notificationViewAdapter;
    private RecyclerView recyclerView;
    private List<NotificationItemModel> notificationItemModelList;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        setToolbar();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationListActivity.this, AddActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        notificationItemModelList = new ArrayList<>();
        notificationViewAdapter = new NotificationViewAdapter(this, notificationItemModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notificationViewAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(notificationViewAdapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        viewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        viewModel.getNotificationDataList().observe(NotificationListActivity.this, new Observer<List<NotificationItemModel>>() {
            @Override
            public void onChanged(@Nullable List<NotificationItemModel> notificationListItem) {
                notificationViewAdapter.addItems(notificationListItem);
                notificationItemModelList.clear();
                notificationItemModelList.addAll(notificationListItem);
                Log.d(TAG, "onChanged(): " +notificationListItem.size() + "::" +notificationItemModelList.size());
            }
        });


    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Log.d(TAG, "onSwiped()_notificationItemModelList: " +notificationItemModelList.size());

        if (viewHolder instanceof NotificationViewAdapter.ItemRecyclerViewHolder) {
            // get the removed item name to display it in snack bar
            String notificationTitle = notificationItemModelList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final NotificationItemModel deletedNotification = notificationItemModelList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            //notificationViewAdapter.removeItem(viewHolder.getAdapterPosition());
            notificationViewAdapter.removeItem(viewHolder.getAdapterPosition(), viewModel, recyclerView);

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, notificationTitle + " removed from list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    notificationViewAdapter.restoreItem(deletedNotification, deletedIndex, viewModel);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.txt_notification));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click: For now finishing the activity
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
