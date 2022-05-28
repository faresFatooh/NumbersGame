package ps.school.numbersgamefinalproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ps.school.numbersgamefinalproject.model.History;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "User.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create Table info(username TEXT primary key, Fullname TEXT ,Email TEXT , Birthdate TEXT , Caontry TEXT , Gender TEXT,Score TEXT)");
        MyDB.execSQL("create Table games(Id integer primary key autoincrement,username TEXT, Score TEXT, Date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists info");
        MyDB.execSQL("drop Table if exists games");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public void insertGame(String username, String score, String Date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("Score", score);
        contentValues.put("Date", Date);
        MyDB.insert("games", null, contentValues);
    }

    public Boolean insertDetails(String username, String fullname, String email, String birthdate, String country, String gender, String Score) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("Fullname", fullname);
        contentValues.put("Email", email);
        contentValues.put("Birthdate", birthdate);
        contentValues.put("Caontry", country);
        contentValues.put("Gender", gender);
        contentValues.put("Score", Score);
        long result = MyDB.insert("info", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from info where username = ?", new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getAge(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from info where username = ?", new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(3);
    }

    public String getScore(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from info where username = ?", new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(6);
    }

    public long update(String username) {
        int last = Integer.parseInt(getScore(username));
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Score", last + 10);

        return db.update("info", contentValues, "username =?", new String[]{username});
    }
    public long updateLos(String username) {
        int last = Integer.parseInt(getScore(username));
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Score", last - 10);

        return db.update("info", contentValues, "username =?", new String[]{username});
    }

//    public long clear(String username) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("Score", 0);
//
//        return db.update("info", contentValues, "username =?", new String[]{username});
//    }

    public long changePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);

        return db.update("users", contentValues, "username =?", new String[]{username});
    }

    public ArrayList<History> getAllGames(String usernameNaw) {
        ArrayList games = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select * from games", null);
        int idIndex = c.getColumnIndex("Id");
        int usernameIndex = c.getColumnIndex("username");
        int scoreIndex = c.getColumnIndex("Score");
        int dateIndex = c.getColumnIndex("Date");
        while (c.moveToNext()) {
            int id = c.getInt(idIndex);
            String username = c.getString(usernameIndex);
            String score = c.getString(scoreIndex);
            String date = c.getString(dateIndex);
            History history = new History(username, score, date);

            if (history.getUsername().equals(usernameNaw)){
                history.setId(id);
                games.add(history);
            }

        }
        return games;
    }

    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE  FROM " + "games");
        db.close();
    }
}