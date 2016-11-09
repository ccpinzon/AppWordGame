package edu.uptc.appwordgame.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.uptc.appwordgame.Logic.User;

/**
 * Created by cristhian on 8/11/16.
 */

public class DatabaseAccess {
    public static final String TAG = "PRUEBA_DATABASEACCESS";
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    private static  DatabaseAccess instance;

    public DatabaseAccess(Context context) {
        this.openHelper = new DBOpenHelper(context);
    }



    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<User> getUsers(){
        List<User> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM USER",null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            String userid = cursor.getString(0);
            String name = cursor.getString(1);
            String nickname = cursor.getString(2);
            String pass = cursor.getString(3);
            String email = cursor.getString(4);
            list.add(new User(userid,name,nickname,pass,email));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> getWords(){
        ArrayList<String> words  = new ArrayList<>();
        Cursor cursor =  database.rawQuery("SELECT sin_acentos FROM palabras",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String word = cursor.getString(0);
            words.add(word);
            cursor.moveToNext();
        }
        return words;
    }

    public void addUser(User user){
        if (user!=null) {
            ContentValues values = new ContentValues();
            values.put("name", user.getName());
            values.put("nickname", user.getNickName());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            database.insert("USER", null, values);
            database.close();
        }else {
            Log.d(TAG,"Error al agregar usuario");
        }
    }
}
