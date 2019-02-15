package ru.nextflight.boatdashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tValue;
    Button btnValue;
    GaugeCircleView gaugeSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tValue = (TextView) findViewById(R.id.tValue);
        btnValue = (Button) findViewById(R.id.btnValue);
        gaugeSpeed = (GaugeCircleView) findViewById(R.id.gaugeSpeed);


//        ImageView gaugeImageView = (ImageView) findViewById(R.id.gauge_analog);
//        Animation gaugeTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.gauge_analog_turn);
//        gaugeImageView.startAnimation(gaugeTurnAnimation);
    }

    public void btnValue_onClick(View view){

        tValue.setText("SET 45");
        gaugeSpeed.setValue(25);

    }
}
