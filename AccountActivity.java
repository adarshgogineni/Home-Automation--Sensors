// written by: Shaan Parikh, Shivum Mehta
// tested by: Shaan Parikh, Shivum Mehta
// debugged by: Shaan Parikh, Shivum Mehta


package com.example.shaan.hazardapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


//import static com.example.shaan.hazardapp.BaseHazardApp.CHANNEL_1_ID;
//import static com.example.shaan.hazardapp.BaseHazardApp.CHANNEL_2_ID;
//import static com.example.shaan.hazardapp.BaseHazardApp.CHANNEL_3_ID;

//import static com.example.shaan.hazardapp.BaseHazardApp.CHANNEL_1_ID;

public class AccountActivity extends AppCompatActivity {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    private NotificationManagerCompat notificationManager;
    private static final String TAG = "AccountActivity";

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

     Boolean lock = false;
     Boolean motion;
     Boolean microphone;

    private TextView frontDoorStatus;
    private TextView airQStatus;
    private TextView COstatus;
    private TextView windowStatus;
    private TextView fireStatus;

    private TextView DoorIDText;
    private TextView airQText;
    private TextView cMonoxideText;
    private TextView windowText;
    private TextView fireText;

    private Boolean loggedOn = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannels();


        firebaseAuth = FirebaseAuth.getInstance();
        frontDoorStatus = findViewById(R.id.frontDoorStatus);
        windowStatus = findViewById(R.id.windowStatus);

        DoorIDText = findViewById(R.id.DoorIDText);
        airQText = findViewById(R.id.airQText);
        cMonoxideText = findViewById(R.id.cMonoxideText);
        windowText = findViewById(R.id.windowText);
        fireText = findViewById(R.id.fireText);

