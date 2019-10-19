package com.julio.carwashtogo3.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CarwashService extends FirebaseMessagingService {
    public static final String TAG="NOTIFICACION_FIREBASE";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived ( remoteMessage );
        String notificacion = remoteMessage.getNotification ().getBody();
        if (notificacion != null && !notificacion.isEmpty ()){
            Log.i (TAG,notificacion);
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken ( s );
        Log.i (TAG,"Tokken"+s);
    }
}
