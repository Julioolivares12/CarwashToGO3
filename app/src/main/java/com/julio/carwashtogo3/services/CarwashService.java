package com.julio.carwashtogo3.services;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.Token;

public class CarwashService extends FirebaseMessagingService {
    public static final String TAG="NOTIFICACION_FIREBASE";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived ( remoteMessage );
        String notificacion = remoteMessage.getNotification ().getBody();

        if (notificacion != null && !notificacion.isEmpty()){
            Log.i (TAG,notificacion);
            String title = remoteMessage.getNotification().getTitle();
            String cuerpo = remoteMessage.getNotification().getBody();
            mostrarNotificacion (title,cuerpo);
        }

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken ( token );

        Log.i (TAG,"Tokken"+token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        DatabaseReference tokenReference = database.getReference ( Constantes.TOKEN_REFERENCE );

        String key =tokenReference.push().getKey();
        final Token to = new Token ();
        to.setUID (key);
        to.setTokenDevice (token);
        if (key != null){
            tokenReference.child(key).setValue (to).addOnFailureListener ( new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i ( "TOKEN ERROR",e.getMessage ());
                }
            } );
        }


    }

    private void mostrarNotificacion(String titulo , String mensaje){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder (this).setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle (titulo).setContentText (mensaje);
        NotificationManager notificationManager = (NotificationManager) getSystemService ( Context.NOTIFICATION_SERVICE );
        mBuilder.setSound ( RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        assert notificationManager != null;
        notificationManager.notify (1,mBuilder.build ());
    }

}
