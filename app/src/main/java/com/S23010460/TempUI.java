package com.S23010460;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class TempUI extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean isTemparatureSensorAvailable;
    private MediaPlayer mediaPlayer;
    private boolean hasPlayed = false;
    private Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_ui);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemparatureSensorAvailable = true;
        } else {
            textView.setText("Temparature sonson is not available");
            isTemparatureSensorAvailable = false;
        }

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(TempUI.this, MapUI.class));
        });

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(event.values[0] + " °C");
        if (event.values[0] >= 60){
            mediaPlayer = MediaPlayer.create(this, R.raw.alert);
            mediaPlayer.start();
            hasPlayed = true;
        }
        if (event.values[0] <= 60) {
            hasPlayed = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    protected void onResume(){
        super.onResume();
        if (isTemparatureSensorAvailable){
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTemparatureSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}