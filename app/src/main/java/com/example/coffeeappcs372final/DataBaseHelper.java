package com.example.coffeeappcs372final;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String BREW_TABLE = "BREW_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_BEANS = "BEANS";
    public static final String COLUMN_BREWER = "BREWER";
    public static final String COLUMN_GRAMS = "GRAMS";
    public static final String COLUMN_WATER = "WATER";
    public static final String COLUMN_TEMP = "TEMPER";
    public static final String COLUMN_METHOD = "METHOD";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_NOTE = "NOTE";
    public static final String COLUMN_FAVORITE = "FAVORITE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "brewDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + BREW_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BEANS + " TEXT DEFAULT 'Default', " +
                COLUMN_BREWER + " TEXT DEFAULT 'Default', " +
                COLUMN_GRAMS + " FLOAT DEFAULT 0.0, " +
                COLUMN_WATER + " FLOAT DEFAULT 0.0, " +
                COLUMN_TEMP + " FLOAT DEFAULT 0.0, " +
                COLUMN_METHOD + " TEXT DEFAULT 'N/A', " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_NOTE + " TEXT DEFAULT 'N/A', " +
                COLUMN_FAVORITE + " INTEGER DEFAULT 0)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // You can implement schema changes and database updates here if needed.
    }

    public boolean addBrew(BrewModel brew) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BEANS, brew.getBeans());
        cv.put(COLUMN_BREWER, brew.getBrewer());
        cv.put(COLUMN_GRAMS, brew.getGrams());
        cv.put(COLUMN_WATER, brew.getWater());
        cv.put(COLUMN_TEMP, brew.getTemp());
        cv.put(COLUMN_METHOD, brew.getMethod());
        cv.put(COLUMN_TIME, brew.getTime());
        cv.put(COLUMN_NOTE, brew.getNote());
        cv.put(COLUMN_FAVORITE, brew.getFavorite());

        long insert = db.insert(BREW_TABLE, null, cv);
        db.close();
        return insert != -1; // return true if insert is successful
    }

    public boolean deleteBrew(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };

        int deleteCount = db.delete(BREW_TABLE, whereClause, whereArgs);
        db.close();

        return deleteCount > 0; // returns true if any rows were deleted
    }

    public List<BrewModel> getAllBrews() {
        List<BrewModel> returnList = new ArrayList<>();

        // Get data from the database
        String queryString = "SELECT * FROM " + BREW_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new brew objects. Put them into the return list.
            do {
                int brewID = cursor.getInt(0); // index 0 is the column where ID is stored
                String beans = cursor.getString(1); // index 1 is the column where BEANS is stored
                String brewer = cursor.getString(2); // index 2 is the column where BREWER is stored
                float grams = cursor.getFloat(3); // index 3 is the column where GRAMS is stored
                float water = cursor.getFloat(4); // index 4 is the column where WATER is stored
                float temp = cursor.getFloat(5); // index 5 is the column where TEMPER is stored
                String method = cursor.getString(6); // index 6 is the column where METHOD is stored
                String time = cursor.getString(7); // index 7 is the column where TIME is stored
                String note = cursor.getString(8);
                Integer favorite = cursor.getInt(9);

                BrewModel newBrew = new BrewModel(brewID, beans, brewer, grams, water, temp, method, time, note, favorite);
                returnList.add(newBrew);
            } while (cursor.moveToNext());
        }  // failure. do not add anything to the list.


        // close both the cursor and the db when done.
        cursor.close();
        db.close();

        return returnList;
    }

    public boolean updateFavorite(int id, int favoriteStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("favorite", favoriteStatus); // Assuming 'favorite' is the column name

        // Updating row
        int updateStatus = db.update("brew_table", contentValues, "id = ?", new String[]{String.valueOf(id)});
        db.close();

        return updateStatus != -1; // returns true if the update was successful
    }

}
