package moviles.hotel.data;

import moviles.hotel.data.HuespedContract.HuespedEntry;
import moviles.hotel.data.TelefonoContract.TelefonoEntry;
import android.content.ContentValues;
import android.database.Cursor;

public class Telefono {
    private String usuario;
    private long telefono;

    public Telefono(String usuario, long telefono) {
        this.usuario = usuario;
        this.telefono = telefono;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put( HuespedEntry.col_usuario, usuario);
        values.put( TelefonoEntry.col_telefono, telefono);
        return values;
    }

    public Telefono(Cursor cursor){
        this.usuario = cursor.getString( cursor.getColumnIndex( HuespedEntry.col_usuario ) );
        this.telefono = cursor.getLong( cursor.getColumnIndex( TelefonoEntry.col_telefono ) );
    }

    public String getUsuario() {
        return usuario;
    }

    public long getTelefono() {
        return telefono;
    }
}
