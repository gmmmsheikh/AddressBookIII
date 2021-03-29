package com.college.addressbookiii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.college.addressbookiii.Adapter.ContactAdapter;
import com.college.addressbookiii.Objects.Contact;
import com.college.addressbookiii.Utilities.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView contactsRecyclerView;
    private ContactAdapter contactsAdapter;
    private ArrayList<Contact> addressBook;
    private FloatingActionButton fab;
    DatabaseHandler db;
    private Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        addressBook = new ArrayList<>();
        fab = findViewById(R.id.fabAdd);
        contactsRecyclerView = findViewById(R.id.contactRecyclerView);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactAdapter(db,this);
        contactsRecyclerView.setAdapter(contactsAdapter);

       ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(contactsAdapter));
       itemTouchHelper.attachToRecyclerView(contactsRecyclerView);


        addressBook = db.getAllContacts();
        Collections.reverse(addressBook);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewContact.newInstance().show(getSupportFragmentManager(), AddNewContact.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        addressBook = db.getAllContacts();
        Collections.reverse(addressBook);
        contactsAdapter.setContact(addressBook);
        contactsAdapter.notifyDataSetChanged();
    }

}
