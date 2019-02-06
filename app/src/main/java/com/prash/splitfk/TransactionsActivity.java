package com.prash.splitfk;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prash.splitfk.data.Person;
import com.prash.splitfk.view.adapters.TransactionListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsActivity extends AppCompatActivity {

    private Button addParticipants;
    private Button addFinal;
    private EditText transDetail;
    private EditText transAmount;
    private RecyclerView recyclerView;
    private TransactionListAdapter transactionListAdapter;
    private List<Person> transactionList;
    private ViewModelFactory mViewModelFactory;
    private PersonViewModel mPersonViewModel;
    private List<Person> availablePersons;
    private Map<String, Double> userMap;
    private static final int PICK_CONTACT = 1000;
    public static final String EXTRA_REPLY = "com.example.android.personlist.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        addParticipants = findViewById(R.id.button);
        addFinal = findViewById(R.id.button2);
        transDetail = findViewById(R.id.transDetail);
        transAmount = findViewById(R.id.transAmount);
        recyclerView = findViewById(R.id.transList);
        transactionListAdapter = new TransactionListAdapter(this);
        recyclerView.setAdapter(transactionListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionList = new ArrayList<>();
        mViewModelFactory = ViewModelFactory.getInstance(getApplication());
        mPersonViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PersonViewModel.class);
        userMap = new HashMap<>();
        mPersonViewModel.getPersonList().observe(this, (persons) -> {
            availablePersons = persons;
            for(Person person: availablePersons)
                userMap.put(person.getName(), person.getTransaction());
        });



        /**
         * This Api fetches a content resolver for contact details
         */
        addParticipants.setOnClickListener((view) -> {
            if(validEntry()) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
            else{
                Snackbar.make(recyclerView, "Please add the paid and share details for the last person", Snackbar.LENGTH_LONG);
            }
        });


        addFinal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(validEntry()){
                    Intent replyIntent = new Intent();
                    if(transactionList.isEmpty()){
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        setChanges();
                        setResult(RESULT_OK, replyIntent);
                    }
                    finish();
                }
                else{
                    Snackbar.make(recyclerView, "Please add the paid and share details for all the persons or remove the unwanted transactions", Snackbar.LENGTH_LONG);
                }
            }
        });
    }

    public boolean validEntry(){
        int size = transactionList.size();
        if(size > 0) {
            Person person = transactionList.get(size-1);
            if (person.getPaid() > 0 && person.getShare() > 0)
                return true;
            else
                return false;
        }
        return true;
    }

    public void setChanges() {
        double newTrans, prevTrans=0;
        for (Person person : transactionList) {
            if(userMap.containsKey(person.getName()))
                prevTrans = userMap.get(person.getName());
            newTrans = prevTrans + person.getPaid() - person.getShare();
            person.setTransaction(newTrans);
            mPersonViewModel.insertPerson(person);
        }

    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor phone = getContentResolver().query(contactData, null, null, null, null);
                    if (phone.moveToFirst()) {
                        String contactNumberName = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        addToList(contactNumberName);
                    }
                }
                break;
        }
    }

    public void addToList(String name){
        Person person = new Person(name);
        transactionList.add(person);
        transactionListAdapter.setList(transactionList);
    }
}
