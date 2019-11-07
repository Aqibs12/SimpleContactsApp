package com.boadu.contactsapp.data;

/**Separate Database functions from Activity. Unrestrict Database transaction from UI/Activities/
 * Performs all database transaction in the UI/Activity
 * @author Boadu Philip Asare
 * @version 1.0
 */

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    /**
     * Instance of {@link ContactRepository}
     */
    private ContactRepository mContactRepository;
    /**
     * LiveData of all contacts list
     */
    private LiveData<List<Contact>> mAllContacts;


    /**
     * Creates an Instance of {@link ContactViewModel } and bind it to the calling Appplication
     * @param application Calling Activity/Fragment
     */
    public ContactViewModel(@NonNull Application application) {
        super(application);
        mContactRepository = new ContactRepository(application);
        mAllContacts = mContactRepository.getmAllContacts();
    }

    /**
     * Get all contacts from contacts table
     * @return LivesData of list of all {@link Contact} contacts
     */
    public LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;
    }

    /**
     * Inset contact into contacts table
     * @param contact {@link Contact}
     */
    public  void  insertContact (Contact contact){
        mContactRepository.insertContact(contact);
    }


    /**
     * Update {@link Contact}contact within table
     * @param contact {@link Contact}
     */
    public void updateContact(Contact contact){
        mContactRepository.updateContact(contact);
    }


    /**
     * Delete  contact from contacts table
     * @param contact {@link Contact}
     */
    public void deleteContact(Contact contact){
        mContactRepository.deleteContact(contact);
    }

}
