package com.julio.carwashtogo3.services;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Suscribir extends Application {
    @Override
    public void onCreate() {
        super.onCreate ();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (this);
        boolean recibir_notificacion_paquete = preferences.getBoolean ("paquete",false);
        boolean recibir_notificacion_promocion = preferences.contains("promocion");
        if (recibir_notificacion_paquete){
            FirebaseMessaging.getInstance ().subscribeToTopic ("paquete").addOnSuccessListener ( new OnSuccessListener<Void> () {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e ( "SUSCRIPCION PAQUETE","suscrito con exito");
                }
            } );
        }else{
            FirebaseMessaging.getInstance ().unsubscribeFromTopic ("paquete").addOnSuccessListener ( new OnSuccessListener<Void> () {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i ("suscripcion cancelada","suscripcion cancelada con exito");
                }
            } );
        }

        if (recibir_notificacion_promocion){
            FirebaseMessaging.getInstance ().subscribeToTopic ("promocion").addOnSuccessListener ( new OnSuccessListener<Void> () {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e ( "SUSCRIPCION PAQUETE","suscrito con exito");
                }
            } );
        }
        else {
            FirebaseMessaging.getInstance ().unsubscribeFromTopic ("promocion").addOnSuccessListener ( new OnSuccessListener<Void> () {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e ( "SUSCRIPCION_CANCELADA: ","suscripcion cancelada con exito");
                }
            } );
        }
    }
}
