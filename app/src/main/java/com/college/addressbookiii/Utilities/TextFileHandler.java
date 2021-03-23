package com.college.addressbookiii.Utilities;
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
    public static final String DELIMITER = "~";
    private static ArrayList<ContactModel> addressBook = new ArrayList<>();
    //static Scanner sc = new Scanner(System.in);


    private static void createFile() {
        try {
            File myFile = new File("AddressBook.txt");
            if(myFile.createNewFile()) {
                System.out.println();
                System.out.println("Welcome to this address book program!");
                System.out.println("** New address book created for you **");
            } else {
                loadAddressBook();
                System.out.println();
                System.out.println("Welcome back to your address book!");
                //Toast.makeText(, "Saved your text", Toast.LENGTH_LONG).show();
            }
        } catch(IOException ioE) {
            System.out.println();
            System.out.println("Error creating file");
            System.out.println(ioE.getMessage());
        }
    }

    private void storeAddressBook(ArrayList<ContactModel> adBook) throws IOException {
        //Collections.sort(addressBook);
        FileWriter fw = new FileWriter("AddressBook.txt");
        PrintWriter pw = new PrintWriter(fw);
        pw.write("\n");
        for(ContactModel cont : adBook) {
            pw.write(cont.getFirstName() + DELIMITER);
            //pw.write(cont.getLastName() + DELIMITER);
            //pw.write(cont.getAddress() + DELIMITER);
            //pw.write(cont.getPhoneNumber() + "");
            pw.write("\n");
        }
        pw.close();
    }

    private static void loadAddressBook() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("AddressBook.txt"));
            String line = "";
            br.readLine();

            while((line = br.readLine()) != null) {
                String[] contactDetails = line.split(DELIMITER);
                if(contactDetails.length > 0) {
                    //ContactModel loadContact = new ContactModel(contactDetails[0], contactDetails[1], contactDetails[2], contactDetails[3]);
                    //addressBook.add(loadContact);
                }
            }
        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ioE) {
                System.out.println("Error occurred while closing the Buffered Reader");
                ioE.printStackTrace();
            }
        }
    }

}
