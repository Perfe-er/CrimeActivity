package com.example.zwq.crimeactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button btn_first;
    private Button btn_last;

    private static final String EXTRA_CRIME_ID="com.example.zwq.crimeactivity.crime_id";

    public static Intent newIntent(Context packageContext,UUID crimeId){
        Intent intent=new Intent(packageContext,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }


    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_crime_pager);
        UUID crimeId=(UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);


        btn_first=(Button)findViewById(R.id.btn_to_first) ;
        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        btn_last=(Button)findViewById(R.id.btn_to_last);
        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size()-1);
            }
        });


        mViewPager=(ViewPager)findViewById(R.id.activity_crime_view_pager);


        mCrimes=CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Crime crime=mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }

        });

        for(int i=0; i < mCrimes.size();i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }
    @Override
    public void onCrimeUpdated(Crime crime){
    }

}
