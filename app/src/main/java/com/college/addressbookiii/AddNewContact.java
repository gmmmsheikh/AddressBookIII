package com.college.addressbookiii;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.college.addressbookiii.Objects.Contact;
import com.college.addressbookiii.Utilities.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewContact extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newContactText;
    private Button newContactSaveButton;
    private DatabaseHandler db;

    public static AddNewContact newInstance() {
        return new AddNewContact();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // uses the new_contact card for new contact insertion
        View view = inflater.inflate(R.layout.new_contact, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newContactText= getView().findViewById(R.id.newContactText);
        newContactSaveButton = getView().findViewById(R.id.newContactButton);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle!=null){
            isUpdate = true;
            String contact = bundle.getString("contact");
            newContactText.setText(contact);
            // To make sure that contact is never null . . .
            assert contact != null;
            // change color of button if text-field is not empty
            if(!contact.equalsIgnoreCase("")){
                newContactSaveButton.setTextColor(ContextCompat.getColor(getContext(),
                        R.color.design_default_color_primary_dark));
            }
        }

        // this occurs when there is a change in the text
        newContactText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // intentionally kept empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newContactSaveButton.setEnabled(false);
                    newContactSaveButton.setTextColor(Color.WHITE);
                }else{
                    newContactSaveButton.setEnabled(true);
                    newContactSaveButton.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // intentionally kept empty
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newContactSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text = newContactText.getText().toString();
                // updates if existing, otherwise create new contact
                if (finalIsUpdate) {
                    db.updateContact(bundle.getInt("key"), text);
                } else {

                    Contact contact = new Contact();
                    contact.setFirstName(text);
                    contact.setStatus(0);
                    db.insertContact(contact);
                }
                //dismiss fragment and fragment dialog
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }



}
