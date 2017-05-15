package com.actiknow.famdent.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.actiknow.famdent.model.Event;
import com.actiknow.famdent.model.EventDetail;
import com.actiknow.famdent.model.EventSpeaker;
import com.actiknow.famdent.model.Exhibitor;
import com.actiknow.famdent.model.ExhibitorDetail;
import com.actiknow.famdent.model.Favourite;
import com.actiknow.famdent.model.Note;
import com.actiknow.famdent.model.Session;
import com.actiknow.famdent.model.SessionDetail;
import com.actiknow.famdent.model.SessionSpeaker;
import com.actiknow.famdent.model.StallDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 5;
    // Database Name
    private static final String DATABASE_NAME = "famdent";

    // Table Names
    private static final String TABLE_EXHIBITORS = "tbl_exhibitors";
    private static final String TABLE_EXHIBITION_PLAN = "tbl_exhibition_plan";

    private static final String TABLE_EVENTS = "tbl_events";
    private static final String TABLE_EVENT_TOPICS = "tbl_event_topics";
    private static final String TABLE_EVENT_SPEAKERS = "tbl_event_speakers";

    private static final String TABLE_SESSIONS = "tbl_sessions";
    private static final String TABLE_SESSION_TOPICS = "tbl_session_topics";
    private static final String TABLE_SESSION_SPEAKERS = "tbl_session_speakers";

    private static final String TABLE_FAVOURITE = "tbl_favourites";

    private static final String TABLE_NOTES = "tbl_notes";


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
    private static final String EXHBTR_CREATED_AT = "exhbtr_created_at";
    // Exhibition Plan Table - column names
    private static final String EXHBTN_PLN_ID = "exhbtn_pln_id";
    private static final String EXHBTN_PLN_EXHBTR_ID = "exhbtn_pln_exhbtr_id";
    private static final String EXHBTN_PLN_HALL_NUMBER = "exhbtn_pln_hall_number";
    private static final String EXHBTN_PLN_STALL_NUMBER = "exhbtn_pln_stall_number";
    private static final String EXHBTN_PLN_STALL_NAME = "exhbtn_pln_stall_name";

    // Events Table - column names
    private static final String EVNT_ID = "envt_id";
    private static final String EVNT_NAME = "envt_name";
    private static final String EVNT_DATE = "envt_date";
    private static final String EVNT_TIME = "envt_time";
    private static final String EVNT_DURATION = "envt_duration";
    private static final String EVNT_LOCATION = "envt_location";
    private static final String EVNT_FEES = "envt_fees";
    private static final String EVNT_NOTES = "envt_notes";
    private static final String EVNT_CREATED_AT = "envt_created_at";

    // Event Topics Table - column names
    private static final String EVNT_TOPC_ID = "envt_topc_id";
    private static final String EVNT_TOPC_EVNT_ID = "envt_topc_evnt_id";
    private static final String EVNT_TOPC_TEXT = "envt_topc_text";

    // Event Topics Table - column names
    private static final String EVNT_SPKR_ID = "envt_spkr_id";
    private static final String EVNT_SPKR_EVNT_ID = "envt_spkr_evnt_id";
    private static final String EVNT_SPKR_IMAGE = "envt_spkr_image";
    private static final String EVNT_SPKR_NAME = "envt_spkr_name";
    private static final String EVNT_SPKR_QUALIFICATION = "envt_spkr_qualification";
    private static final String EVNT_SPKR_EXPERIENCE = "envt_spkr_experience";

    // Session Table - column names
    private static final String SSION_ID = "ssion_id";
    private static final String SSION_TITLE = "ssion_title";
    private static final String SSION_DATE = "ssion_date";
    private static final String SSION_TIME = "ssion_time";
    private static final String SSION_LOCATION = "ssion_location";
    private static final String SSION_CATEGORY = "ssion_category";
    private static final String SSION_CREATED_AT = "ssion_created_at";

    // Event Topics Table - column names
    private static final String SSION_TOPC_ID = "ssion_topc_id";
    private static final String SSION_TOPC_SSION_ID = "ssion_topc_ssion_id";
    private static final String SSION_TOPC_TEXT = "ssion_topc_text";

    // Event Topics Table - column names
    private static final String SSION_SPKR_ID = "ssion_spkr_id";
    private static final String SSION_SPKR_SSION_ID = "ssion_spkr_ssion_id";
    private static final String SSION_SPKR_NAME = "ssion_spkr_name";
    private static final String SSION_SPKR_IMAGE = "ssion_spkr_image";


    // Favourites Table - column names
    private static final String FAV_ID = "fav_id";
    private static final String FAV_EVNT_ID = "fav_evnt_id";
    private static final String FAV_EXHBTR_ID = "fav_exhbtr_id";
    private static final String FAV_SSION_ID = "fav_ssion_id";
    private static final String FAV_TYPE = "fav_type";
    private static final String FAV_CREATED_AT = "fav_created_at";

    private static final String FAV_TYPE_EXHIBITOR = "EXHIBITOR";
    private static final String FAV_TYPE_EVENT = "EVENT";
    private static final String FAV_TYPE_SESSION = "SESSION";


    // Notes Table - column names
    private static final String NOTS_ID = "nots_id";
    private static final String NOTS_EVNT_ID = "nots_evnt_id";
    private static final String NOTS_EXHBTR_ID = "nots_exhbtr_id";
    private static final String NOTS_SSION_ID = "nots_ssion_id";
    private static final String NOTS_TYPE = "nots_type";
    private static final String NOTS_TEXT = "nots_text";
    private static final String NOTS_UPDATED_AT = "nots_updated_at";
    private static final String NOTS_CREATED_AT = "nots_created_at";

    private static final String NOTS_TYPE_EXHIBITOR = "EXHIBITOR";
    private static final String NOTS_TYPE_EVENT = "EVENT";
    private static final String NOTS_TYPE_SESSION = "SESSION";


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

    // Event table Create Statements
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE "
            + TABLE_EVENTS + "(" +
            EVNT_ID + " INTEGER PRIMARY KEY," +
            EVNT_NAME + " TEXT," +
            EVNT_DATE + " DATE," +
            EVNT_TIME + " TIME," +
            EVNT_DURATION + " TEXT," +
            EVNT_LOCATION + " TEXT," +
            EVNT_FEES + " TEXT," +
            EVNT_NOTES + " TEXT," +
            EVNT_CREATED_AT + " DATETIME" + ")";

    // Event Topic table Create Statements
    private static final String CREATE_TABLE_EVENT_TOPICS = "CREATE TABLE "
            + TABLE_EVENT_TOPICS + "(" +
            EVNT_TOPC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EVNT_TOPC_EVNT_ID + " INTEGER," +
            EVNT_TOPC_TEXT + " TEXT" + ")";

    // Event Speaker table Create Statements
    private static final String CREATE_TABLE_EVENT_SPEAKERS = "CREATE TABLE "
            + TABLE_EVENT_SPEAKERS + "(" +
            EVNT_SPKR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EVNT_SPKR_EVNT_ID + " INTEGER," +
            EVNT_SPKR_IMAGE + " TEXT," +
            EVNT_SPKR_NAME + " TEXT," +
            EVNT_SPKR_QUALIFICATION + " TEXT," +
            EVNT_SPKR_EXPERIENCE + " TEXT" + ")";


    // Event table Create Statements
    private static final String CREATE_TABLE_SESSIONS = "CREATE TABLE "
            + TABLE_SESSIONS + "(" +
            SSION_ID + " INTEGER PRIMARY KEY," +
            SSION_TITLE + " TEXT," +
            SSION_DATE + " DATE," +
            SSION_TIME + " TIME," +
            SSION_LOCATION + " TEXT," +
            SSION_CATEGORY + " TEXT," +
            SSION_CREATED_AT + " DATETIME" + ")";

    // Event Topic table Create Statements
    private static final String CREATE_TABLE_SESSION_TOPICS = "CREATE TABLE "
            + TABLE_SESSION_TOPICS + "(" +
            SSION_TOPC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SSION_TOPC_SSION_ID + " INTEGER," +
            SSION_TOPC_TEXT + " TEXT" + ")";

    // Event Speaker table Create Statements
    private static final String CREATE_TABLE_SESSION_SPEAKERS = "CREATE TABLE "
            + TABLE_SESSION_SPEAKERS + "(" +
            SSION_SPKR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SSION_SPKR_SSION_ID + " INTEGER," +
            SSION_SPKR_NAME + " TEXT," +
            SSION_SPKR_IMAGE + " TEXT" + ")";


    // Favourites table Create Statements
    private static final String CREATE_TABLE_FAVOURITES = "CREATE TABLE "
            + TABLE_FAVOURITE + "(" +
            FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FAV_EVNT_ID + " INTEGER DEFAULT NULL," +
            FAV_EXHBTR_ID + " INTEGER DEFAULT NULL," +
            FAV_SSION_ID + " INTEGER DEFAULT NULL," +
            FAV_TYPE + " TEXT," +
            FAV_CREATED_AT + " DATETIME" + ")";

    // Notes table Create Statements
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
            + TABLE_NOTES + "(" +
            NOTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOTS_EVNT_ID + " INTEGER DEFAULT NULL," +
            NOTS_EXHBTR_ID + " INTEGER DEFAULT NULL," +
            NOTS_SSION_ID + " INTEGER DEFAULT NULL," +
            NOTS_TYPE + " TEXT," +
            NOTS_TEXT + " TEXT," +
            NOTS_UPDATED_AT + " DATETIME," +
            NOTS_CREATED_AT + " DATETIME" + ")";


    private boolean LOG_FLAG = true;

    public DatabaseHandler (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL (CREATE_TABLE_EXHIBITORS);
        db.execSQL (CREATE_TABLE_EXHIBITION_PLAN);
        db.execSQL (CREATE_TABLE_EVENTS);
        db.execSQL (CREATE_TABLE_EVENT_SPEAKERS);
        db.execSQL (CREATE_TABLE_EVENT_TOPICS);
        db.execSQL (CREATE_TABLE_SESSIONS);
        db.execSQL (CREATE_TABLE_SESSION_SPEAKERS);
        db.execSQL (CREATE_TABLE_SESSION_TOPICS);
        db.execSQL (CREATE_TABLE_FAVOURITES);
        db.execSQL (CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EXHIBITORS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EXHIBITION_PLAN);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EVENT_SPEAKERS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_EVENT_TOPICS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_SESSION_SPEAKERS);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_SESSION_TOPICS);
