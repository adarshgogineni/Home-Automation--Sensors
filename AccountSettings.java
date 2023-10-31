// written by: Shaan Parikh
// tested by: Shaan Parikh
// debugged by: Shaan Parikh


package com.example.shaan.hazardapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.shaan.hazardapp.BaseHazardApp.CHANNEL_1_ID;

public class AccountSettings extends AppCompatActivity  {

    private Button SignOutButton;
    private Button goToTimePickerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        SignOutButton = findViewById(R.id.SignOutButton);
        goToTimePickerButton = findViewById(R.id.goToTimePickerButton);

        goToTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettings.this, setReminder.class));
            }
        });


        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(AccountSettings.this, MainActivity.class));
            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.Settings:

                startActivity(new Intent(this,SettingsActivity.class ));
                break;

            case R.id.dashboard:

                startActivity(new Intent(this,AccountActivity.class ));
                break;
            case R.id.map:

                startActivity(new Intent(this,MapActivity.class ));
                break;
        }
        return true;
}
}

