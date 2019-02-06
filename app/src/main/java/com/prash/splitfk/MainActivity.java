package com.prash.splitfk;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.prash.splitfk.data.Person;
import com.prash.splitfk.view.adapters.PersonListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TRANS_ACTIVITY_REQUEST_CODE = 1;
    private PersonListAdapter mPersonListAdapter;
    private PersonViewModel mPersonViewModel;
    private ViewModelFactory mViewModelFactory;
    private List<Person> mPersonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.trnsList);
        //final PersonListAdapter adapter = new PersonListAdapter(this);
        mPersonListAdapter = new PersonListAdapter(this);
        recyclerView.setAdapter(mPersonListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModelFactory = ViewModelFactory.getInstance(getApplication());
        mPersonViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PersonViewModel.class);
        mPersonViewModel.getPersonList().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> persons) {
                mPersonList = persons;
                mPersonListAdapter.setPersons(persons);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransactionsActivity.class);
                //intent.putExtra("list", (Serializable) mPersonList);
                startActivityForResult(intent, NEW_TRANS_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_TRANS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//           List<Person> personList = (List<Person>) (data.getSerializableExtra(TransactionsActivity.EXTRA_REPLY));
//           changeSet(personList);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.save_success,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void changeSet(List<Person> personList){
        mPersonListAdapter.setPersons(personList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModelFactory.destroy();
    }
}
