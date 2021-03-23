package com.college.addressbookiii.Utilities;
import android.content.Context;
import android.widget.Toast;

import com.college.addressbookiii.MainActivity;
import com.college.addressbookiii.Model.ContactModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextFileHandler {
    private ArrayList<ContactModel> contactList;
    private final String DELIMITTER = ",";
    private void saveAddressBook(){

    }
    public TextFileHandler(){
        
    }
    public void openAddressBook(){
        try{
            File file = new File("AddressBook.txt");
            if(!(file.createNewFile())){

            }else{
                loadAddressBook();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    

    public void loadAddressBook(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("AddressBook.txt"));
            String line = "";
            br.readLine();

            while((line = br.readLine()) != null){
                String[] contactDetails = line.split(DELIMITTER);
                if(contactDetails.length>0){
                    ContactModel loadContact = new ContactModel();
                    loadContact.setFirstName(contactDetails[0]);
                    loadContact.setStatus(0);
                    loadContact.setId(contactList.size());
                    contactList.add(loadContact);
                }
            }
        }catch (FileNotFoundException fnfe){
            System.out.println(fnfe);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
   public void insertContact(ContactModel contact){
        
   }

    public void updateContact(int id, String contact){

    }
    public void deleteContact(int id){

    }
    public void updateStatus(int id, int status){

    }

    public ArrayList<ContactModel> getAllContacts(){

        return null;
    }
}
