package edu.uptc.appwordgame.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by cristhian on 8/11/16.
 */

public class DBOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "DB_WordGame.db";
    private static final int DATABASE_VERSION = 1;


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
