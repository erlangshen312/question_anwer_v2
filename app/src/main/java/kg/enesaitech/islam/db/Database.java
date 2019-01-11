package kg.enesaitech.islam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by e.zhumanasyrov on 10.01.2019.
 */

public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "islam.db";
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
                    " locked int" +
                    " )";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // When app is starting, this data will set to SQLite
        db.execSQL(SQL_QUESTION);
        db.execSQL(SQL_ANSWER);
        db.execSQL(SQL_TEST);

        db.execSQL("insert into question(id, name, correct_answered_id, answered_id, test_id) values" +
                "(1, 'Бүткүл ааламды ким жараткан?' , 2, null, 1 ), " +
                "(2, 'Ислам дининде пайгамбар ким?' , 8, null, 1 ), " +
                "(3, 'Ыйык диндердин эң акыркысы?' , 12, null, 1) "

        );

        db.execSQL("insert into answer(id, question_id, name) values" +
                "(1, 1, 'Өзүнөн өзү жаралган'), " +
                "(2, 1, 'Аллах жараткан ' ), " +
                "(3, 1, 'Эч ким билбейт' )," +
                "(4, 1, 'Материя жараткан')," +
                "(5, 2, 'Иса (а.с)')," +
                "(6, 2, 'Муса (а.с)')," +
                "(7, 2, 'Дауд (а.с)')," +
                "(8, 2, 'Мухаммед (САВ) ')," +
                "(9, 3, 'Христиан')," +
                "(10, 3, 'Буддизм')," +
                "(11, 3, 'Индуизм')," +
                "(12, 3, 'Ислам')"
        );
        db.execSQL("insert into test(id, name, locked) values" +
                "(1, 'Test 1', 0), " +
                "(2, 'Test 2', 1), " +
                "(3, 'Test 3', 1) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public ArrayList<Test> getTests() {
        ArrayList<Test> tests = new ArrayList<Test>();
        String selectQuery = "SELECT id, name, locked FROM test order by id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Test test = new Test();
                test.setId(c.getInt(c.getColumnIndex("id")));
                test.setName(c.getString(c.getColumnIndex("name")));
                test.setLocked(c.getInt(c.getColumnIndex("locked")));
                tests.add(test);
            } while (c.moveToNext());
        }
        return tests;
    }



}
