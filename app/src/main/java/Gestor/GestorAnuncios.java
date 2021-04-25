
package Gestor;

import android.content.Context;
import android.os.StrictMode;

import androidx.work.Data;
import androidx.work.ListenableWorker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import Modelo.Anuncio;

public class GestorAnuncios {
    // Instancia de patrón Singleton
    private static GestorAnuncios mGestorAnuncios;
    // Lista de anuncios
    private ArrayList<Anuncio> listaAnuncios;

    // Constructora privada
    private GestorAnuncios(){
        listaAnuncios = new ArrayList<Anuncio>();
    };


    public static GestorAnuncios getGestorAnuncios() {
        // Método estático que devuelve la instancia del gestor para patrón Singleton
        if (mGestorAnuncios == null) {
            mGestorAnuncios = new GestorAnuncios();
        }
        return mGestorAnuncios;
    }

    public boolean anadirAnuncio(String titulo, String descripcion, String foto, String contacto, String emailAnunciante) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/aoyanguren004/WEB/webservices_anadirAnuncio.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            String parametros = "foto=" + foto + "&titulo=" + titulo + "&descripcion=" + descripcion + "&contacto=" + contacto + "&emailAnunciante=" + emailAnunciante;
            out.print(parametros);
            out.close();
            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                inputStream.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void eliminarAnuncio(Context context, Anuncio anuncio) {
        /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/aoyanguren004/WEB/webservices_eliminarAnuncio.php";
        HttpURLConnection urlConnection = null;
        try {
            int codigo = anuncio.getCodigo();
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            String parametros = "codigo="+codigo;
            out.print(parametros);
            out.close();
            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            if (statusCode == 200) {
                System.out.print("deletion ok");
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
                JSONParser parser = new JSONParser();
                try {
                    JSONObject json = (JSONObject) parser.parse(result);
                    System.out.println(json);
                    boolean existe = (boolean) json.get("existe");
                    System.out.println(existe);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
        this.listaAnuncios.remove(anuncio);
    }

    public ArrayList<Anuncio> getAnuncios () {
        // Devolver la lista local de anuncios
        return this.listaAnuncios;

    }

    public ArrayList<Anuncio> cargarAnuncios (Context context){

        this.listaAnuncios = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/aoyanguren004/WEB/webservices_cargarAnuncios.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.close();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                inputStream.close();
                JSONParser parser = new JSONParser();
                try {
                    JSONArray jsonArray = (JSONArray) parser.parse(result);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = (JSONObject) jsonArray.get(i);
                        String codigoStr = (String) json.get("codigo");
                        String emailAnunciante = (String) json.get("emailAnunciante");
                        String titulo = (String) json.get("titulo");
                        String descripcion = (String) json.get("descripcion");
                        String contacto = (String) json.get("contacto");
                        String fotoAux = (String) json.get("foto");
                        String foto = fotoAux.replace(" ", "+");
                        int codigo = Integer.parseInt(codigoStr);

                        Anuncio anuncio = new Anuncio(codigo, foto, titulo, descripcion, contacto, emailAnunciante);

                        this.listaAnuncios.add(anuncio);
                    }
                    return this.listaAnuncios;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getCount() {
        // Devuelve el tamaño de la lista local
        return this.listaAnuncios.size();
    }

    public ArrayList<Anuncio> getAnunciosPorEmail (String email) {
        // Obtener una lista con los anuncios de un usuario concreto dado su email
        ArrayList<Anuncio> resultado = new ArrayList<Anuncio>();
        for (Anuncio anuncio : this.listaAnuncios) {
            // Recorre la lista actual y la añade a una nueva
            if(anuncio.getEmailAnunciante() == email) {
                resultado.add(anuncio);
            }
        }
        // Devuelve la lista nueva
        return resultado;
    }

    public String[] getTitulos (){
        // Recorre la lista actual y devuelve un array con todos los títulos
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getTitulo();
            i++;
        }
        return resultado;
    }

    public String[] getDescripciones (){
        // Recorre la lista actual y devuelve un array con todas las descripciones
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getDescripcion();
            i++;
        }
        return resultado;
    }

    public String[] getContactos (){
        // Recorre la lista actual y devuelve un array con todos los contactos
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getContacto();
            i++;
        }
        return resultado;
    }

    public String[] getFotosUrl (){
        // Recorre la lista actual y devuelve un array con todas las url de las fotos
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getFoto();
            i++;
        }
        return resultado;
    }
}
