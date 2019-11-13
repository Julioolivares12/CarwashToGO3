package com.julio.carwashtogo3;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.common.Constantes;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class InicioActivity extends AppCompatActivity {

    private Toolbar tb_inicio;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref_usuarios= firebaseDatabase.getReference(Constantes.REF_USUARIOS);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        AppCenter.start(getApplication(), "fea5db51-1112-4759-81e7-b37e56ef8879",
                Analytics.class, Crashes.class);


        //muestro actividad 4 segundos
        new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public void run() {

                Intent intent = new Intent ( InicioActivity.this,LoginActivity.class );
                startActivity ( intent );
                finish ();


            }
        },4000 );

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser usuarioActual = firebaseAuth.getCurrentUser();
//        if (usuarioActual != null){
//            updateUI(usuarioActual);
//        }
//    }
//
//    private void updateUI(FirebaseUser firebaseUser){
//
//
//        ref_usuarios.child(firebaseUser.getUid()).child("rol").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String rol  = dataSnapshot.getValue(String.class);
//                Intent irPrincipal = new Intent(getApplicationContext(),MainActivity.class);
//                irPrincipal.putExtra(Constantes.ROL_USER,rol);
//                startActivity(irPrincipal);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), "ocurrio un error"+databaseError.getDetails(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
