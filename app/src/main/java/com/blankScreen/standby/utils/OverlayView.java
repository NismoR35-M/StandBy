package com.blankScreen.standby.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.blankScreen.standby.services.OverlayService;

public class OverlayView  extends View {
    //primary constructor
    public OverlayView(Context context) {
        super(context);
    }

    private float yBorder = 0;
    private boolean hiding = false;
    private float hidingVelocity = 40;
    private boolean falling = false;
    private Paint paint = new Paint();
    private OnHideFinishedListener onHideFinishedListener = null;


    //inverting color
    public void setInvertColor(boolean invert) {
        if (invert)
            this.paint.setColor(Resources.getSystem().getColor(android.R.color.white));
        else
            this.paint.setColor(Resources.getSystem().getColor(android.R.color.black));
    }

    public void setOnHideFinishedListener(OnHideFinishedListener onHideFinishedListener) {
        this.onHideFinishedListener = onHideFinishedListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        canvas.drawRect(0,0, (float) width, height-yBorder, this.paint);

        if (hiding) {
            if (yBorder > height) {
                hiding = false;
                yBorder = 0;
                onHideFinishedListener.onHideFinished();
                return;
            }

            yBorder += hidingVelocity;
            invalidate();
            return;
        }

        if (falling) {
            if (yBorder < 0) {
                OverlayService.state = Constants.Overlay.State.VISIBLE;

                falling = false;
                yBorder = 0;
                invalidate();
                return;
            }else {
                yBorder -= 40;
            }
            invalidate();
            return;
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setyBorder(float Y) {
        this.yBorder = Y;
        this.hiding = false;
        this.falling = false;
        invalidate();
    }

    public void setHiding(boolean hiding) {
        this.falling = false;

        this.hiding = hiding;
        invalidate();
    }

    public void setFalling(boolean falling) {
        this.hiding = false;

        this.falling = falling;
        invalidate();
    }

    public void setHidingVelocity(float velocity) {
        this.hidingVelocity = velocity;
    }
}
