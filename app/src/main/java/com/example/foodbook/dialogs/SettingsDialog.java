package com.example.foodbook.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodbook.R;
import com.example.foodbook.activities.NavActivity;
import com.example.foodbook.fragments.HomeFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingsDialog extends DialogFragment {
    SwitchMaterial s;
     int current_fragment;

    public SettingsDialog(int current_fragment) {
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

        int nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES)
            s.setChecked(true);

        Button apply_btn, cancel_btn;
        if (getDialog() == null){
            apply_btn = getView().findViewById(R.id.apply_btn);
            cancel_btn = getView().findViewById(R.id.cancel_btn1);
        }
        else
        {
            apply_btn = getDialog().findViewById(R.id.apply_btn);
            cancel_btn = getDialog().findViewById(R.id.cancel_btn1);
        }

        apply_btn.setOnClickListener(v -> {
            if (s.isChecked())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Intent intent = new Intent(getContext(), NavActivity.class);
            intent.putExtra("currentFragment", current_fragment);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            dismiss();
        });

        cancel_btn.setOnClickListener(v -> dismiss());
    }

}
