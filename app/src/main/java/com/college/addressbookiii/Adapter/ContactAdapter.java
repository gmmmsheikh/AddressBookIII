package com.college.addressbookiii.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.college.addressbookiii.AddNewContact;
import com.college.addressbookiii.MainActivity;
import com.college.addressbookiii.Model.ContactModel;
import com.college.addressbookiii.R;
import com.college.addressbookiii.Utilities.TextFileHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<ContactModel> contactList;
    private MainActivity activity;
    private TextFileHandler tf;

    public ContactAdapter(TextFileHandler tf,MainActivity activity){
        this.tf = tf;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_layout, parent, false);
        return  new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        tf.openAddressBook();
        ContactModel item = contactList.get(position);
        holder.contact.setText(item.getFirstName());
        holder.contact.setChecked(toBoolean(item.getStatus()));
        holder.contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tf.updateStatus(item.getId(), 1);
                }else{
                    tf.updateStatus(item.getId(), 0);
                }
            }
        });
    }
    public int getItemCount(){
        return contactList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setContact(ArrayList<ContactModel> contactList){
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void setContacts(ArrayList<ContactModel> contactList){
        this.contactList = contactList;
    }
    public void deleteContact(int position){
        ContactModel person = contactList.get(position);
        tf.deletePerson(person.getId());
        contactList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ContactModel item = contactList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("contact", item.getFirstName());
        AddNewContact fragment = new AddNewContact();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),
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
