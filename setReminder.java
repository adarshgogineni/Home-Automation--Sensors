// written by: Shaan Parikh, Shivum Mehta
// tested by: Shaan Parikh, Shivum Mehta
// debugged by: Shaan Parikh, Shivum Mehta

package com.example.shaan.hazardapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.util.Calendar;

public class setReminder extends AppCompatActivity {

    TimePicker timePicker;
    Button buttonSet;
    TextView backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        buttonSet = findViewById(R.id.buttonSet);
        timePicker = findViewById(R.id.timePicker);
        backText = findViewById(R.id.backText);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setReminder.this, AccountSettings.class));
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();


                if(Build.VERSION.SDK_INT >=23){
                c.set(c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH),
                        timePicker.getHour(),
                        timePicker.getMinute(),
                        0);
                }
                else {
                    c.set(c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute(),
                            0);
                }


                setAlarm(c.getTimeInMillis());

            }
        });


    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,MyAlarm.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis,AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this,"Alarm is set", Toast.LENGTH_SHORT).show();

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
