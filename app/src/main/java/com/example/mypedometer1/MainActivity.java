package com.example.mypedometer1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = " ";
    private int numSteps;
    private float calCount;
    TextView TvSteps,CalBurn;

    Button BtnStart,BtnStop,map;
    ImageButton moreButton;
    ImageView ButtonImageStart;
    ImageView ButtonImagePause;
    ImageView ButtonImageRefresh;
    ImageView ButtonImageMaps;
    ImageView ButtonImageMore;

    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding the music file to our

        music = MediaPlayer.create(
                this,R.raw.interstellar);


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        CalBurn = (TextView) findViewById(R.id.displayCal);

        ButtonImageStart=(ImageView)findViewById(R.id.btnImgStart);
        ButtonImagePause=(ImageView)findViewById(R.id.btnImgPause);
        ButtonImageRefresh=(ImageView)findViewById(R.id.btnImgRefresh);
        ButtonImageMaps=(ImageView)findViewById(R.id.btnImgMaps);
        ButtonImageMore=(ImageView)findViewById(R.id.btnImgMore);

        //BtnStart = (Button) findViewById(R.id.btn_start);
        //BtnStop = (Button) findViewById(R.id.btn_stop);

        //moreButton = (ImageButton) findViewById(R.id.more);
       // map=(Button)findViewById(R.id.maps);


        ButtonImageStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                music.start();

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        ButtonImagePause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                music.pause();

                sensorManager.unregisterListener(MainActivity.this);

            }
        });








    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(""+numSteps);
        calCount= (float) (numSteps*(0.04));
        CalBurn.setText(""+calCount);
        //Toast.makeText(getApplicationContext(),TEXT_NUM_STEPS + numSteps,Toast.LENGTH_SHORT).show();

    }

    public void Refresh(View v2){
        music.stop();
        music = MediaPlayer.create(this, R.raw.interstellar);
        TvSteps.setText("");
        CalBurn.setText("");
    }

    public void More(View v) {
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);
    }

    public void Maps(View v1) {
        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(i);

    }


}