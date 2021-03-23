package com.college.addressbookiii;

import android.app.Activity;
import android.app.Dialog;
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
import android.

import androidx.core.content.ContextCompat;

import com.college.addressbookiii.Model.ContactModel;
import com.college.addressbookiii.Utilities.TextFileHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewContact extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newContactText;
    private Button newContactSaveButton;
    private TextFileHandler tf;

    public static AddNewContact newInstance() {
        return new AddNewContact();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_contact, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newContactText= getView().findViewById(R.id.newContactText);
        newContactSaveButton = getView().findViewById(R.id.newContactButton);

        tf = new TextFileHandler(getActivity());
        tf.loadAddressBook();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle!=null){
            isUpdate = true;
            String contact = bundle.getString("contact");
            newContactText.setText(contact);
            if(contact.length()>0){
                newContactSaveButton.setTextColor(ContextCompat.getColor(getContext(),
                        R.color.design_default_color_primary_dark));
            }
        }
        newContactText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newContactSaveButton.setEnabled(false);
                    newContactSaveButton.setTextColor(Color.TRANSPARENT);
                }else{
                    newContactSaveButton.setEnabled(true);
                    newContactSaveButton.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        newContactSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text = newContactText.getText().toString();
                if (finalIsUpdate) {
                    tf.updateContact(bundle.getInt("key"), text);
                } else {
                    ContactModel contact = new ContactModel();
                    contact.setFirstName(text);
                    contact.setStatus(0);
                }
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
