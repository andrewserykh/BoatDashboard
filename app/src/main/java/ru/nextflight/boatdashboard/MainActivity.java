package ru.nextflight.boatdashboard;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button btnValue;
    GaugeCircleView gaugeSpeed;
    GaugeCircleView gaugeRpm;
    GaugeCircleView gaugeFuel;
    GaugeCircleView gaugeOil;
    GaugeCircleView gaugeTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnValue = (Button) findViewById(R.id.btnValue);
        gaugeSpeed = (GaugeCircleView) findViewById(R.id.gaugeSpeed);

        gaugeRpm = (GaugeCircleView) findViewById(R.id.gaugeRpm);
        gaugeFuel = (GaugeCircleView) findViewById(R.id.gaugeFuel);
        gaugeOil = (GaugeCircleView) findViewById(R.id.gaugeOil);
        gaugeTemp = (GaugeCircleView) findViewById(R.id.gaugeTemp);


//        ImageView gaugeImageView = (ImageView) findViewById(R.id.gauge_analog);
//        Animation gaugeTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.gauge_analog_turn);
//        gaugeImageView.startAnimation(gaugeTurnAnimation);
    }

    public void btnValue_onClick(View view){

        gaugeSpeed.setValue(25);
        gaugeFuel.setValue(0);
        gaugeRpm.setValue(70);
        gaugeOil.setValue(50);
        gaugeTemp.setValue(130);

    }
}
