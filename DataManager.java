package com.skylan.homeworkpro;

/**
 * Created by danielkawalsky on 3/4/15.
 */

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager extends SQLiteOpenHelper {

    private static final String TAG = "DataManager";
    private static final String DATABASE_NAME = "assignment.db";//To use SQLite Manager, change to petdb.db ; Go to DDMS mode, find in /data/data/[APP_NAME]/databases/
    public static final int DATABASE_VERSION = 2;
    public static final String ASSIGNMENT_TABLE = "Assignment_Table";
    public static final String ASSIGNMENT_NUM = "Assignment_Number_id";
    public static final String ASSIGNMENT_NAME = "Assignment_Name";
    public static final String ASSIGNMENT_CLASS_GRADE = "Assignment_Class_Grade";
    public static final String ASSIGNMENT_CLASS_SUBJECT = "Assignment_Class_Subject";
    public static final String ASSIGNMENT_DAYS_UNTIL_DUE = "Assignment_Days_Until_Due";
    public static final String ASSIGNMENT_DIFFICULTY = "Assignment_Difficulty";
    public static final String ASSIGNMENT_TYPE = "Assignment_Type";
    public static final String ASSIGNMENT_URGENCY_RATING = "Assignment_Urgency_Rating";
    public static final String ASSIGNMENT_ENTRY_DATE = "Assignment_Entry_Date";
    public static final String ASSIGNMENT_DUE_DATE_TEXT = "Assignment_Due_Date_Text";
    public static final String ASSIGNMENT_DUE_DATE_MSEC = "Assignment_Due_Date_Ms";
    public static final String ASSIGNMENT_ARCHIVED = "Assignment_Archived";
    public static final String ASSIGNMENT_COMPLETED = "Assignment_Completed";
    public static final String ASSIGNMENT_OVERDUE = "Assignment_Overdue";
    public static final String[] COLUMN_TITLES = new String[]{
            ASSIGNMENT_NUM,
            ASSIGNMENT_NAME,
            ASSIGNMENT_CLASS_GRADE,
            ASSIGNMENT_CLASS_SUBJECT,
            ASSIGNMENT_DAYS_UNTIL_DUE,
            ASSIGNMENT_DIFFICULTY,
            ASSIGNMENT_TYPE,
            ASSIGNMENT_URGENCY_RATING,
            ASSIGNMENT_ENTRY_DATE,
            ASSIGNMENT_DUE_DATE_TEXT,
            ASSIGNMENT_DUE_DATE_MSEC,
            ASSIGNMENT_ARCHIVED,
            ASSIGNMENT_COMPLETED,
            ASSIGNMENT_OVERDUE
    };
    //SQL Syntax Components
    public static final String COMMA_SEP = ", ";
    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INT = " INTEGER";
    public static final String TYPE_REAL = " REAL";
    private static final String SQL_CREATE_ENTRIES =
            //"CREATE DATABASE " + DATABASE_NAME +
            " CREATE TABLE " + ASSIGNMENT_TABLE + " (" +
                    ASSIGNMENT_NUM + TYPE_INT + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + //so when entered, ID is only handled by DataManager.
                    ASSIGNMENT_NAME + TYPE_TEXT + COMMA_SEP +
                    ASSIGNMENT_CLASS_GRADE + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_CLASS_SUBJECT + TYPE_TEXT + COMMA_SEP +
                    ASSIGNMENT_DAYS_UNTIL_DUE + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_DIFFICULTY + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_TYPE + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_ENTRY_DATE + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_DUE_DATE_TEXT + TYPE_TEXT + COMMA_SEP +
                    ASSIGNMENT_DUE_DATE_MSEC + TYPE_REAL + COMMA_SEP +
                    ASSIGNMENT_URGENCY_RATING + TYPE_REAL + COMMA_SEP +
                    ASSIGNMENT_ARCHIVED + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_COMPLETED + TYPE_INT + COMMA_SEP +
                    ASSIGNMENT_OVERDUE + TYPE_INT +
                    ");";
    public static ArrayList<AssignmentInfo> assignmentDummyList = new ArrayList<AssignmentInfo> ();
    public static AssignmentInfo assignmentDummy = new AssignmentInfo(6, "Dummy Title", 97,"Dummy Subject", 20,
            5, 2, 1234123412, "Dummy Date", 9393993993.00003939,
            876.7, 0, 0, 0);

    public static final String TYPE_DATE = " DATETIME";
    public static ArrayList<AssignmentInfo> assignmentList;
    public int[] assIds;
    public String[] assTitles;
    public int[] assGrades;
    public String[] assSubjects;
    public int[] assDays;
    public int[] assDifficulties;
    public int[] assTypes;
    public int[] assDates;
    public String[] assDueDateTexts;
    public double[] assDueDateMSecs;
    public double[] assUrgencies;
    public boolean[] assArchiveds;
    public boolean[] assCompleteds;
    public boolean[] assOverdues;
    private DataManager dbHelper;
    private SQLiteDatabase dataBase;

    public DataManager(Context context) { //this constructor allows use of object dbHelper
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // TODO Auto-generated constructor stub
    }

    // **** http://www.tutorialspoint.com/sqlite/index.htm
    // Important website because SQLite syntax is different from normal
    // SQL syntax, not just because of hot girl on home page.

    @Override
    public void onCreate(SQLiteDatabase db) {
        //getWritableDatabase(); - REM'ed because recursive error, no longer protests after this.
        createTables(db);
        //System.out.println(SQL_CREATE_ENTRIES);
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // If you need to add a column
        if (newVersion > oldVersion) {
            // db.execSQL("ALTER TABLE " + ASSIGNMENT_TABLE + " ADD COLUMN "+ ASSIGNMENT_DUE_DATE_MSEC  + TYPE_REAL);
        }
    }

    private void createTables(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);

		/*
        String q = "";

		q = "CREATE TABLE Crew_Records ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "Code TEXT, "
				+ "DayEndFixedTime TEXT, " + "DayEndFrom TEXT, "
				+ "DayEndTo TEXT, " + "DayStartFrom TEXT, "
				+ "DayStartTo TEXT, " + "Name TEXT" + ");";
		db.execSQL(q);
		 */
    }

    public void addData(String tableName, String atitle, int grade, String subject, int days,
                        int difficulty, int type, int date, String dueDateText, double dueDateMSec, double rating, int archived, int completed, int overdue) {
        getWritableDatabase().execSQL("INSERT INTO " + ASSIGNMENT_TABLE + " ( "
                        + ASSIGNMENT_NAME + ","
                        + ASSIGNMENT_CLASS_GRADE + ","
                        + ASSIGNMENT_CLASS_SUBJECT + ","
                        + ASSIGNMENT_DAYS_UNTIL_DUE + ","
                        + ASSIGNMENT_DIFFICULTY + ","
                        + ASSIGNMENT_TYPE + ","
                        + ASSIGNMENT_ENTRY_DATE + ","
                        + ASSIGNMENT_DUE_DATE_TEXT + ","
                        + ASSIGNMENT_DUE_DATE_MSEC + ","
                        + ASSIGNMENT_URGENCY_RATING + ","
                        + ASSIGNMENT_ARCHIVED + ","
                        + ASSIGNMENT_COMPLETED + ","
                        + ASSIGNMENT_OVERDUE + ")"
                        + " VALUES ("
                        + "'" + atitle + "',"
                        + grade + ","
                        + "'" + subject + "',"
                        + days + ","
                        + difficulty + ","
                        + type + ","
                        + date + ","
                        + "'" + dueDateText + "',"
                        + dueDateMSec + ","
                        + rating + ","
                        + archived + ","
                        + completed + ","
                        + overdue +
                        ");"
        );
    }

    public void removeData(String tableName, String atitle, int grade,
                           int days, String subject, int difficulty,
                           int type, int date, String dueDateText,
                           double dueDateMSec, double rating, boolean archived,
                           boolean completed, boolean overdue, int idnum) {
        getWritableDatabase().execSQL("DELETE FROM " + ASSIGNMENT_TABLE + " WHERE " + ASSIGNMENT_NUM + " = " + idnum + ";");
    }

    public void removeAllData() {
        //http://www.tutorialspoint.com/sqlite/sqlite_delete_query.htm
    }


    //will become obsolete once archive is implemented (unless after 30 days just trash... nah)
    public void deletePastEntries() {
        assignmentList = getAssignmentInfoArrayList();
        dataBase = dbHelper.getWritableDatabase();
        for (int go = 0; go < assignmentList.size(); go++) {
            dataBase.execSQL("DELETE FROM " + DataManager.ASSIGNMENT_TABLE + " WHERE " +
                    DBRecordsLayer.ASSIGNMENT_DAYS_UNTIL_DUE + " < " + 0 + ";");
        }

    }

    public void populateArrays(ArrayList<AssignmentInfo> assignmentList) {

        assIds = new int[assignmentList.size()];
        assTitles = new String[assignmentList.size()];
        assGrades = new int[assignmentList.size()];
        assSubjects = new String[assignmentList.size()];
        assDays = new int[assignmentList.size()];
        assDifficulties = new int[assignmentList.size()];
        assTypes = new int[assignmentList.size()];
        assDates = new int[assignmentList.size()];
        assDueDateTexts = new String[assignmentList.size()];
        assDueDateMSecs = new double[assignmentList.size()];
        assUrgencies = new double[assignmentList.size()];
        assArchiveds = new boolean[assignmentList.size()];
        assCompleteds = new boolean[assignmentList.size()];
        assOverdues = new boolean[assignmentList.size()];

        for (int go = 0; go < assTitles.length; go++) {
            assIds[go] = assignmentList.get(go).assignmentID;
            assTitles[go] = assignmentList.get(go).assignmentTitle;
            assGrades[go] = assignmentList.get(go).assignmentClassGrade;
            assSubjects[go] = assignmentList.get(go).assignmentClassSubject;
            assDays[go] = assignmentList.get(go).assignmentDaysUntilDue;
            assDifficulties[go] = assignmentList.get(go).assignmentDifficulty;
            assTypes[go] = assignmentList.get(go).assignmentType;
            assDates[go] = assignmentList.get(go).assignmentDate;
            assDueDateTexts[go] = assignmentList.get(go).assignmentDueDateText;
            assDueDateMSecs[go] = assignmentList.get(go).assignmentDueDateMSec;
            assUrgencies[go] = assignmentList.get(go).assignmentUrgencyRating;
            assArchiveds[go] = assignmentList.get(go).assignmentArchived;
            assCompleteds[go] = assignmentList.get(go).assignmentCompleted;
            assOverdues[go] = assignmentList.get(go).assignmentOverdue;

        }
    }

    public void sortListOfAssignments(ArrayList<AssignmentInfo> assignmentList) {
        AssignmentInfo slot;
        for (int prev = (assignmentList.size() - 1); prev >= 0; prev--) {
            for (int go = 0; go < prev; go++) {
                if (assignmentList.get(go).assignmentUrgencyRating < assignmentList.get(prev).assignmentUrgencyRating) {
                    slot = assignmentList.get(go);
                    assignmentList.set(go, assignmentList.get(prev));
                    assignmentList.set(prev, slot);
                }
            }
        }
        DisplayAssignmentsActivity.assignmentList = assignmentList;
    }

    public ArrayList<AssignmentInfo> getAssignmentInfoArrayList() {
        ArrayList<AssignmentInfo> assList = new ArrayList<AssignmentInfo>();
        dataBase = getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DataManager.ASSIGNMENT_TABLE, null);

        assList.clear();

        if (mCursor.moveToFirst()) {
            do {
                assList.add(new AssignmentInfo(
                        //if gives error about type for constructor, check the .get[type] method here
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_NUM)),
                        mCursor.getString(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_NAME)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_CLASS_GRADE)),
                        mCursor.getString(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_CLASS_SUBJECT)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_DAYS_UNTIL_DUE)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_DIFFICULTY)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_TYPE)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_ENTRY_DATE)),
                        mCursor.getString(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_DUE_DATE_TEXT)),
                        mCursor.getDouble(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_DUE_DATE_MSEC)),
                        mCursor.getDouble(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_URGENCY_RATING)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_ARCHIVED)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_COMPLETED)),
                        mCursor.getInt(mCursor
                                .getColumnIndex(DataManager.ASSIGNMENT_OVERDUE))));

            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return assList;

    }
}
