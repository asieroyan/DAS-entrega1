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
        super.onNewToken(s);
        System.out.println(s);
        GestorDispositivos.getGestorDispositivos().anadirToken(s);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {

        }

        if (remoteMessage.getNotification() != null) {

        }

    }
}
