package com.boadu.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {

    TextView txtEmail, txtTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        txtEmail = findViewById(R.id.txtEmail);
        txtTel = findViewById(R.id.txtTel);




    }
}
