package com.example.newfinal;

public class Account {
    String _id;
    String _name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Account(String _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    public Account(){}

    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (o == null||!(o instanceof Account)) {
            return false;
        }

        Account rhs = (Account) o;

        // Compare the data members and return accordingly
        return get_id().equals(rhs.get_id());
    }
}

