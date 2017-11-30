package com.arsan.niloufar.karyab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Niloufar on 4/13/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "KARYABDB";
    // Contacts table name
    private static final String TABLE_IDS = "IDS";
    // IDSs Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_DATA = "DATA";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_IDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DATA + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDS);
// Creating tables again
        onCreate(db);
    }

    // Adding new ids
    public void addIDS(IDS ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ids.getName()); // IDS Name
        values.put(KEY_DATA, ids.getData()); // IDS Phone Number

// Inserting Row
        db.insert(TABLE_IDS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one ids
    public IDS getID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IDS, new String[]{KEY_ID,
                        KEY_NAME, KEY_DATA}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        IDS contact = new IDS(cursor.getInt(0),
                cursor.getString(1), cursor.getLong(2));
        db.close();
        return contact;
    }
    public IDS getIDbyName(String name) {
        IDS ids = new IDS();
        String selectQuery = "SELECT * FROM " + TABLE_IDS +" WHERE "+KEY_NAME+" = "+"'"+""+name+""+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToLast()) {
            ids.setId(cursor.getInt(0));
            ids.setName(cursor.getString(1));
            ids.setData(cursor.getLong(2));
        }
        cursor.close();
        db.close();
        return ids;
    }
    public List<IDS> getAllIDSByName(String name) {
        List<IDS> idsList = new ArrayList<IDS>();
        String selectQuery = "SELECT * FROM " + TABLE_IDS +" WHERE "+KEY_NAME+" = "+"'"+""+name+""+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                IDS ids = new IDS();
                ids.setId(cursor.getInt(0));
                ids.setName(cursor.getString(1));
                ids.setData(cursor.getLong(2));
                idsList.add(ids);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return idsList;
    }
    // Getting All IDSs
    public List<IDS> getAllIDSs() {
        List<IDS> idsList = new ArrayList<IDS>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_IDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                IDS ids = new IDS();
                ids.setId(Integer.parseInt(cursor.getString(0)));
                ids.setName(cursor.getString(1));
                ids.setData(cursor.getLong(2));
// Adding contact to list
                idsList.add(ids);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
// return contact list
        return idsList;
    }

    // Getting idss Count
    public int getIDSsCount() {
        String countQuery = "SELECT * FROM " + TABLE_IDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }

    // Updating a ids
    public int updateIDS(IDS ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ids.getName());
        values.put(KEY_DATA, ids.getData());

// updating row
        return db.update(TABLE_IDS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(ids.getId())});
    }

    // Deleting a ids
    public void deleteIDS(IDS ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IDS, KEY_ID + " = ?",
                new String[]{String.valueOf(ids.getId())});
        db.close();
    }
    public void deleteALLIDS() {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_IDS, null, null);
        db.close();
    }
}