package com.example.zwq.crimeactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeDbSchema.CrimeBaseHelper;
import database.CrimeDbSchema.CrimeCursorWrapper;
import database.CrimeDbSchema.CrimeDbSchema.CrimeTable;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static ContentValues getContentValues(Crime crime){
        ContentValues values=new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved()?1:0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        //values.put(CrimeTable.Cols.PHONE,crime.getPhone());
        return values;
    }

    public void addCrime(Crime c) {
        ContentValues values=getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public void updateCrime(Crime crime){
        String uuidString=crime.getId().toString();
        ContentValues values=getContentValues(crime);
        mDatabase.update(CrimeTable.NAME,values,
                CrimeTable.Cols.UUID+"=?",
                new String[]{uuidString});
    }


    public void removeCrime(Crime c){
        mDatabase.delete(
                CrimeTable.NAME,
                CrimeTable.Cols.UUID + " = ?",
                new String[] { c.getId().toString() }
        );

    }

    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
    public List<Crime> getCrimes() {
        List<Crime> crimes=new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }

        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor=queryCrimes(
                CrimeTable.Cols.UUID+"=?",
                new String[]{id.toString()}
        );
        try {
            if(cursor.getCount()==0){return null;}
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }


    }

}
