package com.example.waterreminder;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Database_Helper extends SQLiteOpenHelper {

    private static final String Database_Name = "WaterReminder";

    private static final int Database_Version = 1;

    private static final String Table_Name = "Cup_Data";
    private static final String Table_Name1 = "History_Data";
    private static final String Table_Name2 = "Reminder_Schedule";
    private static final String Table_Name3 = "Personal_Data";

    private static final String Column_Id = "id";
    private static final String emptyCup = "emptyCup";
    private static final String fullcup = "fullcup";
    private static final String blueCup = "blueCup";
    private static final String cupML = "cupML";
    private static final String cupOZ = "cupOZ";

    private static final String Column_Id1 = "hid";
    private static final String historyCup = "historyCup";
    private static final String historyML = "historyML";
    private static final String historyTime = "historyTime";
    private static final String historyDate = "historyDate";
    private static final String historyOZ = "historyOZ";

    private static final String Column_Id2 = "rid";
    private static final String reminderTime = "reminderTime";
    private static final String reminderStatus = "reminderStatus";
    private static final String reminderDays = "reminderDays";

    private static final String Column_Id3 = "pid";
    private static final String gender = "gender";
    private static final String weightKg = "weightKg";
    private static final String weightLBS = "weightLBS";
    private static final String weightIn = "weightIn";
    private static final String wakeUpTime = "wakeUpTime";
    private static final String sleepTime = "sleepTime";
    private static final String neededML = "neededML";
    private static final String oneTimeNeededML = "oneTimeNeededML";
    private static final String TotalGlassPerDay = "TotalGlassPerDay";
    private static final String tone = "tone";
    private static final String mode = "mode";
    private static final String furtherReminder = "furtherReminder";
    private static final String unitIn = "unitIn";
    private static final String neededOZ = "neededOZ";
    private static final String oneTimeNeededOZ = "oneTimeNeededOZ";


    private static final String Create_Table = "Create table " + Table_Name
            + " ( " + Column_Id + " integer primary key autoincrement, " + emptyCup
            + " BLOB not null, " + fullcup + " BLOB not null, " + blueCup
            + " BLOB not null," + cupML
            + " text not null," + cupOZ
            + " text not null)";

    private static final String Create_Table1 = "Create table " + Table_Name1
            + " ( " + Column_Id1 + " integer primary key autoincrement, " + historyCup
            + " BLOB not null, " + historyML + " text not null, " + historyTime
            + " text not null, " + historyDate + " text not null, " + historyOZ + " text not null)";

    private static final String Create_Table2 = "Create table " + Table_Name2
            + " ( " + Column_Id2 + " integer primary key autoincrement, " + reminderTime
            + " text not null, " + reminderStatus + " Integer not null, " + reminderDays
            + " text not null )";

    private static final String Create_Table3 = "Create table " + Table_Name3
            + " ( " + Column_Id3 + " integer primary key autoincrement, " + gender
            + " text not null, " + weightKg + " Integer not null, " + weightIn
            + " text not null, " + wakeUpTime + " text not null, " + sleepTime
            + " text not null, " + neededML + " Integer not null, " + oneTimeNeededML
            + " Integer not null, " + TotalGlassPerDay
            + " Integer not null ," + tone + " text ,"
            + mode + " Integer , "
            + furtherReminder + " text not null ," +
            unitIn + " text not null ," + neededOZ + " Integer not null , " +
            oneTimeNeededOZ + " Integer not null ," + weightLBS + " Integer not null)";


    private static final String Drop_Table = "Drop table if exists "
            + Table_Name;

    private static final String Drop_Table1 = "Drop table if exists "
            + Table_Name1;

    private static final String Drop_Table2 = "Drop table if exists "
            + Table_Name2;

    private static final String Drop_Table3 = "Drop table if exists "
            + Table_Name3;

    byte[] img, img1, img2;
    Context context;

    public Database_Helper(Context context) {
        super(context, Database_Name, null, Database_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Create_Table);
        db.execSQL(Create_Table1);
        db.execSQL(Create_Table2);
        db.execSQL(Create_Table3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {

        db.execSQL(Drop_Table);
        db.execSQL(Drop_Table1);
        db.execSQL(Drop_Table2);
        onCreate(db);

    }

    public void insertPersonalData(Personal_Data data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(gender, data.getGender());
        values.put(weightKg, data.getWeightKg());
        values.put(neededML, data.getNeededML());
        values.put(oneTimeNeededML, data.getOneTimeNeededML());
        values.put(TotalGlassPerDay, data.getTotalGlassPerDay());
        values.put(weightIn, data.getWeightIn());
        values.put(wakeUpTime, data.getWakeUpTime());
        values.put(sleepTime, data.getSleepTime());
        values.put(furtherReminder, data.getFurtherReminder());
        values.put(unitIn, data.getUnitIn());
        values.put(neededOZ, data.getNeededOZ());
        values.put(oneTimeNeededOZ, data.getOneTimeNeededOZ());
        values.put(weightLBS, data.getWeightLBS());

        db.insert(Table_Name3, null, values);

        db.close();

    }

    public void insertData(CupDetails data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Drawable d = data.getEmptyCup();
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        Drawable d1 = data.getFullcup();
        Bitmap bitmap1 = ((BitmapDrawable) d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitmapdata1 = stream1.toByteArray();

        Drawable d2 = data.getBlueCup();
        Bitmap bitmap2 = ((BitmapDrawable) d2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapdata2 = stream2.toByteArray();

        values.put(emptyCup, bitmapdata);
        values.put(fullcup, bitmapdata1);
        values.put(blueCup, bitmapdata2);
        values.put(cupML, data.getCupML());
        values.put(cupOZ, data.getCupOZ());

        db.insert(Table_Name, null, values);

        db.close();

    }

    public void insertHistoryData(CupHistory data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Drawable d = data.getHistoryCup();
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        values.put(historyCup, bitmapdata);
        values.put(historyML, data.getHistoryML());
        values.put(historyTime, data.getHistoryTime());
        values.put(historyDate, data.getHistoryDate());
        values.put(historyOZ, data.getHistoryOZ());

        db.insert(Table_Name1, null, values);

        db.close();

    }

    public void clearAllReminder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Drop_Table2);
        db.execSQL(Create_Table2);
        db.close();
    }

    public void insertReminderData(ReminderData data) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(reminderTime, data.getReminderTime());
        values.put(reminderStatus, data.getReminderStatus());
        values.put(reminderDays, data.getReminderDays());

        db.insert(Table_Name2, null, values);

        db.close();

    }

    public ArrayList<CupDetails> getAllData() {

        ArrayList<CupDetails> data = new ArrayList<CupDetails>();

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name;

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    img = cursor.getBlob(1);
                    Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(img, 0, img.length));
                    img1 = cursor.getBlob(2);
                    Drawable image1 = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(img1, 0, img1.length));
                    img2 = cursor.getBlob(3);
                    Drawable image2 = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(img2, 0, img2.length));

                    CupDetails data_model = new CupDetails(image,
                            image1, image2, cursor.getString(4), cursor.getString(5));
                    data.add(data_model);

                } while (cursor.moveToNext());

            }
        } finally {

            cursor.close();

        }

        db.close();

        return data;
    }

    public Personal_Data getAllPersonalData() {

        Personal_Data data = null;

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name3;

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {

                    data = new Personal_Data(cursor.getString(1), cursor.getInt(2),
                            cursor.getInt(6), cursor.getInt(7), cursor.getInt(8),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(11), cursor.getString(12),
                            cursor.getInt(13), cursor.getInt(14), cursor.getInt(15));

                } while (cursor.moveToNext());

            }
        } finally {

            cursor.close();

        }

        db.close();

        return data;
    }

    public Uri getTone() {

        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION);
