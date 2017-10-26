package com.chi.nikita.testproject.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.chi.nikita.testproject.data.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DBManager extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private static DBManager ourInstance;
    private static final String DB_NAME = "TEST_DB";
    private static final int VERSION = 1;

    private static final String TABLE_USER = "USER_TABLE";

    /**
     * constants for TABLE_USER
     */
    private static final String ID = "_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";

    /**
     * total constants
     */
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String TYPE_TEXT = " TEXT NOT NULL";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ", ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String UNIQUE = " UNIQUE ";
    private static final String DEFAULT = " DEFAULT ' '";

    /**
     * constant for create TABLE_USER
     */
    private static final String CREATE_TABLE_USER = CREATE_TABLE + TABLE_USER +
            " (" +
            ID + TYPE_INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA_SEP +
            FIRST_NAME + TYPE_TEXT + DEFAULT + COMMA_SEP +
            LAST_NAME + TYPE_TEXT + DEFAULT + COMMA_SEP +
            PHONE + TYPE_INTEGER + UNIQUE +
            ")";

    public DBManager(@NonNull final Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBManager getInstance() {
        return ourInstance;
    }

    public static void initDatabase(@NonNull final Context context) {
        ourInstance = new DBManager(context);
    }

    public static boolean isInit() {
        return ourInstance != null;
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase sqLiteDatabase, int oldVersion,
                          int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    /**
     * Method for open Database
     */
    public void openDB() {
        sqLiteDatabase = getWritableDatabase();
    }

    /**
     * Method for close Database
     */
    public void closeDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public void insertUser(@NonNull final UserModel userModel) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                sqLiteDatabase.beginTransaction();
                try {
                    final ContentValues cv = new ContentValues();
                    cv.put(FIRST_NAME, userModel.getFirstName());
                    cv.put(LAST_NAME, userModel.getLastName());
                    cv.put(PHONE, userModel.getPhone());

                    long id = sqLiteDatabase.insert(TABLE_USER, null, cv);
                    userModel.setId((int) id);
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
            }
        });
    }

    public void updateUser(@NonNull final int id, @NonNull final UserModel userModel) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                sqLiteDatabase.beginTransaction();
                final ContentValues cv = new ContentValues();
                try {
                    cv.put(FIRST_NAME, userModel.getFirstName());
                    cv.put(LAST_NAME, userModel.getLastName());
                    cv.put(PHONE, userModel.getPhone());

                    final String where = ID + " = " + id;
                    sqLiteDatabase.update(TABLE_USER, cv, where, null);
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
            }
        });
    }

    public long deleteUser(@NonNull final int id) {
        final String where = ID + " = " + id;

        return sqLiteDatabase.delete(TABLE_USER, where, null);
    }

    public List<UserModel> getAllUsers() {
        final String select = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        return getUserModelList(cursor);
    }

    private List<UserModel> getUserModelList(@NonNull final Cursor cursor) {
        final List<UserModel> userModelList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            final UserModel userModel = new UserModel();
            userModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            userModel.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME)));
            userModel.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME)));
            userModel.setPhone(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(PHONE))));

            userModelList.add(userModel);
        }
        cursor.close();
        return userModelList;
    }
}
