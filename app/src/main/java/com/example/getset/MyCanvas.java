package com.example.getset;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MyCanvas extends View {

    Paint p;
    RectF rectf;
    float width;
    float height;
    ArrayList<Cards> cards;
    GameActivity gameActivity;

    public MyCanvas(Context context) {
        super(context);
        p = new Paint();
        cards = new ArrayList<>();
        gameActivity = new GameActivity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(gameActivity.getCardsSet() != null) {
            cards = gameActivity.getCardsSet();
        }
        width = getWidth();
        height = getHeight();
        if(cards.size() > 0) {
            Log.d("mylog", "array cards is exist");
            p.setColor(Color.GREEN);
            rectf = new RectF(100, 100, 300, 300);
            canvas.drawRoundRect(rectf, 20, 20, p);
        }
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
        Log.d("mylog", "size"+cards.size());
    }
}
