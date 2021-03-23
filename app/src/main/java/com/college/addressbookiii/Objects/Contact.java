package com.college.addressbookiii.Objects;

public class Contact implements Comparable<Contact> {
    private String firstName;
    private int status, id;
   // private String lastName;
   // private String address;
   // private String phoneNumber;

    public Contact(int id, int status, String firstName/*, String lastName, String address, String phoneNumber*/) {
        super();
        this.firstName = firstName;
        this.id = id;
        this.status=status;
        //this.lastName = lastName;
        //this.address = address;
        //this.phoneNumber = phoneNumber;
    }

    public Contact(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    */
    @Override
    public String toString() {
        return "Contact [firstName=" + firstName;// + ", lastName=" + lastName + ", address="
        // + address + ", phoneNumber="+ phoneNumber + "]\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Contact) {
            Contact otherBook = (Contact) obj;
            return firstName.equalsIgnoreCase(otherBook.firstName); //&&
                    //lastName.equalsIgnoreCase(otherBook.lastName) &&
                    //address.equalsIgnoreCase(otherBook.address) &&
                    //phoneNumber.equalsIgnoreCase(otherBook.phoneNumber);
        } else {
            return false;
        }
    }
    @Override
    public int compareTo(Contact obj) {
        Contact otherContact = (Contact) obj;
        /*if(firstName.equalsIgnoreCase(otherContact.firstName)) {
            return lastName.compareToIgnoreCase(otherContact.lastName);
        } else {
            return firstName.compareToIgnoreCase(otherContact.firstName);
        }*/
        if(firstName.equalsIgnoreCase(otherContact.firstName)){
            return String.valueOf(id).compareToIgnoreCase(String.valueOf(otherContact.getId()));
        }else {
            return firstName.compareToIgnoreCase(otherContact.firstName);
        }
    }




}