package com.boadu.contactsapp.data;

/**
 * @author Boadu Philip Asare
 * @version 1.0
 * Contact Dao, a an ORM(Obkject Relational Mappping Class for Contact(POJO class).
 * Translate an Contact object into Ralational Database record.
 * Definnes CRUD database functions on Contact Database.
 */


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    /**
     * Insert a contact into Contacts Database.
     * @param contacts {@link Contact} Contact to insert into database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertContact(Contact ... contacts);

    /**
     * Update an existing contact in Contacts database.
     * @param contact {@link Contact} Contact in database to be updated..
     */
    @Update
    public void updateContact(Contact contact);

    /**
     * Select all contacts fromm database an sort them in ascending order by contact name.
     * @return   LivaData of all Contacts {@link Contact} list.
     */
    @Query("SELECT * from contact_table ORDER BY contact_name ASC")
    LiveData<List<Contact>> getContacts();

    /**
     *
     * @param name {@link Contact#mName} Name of contact
     * @return  a contact with named  name
     */
    @Query("SELECT * from contact_table WHERE  contact_name LIKE :name ")
     LiveData<List<Contact>> getContactsByName(String name);

    /**
     * Delete multiple contacts
     * @param contacts Contacts to be deleted fro database.
     */
    @Delete
    public  void deleteContact(Contact ... contacts);

    /**
     * Delete all contacts fom contacts table
     */
    @Query("DELETE FROM contact_table")
    void deleteAll();

    /**
     * Select contact by contact Id
     * @param id   {@link Contact#mID}
     * @return Contact {@link Contact}
     */
    @Query("SELECT * FROM contact_table WHERE contact_id = :id LIMIT 1")
    public Contact getContactById(int id);

    /**
     * Delets contact by Contact id
     * @param id {@link Contact#mID}
     */
    @Query("DELETE FROM contact_table WHERE contact_id = :id")
    public void  deleteContact(int id);

}
