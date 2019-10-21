package com.julio.carwashtogo3.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class CarwashFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh ();
        String t = FirebaseInstanceId.getInstance ().getToken ();

    }

    private void sendTokenToFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        DatabaseReference reference = database.getReference ("");
    }
}
