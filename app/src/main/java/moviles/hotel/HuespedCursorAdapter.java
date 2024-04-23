package moviles.hotel;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import moviles.hotel.data.Huesped;
import moviles.hotel.data.Telefono;

public class HuespedCursorAdapter extends CursorAdapter {


    public HuespedCursorAdapter(Context context, Cursor c) {
        super( context, c,0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate( R.layout.huesped_item_list,viewGroup,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView usuario = (TextView) view.findViewById( R.id.txtUsuario );
        TextView password = (TextView)view.findViewById( R.id.txtPaswword );
        TextView name = (TextView) view.findViewById( R.id.textNombre );
        TextView email = (TextView) view.findViewById( R.id.textEmail );
        TextView telefono = (TextView) view.findViewById( R.id.txtTelefono );

        Huesped huespedActual = new Huesped( cursor );
        Telefono tel = new Telefono( cursor );

        usuario.setText( huespedActual.getUsuario() );
        password.setText( huespedActual.getPassword() );
        name.setText( huespedActual.getNombre() );
        email.setText( huespedActual.getEmail() );
        telefono.setText( String.valueOf( tel.getTelefono() ) );
    }
}
