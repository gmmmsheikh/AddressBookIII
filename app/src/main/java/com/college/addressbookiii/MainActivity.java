package com.college.addressbookiii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.college.addressbookiii.Adapter.ContactAdapter;
import com.college.addressbookiii.Model.ContactModel;
import com.college.addressbookiii.Utilities.TextFileHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView contactsRecyclerView;
    private ContactAdapter contactsAdapter;
    private ArrayList<ContactModel> addressBook;
    private FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        tf = new TextFileHandler(this);
        tf.openFile();

        addressBook = new ArrayList<>();
        fab = findViewById(R.id.fab);
        contactsRecyclerView = findViewById(R.id.contactRecyclerView);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactAdapter(tf,this);
        contactsRecyclerView.setAdapter(contactsAdapter);
        addressBook = tf.getAllContacts();
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
            addressBook = tf.getAllTasks();
            Collections.reverse(addressBook);
            contactsAdapter.setContact(addressBook);
            contactsAdapter.notifyDataSetChanged();
        }


    }
}