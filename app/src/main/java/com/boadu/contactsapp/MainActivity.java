package com.boadu.contactsapp;
/**
 * Entrance activity for the App where contact list are displayed.
 * @author Boadu Philip Asare
 * @version 1.0
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.boadu.contactsapp.data.Contact;
import com.boadu.contactsapp.data.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements  View.OnClickListener{

    private ContactViewModel contactViewModel;

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final ContactListAdapter contactListAdapter = new ContactListAdapter(this);

        recyclerView.setAdapter(contactListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        contactViewModel = new ViewModelProvider(MainActivity.this).get(ContactViewModel.class);


        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        contactViewModel.getmAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
                // Update the cached copy of the words in the adapter.
                contactListAdapter.setmContacts(contacts);
                Log.i("onChange Called", "UI updated");
            }
        });




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });
    }


        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            //Contact contact = null;

            if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                String[] contactDetails = data.getStringArrayExtra(AddContact.EXTRA_REPLY);
                if(contactDetails != null){
                   if(contactDetails[2] != null){
                       Contact contact = new Contact(contactDetails[0],contactDetails[1],contactDetails[2]);
                       contactViewModel.insertContact(contact);
                   }else {
                       Contact contact = new Contact(contactDetails[0],contactDetails[1]);
                       contactViewModel.insertContact(contact);
                   }
                }
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }


    @Override
    public void onClick(View view) {
        if( view.getTag()  instanceof  Contact){
            Contact contact = (Contact) view.getTag();
            Log.i("TAG_TYPE", "Contact tag ");
        }else{
            Log.i("TAG_TYPE", "not  Contact tag ");
        }
    }
}
