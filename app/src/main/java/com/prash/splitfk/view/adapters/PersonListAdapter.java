package com.prash.splitfk.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prash.splitfk.R;
import com.prash.splitfk.data.Person;

import java.util.List;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder> {

    private final LayoutInflater mInflater;
    private List<Person> mPersons;

    public PersonListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        if(mPersons != null) {
            Person current = mPersons.get(position);
            holder.personItemView.setText(current.getName());
            //double value = current.getTransaction();
            holder.transItemView.setText(Double.toString(current.getTransaction()));

        }
        else
            holder.personItemView.setText("No Transactions");
    }

    public void setPersons(List<Person> persons){
        mPersons = persons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mPersons != null)
            return mPersons.size();
        else
            return 0;
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        private final TextView personItemView;
        private final TextView transItemView;

        private PersonViewHolder(View itemView) {
            super(itemView);
            personItemView = itemView.findViewById(R.id.personName);
            transItemView = itemView.findViewById(R.id.personTrans);
        }
    }
}
