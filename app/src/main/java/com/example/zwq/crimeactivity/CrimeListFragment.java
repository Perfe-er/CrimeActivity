package com.example.zwq.crimeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private static int mCrimeIndex;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView=(RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }

    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter==null){
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
        }else {
            //mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(mCrimeIndex);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView=(TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.crime_date);
        }

        @Override
        public void onClick(View view){
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            mCrimeIndex=getAdapterPosition();
            startActivity(intent);
        }

        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }


    }
    private class requirePoliceCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private Button mRequirePolice;

        public requirePoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView=(TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.crime_date);
            mRequirePolice=(Button)itemView.findViewById(R.id.require_police);

        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(),"Require Police",Toast.LENGTH_SHORT).show();
        }

        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        @Override
        public int getItemViewType(int position){
            if(mCrimes.get(position).isRequiresPolice()){
                return 1;
            }else{
                return 0;
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            if(i == 1){
                return new requirePoliceCrimeHolder(layoutInflater,viewGroup);
            }else{
                return new CrimeHolder(layoutInflater,viewGroup);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof CrimeHolder){
                Crime crime=mCrimes.get(position);
                ((CrimeHolder)holder).bind(crime);
            }else if (holder instanceof requirePoliceCrimeHolder){
                Crime crime=mCrimes.get(position);
                ((requirePoliceCrimeHolder)holder).bind(crime);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }



}
