package com.julio.carwashtogo3.ui.cliente.configuracion;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.julio.carwashtogo3.R;

public class ConfiguracionCliente extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        addPreferencesFromResource(R.xml.prefencias_usuario);
    }
}
