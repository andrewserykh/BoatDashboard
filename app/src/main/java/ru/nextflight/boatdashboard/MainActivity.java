package ru.nextflight.boatdashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ImageView gaugeImageView = (ImageView) findViewById(R.id.gauge_analog);
//        Animation gaugeTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.gauge_analog_turn);
//        gaugeImageView.startAnimation(gaugeTurnAnimation);
    }
}