//        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
//        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_NOTES);
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
        values.put (EXHBTR_DESCRIPTION, exhibitorDetail.getExhibitor_description ());
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
                isExhibitorFavourite (c.getInt (c.getColumnIndex (EXHBTR_ID))),
                "",
                c.getString (c.getColumnIndex (EXHBTR_NAME)),
                c.getString (c.getColumnIndex (EXHBTR_DESCRIPTION)),
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
        String selectQuery = "SELECT  * FROM " + TABLE_EXHIBITORS + " ORDER BY " + EXHBTR_DESCRIPTION + " ASC";
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Exhibitors", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                Exhibitor exhibitor = new Exhibitor (
                        c.getInt ((c.getColumnIndex (EXHBTR_ID))),
                        "",
                        c.getString ((c.getColumnIndex (EXHBTR_NAME))),
                        c.getString ((c.getColumnIndex (EXHBTR_DESCRIPTION)))
                );
                exhibitor.setStallDetailList (getExhibitorStallDetails (exhibitor.getId ()));
                exhibitorList.add (exhibitor);
            } while (c.moveToNext ());
        }
        return exhibitorList;
    }

    public ArrayList<Exhibitor> getAllFavouriteExhibitors () {
        ArrayList<Favourite> favouriteList = getAllFavourites ();
        ArrayList<Exhibitor> exhibitorList = new ArrayList<Exhibitor> ();
        for (int i = 0; i < favouriteList.size (); i++) {
            Favourite favourite = favouriteList.get (i);
            if (favourite.getType ().equalsIgnoreCase (FAV_TYPE_EXHIBITOR)) {
                String selectQuery = "SELECT  * FROM " + TABLE_EXHIBITORS + " WHERE " + EXHBTR_ID + " = " + favourite.getExhibitor_id ();
                Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Exhibitors", false);
                SQLiteDatabase db = this.getReadableDatabase ();
                Cursor c = db.rawQuery (selectQuery, null);
                // looping through all rows and adding to list
                if (c.moveToFirst ()) {
                    do {
                        Exhibitor exhibitor = new Exhibitor (
                                c.getInt ((c.getColumnIndex (EXHBTR_ID))),
                                "",
                                c.getString ((c.getColumnIndex (EXHBTR_NAME))),
                                c.getString ((c.getColumnIndex (EXHBTR_DESCRIPTION)))
                        );
                        exhibitor.setStallDetailList (getExhibitorStallDetails (exhibitor.getId ()));
                        exhibitorList.add (exhibitor);
                    } while (c.moveToNext ());
                }
            }
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

    // ------------------------ "Events" table methods ----------------//

    public long createEvent (EventDetail eventDetail) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Event", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (EVNT_ID, eventDetail.getId ());
        values.put (EVNT_NAME, eventDetail.getName ());
        values.put (EVNT_DATE, eventDetail.getDate ());
        values.put (EVNT_TIME, eventDetail.getTime ());
        values.put (EVNT_DURATION, eventDetail.getDuration ());
        values.put (EVNT_LOCATION, eventDetail.getLocation ());
        values.put (EVNT_FEES, eventDetail.getFees ());
        values.put (EVNT_NOTES, eventDetail.getNotes ());
        values.put (EVNT_CREATED_AT, getDateTime ());
        long exhibitor_id = db.insert (TABLE_EVENTS, null, values);
        return exhibitor_id;
    }

    public long createEventSpeaker (EventSpeaker eventSpeaker, long event_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating event speaker", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (EVNT_SPKR_EVNT_ID, event_id);
        values.put (EVNT_SPKR_IMAGE, eventSpeaker.getImage ());
        values.put (EVNT_SPKR_NAME, eventSpeaker.getName ());
        values.put (EVNT_SPKR_QUALIFICATION, eventSpeaker.getQualification ());
        values.put (EVNT_SPKR_EXPERIENCE, eventSpeaker.getExperience ());
        long event_speaker_id = db.insert (TABLE_EVENT_SPEAKERS, null, values);
        return event_speaker_id;
    }

    public long createEventTopic (String topic, long event_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating event topic", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (EVNT_TOPC_EVNT_ID, event_id);
        values.put (EVNT_TOPC_TEXT, topic);
        long event_topic_id = db.insert (TABLE_EVENT_TOPICS, null, values);
        return event_topic_id;
    }

    public EventDetail getEventDetail (long event_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS + " WHERE " + EVNT_ID + " = " + event_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Event where ID = " + event_id, LOG_FLAG);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();

        EventDetail eventDetail = new EventDetail (
                c.getInt (c.getColumnIndex (EVNT_ID)),
                isEventFavourite (c.getInt (c.getColumnIndex (EVNT_ID))),
                c.getString (c.getColumnIndex (EVNT_NAME)),
                c.getString (c.getColumnIndex (EVNT_DATE)),
                c.getString (c.getColumnIndex (EVNT_TIME)),
                c.getString (c.getColumnIndex (EVNT_DURATION)),
                c.getString (c.getColumnIndex (EVNT_LOCATION)),
                c.getString (c.getColumnIndex (EVNT_FEES)),
                c.getString (c.getColumnIndex (EVNT_NOTES))
        );

        eventDetail.setTopicList (getAllEventTopics (c.getInt (c.getColumnIndex (EVNT_ID))));
        eventDetail.setEventSpeakerList (getAllEventSpeakers (c.getInt (c.getColumnIndex (EVNT_ID))));

        return eventDetail;
    }

    public ArrayList<EventSpeaker> getAllEventSpeakers (long event_id) {
        ArrayList<EventSpeaker> eventSpeakerList = new ArrayList<EventSpeaker> ();
        String selectQuery = "SELECT * FROM " + TABLE_EVENT_SPEAKERS + " WHERE " + EVNT_SPKR_EVNT_ID + " = " + event_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get event speaker List", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    eventSpeakerList.add (new EventSpeaker (
                            0,
                            c.getString (c.getColumnIndex (EVNT_SPKR_IMAGE)),
                            c.getString (c.getColumnIndex (EVNT_SPKR_NAME)),
                            c.getString (c.getColumnIndex (EVNT_SPKR_QUALIFICATION)),
                            c.getString (c.getColumnIndex (EVNT_SPKR_EXPERIENCE))
                    ));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return eventSpeakerList;
    }

    public ArrayList<String> getAllEventTopics (long event_id) {
        ArrayList<String> eventTopicList = new ArrayList<> ();
        String selectQuery = "SELECT * FROM " + TABLE_EVENT_TOPICS + " WHERE " + EVNT_TOPC_EVNT_ID + " = " + event_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get event topic List", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    eventTopicList.add (c.getString (c.getColumnIndex (EVNT_TOPC_TEXT)));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return eventTopicList;
    }

    public ArrayList<Event> getAllEventList () {
        ArrayList<Event> eventList = new ArrayList<Event> ();
        String selectQuery = "SELECT *, (SELECT GROUP_CONCAT(" + EVNT_SPKR_NAME + ") FROM " + TABLE_EVENT_SPEAKERS + " WHERE " + EVNT_SPKR_EVNT_ID + " = " + EVNT_ID + ") as `evnt_speakers`  FROM " + TABLE_EVENTS;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Events", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                eventList.add (new Event (
                        c.getInt ((c.getColumnIndex (EVNT_ID))),
                        c.getString ((c.getColumnIndex (EVNT_NAME))),
                        c.getString ((c.getColumnIndex ("evnt_speakers"))),
                        c.getString ((c.getColumnIndex (EVNT_DATE))),
                        c.getString ((c.getColumnIndex (EVNT_TIME)))
                ));

            } while (c.moveToNext ());
        }
        return eventList;
    }

    public ArrayList<Event> getAllFavouriteEvent () {
        ArrayList<Favourite> favouriteList = getAllFavourites ();
        ArrayList<Event> eventList = new ArrayList<Event> ();
        for (int i = 0; i < favouriteList.size (); i++) {
            Favourite favourite = favouriteList.get (i);
            if (favourite.getType ().equalsIgnoreCase (FAV_TYPE_EVENT)) {
                String selectQuery = "SELECT *, (SELECT GROUP_CONCAT(" + EVNT_SPKR_NAME + ") FROM " + TABLE_EVENT_SPEAKERS + " WHERE " + EVNT_SPKR_EVNT_ID + " = " + EVNT_ID + ") as `evnt_speakers`  FROM " + TABLE_EVENTS + " WHERE " + EVNT_ID + " = " + favourite.getEvent_id ();
                Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Events", false);
                SQLiteDatabase db = this.getReadableDatabase ();
                Cursor c = db.rawQuery (selectQuery, null);
                // looping through all rows and adding to list
                if (c.moveToFirst ()) {
                    do {
                        eventList.add (new Event (
                                c.getInt ((c.getColumnIndex (EVNT_ID))),
                                c.getString ((c.getColumnIndex (EVNT_NAME))),
                                c.getString ((c.getColumnIndex ("evnt_speakers"))),
                                c.getString ((c.getColumnIndex (EVNT_DATE))),
                                c.getString ((c.getColumnIndex (EVNT_TIME)))
                        ));

                    } while (c.moveToNext ());
                }
            }
        }
        return eventList;
    }

    public void deleteAllEvents () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all events", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_EVENTS);
    }

    public void deleteAllEventTopics () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all event topic details", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_EVENT_TOPICS);
    }

    public void deleteAllEventSpeakers () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all event speaker details", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_EVENT_SPEAKERS);
    }


    // ------------------------ "Sessions" table methods ----------------//

    public long createSession (SessionDetail sessionDetail) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Session", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (SSION_ID, sessionDetail.getId ());
        values.put (SSION_TITLE, sessionDetail.getTitle ());
        values.put (SSION_DATE, sessionDetail.getDate ());
        values.put (SSION_TIME, sessionDetail.getTime ());
        values.put (SSION_LOCATION, sessionDetail.getLocation ());
        values.put (SSION_CATEGORY, sessionDetail.getCategory ());
        values.put (SSION_CREATED_AT, getDateTime ());
        long session_id = db.insert (TABLE_SESSIONS, null, values);
        return session_id;
    }

    public long createSessionSpeaker (SessionSpeaker sessionSpeaker, long session_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating session speaker", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (SSION_SPKR_SSION_ID, session_id);
        values.put (SSION_SPKR_NAME, sessionSpeaker.getName ());
        values.put (SSION_SPKR_IMAGE, sessionSpeaker.getImage ());
        long session_speaker_id = db.insert (TABLE_SESSION_SPEAKERS, null, values);
        return session_speaker_id;
    }

    public long createSessionTopic (String topic, long session_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating session topic", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (SSION_TOPC_SSION_ID, session_id);
        values.put (SSION_TOPC_TEXT, topic);
        long session_topic_id = db.insert (TABLE_SESSION_TOPICS, null, values);
        return session_topic_id;
    }

    public SessionDetail getSessionDetail (long session_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_SESSIONS + " WHERE " + SSION_ID + " = " + session_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get session where ID = " + session_id, LOG_FLAG);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();

        SessionDetail sessionDetail = new SessionDetail (
                c.getInt (c.getColumnIndex (SSION_ID)),
                isSessionFavourite (c.getInt (c.getColumnIndex (SSION_ID))),
                c.getString (c.getColumnIndex (SSION_TITLE)),
                c.getString (c.getColumnIndex (SSION_DATE)),
                c.getString (c.getColumnIndex (SSION_TIME)),
                c.getString (c.getColumnIndex (SSION_LOCATION)),
                c.getString (c.getColumnIndex (SSION_CATEGORY))
        );

        sessionDetail.setTopicList (getAllSessionTopics (c.getInt (c.getColumnIndex (SSION_ID))));
        sessionDetail.setSessionSpeakerList (getAllSessionSpeakers (c.getInt (c.getColumnIndex (SSION_ID))));

        return sessionDetail;
    }

    public ArrayList<SessionSpeaker> getAllSessionSpeakers (long session_id) {
        ArrayList<SessionSpeaker> sessionSpeakerList = new ArrayList<SessionSpeaker> ();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_SPEAKERS + " WHERE " + SSION_SPKR_SSION_ID + " = " + session_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get session speaker List", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    sessionSpeakerList.add (new SessionSpeaker (
                            c.getInt (c.getColumnIndex (SSION_SPKR_ID)),
                            c.getString (c.getColumnIndex (SSION_SPKR_IMAGE)),
                            c.getString (c.getColumnIndex (SSION_SPKR_NAME))
                    ));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return sessionSpeakerList;
    }

    public ArrayList<String> getAllSessionTopics (long session_id) {
        ArrayList<String> sessionTopicList = new ArrayList<> ();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_TOPICS + " WHERE " + SSION_TOPC_SSION_ID + " = " + session_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get session topic List", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    sessionTopicList.add (c.getString (c.getColumnIndex (SSION_TOPC_TEXT)));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return sessionTopicList;
    }

    public ArrayList<Session> getAllSessionList () {
        ArrayList<Session> sessionList = new ArrayList<Session> ();
        String selectQuery = "SELECT *, (SELECT GROUP_CONCAT(" + SSION_SPKR_NAME + ") FROM " + TABLE_SESSION_SPEAKERS + " WHERE " + SSION_SPKR_SSION_ID + " = " + SSION_ID + ") as `ssion_speakers`  FROM " + TABLE_SESSIONS;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Sessions", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                sessionList.add (new Session (
                        c.getInt ((c.getColumnIndex (SSION_ID))),
                        c.getString ((c.getColumnIndex (SSION_TITLE))),
                        c.getString ((c.getColumnIndex ("ssion_speakers"))),
                        c.getString ((c.getColumnIndex (SSION_DATE))),
                        c.getString ((c.getColumnIndex (SSION_TIME))),
                        c.getString ((c.getColumnIndex (SSION_LOCATION))),
                        c.getString ((c.getColumnIndex (SSION_CATEGORY)))
                ));

            } while (c.moveToNext ());
        }
        return sessionList;
    }

    public ArrayList<Session> getAllFavouriteSessions () {
        ArrayList<Favourite> favouriteList = getAllFavourites ();
        ArrayList<Session> sessionList = new ArrayList<Session> ();
        for (int i = 0; i < favouriteList.size (); i++) {
            Favourite favourite = favouriteList.get (i);
            if (favourite.getType ().equalsIgnoreCase (FAV_TYPE_SESSION)) {
                String selectQuery = "SELECT *, (SELECT GROUP_CONCAT(" + SSION_SPKR_NAME + ") FROM " + TABLE_SESSION_SPEAKERS + " WHERE " + SSION_SPKR_SSION_ID + " = " + SSION_ID + ") as `ssion_speakers`  FROM " + TABLE_SESSIONS + " WHERE " + SSION_ID + " = " + favourite.getSession_id ();
                Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Sessions", false);
                SQLiteDatabase db = this.getReadableDatabase ();
                Cursor c = db.rawQuery (selectQuery, null);
                // looping through all rows and adding to list
                if (c.moveToFirst ()) {
                    do {
                        sessionList.add (new Session (
                                c.getInt ((c.getColumnIndex (SSION_ID))),
                                c.getString ((c.getColumnIndex (SSION_TITLE))),
                                c.getString ((c.getColumnIndex ("ssion_speakers"))),
                                c.getString ((c.getColumnIndex (SSION_DATE))),
                                c.getString ((c.getColumnIndex (SSION_TIME))),
                                c.getString ((c.getColumnIndex (SSION_LOCATION))),
                                c.getString ((c.getColumnIndex (SSION_CATEGORY)))
                        ));
                    } while (c.moveToNext ());
                }
            }
        }
        return sessionList;
    }

    public void deleteAllSessions () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all sessions", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_SESSIONS);
    }

    public void deleteAllSessionTopics () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all session topic details", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_SESSION_TOPICS);
    }

    public void deleteAllSessionSpeakers () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all session speaker details", LOG_FLAG);
        db.execSQL ("delete from " + TABLE_SESSION_SPEAKERS);
    }


    public long addExhibitorToFavourite (int exhibitor_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Adding Exhibitor to favourite", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (FAV_EXHBTR_ID, exhibitor_id);
        values.put (FAV_TYPE, FAV_TYPE_EXHIBITOR);
        values.put (FAV_CREATED_AT, getDateTime ());
        long fav_id = db.insert (TABLE_FAVOURITE, null, values);
        return fav_id;
    }

    public long addEventToFavourite (int event_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Adding Event to favourite", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (FAV_EVNT_ID, event_id);
        values.put (FAV_TYPE, FAV_TYPE_EVENT);
        values.put (FAV_CREATED_AT, getDateTime ());
        long fav_id = db.insert (TABLE_FAVOURITE, null, values);
        return fav_id;
    }

    public long addSessionToFavourite (int session_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Adding Session to favourite", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (FAV_SSION_ID, session_id);
        values.put (FAV_TYPE, FAV_TYPE_SESSION);
        values.put (FAV_CREATED_AT, getDateTime ());
        long fav_id = db.insert (TABLE_FAVOURITE, null, values);
        return fav_id;
    }

    public void removeExhibitorFromFavourite (int exhibitor_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete exhibitor from Favorite where Exhibitor ID = " + exhibitor_id, LOG_FLAG);
        db.delete (TABLE_FAVOURITE, FAV_EXHBTR_ID + " = ? AND " + FAV_TYPE + " = ?", new String[] {String.valueOf (exhibitor_id), FAV_TYPE_EXHIBITOR});
    }

    public void removeEventFromFavourite (long event_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete event from Favorite where Event ID = " + event_id, LOG_FLAG);
        db.delete (TABLE_FAVOURITE, FAV_EVNT_ID + " = ? AND " + FAV_TYPE + " = ?", new String[] {String.valueOf (event_id), FAV_TYPE_EVENT});
    }

    public void removeSessionFromFavourite (long session_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete session from Favorite where Session ID = " + session_id, LOG_FLAG);
        db.delete (TABLE_FAVOURITE, FAV_SSION_ID + " = ? AND " + FAV_TYPE + " = ?", new String[] {String.valueOf (session_id), FAV_TYPE_SESSION});
    }

    public boolean isExhibitorFavourite (int exhibitor_id) {
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITE + " WHERE " + FAV_EXHBTR_ID + " = " + exhibitor_id + " AND " + FAV_TYPE + " = '" + FAV_TYPE_EXHIBITOR + "'";
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEventFavourite (int event_id) {
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITE + " WHERE " + FAV_EVNT_ID + " = " + event_id + " AND " + FAV_TYPE + " = '" + FAV_TYPE_EVENT + "'";
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSessionFavourite (int session_id) {
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITE + " WHERE " + FAV_SSION_ID + " = " + session_id + " AND " + FAV_TYPE + " = '" + FAV_TYPE_SESSION + "'";
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Favourite> getAllFavourites () {
        ArrayList<Favourite> favouriteList = new ArrayList<Favourite> ();
        String selectQuery = "SELECT * FROM " + TABLE_FAVOURITE;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get favourite List", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    favouriteList.add (new Favourite (
                            c.getInt (c.getColumnIndex (FAV_ID)),
                            c.getInt (c.getColumnIndex (FAV_SSION_ID)),
                            c.getInt (c.getColumnIndex (FAV_EVNT_ID)),
                            c.getInt (c.getColumnIndex (FAV_EXHBTR_ID)),
                            c.getString (c.getColumnIndex (FAV_TYPE))
                    ));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return favouriteList;
    }


    public long addNoteToExhibitor (int exhibitor_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Adding Note to exhibitor", LOG_FLAG);
        ContentValues values = new ContentValues ();
        values.put (NOTS_EXHBTR_ID, exhibitor_id);
        values.put (NOTS_TYPE, NOTS_TYPE_EXHIBITOR);
        values.put (NOTS_CREATED_AT, getDateTime ());
        long nots_id = db.insert (TABLE_NOTES, null, values);
        return nots_id;
    }

    public void removeNoteFromExhibitor (int exhibitor_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete note from exhibitor where Exhibitor ID = " + exhibitor_id, LOG_FLAG);
        db.delete (TABLE_NOTES, NOTS_EXHBTR_ID + " = ? AND " + NOTS_TYPE + " = ?", new String[] {String.valueOf (exhibitor_id), NOTS_TYPE_EXHIBITOR});
    }

    public boolean isNoteInExhibitor (int exhibitor_id) {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + NOTS_EXHBTR_ID + " = " + exhibitor_id + " AND " + NOTS_TYPE + " = '" + NOTS_TYPE_EXHIBITOR + "'";
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int updateNoteInExhibitor (String text, int exhibitor_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Update not in exhibitor", false);
        ContentValues values = new ContentValues ();
        values.put (NOTS_TEXT, text);
        return db.update (TABLE_NOTES, values, NOTS_EXHBTR_ID + " = ? AND " + NOTS_TYPE + " = ?", new String[] {String.valueOf (exhibitor_id), NOTS_TYPE_EXHIBITOR});
    }

    public String getExhibitorNote (int exhibitor_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + NOTS_EXHBTR_ID + " = " + exhibitor_id + " AND " + NOTS_TYPE + " = " + NOTS_TYPE_EXHIBITOR;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get note where exhibitor ID = " + exhibitor_id, LOG_FLAG);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();

        return c.getString (c.getColumnIndex (NOTS_TEXT));
    }

    public ArrayList<Note> getAllNotes () {
        ArrayList<Note> noteList = new ArrayList<Note> ();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Notes List", LOG_FLAG);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        if (c.moveToFirst ()) {
            do {
                try {
                    noteList.add (new Note (
                            c.getInt (c.getColumnIndex (NOTS_ID)),
                            c.getInt (c.getColumnIndex (NOTS_SSION_ID)),
                            c.getInt (c.getColumnIndex (NOTS_EVNT_ID)),
                            c.getInt (c.getColumnIndex (NOTS_EXHBTR_ID)),
                            c.getString (c.getColumnIndex (NOTS_TYPE))
                    ));
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showLog (Log.DEBUG, "EXCEPTION", e.getMessage (), true);
                }
            } while (c.moveToNext ());
        }
        return noteList;
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