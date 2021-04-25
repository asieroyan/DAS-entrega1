package com.example.practica1;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import Gestor.GestorDispositivos;

public class ServicioFirebase extends FirebaseMessagingService {

    public ServicioFirebase () {}

    @Override
    public void onNewToken(@NonNull String s) {
        // Obtiene el token del dispositivo en el que se ha instalado la app
        super.onNewToken(s);
        System.out.println(s);
        GestorDispositivos.getGestorDispositivos().anadirToken(s);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Método vacío (sólo recibe una notificación si la app está cerrada
        if (remoteMessage.getData().size() > 0) {

        }

        if (remoteMessage.getNotification() != null) {

        }

    }
}
