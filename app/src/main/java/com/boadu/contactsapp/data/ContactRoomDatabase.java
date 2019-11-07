package com.boadu.contactsapp.data;

/**
 * @author Boadu Philip Asare
 * @version 1.0
 * Create the contact  Database and define basic funtions to be performed when Database is created.
 * */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {

    /**
     * An Instance of ContactDao interface with its methods
     * @return {@link ContactDao}
     */
    public abstract ContactDao contactDao();

    /**
     * An instance of Contacts Database.
     */
    private static volatile ContactRoomDatabase INSTANCE = null;

    /**
     * A Callback(function to be performed when Database os created or opened) to on Create Database.
     */
    private  static  RoomDatabase.Callback roomDatabaseCallback =
        new RoomDatabase.Callback(){
            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                //new PopulateDbAsync(INSTANCE).execute();
                Log.i("Database open", "Database opened");
            }

    };


    /**
     * Insert contacts into database. Just for Debugging
     * U can skip me :)
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ContactDao mDao;
        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mDao.deleteAll();
            /*
            Contact contact = new Contact("Boadu Asare", "0266112077", "asarephilip8@gmail.com");
            mDao.insertContact(contact);
            Log.i("Contact object", "Contact added");

            contact = new Contact("Kwaku Manu", "0203948393", "kmanu@ymail.com");
            mDao.insertContact(contact);
            Log.i("Contact object", "Contact added"); */
            return null;
        }
    }

    /**
     * Return an instance od Database if it exists or creates on if not.
     * @param context Calling Activity Context
     * @return  an instance od Contact Database
     */
    static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                    Log.i("Database Instace", "Database instance crrated");
                }
            }
        }
        return INSTANCE;
    }

}
