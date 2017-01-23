package com.alz313.q4task.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Q4Task";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbSchema.TaskTable.NAME + "(" +
                DbSchema.TaskTable.Cols.ID + " integer primary key autoincrement, " +
                DbSchema.TaskTable.Cols.TITLE + " TEXT, " +
                DbSchema.TaskTable.Cols.DESCRIPTION + " TEXT, " +
                DbSchema.TaskTable.Cols.IMPORTANCE + " INTEGER, " +
                DbSchema.TaskTable.Cols.URGENCY + " INTEGER , " +

                DbSchema.TaskTable.Cols.ESTIMATE + " INTEGER, " +
                DbSchema.TaskTable.Cols.IS_SOLVED + " INTEGER, " +
                DbSchema.TaskTable.Cols.IS_FROG + " INTEGER, " +

                DbSchema.TaskTable.Cols.DEADLINE + " TEXT, " +
                DbSchema.TaskTable.Cols.LOCATION + " TEXT, " +
                DbSchema.TaskTable.Cols.RESOURCE + " TEXT)"
        );
        Log.d("logalz", "TaskBaseHelper: Table " + DbSchema.TaskTable.NAME + " created");


        insertData(db,"Task 25 , 89", "Description - 2",  25 , 89 , 2 , 0 ,  0);
        insertData(db,"Task 39 , 67", "Description - 4",  39 , 67 , 4 , 0 ,  0);
        insertData(db,"Task 16 , 59", "Description - 6",  16 , 59 , 6 , 0 ,  1);
        insertData(db,"Task 18 , 60", "",  18 , 60 , 1 , 0 ,  0);
        insertData(db,"Task 51 , 96", "Description - 4",  51 , 96 , 4 , 0 ,  0);
        insertData(db,"Task 95 , 16", "Description - 6",  95 , 16 , 6 , 0 ,  1);
        insertData(db,"Task 37 , 92", "Description - 2",  37 , 92 , 2 , 0 ,  0);
        insertData(db,"Task 88 , 15", "Description - 4",  88 , 15 , 4 , 0 ,  0);
        insertData(db,"Task 43 , 82", "Description - 0",  43 , 82 , 0 , 0 ,  0);
        insertData(db,"Task 58 , 25", "",  58 , 25 , 0 , 0 ,  1);
        insertData(db,"Task 26 , 34", "Description - 1",  26 , 34 , 1 , 0 ,  0);
        insertData(db,"Task 43 , 71", "Description - 1",  43 , 71 , 1 , 0 ,  0);
        insertData(db,"Task 97 , 31", "Description - 6",  97 , 31 , 6 , 0 ,  0);
        insertData(db,"Task 65 , 2", "Description - 3",  65 , 2 , 3 , 0 ,  0);
        insertData(db,"Task 64 , 12", "Description - 4",  64 , 12 , 4 , 0 ,  1);
        insertData(db,"Task 16 , 87", "",  16 , 87 , 4 , 0 ,  0);
        insertData(db,"Task 72 , 12", "Description - 0",  72 , 12 , 0 , 0 ,  0);
        insertData(db,"Task 90 , 91", "Description - 6",  90 , 91 , 6 , 0 ,  0);
        insertData(db,"Task 10 , 83", "Description - 2",  10 , 83 , 2 , 0 ,  0);
        insertData(db,"Task 3 , 54", "Description - 4",  3 , 54 , 4 , 0 ,  0);
        insertData(db,"Task 63 , 14", "Description - 0",  63 , 14 , 0 , 0 ,  1);
        insertData(db,"Task 90 , 9", "Description - 0",  90 , 9 , 0 , 0 ,  0);
        insertData(db,"Task 97 , 40", "Description - 6",  97 , 40 , 6 , 0 ,  0);
        insertData(db,"Task 80 , 57", "",  80 , 57 , 0 , 0 ,  0);
        insertData(db,"Task 74 , 7", "Description - 5",  74 , 7 , 5 , 0 ,  1);
        insertData(db,"Task 1 , 46", "Description - 5",  1 , 46 , 5 , 0 ,  0);
        insertData(db,"Task 96 , 68", "Description - 0",  96 , 68 , 0 , 0 ,  0);
        insertData(db,"Task 62 , 70", "Description - 1",  62 , 70 , 1 , 0 ,  0);
        insertData(db,"Task 44 , 65", "Description - 6",  44 , 65 , 6 , 0 ,  0);
        insertData(db,"Task 26 , 88", "",  26 , 88 , 5 , 0 ,  0);
        insertData(db,"Task 81 , 83", "Description - 2",  81 , 83 , 2 , 0 ,  0);
        insertData(db,"Task 12 , 43", "Description - 6",  12 , 43 , 6 , 0 ,  1);
        insertData(db,"Task 40 , 59", "Description - 3",  40 , 59 , 3 , 0 ,  0);
        insertData(db,"Task 96 , 70", "Description - 6",  96 , 70 , 6 , 0 ,  0);
        insertData(db,"Task 56 , 58", "",  56 , 58 , 5 , 0 ,  0);
        insertData(db,"Task 47 , 83", "Description - 0",  47 , 83 , 0 , 0 ,  0);
        insertData(db,"Task 58 , 32", "Description - 3",  58 , 32 , 3 , 0 ,  1);
        insertData(db,"Task 68 , 56", "",  68 , 56 , 2 , 0 ,  0);
        insertData(db,"Task 70 , 76", "Description - 2",  70 , 76 , 2 , 0 ,  0);
        insertData(db,"Task 60 , 66", "Description - 6",  60 , 66 , 6 , 0 ,  0);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.TaskTable.NAME);
    }

    private void insertData(SQLiteDatabase db, String name, String desc, int importance, int urgency, int estimate, int isSolved, int isFrog) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.TaskTable.Cols.TITLE, name);
        values.put(DbSchema.TaskTable.Cols.DESCRIPTION, desc);
        values.put(DbSchema.TaskTable.Cols.IMPORTANCE, importance);
        values.put(DbSchema.TaskTable.Cols.URGENCY, urgency);

        values.put(DbSchema.TaskTable.Cols.ESTIMATE, estimate);
        values.put(DbSchema.TaskTable.Cols.IS_SOLVED, isSolved);
        values.put(DbSchema.TaskTable.Cols.IS_FROG, isFrog);

        db.insert(DbSchema.TaskTable.NAME, null, values);
        values.clear();

    }
}
