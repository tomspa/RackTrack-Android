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
    private Context context;
    private final ArrayList<Contact> contacts = new ArrayList<>(
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
        ListView contactsList = contactsView.findViewById(R.id.contact_list);

        final ArrayAdapter<Contact> arrayAdapter =
                new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, this.contacts);

        contactsList.setAdapter(arrayAdapter);

        //Add listeners
        contactsList.setOnItemClickListener((adapterView, view, pos, id) -> this.openCallWindow(arrayAdapter.getItem(pos)));

        return contactsView;
    }

    private void openCallWindow(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context)
            .setTitle(R.string.call)
            .setMessage(getString(R.string.request_to_call) + contact.getFirstName() + " " + contact.getLastName() + "?")
            .setNegativeButton(R.string.decline_interface, ((dialogInterface, i) -> dialogInterface.cancel()))
            .setPositiveButton(R.string.accept_interface, ((dialogInterface, i) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(getString(R.string.uri_parse_prefix) + contact.getPhoneNumber()));
                startActivity(intent);
            }));

        builder.show();
    }
}
