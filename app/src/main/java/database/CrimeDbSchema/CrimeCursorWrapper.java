package database.CrimeDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.zwq.crimeactivity.Crime;

import java.util.Date;
import java.util.UUID;

import database.CrimeDbSchema.CrimeDbSchema.CrimeTable;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public Crime getCrime(){
        String uuidString=getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title=getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        long date=getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved=getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        //String phone = getString(getColumnIndex(CrimeTable.Cols.PHONE));

        Crime crime=new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved!=0);
        crime.setSuspect(suspect);
        //crime.setPhone(phone);
        return crime;
    }
}
