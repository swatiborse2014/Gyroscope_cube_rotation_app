package com.example.gyroscopeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.gyroscopeapp.fragment.OrientationFragment;
import com.example.gyroscopeapp.R;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (checkSensorAvailability(Sensor.TYPE_GYROSCOPE)) {
        } else {
            displayHardwareMissingWarning();
        }

        if (savedInstanceState == null) {
            OrientationFragment themeFragment = new OrientationFragment();
            forwardFragment(themeFragment);
        }
    }

    public void forwardFragment(final Fragment fragment) {
        new Handler().postDelayed(() -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_fragment, fragment);
            transaction.addToBackStack(fragment.getClass().getName());
            transaction.commitAllowingStateLoss();
        }, 200);
    }

    private void displayHardwareMissingWarning() {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false);
        ad.setTitle(getResources().getString(R.string.gyroscope_missing));
        ad.setMessage(getResources().getString(R.string.gyroscope_missing_message));
        ad.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.OK), (dialog, which) -> dialog.dismiss());
        ad.show();
    }

    public boolean checkSensorAvailability(int sensorType) {
        boolean isSensor = false;
        if (sensorManager.getDefaultSensor(sensorType) != null) {
            isSensor = true;
        }
        return isSensor;
    }
}