package moviles.hotel;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import moviles.hotel.data.Huesped;
import moviles.hotel.data.Telefono;

public class HuespedRecyclerAdapter extends RecyclerView.Adapter<HuespedRecyclerAdapter.ViewHolder>{

    private Cursor cursorListaHuesped;
    private OnItemClickListener listenerClick;

    public  HuespedRecyclerAdapter(OnItemClickListener listenerClick) {
        this.listenerClick = listenerClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.huesped_item_list,parent,false );
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursorListaHuesped.moveToPosition(position);
        Huesped huespedSeleccionado = new Huesped( cursorListaHuesped );
        Telefono telefononuevo = new Telefono(cursorListaHuesped );
        holder.usuario.setText( huespedSeleccionado.getUsuario() );
        holder.password.setText( huespedSeleccionado.getPassword() );
        holder.name.setText( huespedSeleccionado.getNombre() );
        holder.email.setText( huespedSeleccionado.getEmail());
        holder.telefono.setText(String.valueOf( telefononuevo.getTelefono() ));
    }

    @Override
    public int getItemCount() {
        if (cursorListaHuesped!=null)
            return cursorListaHuesped.getCount();
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor){
        if(nuevoCursor!=null){
            cursorListaHuesped = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    interface OnItemClickListener{
        public void onClick(ViewHolder view, Huesped huespedActualizado);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView usuario;
        TextView password;
        TextView name;
        TextView email;
        TextView telefono;
        public ViewHolder(View itemView) {
            super( itemView );
            usuario = (TextView) itemView.findViewById( R.id.txtUsuario );
            password = (TextView)itemView.findViewById( R.id.txtPaswword );
            name = (TextView) itemView.findViewById( R.id.textNombre );
            email = (TextView) itemView.findViewById( R.id.textEmail );
            telefono = (TextView) itemView.findViewById( R.id.txtTelefono );
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View view) {
            Huesped huespedClickeado = obtenerUsuario( getAdapterPosition());
            huespedClickeado.setPassword( "1111" );
            listenerClick.onClick( this,huespedClickeado);
        }
    }

    private Huesped obtenerUsuario(int posicion){
        if (cursorListaHuesped!=null){
            cursorListaHuesped.moveToPosition( posicion );
            Huesped huespedObtenido = new Huesped( cursorListaHuesped);
            return huespedObtenido;
        }
        return null;
    }
}
