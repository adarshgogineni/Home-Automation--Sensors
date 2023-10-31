// written by: Shaan Parikh
// tested by: Shaan Parikh
// debugged by: Shaan Parikh

package com.example.shaan.hazardapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPass extends AppCompatActivity {

    private EditText enterEmailText;
    private Button resetButton;
    private TextView backToMainText;
    private Boolean reset = false;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        enterEmailText = (EditText) findViewById(R.id.enterEmailText);
        resetButton = (Button) findViewById(R.id.resetButton);
        backToMainText = (TextView) findViewById(R.id.backToMainText);

        firebaseAuth = FirebaseAuth.getInstance();

        backToMainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgotPass.this,MainActivity.class));
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(enterEmailText.getText().toString().isEmpty()){
                    Toast.makeText(forgotPass.this, "Fields are Empty.", Toast.LENGTH_LONG).show();
                }
                else {

                    firebaseAuth.sendPasswordResetEmail(enterEmailText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(forgotPass.this, "Password Reset sent to your Email.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(forgotPass.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                    reset = true;
                }

                if(reset == true){
                    startActivity(new Intent(forgotPass.this, MainActivity.class));
                }
            }
        });
    }
}
