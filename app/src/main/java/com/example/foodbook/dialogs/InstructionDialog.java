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
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodbook.R;
import com.example.foodbook.activities.NavActivity;
import com.example.foodbook.fragments.HomeFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;


public class InstructionDialog extends DialogFragment {

    TextView textView;

    public InstructionDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instruction_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.instruction_dialog, null));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        textView = getDialog().findViewById(R.id.text);

        getDialog().findViewById(R.id.next1_btn).setOnClickListener(view -> {
            textView.setText(getString(R.string.home_instruction));
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home, 0, 0, 0);
            getDialog().findViewById(R.id.next1_btn).setVisibility(View.INVISIBLE);
            getDialog().findViewById(R.id.next2_btn).setVisibility(View.VISIBLE);
        });

        getDialog().findViewById(R.id.next2_btn).setOnClickListener(view -> {
            textView.setText(getString(R.string.profile_instruction));
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_profile, 0, 0, 0);
            getDialog().findViewById(R.id.next2_btn).setVisibility(View.INVISIBLE);
            getDialog().findViewById(R.id.next3_btn).setVisibility(View.VISIBLE);
        });

        getDialog().findViewById(R.id.next3_btn).setOnClickListener(view -> {
            textView.setText(getString(R.string.search_instruction));
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0);
            getDialog().findViewById(R.id.next3_btn).setVisibility(View.INVISIBLE);
            getDialog().findViewById(R.id.next4_btn).setVisibility(View.VISIBLE);
        });

        getDialog().findViewById(R.id.next4_btn).setOnClickListener(view -> {
            textView.setText(getString(R.string.top10_instruction));
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_top10likes, 0, 0, 0);
            getDialog().findViewById(R.id.next4_btn).setVisibility(View.INVISIBLE);
            getDialog().findViewById(R.id.lets_start_btn).setVisibility(View.VISIBLE);
            getDialog().findViewById(R.id.skip_btn).setVisibility(View.INVISIBLE);
        });

        getDialog().findViewById(R.id.lets_start_btn).setOnClickListener(view -> {
            dismiss();
        });

        getDialog().findViewById(R.id.skip_btn).setOnClickListener(view -> {
            dismiss();
        });


    }

}

