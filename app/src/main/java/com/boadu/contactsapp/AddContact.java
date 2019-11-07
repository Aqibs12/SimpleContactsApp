package com.boadu.contactsapp;
/**
 * Insert a contact into contacts table
 * @author Boadu Philip Asare
 * @version 1.0
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boadu.contactsapp.data.ContactViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddContact extends AppCompatActivity {

    private EditText edtContactName, edtContactTel, edtContactEmail;

    ContactViewModel contactViewModel;


    public static final String EXTRA_REPLY = "com.boadu.contactsapp.wordlistsql.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        final Button btnAddContact = findViewById(R.id.btn_add_contact);

        edtContactName = findViewById(R.id.edt_name);
        edtContactTel = findViewById(R.id.edt_tel);
        edtContactEmail = findViewById(R.id.edt_email);

        /**
         * ContactviewModel {@link ContactViewModel} to perform database transactions.
         */
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(edtContactName.getText()) || TextUtils.isEmpty(edtContactName.getText()) ){
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String[] contactDetails =  new String[3];

                    if(isValidTel(edtContactTel.getText().toString())) {
                        if (!TextUtils.isEmpty( getText(edtContactEmail) )) {
                            if (isValidEmail(getText(edtContactEmail)  )) {
                                if(!TextUtils.isEmpty(getText(edtContactName))){
                                    contactDetails[0] = getText(edtContactName);
                                    contactDetails[1] = getText(edtContactTel);
                                    contactDetails[2] = getText(edtContactEmail);
                                    replyIntent.putExtra( EXTRA_REPLY, contactDetails);
                                    setResult(RESULT_OK, replyIntent);
                                }else {Toast.makeText(getApplicationContext(), "Enter Contact name", Toast.LENGTH_SHORT).show();}

                            }else {Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();}
                        }else {
                            if(!TextUtils.isEmpty(getText(edtContactName))){
                                contactDetails[0] = getText(edtContactName);
                                contactDetails[1] = getText(edtContactTel);
                                replyIntent.putExtra( EXTRA_REPLY, contactDetails);
                                setResult(RESULT_OK, replyIntent);
                            }
                        }
                    }else {
                        edtContactTel.requestFocus();
                        Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();
                    }
                    /*
                    String[] contactDetails =  new String[3];

                    if(!TextUtils.isEmpty(edtContactName.getText().toString().trim())){
                        contactDetails[0] = edtContactName.getText().toString();
                    }else{
                        edtContactName.requestFocus();
                        Toast.makeText(getApplicationContext(), "Enter Contact name", Toast.LENGTH_SHORT).show();
                    }

                    if(!TextUtils.isEmpty(edtContactTel.getText().toString().trim())){
                        contactDetails[1] = edtContactTel.getText().toString();
                    }else{
                        edtContactTel.requestFocus();
                        Toast.makeText(getApplicationContext(), "Enter Contact phone number", Toast.LENGTH_SHORT).show();
                    }

                    if( !TextUtils.isEmpty(edtContactEmail.getText().toString().trim())){
                        if(isValidEmail(edtContactEmail.getText().toString().trim())){
                            contactDetails[2] = edtContactEmail.getText().toString();
                        }else {
                            edtContactEmail.requestFocus();
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        }
                    }else {contactDetails[2] = null;}

                    replyIntent.putExtra( EXTRA_REPLY, contactDetails);
                    setResult(RESULT_OK, replyIntent);
                */}
                finish();
            }
        });

    }

    /**
     *
     * @param target email(String object) to be validated
     * @return  boolean, true if target has valid email formatelse false.
     */
    public boolean isValidEmail(CharSequence target) {
        return ( Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Checks if phone number has 10 digits and contanins only digits
     * @param tel Phone/Telephone number to be validated.
     * @return true if string has valid phone number format.
     */
    public boolean isValidTel(String tel){
        return (!TextUtils.isEmpty(tel)) && Pattern.matches("\\d{10}", tel);
    }

    private String  getText(EditText editText){
        return  editText.getText().toString().trim();
    }


}
