package kg.enesaitech.islam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by e.zhumanasyrov on 10.01.2019.
 */

public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "islam.db";
    private Context context;


    private static final String SQL_CONFIG =
            "CREATE TABLE config (" +
                    " is_imported int default 0" +
                    " )";

    private static final String SQL_QUESTION =
            "CREATE TABLE question (" +
                    " id INTEGER PRIMARY KEY autoincrement," +
                    " name varchar(256), " +
                    " correct_answered_id int, " +
                    " answered_id int, " +
                    " test_id int" +
                    " )";
    private static final String SQL_ANSWER =
            "CREATE TABLE answer (" +
                    " id INTEGER PRIMARY KEY autoincrement," +
                    " name varchar(256), " +
                    " question_id int" +
                    " )";

    private static final String SQL_TEST =
            "CREATE TABLE test (" +
                    " id INTEGER PRIMARY KEY autoincrement," +
                    " name varchar(256), " +
                    " locked int, " +
                    " position int default 0 " +
                    " )";

    private static final String SQL_POINTS =
            "CREATE TABLE points (" +
                    " id INTEGER PRIMARY KEY autoincrement," +
                    " test_id int, " +
                    " correct int, " +
                    " wrong int, " +
                    " empty int " +
                    " )";



    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    //   NEW WAY__________________________________________________________________________________
    public SQLiteDatabase openDatabase() {


        SQLiteDatabase database = this.getReadableDatabase();
        String filePath = database.getPath();
        database.close();

        File dbFile = new File(filePath);

        //        File dbFile = context.getDatabasePath(DATABASE_NAME);

//        File data = Environment.getDataDirectory();
//        String myDBPath = "/data/kg.enesaitech.islam/databases/islam.db";
//        File dbFile = new File(data, myDBPath);
        Log.d("**********************", String.valueOf(dbFile.exists()));
//        if (!dbFile.exists()) {
        Log.d("S", "############# starting copy DATABASE");
        try {
            copyDatabase(dbFile);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("**********************", "ERROR CREATING SOURCE DATABASES");
            throw new RuntimeException("Error creating source database", e);
        }
//        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile) throws IOException {

        InputStream is = context.getAssets().open("databases/" + DATABASE_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }
        os.flush();
        os.close();
        is.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // When app is starting, this data will set to SQLite
        db.execSQL(SQL_CONFIG);
//        db.execSQL(SQL_QUESTION);
//        db.execSQL(SQL_ANSWER);
//        db.execSQL(SQL_TEST);
//        db.execSQL(SQL_POINTS);
//        db.execSQL(db_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public ArrayList<Test> getTests() {
        ArrayList<Test> tests = new ArrayList<Test>();
        String selectQuery = "SELECT id, name, locked, position FROM test order by id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Test test = new Test();
                test.setId(c.getInt(c.getColumnIndex("id")));
                test.setName(c.getString(c.getColumnIndex("name")));
                test.setLocked(c.getInt(c.getColumnIndex("locked")));
                test.setPosition(c.getInt(c.getColumnIndex("position")));
                tests.add(test);
            } while (c.moveToNext());
        }
        return tests;
    }

    public ArrayList<Question> getQuestions(int test_id) {
        ArrayList<Question> question = new ArrayList<Question>();
        String selectQuery = "SELECT id, name, correct_answered_id, answered_id, test_id FROM question where test_id = " + test_id + " order by id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Question que = new Question();
                que.setId(c.getInt(c.getColumnIndex("id")));
                que.setName(c.getString(c.getColumnIndex("name")));
                que.setCorrect_answered_id(c.getInt(c.getColumnIndex("correct_answered_id")));
                que.setAnswered_id(c.getInt(c.getColumnIndex("answered_id")));
                que.setTest_id(c.getInt(c.getColumnIndex("test_id")));
                question.add(que);
            } while (c.moveToNext());
        }
        return question;
    }

    public ArrayList<Answer> getAnswers(int question_id) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        String selectQuery = "SELECT id, question_id, name FROM answer where question_id = " + question_id + " order by id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Answer ans = new Answer();
                ans.setId(c.getInt(c.getColumnIndex("id")));
                ans.setName(c.getString(c.getColumnIndex("name")));
                ans.setQuestion_id(c.getInt(c.getColumnIndex("question_id")));
                answers.add(ans);
            } while (c.moveToNext());
        }
        return answers;
    }


    public void setAnswered(int question_id, int answer_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update question set answered_id = " + answer_id + " where id = " + question_id);
    }

    public void setPosition(int test_id, int position) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update test set position = " + position + " where id = " + test_id);
    }

    public Test getTest(int test_id) {
        String selectQuery = "SELECT id, name, locked, position FROM test where id = " + test_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        Test test = new Test();
        if (c.moveToFirst()) {
            do {
                test.setId(c.getInt(c.getColumnIndex("id")));
                test.setName(c.getString(c.getColumnIndex("name")));
                test.setLocked(c.getInt(c.getColumnIndex("locked")));
                test.setPosition(c.getInt(c.getColumnIndex("position")));
            } while (c.moveToNext());
        }
        return test;
    }

