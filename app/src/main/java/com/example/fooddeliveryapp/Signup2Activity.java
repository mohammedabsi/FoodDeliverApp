package com.example.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup2Activity extends AppCompatActivity {

    private TextView username;
    private RelativeLayout customerid, farmerid, deliveryid, ownerid;
    private Button registerButton;
    private FirebaseFirestore db ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2_fragment);



        username = findViewById(R.id.username);
        customerid = findViewById(R.id.customerid);
        farmerid = findViewById(R.id.farmerid);
        deliveryid = findViewById(R.id.deliveryid);
        ownerid = findViewById(R.id.ownerid);
        registerButton = findViewById(R.id.registerButton);
        db = FirebaseFirestore.getInstance();







    }


    public void chooseType(View view) {
        Bundle bundle = getIntent().getExtras();
        String   message = bundle.getString("message");
         String x = "null";

        switch (view.getId()) {
            case R.id.customerid:
                customerid.setBackgroundColor(getResources().getColor(R.color.darkgray));
                farmerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                deliveryid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                ownerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                x = "costumer" ;
                db.collection("User").document(message).update("type", x);


;
                break;

            case R.id.farmerid:
                farmerid.setBackgroundColor(getResources().getColor(R.color.darkgray));
                customerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                deliveryid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                ownerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                x = "farmer" ;
                db.collection("User").document(message).update("type", x);
                break;

            case R.id.deliveryid:
                deliveryid.setBackgroundColor(getResources().getColor(R.color.darkgray));
                farmerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                customerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                ownerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                x = "delivery" ;
                db.collection("User").document(message).update("type", x);
                break;

            case R.id.ownerid:
                ownerid.setBackgroundColor(getResources().getColor(R.color.darkgray));
                customerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                farmerid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                deliveryid.setBackground(getResources().getDrawable(R.drawable.background_btn));
                x = "owner" ;
                db.collection("User").document(message).update("type", x);
                break;



        }


    }

    public void goregHome(View view) {
        Bundle bundle = getIntent().getExtras();
        String   message = bundle.getString("message");
        db.collection("User").document(message).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                 if (task.getResult().get("type").toString().equalsIgnoreCase("null")){
                     Toast.makeText(Signup2Activity.this, "Choose your Acoount Type please ", Toast.LENGTH_SHORT).show();
                 } else {
                     Intent intent = new Intent(Signup2Activity.this , MainActivity.class);
                     startActivity(intent);
                     finish();
                 }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup2Activity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}