        DoorIDText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(AccountActivity.this, doorSensor.class));
            }
        });
        airQText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(AccountActivity.this, airSensor.class));
            }
        });
        cMonoxideText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, cMonSensor.class));
            }
        });
        windowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, windowSensor.class));
            }
        });
        fireText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, fireSensor.class));
            }
        });


        String userID = firebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(userID).child("frontDoor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String str = String.valueOf(value);

                frontDoorStatus = (TextView) findViewById(R.id.frontDoorStatus);

                if(str == "true" || str == "True") {
                    frontDoorStatus.setText("Open");
                    if(lock == true){
                       doorNotif();
                    }

                }
                else if(str == "false" || str == "False") {
                    frontDoorStatus.setText("Closed");
                }
                else{
                    frontDoorStatus.setText("Not Connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(userID).child("airQ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String str = String.valueOf(value);
                int val = Integer.parseInt(str);
                airQStatus = (TextView) findViewById(R.id.airQStatus);

                if(val == 0) {
                    airQStatus.setText("Normal");

                }

                else if(val == 1) {
                    airQStatus.setText("Safe");

                }
                else if(val == 2) {
                    airQStatus.setText("Unhealthy");
                    airNotif();
                }
                else if(val == 3) {
                    airQStatus.setText("Very Unhealthy");
                    airNotif();
                }
                else if(val == 4) {
                    airQStatus.setText("Hazardous");
                    airNotif();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(userID).child("cmonoxide").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String str = String.valueOf(value);
                int val = Integer.parseInt(str);

                COstatus = (TextView) findViewById(R.id.COstatus);

                //1 = normal
                //2 = Safe
                //3 = Detected
                //4 = Life Threatening

                if(val == 1) {
                    COstatus.setText("Normal");
                   // coNotif();
                }

               else if(val == 2) {
                    COstatus.setText("Safe");
                    coNotif();
                }
                else if(val == 3) {
                    COstatus.setText("Detected");
                    coNotif();
                }
                else if(val == 4) {
                    COstatus.setText("Deadly");
                    coNotif();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(userID).child("flame").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String str = String.valueOf(value);

                fireStatus = (TextView) findViewById(R.id.fireStatus);

                if(str == "true" || str == "True"){
                    fireStatus.setText("Detected");

                    if(loggedOn == true){
                    fireNotif();}

                }
               else if(str == "false" || str == "False") {
                    fireStatus.setText("Safe");
                }
                else{
                    fireStatus.setText("Not Connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(userID).child("window").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                String str = String.valueOf(value);

                windowStatus = findViewById(R.id.windowStatus);

                if(str == "true" || str == "True"){
                    windowStatus.setText("Intruder");
                    windowNotif();
                }
               else  if(str == "false" || str == "False"){
                    windowStatus.setText("Safe");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









        //switch Lock Mode
        final Switch lockMode = (Switch) findViewById(R.id.lockSwitch);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (loggedOn == true) {



            //switch operations
            lockMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        //toggle enabled
                        Toast.makeText(AccountActivity.this, "Lock Mode On", Toast.LENGTH_LONG).show();
                        sendOnChannel1();
                        lock = true;

                    } else {
                        //toggle disabled
                        Toast.makeText(AccountActivity.this, "Lock Mode Off", Toast.LENGTH_LONG).show();


                    }

                    //Toast.makeText(AccountActivity.this, "Lock Mode Changed", Toast.LENGTH_LONG).show();
                }
            });


            firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    // FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        //user is signed in
                        Log.d(TAG, "onAuthStateChanged:signed-in:" + user.getUid());
                        //Toast.makeText(AccountActivity.this,"Successfully Logged in with " + user.getEmail(),Toast.LENGTH_SHORT).show();
                    } else {
                        //user is signed out
                        Log.d(TAG, "onAuthStateChanged:signed-out");
                        //Toast.makeText(AccountActivity.this,"Successfully Logged out ",Toast.LENGTH_SHORT).show();

                    }
                }
            };

        }
    }


    // NOTIF
    public void sendOnChannel1() {
        String title = "Lock Mode is ON";
        String message = "We will keep your house safe!";
        Intent resultIntent = new Intent(this, AccountActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void fireNotif() {
        String title = "Fire detected!";
        String message = "A fire has been detected";
        Intent resultIntent = new Intent(this, AccountActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(2, notification);
    }
    public void doorNotif() {
        String title = "Door Open!";
        String message = "Your door is open";
        Intent resultIntent = new Intent(this, AccountActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(2, notification);
    }
    public void coNotif() {
        String title = "Carbon Monoxide detected!";
        String message = "CO has been detected, please be cautious";
        Intent resultIntent = new Intent(this, AccountActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(2, notification);
    }
    public void airNotif() {
        String title = "Air Quality Changed";
        String message = "Air Quality has changed, please be cautious";
        Intent resultIntent = new Intent(this, AccountActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(2, notification);
    }
    public void windowNotif() {
        String title = "Intruder Alert!";
        String message = "Windows have been damaged!";
        Intent resultIntent = new Intent(this, AccountActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(2, notification);
    }


    @Override
        protected void onStart () {
            super.onStart();

            firebaseAuth.addAuthStateListener(firebaseAuthListener);


        }

        @Override
        public void onStop () {
            super.onStop();
            if (firebaseAuthListener != null) {
                firebaseAuth.removeAuthStateListener(firebaseAuthListener);
            }
        }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);

            return true;
        }


        //tab options
        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            switch (item.getItemId()) {
                case R.id.menuLogout:

                    FirebaseAuth.getInstance().signOut();
                    finish();
                    loggedOn = false;
                    startActivity(new Intent(this, MainActivity.class));
                    break;

                case R.id.Settings:

                    startActivity(new Intent(this, SettingsActivity.class));
                    break;

                case R.id.map:

                    startActivity(new Intent(this, MapActivity.class));
                    break;
                case R.id.AccountSettings:

                    startActivity(new Intent(this, AccountSettings.class));
                    break;
            }


            return true;

        }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            ////////

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is Channel 2");

            ////////



            ////////

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }


}
