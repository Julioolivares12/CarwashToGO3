package com.julio.carwashtogo3.ui.cliente.configuracion;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConfiguracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content
                        ,new ConfiguracionCliente())
                .commit();

    }
}
