package ru.nextflight.boatdashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GaugeCircleView extends View {

    private float scale;
    private Paint pScale;
    private Paint pFont;
    private Paint pHand;

    public GaugeCircleView(Context context, AttributeSet attrs){
        super(context, attrs);

        InitDraw();
    }

    private void InitDraw (){
        scale = getResources().getDisplayMetrics().density;

        pScale = new Paint();
        pScale.setStyle(Paint.Style.STROKE);
        pScale.setColor(Color.parseColor("#327d71"));
        pScale.setStrokeWidth(5);
        pScale.setAntiAlias(true);

        pHand = new Paint();
        pHand.setStyle(Paint.Style.STROKE);
        pHand.setColor(Color.parseColor("#ff0000"));
        pHand.setStrokeWidth(5);
        pHand.setAntiAlias(true);


        pFont = new Paint(Paint.ANTI_ALIAS_FLAG);
        pFont.setTextSize(50);
        pFont.setAntiAlias(true);
        pFont.setStyle(Paint.Style.STROKE);
        pFont.setColor(Color.parseColor("#FFFFFF"));



    }

    private void DrawRim (Canvas canvas){

        //canvas.drawCircle((getWidth()/2), (getHeight()/2),getWidth()/2,pScale);
        //canvas.drawArc(2,2,getWidth()-4,getHeight()-4,130,280,false,pScale);

        int ticks = 7;
        int min = 0;
        int max = 60;

        int tickCount = 0;
        int tickValue = (max-min)/(ticks-1);
        int padding = (int)(20 * scale + 0.5f);
        int weight = (int)(2 * scale +0.5f);

        // RPM x 100 0-70

        int ticksInterval = (int)((270.0f / (ticks-1))*10.0f); //циферблат 270 градусов, расчет интервала по количеству Рисок будет:

        canvas.rotate(225,getWidth()/2,(getHeight() / 2)+(padding/2));
        for (int i=0; i<360*10; i++) {
            if (i<270*10) canvas.drawLine(getWidth() / 2, padding, getWidth() / 2, padding + weight, pScale);
            if ((tickCount>=ticksInterval || tickCount==0) && i<271*10) {
                canvas.drawLine(getWidth() / 2, padding, getWidth() / 2, padding + (weight*10), pScale);
                tickCount=0;
            }
            canvas.rotate(0.1f, getWidth() / 2, (getHeight() / 2)+(padding/2));
            tickCount++;
        }

        int x,y,angle;
        float value;
        canvas.rotate(135, getWidth() / 2, (getHeight() / 2)+(padding/2));

        for (int i=0; i<ticks; i++) {
            value = tickValue*i;
            angle = (ticksInterval/10)*i;
            angle = angle+135;
            x = (int) ((float) ((getWidth() / 2) - 2*padding) * (float) Math.cos(angle / 180.0 * Math.PI) + (getWidth() / 2) - padding/2); // x = r * cos (ф)
            y = (int) ((float) ((getWidth() / 2) - 2*padding) * (float) Math.sin(angle / 180.0 * Math.PI) + ((getHeight() / 2) + padding)); // y = r * sin(ф)
            canvas.drawText( String.valueOf((int)value),x,y,pFont);
        }

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.restore();

    }

    protected void DrawHand(Canvas canvas){

        int padding = (int)(20 * scale + 0.5f);
        double value = 75;
        double handLen = getWidth()/2;
        //double angle = Math.PI * value / 30 - Math.PI / 2;
        double angle = value / 180.0 * Math.PI;
        canvas.drawLine(
                (getWidth() / 2), (getHeight()/2)+padding/2,
                (float) ((getWidth() / 2)+ Math.cos(angle) * handLen),
                (float) ((getHeight()/2)+padding/2 + Math.sin(angle) * handLen),
                pHand);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        //canvas.drawColor(Color.BLACK);
        DrawRim(canvas);
        DrawHand(canvas);


    }
}
