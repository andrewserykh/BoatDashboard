package ru.nextflight.boatdashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;


public class MainActivity extends Activity {

    private OkHttpClient httpClient;
    private Request httpRequest;
    private WebSocketClass wsClass;
    private WebSocket wsClient;

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

        httpClient = new OkHttpClient.Builder()
                .build();

        httpRequest = new Request.Builder()
                .url("wss://echo.websocket.org")
                .build();

        //WebSocketClass wsc = new WebSocketClass();
        //WebSocket ws = client.newWebSocket(request, wsc);

        wsClass = new WebSocketClass();
        wsClient = httpClient.newWebSocket(httpRequest, wsClass);


//        ImageView gaugeImageView = (ImageView) findViewById(R.id.gauge_analog);
//        Animation gaugeTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.gauge_analog_turn);
//        gaugeImageView.startAnimation(gaugeTurnAnimation);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        httpClient.dispatcher().executorService().shutdown();

    }

    public void btnValue_onClick(View view){

        wsClient.send("clicked");

        gaugeSpeed.setValue(25);
        gaugeFuel.setValue(0);
        gaugeRpm.setValue(70);
        gaugeOil.setValue(50);
        gaugeTemp.setValue(130);

    }
}
