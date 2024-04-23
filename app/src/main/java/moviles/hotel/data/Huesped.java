package moviles.hotel.data;

import android.content.ContentValues;
import android.database.Cursor;

import moviles.hotel.data.HuespedContract.HuespedEntry;

public class Huesped {
    private String usuario;
    private String password;
    private String nombre;
    private String email;

    private Telefono telefono;

    public Huesped(String usuario, String password, String nombre, String email) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HuespedEntry.col_usuario, usuario);
        values.put(HuespedEntry.col_password, password);
        values.put(HuespedEntry.col_nombre, nombre);
        values.put(HuespedEntry.col_email, email);
        return values;
    }

    public Huesped(Cursor cursor){
        this.usuario = cursor.getString( cursor.getColumnIndex( HuespedEntry.col_usuario ) );
        this.password = cursor.getString( cursor.getColumnIndex( HuespedEntry.col_password ) );
        this.nombre = cursor.getString( cursor.getColumnIndex( HuespedEntry.col_nombre ) );
        this.email = cursor.getString( cursor.getColumnIndex( HuespedEntry.col_email ) );
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }
}
