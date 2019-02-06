package com.prash.splitfk.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prash.splitfk.R;
import com.prash.splitfk.data.Person;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>{

    private final LayoutInflater mInflater;
    private List<Person> mPersons;

    public TransactionListAdapter(Context context) { mInflater = LayoutInflater.from(context); }


    class TransactionViewHolder extends RecyclerView.ViewHolder{
        private final TextView personName;
        private final EditText personShare;
        private final EditText personPaid;
        private final ImageButton removePerson;
        public TransactionViewHolder(View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);
            personShare = itemView.findViewById(R.id.personShared);
            personPaid = itemView.findViewById(R.id.personPaid);
            removePerson = itemView.findViewById(R.id.delete);
        }
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_item, parent, false);
        return new TransactionViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionViewHolder holder, final int position) {
        if(mPersons != null){
            final Person current = mPersons.get(position);
            holder.personName.setText(current.getName());

            holder.personPaid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }


                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(i2>0) {
                        double pay = Double.valueOf(charSequence.toString());
                        current.setPaid(pay);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            holder.personShare.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(i2>0) {
                        double share = Double.valueOf(charSequence.toString());
                        current.setShare(share);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            holder.removePerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPersons.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void setList(List<Person> persons){
        mPersons = persons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public int getItemCount() {
        if(mPersons == null)
            return 0;
        return mPersons.size();
    }


}
