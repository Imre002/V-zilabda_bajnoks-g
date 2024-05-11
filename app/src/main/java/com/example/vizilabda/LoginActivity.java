package com.example.vizilabda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private static final String LOG_TAG = LoginActivity.class.getName();

    private FirebaseAuth mAuth;

    EditText userNameET;
    EditText passwordET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if(secret_key != 99) {
            finish();
        }

        userNameET = findViewById(R.id.userNameET);
        passwordET = findViewById(R.id.passwordET);

        mAuth = FirebaseAuth.getInstance();
    }


    public void cancel(View view) {
        finish();
    }

    public void login(View view) {
      String userName = userNameET.getText().toString();
      String password = passwordET.getText().toString();

      mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Log.d(LOG_TAG, "Sikeres belepes");
                          loggedIn();
                      } else {
                          Log.d(LOG_TAG, "Sikertelen belepes");
                          Toast.makeText(LoginActivity.this, "Sikertelen belepes: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                      }
                  }
              });


    }

    public void loggedIn() {
        Intent intent = new Intent(this, dataListActivity.class);
        startActivity(intent);

    }
}