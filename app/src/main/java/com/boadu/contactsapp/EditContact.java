package com.boadu.contactsapp;
/**
 *Activity to edit a Contact.
 * @author Boadu Philip Asare
 * @version 1.0
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boadu.contactsapp.data.Contact;
import com.boadu.contactsapp.data.ContactViewModel;

import java.util.regex.Pattern;

public class EditContact extends AppCompatActivity implements  View.OnClickListener{

    EditText edtEditName, edtEditTel, edtEditEmail;
    Button  btnDeleteContact;
    Button btnCancelEdit, btnUpdateContact;
    String[] contactDetails = null;
    PackageManager packageManager;
    ClipboardManager clipboardManager;


    /**
     * {@link ContactViewModel} to perform database transactions
     */
    ContactViewModel contactViewModel;

    //Contact object created from contact details passed fro the calling Activity
    Contact passedContact;
    //Contact object created from updated contact
    Contact updatedContact;

    Intent mainActivityIntent;

    /**
     * Alert dialogue  to delete Contact
     */
    AlertDialog.Builder alertDialogBuilder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        edtEditName = findViewById(R.id.edt_edit_name);
        edtEditTel = findViewById(R.id.edt_edit_tel);
        edtEditEmail = findViewById(R.id.edt_edit_email);

        // ContactViewModel to for database CRUD functions
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);


        btnDeleteContact = findViewById(R.id.btn_delete_contact);
        btnDeleteContact.setOnClickListener(buttonClickListener);

        btnCancelEdit = findViewById(R.id.btn_cancel_edit);
        btnCancelEdit.setOnClickListener(buttonClickListener);

        btnUpdateContact = findViewById(R.id.btn_update_contact);
        btnUpdateContact.setOnClickListener(buttonClickListener);

        edtEditEmail.setTextIsSelectable(true);
        edtEditName.setTextIsSelectable(true);
        edtEditTel.setTextIsSelectable(true);

        Intent intent = getIntent();



        packageManager = this.getPackageManager();
        clipboardManager = (android.content.ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);


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


        //Alert dialogue to delete contact
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete Contact");
        alertDialogBuilder
                .setMessage("Continue to delete contact")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        contactViewModel.deleteContact(passedContact);
                        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivityIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

    }


    /**
     * Update UI with contact details
     * @param contactDetails string array containing contact details
     */
    private void setText(String[] contactDetails){
        if(contactDetails != null){
            edtEditName.setText(contactDetails[1]);
            edtEditTel.setText(contactDetails[2]);
            if(contactDetails[3] != null){
                edtEditEmail.setText(contactDetails[3]);
            }
        }
    }

    /**
     * Get string from editext
     * @param editText editext that contains the string
     * @return String value from editext
     */
    private String  getText(EditText editText){
        return  editText.getText().toString().trim();
    }


    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId){
                //Delete contact
                case R.id.btn_delete_contact:
                    alertDialogBuilder.create();
                    alertDialogBuilder.show();
                    break;
                //Concel contact editing
                case R.id.btn_cancel_edit:
                    startActivity(mainActivityIntent);
                    break;
                //Update contact
                case  R.id.btn_update_contact:
                    if(isValidTel(edtEditTel.getText().toString())) {

                        if (!TextUtils.isEmpty(getText(edtEditEmail))) {
                            if(isValidEmail(getText(edtEditEmail))) {
                                updatedContact = new Contact(
                                        passedContact.getMid(),
                                        getText(edtEditName),
                                        getText(edtEditTel),
                                        getText(edtEditEmail));
                                contactViewModel.updateContact(updatedContact);
                                Toast.makeText(getApplicationContext(), "Contact update succesfull." ,Toast.LENGTH_LONG).show();
                                startActivity(mainActivityIntent);
                            }else {
                                edtEditEmail.requestFocus();
                                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            updatedContact = new Contact(
                                    passedContact.getMid(),
                                    getText(edtEditName),
                                    getText(edtEditTel),
                                    getText(edtEditEmail));

                            contactViewModel.updateContact(updatedContact);
                            Toast.makeText(getApplicationContext(), "Contact update succesfull." ,Toast.LENGTH_LONG).show();
                            startActivity(mainActivityIntent);
                        }
                    }else {
                        edtEditTel.requestFocus();
                        Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();

                    }

            }
        }
    };

    @Override
    public void onClick(View view) {

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



    /**
     * Copies text(String) to System Clipboard
     * @param text Text that will be copied to clipboard, String Object.
     */
    private void copyText(String text){
        ClipData clipData = ClipData.newPlainText("info", text);
        clipboardManager.setPrimaryClip(clipData);

    }


}