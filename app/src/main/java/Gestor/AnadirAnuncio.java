package Gestor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AnadirAnuncio extends Worker {
    public AnadirAnuncio(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Petición http
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
            // Añadir parámetros a la petición
            String foto= getInputData().getString("foto");
            String titulo= getInputData().getString("titulo");
            String descripcion= getInputData().getString("descripcion");
            String contacto= getInputData().getString("contacto");
            String emailAnunciante= getInputData().getString("emailAnunciante");
            String parametros = "foto="+foto+"&titulo="+titulo+"&descripcion="+descripcion+"&contacto="+contacto+"&emailAnunciante="+emailAnunciante;
            out.print(parametros);
            out.close();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                // Parsea y devuelve el resultado
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
                Data resultados = new Data.Builder()
                        .putString("resultado",result)
                        .build();

                return Result.success(resultados);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
