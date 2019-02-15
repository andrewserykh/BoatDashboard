package ru.nextflight.boatdashboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

public class GaugeCircleView extends View {

    public int TicksCount;
    public int ValueMin;
    public int ValueMax;
    public float Value;
    public float ValueHand;
    public float ValueHandCur;

    private float animVelocity;

    private float scale;
    private Paint pScale;
    private Paint pScaleShadow;
    private Paint pFont;
    private Paint pHand;
    private Paint pFontValue;
    private Paint pFontLabel;
    private Typeface FontMicra;

    public GaugeCircleView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs);

        InitDraw();
    }

    private void init (Context context, AttributeSet attrs){
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.GaugeCircleView, 0, 0);

            TicksCount = a.getInteger(R.styleable.GaugeCircleView_TicksCount, TicksCount);
            ValueMin = a.getInteger(R.styleable.GaugeCircleView_ValueMin, ValueMin);
            ValueMax = a.getInteger(R.styleable.GaugeCircleView_ValueMax, ValueMax);
            Value = a.getFloat(R.styleable.GaugeCircleView_Value, Value);
            a.recycle();
        }
        FontMicra = ResourcesCompat.getFont(context, R.font.micra);

        animVelocity = 0;

    }

    private void InitDraw (){
        scale = getResources().getDisplayMetrics().density;

        pScale = new Paint();
        pScale.setStyle(Paint.Style.STROKE);
        pScale.setColor(Color.parseColor("#327d71"));
        pScale.setStrokeWidth(5);
        pScale.setAntiAlias(true);

        pScaleShadow = new Paint();
        pScaleShadow.setStyle(Paint.Style.STROKE);
        pScaleShadow.setColor(Color.parseColor("#185b50")); //236b60
        pScaleShadow.setStrokeWidth(5);
        pScaleShadow.setAntiAlias(true);

        pHand = new Paint();
        pHand.setStyle(Paint.Style.STROKE);
        pHand.setColor(Color.parseColor("#ff0000"));
        pHand.setStrokeWidth(10);
        pHand.setAntiAlias(true);

        pFont = new Paint(Paint.ANTI_ALIAS_FLAG);
        pFont.setTypeface(FontMicra);
        pFont.setTextSize(40);
        pFont.setAntiAlias(true);
        pFont.setStyle(Paint.Style.STROKE);
        pFont.setColor(Color.parseColor("#FFFFFF"));

        pFontLabel = new Paint(Paint.ANTI_ALIAS_FLAG);
        pFontLabel.setTypeface(FontMicra);
        pFontLabel.setTextSize(30);
        pFontLabel.setAntiAlias(true);
        pFontLabel.setStyle(Paint.Style.STROKE);
        pFontLabel.setColor(Color.parseColor("#FFFFFF"));

        pFontValue = new Paint(Paint.ANTI_ALIAS_FLAG);
        pFontValue.setTypeface(FontMicra);
        pFontValue.setTextSize(100);
        pFontValue.setAntiAlias(true);
        pFontValue.setStyle(Paint.Style.STROKE);
        pFontValue.setColor(Color.parseColor("#327d71"));

    }

    private void DrawRim (Canvas canvas){

        //canvas.drawCircle((getWidth()/2), (getHeight()/2),getWidth()/2,pScale);
        //canvas.drawArc(2,2,getWidth()-4,getHeight()-4,130,280,false,pScale);

        int tickCnt = 0;
        int tickValue = (ValueMax-ValueMin)/(TicksCount-1);
        int padding = (int)(20 * scale + 0.5f);
        int weight = (int)(2 * scale +0.5f);

        // RPM x 100 0-70

        int ticksInterval = (int)((270.0f / (TicksCount-1))*10.0f); //циферблат 270 градусов, расчет интервала по количеству Рисок будет:

        canvas.rotate(225,getWidth()/2,(getHeight() / 2)+(padding/2));
        for (int i=0; i<360*10; i++) {
            if (i<270*10) { //отрисовка дуги
                canvas.drawLine(getWidth() / 2, padding, getWidth() / 2, padding + weight, pScale);
                canvas.drawLine(getWidth() / 2, padding + weight, getWidth() / 2, padding + weight*2, pScaleShadow);
            }
            if ((tickCnt>=ticksInterval || tickCnt==0) && i<271*10) { //отрисовка рисок
                canvas.drawLine(getWidth() / 2, padding, getWidth() / 2, padding + (weight*10), pScale);
                tickCnt=0;
            }
            canvas.rotate(0.1f, getWidth() / 2, (getHeight() / 2)+(padding/2));
            tickCnt++;
        }

        int x,y,angle;
        float valuef;
        canvas.rotate(135, getWidth() / 2, (getHeight() / 2)+(padding/2));

        for (int i=0; i<TicksCount; i++) {
            valuef = tickValue*i;
            angle = (ticksInterval/10)*i;
            angle = angle+135;
            x = (int) ((float) ((getWidth() / 2) - 2*padding) * (float) Math.cos(angle / 180.0 * Math.PI) + (getWidth() / 2) - padding/2); // x = r * cos (ф)
            y = (int) ((float) ((getWidth() / 2) - 2*padding) * (float) Math.sin(angle / 180.0 * Math.PI) + ((getHeight() / 2) + padding)); // y = r * sin(ф)
            canvas.drawText( String.valueOf((int)valuef),x,y-16,pFont);
        }

        canvas.drawText( "knots",(getWidth()/2)-(pFontLabel.measureText("knots")/2),(getHeight()/2)+2*padding,pFontLabel);

        canvas.drawText( "knots",(getWidth()/2)-(pFontLabel.measureText("knots")/2),(getHeight()/2)+2*padding,pFontLabel);

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.restore();

    }

    protected void DrawHand(Canvas canvas){

        // 0% - 100% = 0 - 270deg

        int padding = (int)(20 * scale + 0.5f);
        float valuef = ( (ValueHandCur - ValueMin) * 270 ) / (ValueMax - ValueMin);
        valuef = valuef - 225;
        double handLen = (getWidth()/2) - padding;
        //double angle = Math.PI * value / 30 - Math.PI / 2;
        double angle = valuef / 180.0 * Math.PI;
        canvas.drawLine(
                (getWidth() / 2), (getHeight()/2)+padding/2,
                (float) ((getWidth() / 2)+ Math.cos(angle) * handLen),
                (float) ((getHeight()/2)+padding/2 + Math.sin(angle) * handLen),
                pHand);

        //подпись скорости текстом
        canvas.drawText( String.valueOf(Value),(getHeight()/2) - (pFontValue.measureText(String.valueOf(Value))/2),getWidth()-50,pFontValue);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        DrawRim(canvas);
        DrawHand(canvas);

        if (ValueHand!=ValueHandCur) {
            if (ValueHandCur>ValueHand) ValueHandCur = ValueHandCur - animVelocity;
            if (ValueHandCur<ValueHand) ValueHandCur = ValueHandCur + animVelocity;
            animVelocity=animVelocity+0.1f;
            postInvalidateOnAnimation();
        }


    }

    public void setValue(int ValueIn){
        Value = ValueIn;

        if ( Value <= ValueMax && Value >= ValueMin) {
            ValueHand = ValueIn;
        } else {
            if (Value>ValueMax) ValueHand = ValueMax;
            if (Value<ValueMin) ValueHand = ValueMin;
        }

        animVelocity=1;

        invalidate();
    }


}
