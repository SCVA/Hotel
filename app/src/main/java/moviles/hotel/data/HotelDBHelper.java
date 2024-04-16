package moviles.hotel.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

import androidx.annotation.Nullable;

import moviles.hotel.data.HuespedContract.HuespedEntry;
import moviles.hotel.data.TelefonoContract.TelefonoEntry;

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
                "UNIQUE ("+HuespedEntry.col_usuario+"), UNIQUE("+HuespedEntry.col_email+"))");

        sqLiteDatabase.execSQL( "CREATE TABLE " + TelefonoEntry.TABLE_NAME + " (" +
                HuespedEntry.col_usuario+" TEXT NOT NULL, " +
                TelefonoEntry.col_telefono+" NUMERIC(12,0) NOT NULL," +
                "PRIMARY KEY ("+HuespedEntry.col_usuario+","+TelefonoEntry.col_telefono+")," +
                "UNIQUE("+TelefonoEntry.col_telefono+"),"+
                "FOREIGN KEY ("+HuespedEntry.col_usuario+") REFERENCES "+ HuespedEntry.TABLE_NAME +"("+ HuespedEntry.col_usuario+") ON DELETE CASCADE)");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen( db );
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    public long saveHuesped(Huesped huesped, Telefono telefono) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(
                HuespedEntry.TABLE_NAME,
                null,
                huesped.toContentValues());
        return saveTelefono( telefono );
    }

    public long saveTelefono(Telefono telefono){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                TelefonoEntry.TABLE_NAME,
                null,
                telefono.toContentValues());
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

    public Cursor getUsuarioTelefono(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String tables = HuespedEntry.TABLE_NAME+" INNER JOIN "+TelefonoEntry.TABLE_NAME+
                " on "+HuespedEntry.TABLE_NAME+"."+HuespedEntry.col_usuario+" = "+TelefonoEntry.TABLE_NAME+"."+HuespedEntry.col_usuario;
        builder.setTables( tables);
        //String columnas[] = new String["user","name","tel"];
        //return builder.query( db, columnas,null,null,null,null,null );
        return builder.query( db, null,null,null,null,null,null );
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