//       Uri uri=RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION);

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name3;

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    uri = Uri.parse(cursor.getString(9));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        db.close();
        return uri;
    }

    public int getMode() {

        int modeID = 1;
        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name3;

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    modeID = cursor.getInt(10);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        db.close();
        return modeID;
    }

    public ArrayList<CupHistory> getAllHistoryData() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate1 = df.format(c);

        ArrayList<CupHistory> data = new ArrayList<CupHistory>();

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name1 + " where historyDate = '" + formattedDate1 + "'";
        Log.e("select query: ", select_query);

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    img = cursor.getBlob(1);
                    Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(img, 0, img.length));

                    CupHistory data_model = new CupHistory(cursor.getInt(0), image,
                            cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    data.add(data_model);

                } while (cursor.moveToNext());

            }
        } finally {

            cursor.close();

        }

        db.close();

        return data;
    }

    public int getDrunkML() {

        int drunkML = 0;
        String drunkML1 = "";
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate1 = df.format(c);

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name1 + " where historyDate = '" + formattedDate1 + "'";
        Log.e("select query: ", select_query);

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    drunkML1 = cursor.getString(2);
                    drunkML1 = drunkML1.replace(" ml", "");
                    drunkML1 = drunkML1.trim();
                    drunkML = drunkML + Integer.parseInt(drunkML1);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        db.close();
        Log.e("Drunk ML: ", String.valueOf(drunkML));
        return drunkML;
    }

    public int getDrunkOZ() {

        int drunkOZ = 0;
        String drunkOZ1 = "";
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate1 = df.format(c);

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name1 + " where historyDate = '" + formattedDate1 + "'";
        Log.e("select query: ", select_query);

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    drunkOZ1 = cursor.getString(5);
                    drunkOZ1 = drunkOZ1.replace(" fl oz", "");
                    drunkOZ1 = drunkOZ1.trim();
                    drunkOZ = drunkOZ + Integer.parseInt(drunkOZ1);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        db.close();
        Log.e("Drunk OZ: ", String.valueOf(drunkOZ));
        return drunkOZ;
    }


    public ArrayList<ReminderData> getAllReminderData() {

        ArrayList<ReminderData> data = new ArrayList<ReminderData>();

        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name2;

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    ReminderData data_model = new ReminderData(cursor.getInt(0), cursor.getString(1),
                            cursor.getInt(2), cursor.getString(3));
                    data.add(data_model);

                } while (cursor.moveToNext());

            }
        } finally {

            cursor.close();

        }

        db.close();

        return data;
    }


    public int getFirstCupID() {
        int cupID = 1;
        SQLiteDatabase db = this.getReadableDatabase();
        String select_query = "Select * from " + Table_Name + " ORDER BY " + Column_Id + " asc limit 1";
        Log.e("LLLL query: ", select_query);
        Cursor cursor = db.rawQuery(select_query, null);
        try {
            if (cursor.moveToFirst()) {

                do {
                    cupID = cursor.getInt(0);

                } while (cursor.moveToNext());

            }
        } finally {

            cursor.close();

        }


        db.close();

        Log.e("First Cup ID: ", String.valueOf(cupID));
        return cupID;

    }

    public String getNextReminderTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");

        String time = null;
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String select_query = "Select * from " + Table_Name2;

        Cursor cursor = db.rawQuery(select_query, null);
        try {
            Calendar cal = Calendar.getInstance();
            String currentTime = dateFormat.format(cal.getTime());
            Log.e("Current time: ", currentTime + "");

            Date currentT = dateFormat.parse(currentTime, new ParsePosition(0));

            if (cursor.moveToFirst()) {
                String nextTime;
                do {
                    nextTime = cursor.getString(1);
                    Date nextT = dateFormat.parse(nextTime, new ParsePosition(0));
                    if (currentT.compareTo(nextT) <= 0) {
                        i++;
                        if (i == 1)
                            time = dateFormat.format(nextT.getTime());
                    }
                } while (cursor.moveToNext());

            }
            Log.e("Next time: ", time + "");
        } finally {
            cursor.close();
        }

        db.close();

        return time;
    }

    public void deleteTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Name, null, null);

        db.close();

    }

    public void deleteTable1() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Name1, null, null);

        db.close();

    }

    public void deleteTable2() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Name2, null, null);

        db.close();

    }

    public void deleteTable3() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Name3, null, null);

        db.close();

    }

    public void updateReminderStatus(int rid, int status) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("reminderStatus", status); //These Fields should be your String values of actual column names

        db.update(Table_Name2, cv, Column_Id2 + " = ?", new String[]{String.valueOf(rid)});

        db.close();

    }

    public void updateFurtherReminderStatus(String status) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(furtherReminder, status); //These Fields should be your String values of actual column names

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});

        db.close();

    }

    public void updateTone(Uri tone1) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(tone, tone1.toString()); //These Fields should be your String values of actual column names

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateWeightIn(String weight) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(weightIn, weight); //These Fields should be your String values of actual column names

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateWeight(int weightKg1, int weightLbs1, int neededMl1, int neededOz1) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(weightKg, weightKg1);
        cv.put(weightLBS, weightLbs1);
        cv.put(neededML, neededMl1);
        cv.put(neededOZ, neededOz1);

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateWakeUpTime(String wTime) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(wakeUpTime, wTime);

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateSleepTime(String sTime) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(sleepTime, sTime);

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateNeededMlOz(int ml, int oz) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(neededML, ml); //These Fields should be your String values of actual column names
        cv.put(neededOZ, oz); //These Fields should be your String values of actual column names

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateMode(int mode1) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(mode, String.valueOf(mode1)); //These Fields should be your String values of actual column names

        db.update(Table_Name3, cv, Column_Id3 + " > ?", new String[]{String.valueOf(0)});
        db.close();
    }

    public void updateReminderDays(int rid, String days) {
        //  reminderStatus==0 for reminder off
        //  reminderStatus==1 for reminder on
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("reminderDays", days); //These Fields should be your String values of actual column names

        db.update(Table_Name2, cv, Column_Id2 + " = ?", new String[]{String.valueOf(rid)});

        db.close();
    }

    public void deleteReminder(int rid) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name2, Column_Id2 + " = ?", new String[]{String.valueOf(rid)});
        db.close();
    }

    public void deleteEntry(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name1, Column_Id1 + " = ?", new String[]{String.valueOf(ID)});
        db.close();

    }
}
