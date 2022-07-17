package com.example.foodbook.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import com.example.foodbook.R;
import com.example.foodbook.activities.NavActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;


public class SettingsDialog extends DialogFragment {
    SwitchMaterial s;
    RadioButton english, spanish;
    String current_fragment;

    public SettingsDialog(String current_fragment) {
        this.current_fragment = current_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.settings_dialog, null));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        s = getDialog().findViewById(R.id.DarkModeSwitch);
        spanish = getDialog().findViewById(R.id.spanishRadioButton);
        english = getDialog().findViewById(R.id.englishRadioButton);

        int nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES)
            s.setChecked(true);
        String current_lang = getLocale();
        if (current_lang.equals("en_US"))
            english.setChecked(true);
        else
            spanish.setChecked(true);

        Button apply_btn, cancel_btn;
        if (getDialog() == null){
            apply_btn = getView().findViewById(R.id.first_next_btn);
            cancel_btn = getView().findViewById(R.id.skip_btn);
        }
        else
        {
            apply_btn = getDialog().findViewById(R.id.first_next_btn);
            cancel_btn = getDialog().findViewById(R.id.skip_btn);
        }

        apply_btn.setOnClickListener(v -> {
            if (s.isChecked())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            if (spanish.isChecked())
                setLocale("es");
            Intent intent = new Intent(getContext(), NavActivity.class);
            intent.putExtra("currentFragment", current_fragment);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            dismiss();
        });

        cancel_btn.setOnClickListener(v -> dismiss());
    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private String getLocale() {
        Resources res = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return res.getConfiguration().getLocales().get(0).toString();
        }
        return "";
    }

}
