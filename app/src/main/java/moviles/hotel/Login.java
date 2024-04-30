package moviles.hotel;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import moviles.hotel.data.HotelDBHelper;
import moviles.hotel.data.Huesped;
import moviles.hotel.data.HuespedContract;
import moviles.hotel.data.Telefono;
import moviles.hotel.data.TelefonoContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment implements View.OnClickListener {

    private Boolean recyclerActivo = true;
    private HotelDBHelper db;
    private Button btnLogin;
    private EditText usrText;
    private EditText passwordText;
    private ListView listaHuesped;
    private RecyclerView listaHuespedR;
    private HuespedRecyclerAdapter adapatador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_login, container, false );
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        btnLogin = (Button) getActivity().findViewById( R.id.btnIngresar );
        usrText = (EditText)  getActivity().findViewById( R.id.userText);
        passwordText = (EditText) getActivity().findViewById( R.id.passText ) ;
        btnLogin.setOnClickListener( this );
        db = new HotelDBHelper( getContext() );


        if(recyclerActivo){
            listaHuespedR = (RecyclerView) getActivity().findViewById( R.id.listaItemsR );
            listaHuespedR.setHasFixedSize( true );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );
            listaHuespedR.setLayoutManager( linearLayoutManager );
            adapatador = new HuespedRecyclerAdapter( this::onClick2 );
            listaHuespedR.setAdapter( adapatador );
            loadPersonas();
        }else{
            //listaHuesped = (ListView) getActivity().findViewById( R.id.listViewHuesped );

            // Adaptador lista
            /*
            ArrayList<Huesped> listaHuespedes = new ArrayList<Huesped>();
            Cursor cursor = db.getUsuarioTelefono();
            if(cursor.moveToFirst()){
                do{
                    Huesped nuevo = new Huesped( cursor );
                    Telefono telefononuevo = new Telefono( cursor );
                    nuevo.setTelefono( telefononuevo );
                    listaHuespedes.add(nuevo);
                }while(cursor.moveToNext());
            }
            HuespedAdapter adaptador = new HuespedAdapter( getActivity(), listaHuespedes);
            listaHuesped.setAdapter( adaptador );
            */

            //Adaptador Cursores
            Cursor cursor = db.getUsuarioTelefono();
            HuespedCursorAdapter adaptador = new HuespedCursorAdapter( getActivity(),cursor );
            listaHuesped.setAdapter( adaptador );

            listaHuesped.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    cursor.move( i );
                    Huesped huespedModificado = new Huesped( cursor );
                    huespedModificado.setPassword( "1111" );
                    db.updateHuesped(  huespedModificado, huespedModificado.getUsuario() );
                }
            } );
        }
    }

    @Override
    public void onClick(View view) {
        Cursor cursor2 = db.getHuespedByUser( usrText.getText().toString() );
        if (!cursor2.moveToNext()){
            Huesped hps2 = new Huesped( usrText.getText().toString(),passwordText.getText().toString(),usrText.getText().toString(),usrText.getText().toString()+"@libertadores.edu.co");
            Telefono tel = new Telefono( usrText.getText().toString(),(long)(Math.random()*100000000+1) );
            db.saveHuesped( hps2,tel );
        }
        Cursor cursor = db.getHuespedByUser( usrText.getText().toString(), passwordText.getText().toString() );
        if(cursor.moveToNext()){
            Huesped hps = new Huesped( cursor );
            if(view.getId()==btnLogin.getId()){
                Bundle bundle = new Bundle();
                bundle.putString( "userName", hps.getNombre());
                Navigation.findNavController( view ).navigate( R.id.menu,bundle);
            }
        }else{
            Toast.makeText(getContext(),"Credenciales invalidas",Toast.LENGTH_LONG).show();
        }
    }

    public void onClick2(HuespedRecyclerAdapter.ViewHolder view, Huesped huespedModificado) {
        db.updateHuesped(  huespedModificado, huespedModificado.getUsuario() );
        loadPersonas();
    }

    private void loadPersonas() {
        new HuespedLoaderTask().execute( );
    }

    private class HuespedLoaderTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getUsuarioTelefono();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                adapatador.swapCursor( cursor );
            }
        }
    }
}