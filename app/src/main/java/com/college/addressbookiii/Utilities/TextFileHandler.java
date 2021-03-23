package com.college.addressbookiii.Utilities;

import com.college.addressbookiii.Objects.Contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class TextFileHandler {
    private ArrayList<Contact> contactList;
    private final String DELIMITER = ",";
    public void saveAddressBook() {
        Collections.sort(contactList);
        FileWriter fw;
        try {
            fw = new FileWriter("AddressBook.csv");
            PrintWriter pw = new PrintWriter(fw);
            pw.write("\n");
            for(Contact cont : contactList) {
                pw.write(cont.getFirstName() + DELIMITER);
                //pw.write(cont.getLastName() + DELIMITER);
               // pw.write(cont.getAddress() + DELIMITER);
                //pw.write(cont.getPhoneNumber() + "");
                pw.write("\n");
            }
            pw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
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
                String[] contactDetails = line.split(DELIMITER);
                if(contactDetails.length>0){
                    Contact loadContact = new Contact();
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
   public void insertContact(Contact contact){
        contactList.add(contact);
        saveAddressBook();

   }

    public void updateContact(int id, String newName){
        for(Contact c : contactList){
            if(c.getId() == id){
                c.setFirstName(newName);
                saveAddressBook();
                break;
            }
        }
    }
    public void deleteContact(int id){
        for(Contact c : contactList){
            if(c.getId() == id){
                int index = contactList.indexOf(c);
                contactList.remove(index);
                saveAddressBook();
                break;
            }
        }
    }
    public void updateStatus(int id, int status){
        for(Contact c : contactList){
            if(c.getId() == id){
                c.setStatus(status);
                saveAddressBook();
                break;
            }
        }
    }

    public ArrayList<Contact> getAllContacts(){
        return contactList;
    }
}
