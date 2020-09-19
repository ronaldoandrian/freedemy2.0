package com.master.mobile.freedemy.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mobile.freedemy.MainActivity;
import com.master.mobile.freedemy.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        mAuth = FirebaseAuth.getInstance();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
    }

    public void onLoginClick(View View){
        Handler handler = new Handler();
        CircularProgressButton btn = findViewById(R.id.cirLoginButton);
        btn.startAnimation();

        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();

        if(email == null || email.isEmpty() || password == null || password.isEmpty()) {
            stopAnimation();
            Toast.makeText(LoginActivity.this, "Tous les champs sont obligatoires!", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user.isEmailVerified()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    stopAnimation();
                                    verificationEmailSent();
                                }
                            } else {
                                stopAnimation();
                                Toast.makeText(LoginActivity.this, "Mail/mot de passe incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void loadRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    private void verificationEmailSent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Votre compte n'est pas encore verifié. Un mail vous a été envoyé pour confirmer votre compte.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, id) -> { });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void stopAnimation() {
        CircularProgressButton btn = findViewById(R.id.cirLoginButton);
        btn.revertAnimation();
    }


    private void startAnimation() {
        CircularProgressButton btn = findViewById(R.id.cirRegisterButton);
        btn.startAnimation();
    }
}
