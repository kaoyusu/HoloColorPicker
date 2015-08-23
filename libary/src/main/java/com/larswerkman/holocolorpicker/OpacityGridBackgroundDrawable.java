package com.larswerkman.holocolorpicker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class OpacityGridBackgroundDrawable extends Drawable {
    private int width;
    private int height;
    private int gridSize;
    private final Paint mWhitePaint = new Paint();
    private final Paint mGrayPaint = new Paint();

    public OpacityGridBackgroundDrawable() {
        mWhitePaint.setColor(Color.WHITE);
        mGrayPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        width = bounds.width();
        height = bounds.height();
        if (width > height) {
            gridSize = height / 2;
        } else {
            gridSize = width / 2;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (width == 0 || height == 0) {
            throw new IllegalStateException("bounds doesn't been set yet.");
        }
        canvas.save();
        canvas.clipRect(getBounds());
        int left = getBounds().left;
        int top = getBounds().top;
        int x = 0;
        int y = 0;
        do {
            do {
                canvas.drawRect(left + x, top + y, left + x + gridSize, top + y + gridSize,
                        ((x / gridSize + y / gridSize) % 2 == 0) ? mWhitePaint : mGrayPaint);
                x += gridSize;
            } while (x < width + gridSize);
            x = 0;
            y += gridSize;
        } while (y < height + gridSize);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
