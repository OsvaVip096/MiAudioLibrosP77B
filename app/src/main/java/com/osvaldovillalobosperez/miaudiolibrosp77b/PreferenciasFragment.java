package com.osvaldovillalobosperez.miaudiolibrosp77b;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class PreferenciasFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
