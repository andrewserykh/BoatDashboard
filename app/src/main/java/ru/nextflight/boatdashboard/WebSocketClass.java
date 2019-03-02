package ru.nextflight.boatdashboard;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by andrew.serykh on 24.01.19.
 */

public class WebSocketClass extends WebSocketListener {
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Hello");
        Log.d("WS", "Msg sent");
    }

    public void Send(WebSocket webSocket, Response response){
        webSocket.send("Hello");
        Log.d("WS", "Msg sent");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text)
    {
        Log.d("WS", "rcv:" + text);
        System.out.print(text);
    }
}
