package com.akki.androidroompoc.addNotification;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.akki.androidroompoc.R;
import com.akki.androidroompoc.db.NotificationItemModel;
import com.akki.androidroompoc.db.NotificationViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by v-akhilesh.chaudhary on 11/20/2017.
 */

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Date date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private EditText titleEditText;
    private EditText detailEditText;
    private EditText promoCodeText;
    private Spinner imageUrlSpinner;
    private Button setNotification;

    private NotificationViewModel notificationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setToolbar();

        titleEditText = findViewById(R.id.notificationTitle);
        detailEditText = findViewById(R.id.notificationDetail);

        calendar = Calendar.getInstance();
        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);

        datePickerDialog = new DatePickerDialog(this, AddActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        promoCodeText = findViewById(R.id.promo_code);
        imageUrlSpinner = findViewById(R.id.image_url);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEditText.getText() == null || detailEditText.getText() == null)
                    Toast.makeText(AddActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                else {
                    String title = titleEditText.getText().toString();
                    String message = detailEditText.getText().toString();

                    String validity = "abc";
                    if(date != null) {
                        validity = date.toString();
                    }

                    String promoCode = promoCodeText.getText().toString();
                    String imageUrl = "";
                    if(!imageUrlSpinner.getSelectedItem().toString().isEmpty()
                            || !imageUrlSpinner.getSelectedItem().toString().equals("Image Url")) {
                        imageUrl = imageUrlSpinner.getSelectedItem().toString();
                    }

                    String action = "";
                    String pushFrom = "";
                    String notificationType = "";
                    String activityName = "";
                    String otherParams = "";
                    //boolean isRead = false;
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                    notificationViewModel.addNotification(new NotificationItemModel(
                            title, message, validity, promoCode, imageUrl, action, pushFrom, notificationType,
                            activityName, otherParams, false, timeStamp));
                    finish();
                }
            }
        });


        findViewById(R.id.btn_set_validity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();

    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_add_notification));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
