package moviles.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import moviles.hotel.data.Huesped;
import moviles.hotel.data.Telefono;

public class HuespedAdapter extends ArrayAdapter<Huesped> {

    public HuespedAdapter(@NonNull Context context, @NonNull List<Huesped> objects) {
        super( context, 0, objects );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(null == convertView){
            convertView = inflater.inflate( R.layout.huesped_item_list, parent, false );
        }
        TextView usuario = (TextView) convertView.findViewById( R.id.txtUsuario );
        TextView password = (TextView) convertView.findViewById( R.id.txtPaswword );
        TextView name = (TextView) convertView.findViewById( R.id.textNombre );
        TextView email = (TextView) convertView.findViewById( R.id.textEmail );
        TextView telefono = (TextView) convertView.findViewById( R.id.txtTelefono );

        Huesped huespedActual = getItem( position );
        Telefono tel = huespedActual.getTelefono();

        usuario.setText( huespedActual.getUsuario() );
        password.setText( huespedActual.getPassword() );
        name.setText( huespedActual.getNombre() );
        email.setText( huespedActual.getEmail() );
        telefono.setText( String.valueOf( tel.getTelefono() ) );

        return convertView;
    }
}
