package com.master.mobile.freedemy.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void onRegisterClick(View view) {
        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
        String password1 = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.editTextPasswordConfirm)).getText().toString();
        if(!password1.equals(password2)) {
            Toast.makeText(RegisterActivity.this, "Mot de passe incohérent!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password1 == null || password1.equals("")) {
            Toast.makeText(RegisterActivity.this, "Mot de passe obligatoire!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password1.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Le mot de passe doit être supérieure à 6 caractères!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            startAnimation();
            mAuth.createUserWithEmailAndPassword(email, password1);
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                verificationEmailSent();
                            } else {
                                stopAnimation();
                                Toast.makeText(RegisterActivity.this, "Echec d'envoi du mail de vérification!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void verificationEmailSent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Un mail vous a été envoyé pour confirmer votre compte.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK",
                (dialog, id) -> {
                    startActivity(new Intent(this,LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startAnimation() {
        CircularProgressButton btn = findViewById(R.id.cirRegisterButton);
        btn.startAnimation();
    }

    private void stopAnimation() {
        CircularProgressButton btn = findViewById(R.id.cirRegisterButton);
        btn.revertAnimation();
    }
}
