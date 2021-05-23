package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseauth.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth = FirebaseAuth.getInstance();

        binding.logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.emailET.getText().toString() != "" && binding.passwordET.getText().toString() != ""){
                    binding.loader.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(binding.emailET.getText().toString(), binding.passwordET.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        binding.loader.setVisibility(View.GONE);
                                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(LogInActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(LogInActivity.this, "Couldn't Login User", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //Send user to SignUp page
        binding.newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    //If user is already SignedUp
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}