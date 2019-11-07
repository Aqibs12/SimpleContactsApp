package com.boadu.contactsapp.data;
/**
 * @author Boadu Philip Asare
 * @version 1.0
 * Room(an ORM for SQLite Database) performs database transaction in the background(not in the manin thread).
 * Methods for database transaction are defined in backround threads in this class.
 */



import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {

    /**
     * {@link ContactDao}
     */
    private ContactDao mContactDao;


    /**
     * List of all Contacts in a LiveData
     */
    private LiveData<List<Contact>> mAllContacts;

    /**
     *
     * @param context Context of Activity where Constructor is being instantiated.
     */
    ContactRepository(Context context){
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(context);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getContacts();

    }

    /**
     * Get all contacts
     * @return LiveData of list of all contacts {@link Contact}
     */
    LiveData<List<Contact>> getmAllContacts(){
        return mAllContacts;
    }


    /**
     * Wrapper for {@link ContactRepository#insertContact(Contact)}}
     * @param contact {@link Contact} new Contact to be deleted.
     */
    public  void  insertContact(Contact contact){
        new insertAsyncTask(mContactDao).execute(contact);
    }

    /**
     * Wrapper for {@link ContactRepository#deleteContact(Contact)}
     * @param contact {@link Contact} Contact to be deleted.
     */
    public void deleteContact(Contact contact){
        new deleteAsyncTask(mContactDao).execute(contact);
    }


    /**
     * Wrapper for {@link ContactRepository#updateContact(Contact)}
     * @param contact {@link Contact} Contact to be updated.
     */
    public  void  updateContact(Contact contact){
        new updateAsyncTask(mContactDao).execute(contact);
    }


    /**
     * Insert contact into Contacts table   in background thread
     */
    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insertContact(params[0]);
            return null;
        }
    }


    /**
     * Update contact in background thread
     */
    private static class updateAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        updateAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Contact... params) {
            mAsyncTaskDao.updateContact(params[0]);
            return null;
        }
    }


    /**
     * Delete contact(s) in background thread
     */
    private static class deleteAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        deleteAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Contact... params) {
            mAsyncTaskDao.deleteContact(params[0]);
            return null;
        }
    }

}
