package com.college.addressbookiii.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.college.addressbookiii.AddNewContact;
import com.college.addressbookiii.MainActivity;
import com.college.addressbookiii.Objects.Contact;
import com.college.addressbookiii.R;
import com.college.addressbookiii.Utilities.DatabaseHandler;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> contactList;
    private MainActivity activity;
    private DatabaseHandler tf;
    public int itemCount = 0;

    public ContactAdapter(DatabaseHandler tf, MainActivity activity){
        this.tf = tf;
        this.activity = activity;
    }
    @Nullable
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_layout, parent, false);
        return  new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            tf.openAddressBook();
            final Contact item = contactList.get(position);
            holder.contact.setText(item.getFirstName());
            holder.contact.setChecked(toBoolean(item.getStatus()));
            holder.contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        tf.updateStatus(item.getId(), 1);
                    } else {
                        tf.updateStatus(item.getId(), 0);
                    }
                }
            });
        }catch (Exception e){System.out.println(e);}
    }

    @Override
    public int getItemCount(){
        return contactList.size();
    }

    //helper function to chnage int to boolean
    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setContact(ArrayList<Contact> contactList){
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteContact(int position){
        Contact person = contactList.get(position);
        tf.deleteContact(person.getId());
        contactList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        Contact item = contactList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("contact", item.getFirstName());
        AddNewContact newPerson = new AddNewContact();
        newPerson.setArguments(bundle);
        newPerson.show(activity.getSupportFragmentManager(),
                AddNewContact.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox contact;
        ViewHolder(View view){
            super(view);
            contact = view.findViewById(R.id.contactCheckBox);
        }
    }
}
