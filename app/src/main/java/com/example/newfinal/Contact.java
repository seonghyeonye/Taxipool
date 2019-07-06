package com.example.newfinal;

import com.google.gson.Gson;

public class Contact {
    String _id;
    String name;
    String phoneNumber;
    String email;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(String _id, String name, String phoneNumber, String email) {
        this._id = _id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (o == null||!(o instanceof Contact)) {
            return false;
        }

        Contact rhs = (Contact) o;

        // Compare the data members and return accordingly
        return get_id().equals(rhs.get_id());
    }

    public String toString (){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
