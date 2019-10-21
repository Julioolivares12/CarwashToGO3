package com.julio.carwashtogo3.services;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.Token;

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

}
