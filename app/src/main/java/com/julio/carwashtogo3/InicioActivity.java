package com.julio.carwashtogo3;

import android.content.Intent;
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

public class InicioActivity extends AppCompatActivity {

    private Toolbar tb_inicio;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref_usuarios= firebaseDatabase.getReference(Constantes.REF_USUARIOS);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        tb_inicio = findViewById(R.id.tb_inicio);
        setSupportActionBar(tb_inicio);

        tb_inicio.setTitle("");
        firebaseAuth = FirebaseAuth.getInstance();
        Button toolbarLogin = findViewById(R.id.btnToolbarLogin);
        Button toolbarRegistro = findViewById(R.id.btnToolbarRegistro);
        toolbarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        toolbarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioActual = firebaseAuth.getCurrentUser();
        if (usuarioActual != null){
            updateUI(usuarioActual);
        }
    }

    private void updateUI(FirebaseUser firebaseUser){


        ref_usuarios.child(firebaseUser.getUid()).child("rol").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String rol  = dataSnapshot.getValue(String.class);
                Intent irPrincipal = new Intent(getApplicationContext(),MainActivity.class);
                irPrincipal.putExtra(Constantes.ROL_USER,rol);
                startActivity(irPrincipal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "ocurrio un error"+databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
