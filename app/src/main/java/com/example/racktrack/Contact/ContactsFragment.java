package com.example.racktrack.Contact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.racktrack.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactsFragment extends Fragment {
    private FragmentActivity mainActivity;
    private ListView contactsList;
    private Context context;
    private ArrayList<Contact> contacts = new ArrayList<Contact>(
        Arrays.asList(
            new Contact("Kevin", "van Spreuwel", "0612344567"),
            new Contact("Bode", "Wijnands", "0612344567"),
            new Contact("Tom", "Spa", "0612344567")
        )
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = getActivity();
        this.context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contactsView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        this.contactsList = contactsView.findViewById(R.id.contact_list);

        final ArrayAdapter<Contact> arrayAdapter =
                new ArrayAdapter<Contact>(mainActivity, android.R.layout.simple_list_item_1, this.contacts);

        this.contactsList.setAdapter(arrayAdapter);

        //Add listeners
        this.contactsList.setOnItemClickListener((adapterView, view, pos, id) -> {
            this.openCallWindow(arrayAdapter.getItem(pos));
        });

        return contactsView;
    }

    private void openCallWindow(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context)
            .setTitle("Call")
            .setMessage("Would you like to call " + contact.getFirstName() + " " + contact.getLastName() + "?")
            .setNegativeButton("No", ((dialogInterface, i) -> dialogInterface.cancel()))
            .setPositiveButton("Yes", ((dialogInterface, i) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhoneNumber()));
                startActivity(intent);
            }));

        builder.show();
    }
}
