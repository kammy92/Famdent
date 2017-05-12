package com.actiknow.famdent.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.actiknow.famdent.model.Exhibitor;
import com.actiknow.famdent.model.ExhibitorDetail;
import com.actiknow.famdent.model.StallDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "famdent";
    // Table Names
    private static final String TABLE_EXHIBITORS = "tbl_exhibitors";
    private static final String TABLE_EXHIBITION_PLAN = "tbl_exhibition_plan";
    // Exhibitors Table - column names
    private static final String EXHBTR_ID = "exhbtr_id";
    private static final String EXHBTR_NAME = "exhbtr_name";
    private static final String EXHBTR_ADDRESS = "exhbtr_address";
    private static final String EXHBTR_CONTACT_PERSON = "exhbtr_contact_person";
    private static final String EXHBTR_EMAIL = "exhbtr_email";
    private static final String EXHBTR_DESCRIPTION = "exhbtr_description";
    private static final String EXHBTR_WEBSITE = "exhbtr_website";
    private static final String EXHBTR_CONTACT1 = "exhbtr_contact1";
    private static final String EXHBTR_CONTACT2 = "exhbtr_contact2";
    private static final String EXHBTR_CONTACT3 = "exhbtr_contact3";
    private static final String EXHBTR_ACTIVE = "exhbtr_active";
    private static final String EXHBTR_CREATED_AT = "exhbtr_created_at";
    // Exhibition Plan Table - column names
    private static final String EXHBTN_PLN_ID = "exhbtn_pln_id";
    private static final String EXHBTN_PLN_EXHBTR_ID = "exhbtn_pln_exhbtr_id";
    private static final String EXHBTN_PLN_HALL_NUMBER = "exhbtn_pln_hall_number";
    private static final String EXHBTN_PLN_STALL_NUMBER = "exhbtn_pln_stall_number";
    private static final String EXHBTN_PLN_STALL_NAME = "exhbtn_pln_stall_name";
    // Question table Create Statements
    private static final String CREATE_TABLE_EXHIBITORS = "CREATE TABLE "
            + TABLE_EXHIBITORS + "(" +
            EXHBTR_ID + " INTEGER PRIMARY KEY," +
            EXHBTR_NAME + " TEXT," +
            EXHBTR_ADDRESS + " TEXT," +
            EXHBTR_CONTACT_PERSON + " TEXT," +
            EXHBTR_EMAIL + " TEXT," +
            EXHBTR_DESCRIPTION + " TEXT," +
            EXHBTR_WEBSITE + " TEXT," +
            EXHBTR_CONTACT1 + " TEXT," +
            EXHBTR_CONTACT2 + " TEXT," +
            EXHBTR_CONTACT3 + " TEXT," +
            EXHBTR_CREATED_AT + " DATETIME" + ")";
    // Question table Create Statements
    private static final String CREATE_TABLE_EXHIBITION_PLAN = "CREATE TABLE "
            + TABLE_EXHIBITION_PLAN + "(" +
            EXHBTN_PLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EXHBTN_PLN_EXHBTR_ID + " INTEGER," +
            EXHBTN_PLN_HALL_NUMBER + " TEXT," +
            EXHBTN_PLN_STALL_NUMBER + " TEXT," +
            EXHBTN_PLN_STALL_NAME + " TEXT" + ")";
    //Show database logs
    private boolean LOG_FLAG = false;

    public DatabaseHandler (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL (CREATE_TABLE_EXHIBITORS);
        db.execSQL (CREATE_TABLE_EXHIBITION_PLAN);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EXHIBITORS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EXHIBITION_PLAN);
        onCreate (db);
    }

    // ------------------------ "Exhibitors" table methods ----------------//

    public long createExhibitor (ExhibitorDetail exhibitorDetail) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Exhibitor", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (EXHBTR_ID, exhibitorDetail.getId ());
        values.put (EXHBTR_NAME, exhibitorDetail.getExhibitor_name ());
        values.put (EXHBTR_ADDRESS, exhibitorDetail.getAddress ());
        values.put (EXHBTR_CONTACT_PERSON, exhibitorDetail.getContact_person ());
        values.put (EXHBTR_EMAIL, exhibitorDetail.getEmail ());
        values.put (EXHBTR_DESCRIPTION, "");
        values.put (EXHBTR_WEBSITE, exhibitorDetail.getWebsite ());

        ArrayList<String> contactList = exhibitorDetail.getContactList ();
        switch (exhibitorDetail.getContactList ().size ()) {
            case 0:
                values.put (EXHBTR_CONTACT1, "");
                values.put (EXHBTR_CONTACT2, "");
                values.put (EXHBTR_CONTACT3, "");
                break;
            case 1:
                values.put (EXHBTR_CONTACT1, contactList.get (0));
                values.put (EXHBTR_CONTACT2, "");
                values.put (EXHBTR_CONTACT3, "");
                break;
            case 2:
                values.put (EXHBTR_CONTACT1, contactList.get (0));
                values.put (EXHBTR_CONTACT2, contactList.get (1));
                values.put (EXHBTR_CONTACT3, "");
                break;
            case 3:
                values.put (EXHBTR_CONTACT1, contactList.get (0));
                values.put (EXHBTR_CONTACT2, contactList.get (1));
                values.put (EXHBTR_CONTACT3, contactList.get (2));
                break;
        }

        values.put (EXHBTR_CREATED_AT, getDateTime ());
        long exhibitor_id = db.insert (TABLE_EXHIBITORS, null, values);
        return exhibitor_id;
    }

    public long createExhibitorStallDetail (StallDetail stallDetail, long exhibitor_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Stall Detail", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (EXHBTN_PLN_EXHBTR_ID, exhibitor_id);
        values.put (EXHBTN_PLN_HALL_NUMBER, stallDetail.getHall_number ());
        values.put (EXHBTN_PLN_STALL_NUMBER, stallDetail.getStall_number ());
        values.put (EXHBTN_PLN_STALL_NAME, stallDetail.getStall_name ());
        long exhibition_plan_id = db.insert (TABLE_EXHIBITION_PLAN, null, values);
        return exhibition_plan_id;
    }

    public ExhibitorDetail getExhibitorDetail (long exhibitor_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_EXHIBITORS + " WHERE " + EXHBTR_ID + " = " + exhibitor_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Exhibitor where ID = " + exhibitor_id, LOG_FLAG);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();

        ExhibitorDetail exhibitorDetail = new ExhibitorDetail (
                c.getInt (c.getColumnIndex (EXHBTR_ID)),
                false,
                "",
                c.getString (c.getColumnIndex (EXHBTR_NAME)),
                c.getString (c.getColumnIndex (EXHBTR_CONTACT_PERSON)),
                c.getString (c.getColumnIndex (EXHBTR_ADDRESS)),
                c.getString (c.getColumnIndex (EXHBTR_EMAIL)),
                c.getString (c.getColumnIndex (EXHBTR_WEBSITE)),
                ""
        );
        exhibitorDetail.setStallDetailList (getExhibitorStallDetails (exhibitor_id));
        ArrayList<String> contacts = new ArrayList<> ();
        if (c.getString (c.getColumnIndex (EXHBTR_CONTACT1)).length () > 0) {
            contacts.add (c.getString (c.getColumnIndex (EXHBTR_CONTACT1)));
        }
        if (c.getString (c.getColumnIndex (EXHBTR_CONTACT2)).length () > 0) {
            contacts.add (c.getString (c.getColumnIndex (EXHBTR_CONTACT2)));
        }
        if (c.getString (c.getColumnIndex (EXHBTR_CONTACT3)).length () > 0) {
            contacts.add (c.getString (c.getColumnIndex (EXHBTR_CONTACT3)));
        }
        exhibitorDetail.setContactList (contacts);
        return exhibitorDetail;
    }

    public ArrayList<StallDetail> getExhibitorStallDetails (long exhibitor_id) {
        ArrayList<StallDetail> stallDetailList = new ArrayList<StallDetail> ();
        String selectQuery = "SELECT * FROM " + TABLE_EXHIBITION_PLAN + " WHERE " + EXHBTN_PLN_EXHBTR_ID + " = " + exhibitor_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get exhibitorList", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    stallDetailList.add (new StallDetail (
                            c.getString (c.getColumnIndex (EXHBTN_PLN_STALL_NAME)),
                            c.getString (c.getColumnIndex (EXHBTN_PLN_HALL_NUMBER)),
                            c.getString (c.getColumnIndex (EXHBTN_PLN_STALL_NUMBER))
                    ));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return stallDetailList;
    }

    public ArrayList<Exhibitor> getAllExhibitorList () {
        ArrayList<Exhibitor> exhibitorList = new ArrayList<Exhibitor> ();
        String selectQuery = "SELECT  * FROM " + TABLE_EXHIBITORS;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Exhibitors", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                Exhibitor exhibitor = new Exhibitor (
                        c.getInt ((c.getColumnIndex (EXHBTR_ID))),
                        "",
                        c.getString ((c.getColumnIndex (EXHBTR_NAME)))
                );
                exhibitor.setStallDetailList (getExhibitorStallDetails (exhibitor.getId ()));
                exhibitorList.add (exhibitor);
            } while (c.moveToNext ());
        }
        return exhibitorList;
    }

    public void deleteAllExhibitors () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all exhibitors", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_EXHIBITORS);
    }

    public void deleteAllStallDetails () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all stall details", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_EXHIBITION_PLAN);
    }

    public void closeDB () {
        SQLiteDatabase db = this.getReadableDatabase ();
        if (db != null && db.isOpen ())
            db.close ();
    }

    private String getDateTime () {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", Locale.getDefault ());
        Date date = new Date ();
        return dateFormat.format (date);
    }
}