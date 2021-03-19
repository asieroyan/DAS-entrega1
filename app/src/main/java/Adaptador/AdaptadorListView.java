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

    private Context contexto;
    private LayoutInflater inflater;
    private String[] titulos;
    private String[] descripciones;
    private String[] fotosUrl;
    private String[] contactos;

    public AdaptadorListView(Context pcontext, String[] pTitulos, String[] pDescripciones, String[] pFotosUrl, String[] pContactos) {
        contexto = pcontext;
        titulos = pTitulos;
        descripciones = pDescripciones;
        fotosUrl = pFotosUrl;
        contactos = pContactos;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int position) {
        return titulos[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.fila_lista, null);
        TextView nombre= (TextView) view.findViewById(R.id.textFilaListaTitulo);
        ImageView img=(ImageView) view.findViewById(R.id.fotoUrlLista);
        nombre.setText(titulos[position]);
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
