package com.example.racktrack.Contact;

import androidx.annotation.NonNull;

public class Contact {
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "☎ " + this.getFirstName() + " " + this.getLastName() + ": " + this.getPhoneNumber();
    }
}
