package Adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practica1.R;

public class AdaptadorListView extends BaseAdapter {
    //Clase para hacer un adaptador de ListView personalizado

    // Argumentos de la clase

    private Context contexto;
    private LayoutInflater inflater;
    private String[] titulos;
    private String[] descripciones;
    private String[] fotos;
    private String[] contactos;

    // Constructora de la clase
    public AdaptadorListView(Context pcontext, String[] pTitulos, String[] pDescripciones, String[] pFotos, String[] pContactos) {
        contexto = pcontext;
        titulos = pTitulos;
        descripciones = pDescripciones;
        fotos = pFotos;
        contactos = pContactos;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Obtener el tamaño del adaptador
    @Override
    public int getCount() {
        return titulos.length;
    }

    // Obtener un elemento concreto del adaptador
    @Override
    public Object getItem(int position) {
        return titulos[position];
    }

    // Obtener el Id de un elemento concreto del adaptador
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Describir y configurar cada fila del listView
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.fila_lista, null);

        // Configurar los elementos del layout de la fila
        TextView nombre= (TextView) view.findViewById(R.id.textFilaListaTitulo);
        nombre.setText(titulos[position]);
        ImageView img=(ImageView) view.findViewById(R.id.fotoUrlLista);

        //Decodificar la imagen de base 64 a BitMap y ponerla en el imageView
        byte[] decodedString = Base64.decode(fotos[position], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
        return view;
    }
}