//    public void resetTest(int test_id) {
//        SQLiteDatabase myDB = this.getWritableDatabase();
//        myDB.execSQL("update question set answered_id = 0 where test_id = " + test_id);
//    }

    public void unlockNext(int test_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update test set locked = " + 0 + " where id = " + test_id);
    }

    public boolean isImported() {
        String selectQuery = "SELECT * FROM config";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                return true;
            } while (c.moveToNext());
        }
        return false;
    }

    public void setImported() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("insert into config values(1)");
    }

    public int insertTest(String name, int locked) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("name", name);
        insertValues.put("locked", locked);
        Long id = myDB.insert("test", null, insertValues);
        return id.intValue();
    }

    public int insertQuestion(int test_id, String name) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("test_id", test_id);
        insertValues.put("name", name);
        Long id = myDB.insert("question", null, insertValues);
        return id.intValue();
    }

    public void updateCorrectAnswer(int question_id, int answer_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update question set correct_answered_id = " + answer_id + " where id = " + question_id);
    }

    public int insertAnswer(int question_id, String name) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("question_id", question_id);
        insertValues.put("name", name);
        Long id = myDB.insert("answer", null, insertValues);
        return id.intValue();
    }

    public void resetTest(int test_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update question set answered_id = 0 where test_id = " + test_id);
    }

    public void setResults(Point p) {
        SQLiteDatabase myDB = this.getWritableDatabase();


        String selectQuery = "SELECT id FROM points where test_id = " + p.getTest_id();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        int point_id = 0;
        if (c.moveToFirst()) {
            do {
                point_id = c.getInt(c.getColumnIndex("id"));
            } while (c.moveToNext());
        }

        if (point_id == 0){
            ContentValues insertValues = new ContentValues();
            insertValues.put("test_id", p.getTest_id());
            insertValues.put("correct", p.getCorrect());
            insertValues.put("wrong", p.getWrong());
            insertValues.put("empty", p.getEmpty());
            Long id = myDB.insert("points", null, insertValues);
        } else{
            myDB.execSQL("update points set correct = " + p.getCorrect()
                    +", wrong = " + p.getWrong()
                    +", empty = " + p.getEmpty()
                    + " where id = " + point_id);
        }
    }



    public Point getResults() {
        SQLiteDatabase myDB = this.getWritableDatabase();

        Point point = new Point();
        String selectQuery = "SELECT sum(correct) as correct, sum(wrong) as wrong," +
                " sum(empty) as emp FROM points";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                point.setCorrect(c.getInt(c.getColumnIndex("correct")));
                point.setWrong(c.getInt(c.getColumnIndex("wrong")));
                point.setEmpty(c.getInt(c.getColumnIndex("emp")));
            } while (c.moveToNext());
        }
        return point;
    }


}