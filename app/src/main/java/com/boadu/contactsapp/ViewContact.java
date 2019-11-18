package com.boadu.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boadu.contactsapp.data.Contact;

import java.util.regex.Pattern;

public class ViewContact extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    TextView txtName, txtEmail, txtTel;
    String[] contactDetails;
    PackageManager packageManager;
    ClipboardManager clipboardManager;
    Contact passedContact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        txtName = findViewById(R.id.txtName);
        txtName.setOnClickListener(this);
        txtName.setOnLongClickListener(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtEmail.setOnClickListener(this);
        txtEmail.setOnLongClickListener(this);

        txtTel = findViewById(R.id.txtTel);
        txtTel.setOnClickListener(this);
        txtTel.setOnLongClickListener(this);


        packageManager = this.getPackageManager();
        clipboardManager = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);




        Intent intent = getIntent();

        //Get contact details passed into this Activity from the calling Activity
        if(intent.hasExtra("CONTACT_DETAILS")){
            contactDetails = intent.getStringArrayExtra("CONTACT_DETAILS");
            if(contactDetails != null){
                setText(contactDetails);
                if(contactDetails[3] != null){
                    passedContact = new Contact(
                            Integer.parseInt(contactDetails[0]),
                            contactDetails[1],
                            contactDetails[2],
                            contactDetails[3]);
                }else {
                    passedContact = new Contact(
                            Integer.parseInt(contactDetails[0]),
                            contactDetails[1],
                            contactDetails[2]);
                }
            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtTel:
                placeCall(txtTel.getText().toString());
                break;
            case R.id.txtEmail:
                sendEmail(txtEmail.getText().toString());
                break;
            case R.id.txtName:
                Intent editContactIntent = new Intent(this , EditContact.class);
                editContactIntent.putExtra("CONTACT_DETAILS", contactDetails);
                startActivity(editContactIntent);
        }
    }


    private  Toast copyAlert(String name){
        return Toast.makeText(this, name, Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onLongClick(View view) {

        switch (view.getId()){
            case R.id.txtTel:
                copyText(txtTel.getText().toString());
                copyAlert("Phone number Copied").show();
                return true;
            case R.id.txtEmail:
                copyText(txtEmail.getText().toString());
                copyAlert("Email Address copied to clipboard").show();
                return true;

            case R.id.txtName:
                copyText(txtName.getText().toString());
                copyAlert("Name copied to clipboard").show();
                return true;
        }

        return true;
    }

    /**
     * Opens the System Dialer Application with the given telephone number displayed
     * @param phoneNumber String object , a telephone number
     */
    private void placeCall(String phoneNumber){
        Intent dialer = new Intent(Intent.ACTION_DIAL);
        dialer.setData(Uri.parse("tel:"+ phoneNumber));
        if(dialer.resolveActivity(packageManager) != null){
            startActivity(dialer);
        }else{
            Toast.makeText(this, "Package manager empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Takes three String objects as paremeter, sends an Email through the System
     * Default Mailer Application.
     * @param email email address, a string object
     */
    private void sendEmail(String email){
        Intent sendMail = new Intent(Intent.ACTION_SENDTO);
        sendMail.setData(Uri.parse("mailto:"+ email));
        //sendMail.putExtra(Intent.EXTRA_EMAIL, email);

        if (sendMail.resolveActivity(packageManager) != null) {
            startActivity(sendMail);
        }
    }


    /**
     * Copies text(String) to System Clipboard
     * @param text Text that will be copied to clipboard, String Object.
     */
    private void copyText(String text){
        ClipData clipData = ClipData.newPlainText("info", text);
        clipboardManager.setPrimaryClip(clipData);

    }


    /**
     * Update UI with contact details
     * @param contactDetails string array containing contact details
     */
    private void setText(String[] contactDetails){
        if(contactDetails != null){
            txtName.setText(contactDetails[1]);
            txtTel.setText(contactDetails[2]);
            if(contactDetails[3] != null){
                txtEmail.setText(contactDetails[3]);
            }
        }
    }

}
