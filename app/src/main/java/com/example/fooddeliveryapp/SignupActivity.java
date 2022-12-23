package com.example.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private EditText confirmPasswordEdt, userNameEdt, emailEdt, passwordEdt , phone;
    private ProgressBar reg_progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fragment);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        userNameEdt = findViewById(R.id.userNameEdt);
        emailEdt = findViewById(R.id.emailEdt);
        passwordEdt = findViewById(R.id.passwordreg);
        confirmPasswordEdt = findViewById(R.id.confirmPasswordEdt);
        phone = findViewById(R.id.phone);
        reg_progressBar = findViewById(R.id.reg_progressBar);





    }

    public void returnLogin(View view) {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void register2() {
        Intent intent = new Intent(this, Signup2Activity.class);
        intent.putExtra("message", mAuth.getCurrentUser().getUid());
        startActivity(intent);

    }



    public void Register(View view) {
        closeKeyboard();
        String user_name = userNameEdt.getText().toString().trim();
        String email = emailEdt.getText().toString().toLowerCase().trim();
        String password = passwordEdt.getText().toString().trim();
        String confirm_password = confirmPasswordEdt.getText().toString().trim();


        String phones = phone.getText().toString().trim();



        reg_progressBar.setVisibility(View.VISIBLE);


        if (!user_name.isEmpty()) {
            if (!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!password.isEmpty() && password.length() >6) {
                    if (!confirm_password.isEmpty()) {
                        if (password.equalsIgnoreCase(confirm_password)) {


                            User user = new User(user_name, email , password, phones , "null");

                            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        firestore.collection("User").document(mAuth.getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignupActivity.this, "Register success :)", Toast.LENGTH_SHORT).show();
                                                    reg_progressBar.setVisibility(View.INVISIBLE);
                                                    register2();

                                                } else {
                                                    Toast.makeText(SignupActivity.this, "Register Failed :(", Toast.LENGTH_SHORT).show();
                                                    reg_progressBar.setVisibility(View.INVISIBLE);

                                                }
                                            }
                                        });

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignupActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            confirmPasswordEdt.setError("Passwords Doesnt match , retry again  !!");
                            confirmPasswordEdt.requestFocus();
                            reg_progressBar.setVisibility(View.INVISIBLE);


                        }
                    } else {
                        confirmPasswordEdt.setError("Re type your password correctly !!");
                        confirmPasswordEdt.requestFocus();
                        reg_progressBar.setVisibility(View.INVISIBLE);

                    }

                } else {
                    passwordEdt.setError("The password should contain more than 6 symbols");
                    passwordEdt.requestFocus();
                    reg_progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

            } else {
                emailEdt.setError("Please provide valid email");
                emailEdt.requestFocus();
                reg_progressBar.setVisibility(View.INVISIBLE);
                return;
            }
        } else {
            userNameEdt.setError("Empty Field");
            userNameEdt.requestFocus();
            reg_progressBar.setVisibility(View.INVISIBLE);
            return;
        }







    }
}