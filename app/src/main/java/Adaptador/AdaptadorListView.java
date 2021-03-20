package Adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practica1.R;

import java.io.IOException;
import java.net.URL;

public class AdaptadorListView extends BaseAdapter {
    //Clase para hacer un adaptador de ListView personalizado

    // Argumentos de la clase

    private Context contexto;
    private LayoutInflater inflater;
    private String[] titulos;
    private String[] descripciones;
    private String[] fotosUrl;
    private String[] contactos;

    // Constructora de la clase
    public AdaptadorListView(Context pcontext, String[] pTitulos, String[] pDescripciones, String[] pFotosUrl, String[] pContactos) {
        contexto = pcontext;
        titulos = pTitulos;
        descripciones = pDescripciones;
        fotosUrl = pFotosUrl;
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
        // Configurar la imagen con la URL
        Bitmap bmp = null;
        try {
            URL url = new URL(fotosUrl[position]);
            // bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // img.setImageBitmap(bmp);
        return view;
    }
}
