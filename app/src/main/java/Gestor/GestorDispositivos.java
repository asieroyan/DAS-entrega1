package Gestor;

import android.os.StrictMode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Modelo.Anuncio;

public class GestorDispositivos {
    private static GestorDispositivos mGestorDispositivos;

    private GestorDispositivos () {}

    public static GestorDispositivos getGestorDispositivos () {
        if (mGestorDispositivos == null) {
            mGestorDispositivos = new GestorDispositivos();
        }
        return mGestorDispositivos;
    }

    public void anadirToken (String token) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/aoyanguren004/WEB/webservices_anadirToken.php";
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
            String parametros = "token=" + token;
            out.print(parametros);
            out.close();
            int statusCode = urlConnection.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
