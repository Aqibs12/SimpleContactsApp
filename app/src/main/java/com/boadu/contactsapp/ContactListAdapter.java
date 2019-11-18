package com.boadu.contactsapp;
/**
 *Recycler view Adapter to polulate UI with contact recored from Contact table.
 * @author Boadu Philip Asare
 * @version 1.0
 */


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boadu.contactsapp.data.Contact;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {


    private final LayoutInflater mLayoutInfalter;
    private List<Contact> mContacts;

    private Context mContext;

    String[] contactDetails;

    class ContactViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private final TextView txtvContactName;
        private final TextView txtvContactTel;
        private final TextView txtvContactEmail;



        private ContactViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtvContactName = itemView.findViewById(R.id.txtV_contact_name);
            txtvContactTel  = itemView.findViewById(R.id.txtV_contact_tel);
            txtvContactEmail = itemView.findViewById(R.id.txtV_contact_email);

        }

        @Override
        public void onClick(View view) {

        }
    }

    ContactListAdapter(Context context){
        mLayoutInfalter = LayoutInflater.from(context);
    }

    /**
     *
     * @param contacts Contact to updated on UI
     */
    public  void setmContacts(List<Contact> contacts){
        mContacts = contacts;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = mLayoutInfalter.inflate(R.layout.recyclerview_item, parent, false);
        mContext = parent.getContext();
        return  new ContactViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, int position) {
        if(mContacts != null){
            //Set contact propertied on UI
            Contact contact = mContacts.get(position);
            holder.txtvContactEmail.setText(contact.getMEmail());
            holder.txtvContactTel.setText(contact.getMtelephone());
            holder.txtvContactName.setText(contact.getMname());
            holder.itemView.setTag(getContact(position));
            Log.i("Contactviewholder pos",   Integer.toString(holder.getAdapterPosition()));
        }else{
            Log.i("Contact List ", "Contact List empty");
        }


        //Open a new activity that display contact details where contact may be upddated or deleted
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag() instanceof  Contact){
                    Contact contact = (Contact) view.getTag();
                    contactDetails = new String[4];
                    if(contact != null){
                        contactDetails[0] = Integer.toString(contact.getMid());
                        contactDetails[1] = contact.getMname();
                        contactDetails[2] = contact.getMtelephone();
                        if(contact.getMEmail() != null){
                            contactDetails[3] = contact.getMEmail();
                        }
                    }
                }

                Intent editContactIntent = new Intent(mContext , ViewContact.class);
                editContactIntent.putExtra("CONTACT_DETAILS", contactDetails);
                mContext.startActivity(editContactIntent);
            }
        });
    }

    /**
     * Returns the number of contacts on the Contact adapter
     * @return integer value.
     */
    @Override
    public int getItemCount() {
        if(mContacts != null){
            return mContacts.size();
        }else return 0;
    }

    /**
     * Return a contact at a specific position in the Contactacts adapter
     * @param position position of contact in contact Adapter.
     * @return
     */
    public Contact getContact(int position){
        return mContacts.get(position);
    }

}
