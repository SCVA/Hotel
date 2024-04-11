package moviles.hotel.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import moviles.hotel.data.HuespedContract.HuespedEntry;

public class HotelDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotel";
    private static final int DATABASE_VERSION = 1;
    public HotelDBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ HuespedEntry.TABLE_NAME + " (" +
                HuespedEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HuespedEntry.col_usuario+" TEXT NOT NULL, " +
                HuespedEntry.col_password+" TEXT NOT NULL, " +
                HuespedEntry.col_nombre+" TEXT NOT NULL, " +
                HuespedEntry.col_email+" TEXT NOT NULL, " +
                HuespedEntry.col_telefono+" NUMERIC(12,0) NOT NULL," +
                "UNIQUE ("+HuespedEntry.col_usuario+"), UNIQUE("+HuespedEntry.col_email+"), UNIQUE("+HuespedEntry.col_telefono+"))");
    }

    public long saveHuesped(Huesped huesped) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                HuespedEntry.TABLE_NAME,
                null,
                huesped.toContentValues());
    }

    public Cursor getTodosHuesped() {
        return getReadableDatabase()
                .query(
                        HuespedEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getHuespedByUser(String user) {
        Cursor c = getReadableDatabase().query(
                HuespedEntry.TABLE_NAME,
                null,
                HuespedEntry.col_usuario + " LIKE ?",
                new String[]{user},
                null,
                null,
                null);
        return c;
    }

    public Cursor getHuespedByUser(String user, String password) {
        Cursor c = getReadableDatabase().query(
                HuespedEntry.TABLE_NAME,
                null,
                HuespedEntry.col_usuario + " LIKE ? AND "+HuespedEntry.col_password + " LIKE ?",
                new String[]{user,password},
                null,
                null,
                null);
        return c;
    }

    public int deleteHuesped(String user) {
        return getWritableDatabase().delete(
                HuespedEntry.TABLE_NAME,
                HuespedEntry.col_usuario + " LIKE ?",
                new String[]{user});
    }

    public int updateHuesped(Huesped huesped, String user) {
        return getWritableDatabase().update(
                HuespedEntry.TABLE_NAME,
                huesped.toContentValues(),
                HuespedEntry.col_usuario + " LIKE ?",
                new String[]{user}
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
