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
                    " locked int, " +
                    " position int default 0 " +
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
                "(3, 'Ыйык диндердин эң акыркысы?' , 12, null, 1), " +
                "(4, 'Мухаммед (САВ) Пайгамбарга кайсы китеп түшүрүлгөн?' , 16, null, 1), " +
                "(5, 'Исламга кирүү үчүн эмне кылуу керек?' , 17, null, 1), " +
                "(6, 'Бир күн-түн ичинде мусулмандарга канча убак намаз окуу парз?' , 23, null, 1), " +
                "(7, 'Ислам аалымдарынын көз караштары боюнча кыз балдар үчүн, эркек балдар үчүн  балакат жашы канча жаш болуп эсептелет?' , 27, null, 1), " +
                "(8, 'Ачык-айкын, күмөнсүз далилдер менен келген  Алла Тааланын, же пайгамбарынын (САВ) буйруктарына карата айтылат.  М: беш убак намаз окуу, орозо кармоо, ж.б' , 30, null, 1), " +
                "(9, 'Бейиш ___ таманы астында.' , 35, null, 1), " +
                "(10, 'Намаз - мусулман болгон бардык аял-эркекке ___.' , 37, null, 1), " +
                "(11, 'Мухаммед (САВ) канчанчы жылы төрөлгөн?' , 41, null, 2), " +
                "(12, 'Азирети Муса пайгамбарга кайсы китеп түшүрүлгөн? ' , 45, null, 2), " +
                "(13, 'Ислам дининде жасалышы жана пайдаланышына кескин түрдө тыюу салынган нерселер. Алкоголдук ичимдиктерди ичүү, кумар ойноо, ойноштук кылуу, адам өлтүрүү, ушакчылык кылуу, жалаа жабуу ж.б ' , 51, null, 2), " +
                "(14, '“____ - ыймандын жарымы” (хадис)' , 56, null, 2), " +
                "(15, 'Ислам дининде рамазан айында орозо камоо  ____ ' , 58, null, 2), " +
                "(16, 'Белгилүү мал-мүлк түрлөрүн белгилүү бир бөлүгүн Аллах куранда көрсөткөн айрым мусулмандарга менчиктеп берүү. Бул ибадат парз.' , 62, null, 2), " +
                "(17, 'Шариат белгиленген убакытта атайын белгиленген амалдар менен Байтул харамды зыярат кылуу ибадаты эмне деп аталат?' , 68, null, 2), " +
                "(18, 'Даараттын канча парзы бар?' , 71, null, 2), " +
                "(19, 'Мухаммед (САВ) дын атасынын аты ким?' , 73, null, 2), " +
                "(20, 'Аллах Адам атаны эмнеден жараткан?' , 78, null, 2) "

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
                        "(12, 3, 'Ислам')," +
                        "(13, 4, 'Тоорат' ), " +
                        "(14, 4,'B.\tЗабур'), " +
                        "(15, 4,'C.\tИнжил'), " +
                        "(16, 4,'D.\tКуран ' ), " +
                        "(17, 5,'A.\tШахадат келмесин айтуу '), " +
                        "(18, 5,'B.\tБисмиллахты айтуу'), " +
                        "(19, 5,'C.\tОрозо тутуу' ), " +
                        "(20, 5,'D.\tНамаз окуу'), " +
                        "(21, 6,'2'), " +
                        "(22, 6,'3' ), " +
                        "(23, 6,'5'), " +
                        "(24, 6,'6'), " +
                        "(25, 7,'A.\tКыз 16-18 эркек 17-18' ), " +
                        "(26, 7,'B.\tКыз 7-9 эркек 10-11'), " +
                        "(27, 7,'C.\tКыз 9-15 эркек 12-15 '), " +
                        "(28, 7,'D.\tКыз 17-18 Эркек 17-18' ), " +
                        "(29, 8,'A.\tСүннөт'), " +
                        "(30, 8,'B.\tПарз '), " +
                        "(31, 8,'C.\tВажиб' ), " +
                        "(32, 8,'D.\tМубах'), " +
                        "(33, 9,'A.\tАтанын'), " +
                        "(34, 9,'B.\tДостун' ), " +
                        "(35, 9,'C.\tЭненин '), " +
                        "(36, 9,'D.\tКошунанын'), " +
                        "(37,10,'A.\tПарз ' ), " +
                        "(38,10,'B.\tВажиб'), " +
                        "(39,10,'C.\tСүннөт'), " +
                        "(40,10,'D.\tМустахаб' ), " +
                        "(41, 11,'A.\t571 '), " +
                        "(42, 11,'B.\t671'), " +
                        "(43, 11,'-571' ), " +
                        "(44, 11,'D.\t470'), " +
                        "(45, 12,'A.\tТоорат '), " +
                        "(46, 12,'B.\tЗабур' ), " +
                        "(47, 12,'C.\tИнжил'), " +
                        "(48, 12,'D.\tКуран'), " +
                        "(49, 13,'A.\tМандуб' ), " +
                        "(50, 13,'B.\tМубах'), " +
                        "(51, 13,'C.\tХарам '), " +
                        "(52, 13,'D.\tМакрух' ), " +
                        "(53, 14,'A.\tЖылмаюу'), " +
                        "(54, 14,'B.\tТакыбалык'), " +
                        "(55, 14,'C.\tСүкүт кормоо' ), " +
                        "(56, 14,'D.\tТазалык '), " +
                        "(57, 15,'A.\tВажиб '), " +
                        "(58, 15,'B.\tПарз ' ), " +
                        "(59, 15,'C.\tСүннөт'), " +
                        "(60, 15,'D.\tНафил'), " +
                        "(61, 16,'A.\tСадака' ), " +
                        "(62, 16,'B.\tЗекет '), " +
                        "(63, 16,'C.\tНамаз'), " +
                        "(64, 16,'D.\tСалык' ), " +
                        "(65, 17,'A.\tНамаз'), " +
                        "(66, 17,'B.\tЗекет'), " +
                        "(67, 17,'C.\tСаякат' ), " +
                        "(68, 17,'D.\tАжылык '), " +
                        "(69, 18,'5'), " +
                        "(70, 18,'6' ), " +
                        "(71, 18,'4'), " +
                        "(72, 18,'3'), " +
                        "(73, 19,'A.\tАбдулла' ), " +
                        "(74, 19,'B.\tАбдулмуталиб'), " +
                        "(75, 19,'C.\tАбуталиб'), " +
                        "(76, 19,'D.\tИбрахим' ), " +
                        "(77, 20,'A.\tОттон'), " +
                        "(78, 20,'B.\tЫлайдан '), " +
                        "(79, 20,'C.\tТаштан' ), " +
                        "(80, 20,'D.\tНурдан') "
        );
        db.execSQL("insert into test(id, name, locked) values" +
                "(1, '1', 0 ), " +
                "(2, '2', 1 )"


        );
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

    public void resetTest(int test_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update question set answered_id = 0 where test_id = " + test_id);
    }

    public void unlockNext(int test_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("update test set locked = " + 0 + " where id = " + test_id);
    }


}