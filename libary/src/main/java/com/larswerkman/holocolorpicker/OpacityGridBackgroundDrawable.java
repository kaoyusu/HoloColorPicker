package com.larswerkman.holocolorpicker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

@SuppressWarnings("unused")
public class OpacityGridBackgroundDrawable extends Drawable {
    private int mWidth;
    private int mHeight;
    private int mGridSize;
    private final Paint mWhitePaint = new Paint();
    private final Paint mGrayPaint = new Paint();
    private Path mCirclePath = new Path();

    public OpacityGridBackgroundDrawable(int gridSize) {
        this();
        this.mGridSize = gridSize;
    }

    public OpacityGridBackgroundDrawable() {
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(Color.WHITE);
        mGrayPaint.setAntiAlias(true);
        mGrayPaint.setColor(Color.GRAY);
        mGridSize = 20;
    }

    public void setGridSize(int gridSize) {
        this.mGridSize = gridSize;
        invalidateSelf();
    }

    public void setCircleClip(RectF rectF) {
        setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        mCirclePath.reset();
        mCirclePath.addArc(rectF, 0, 360);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mWidth = bounds.width();
        mHeight = bounds.height();
        if (mGridSize > mWidth || mGridSize > mHeight) {
            if (mWidth > mHeight) {
                mGridSize = mHeight / 2;
            } else {
                mGridSize = mWidth / 2;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

        if (mGridSize <= 0) {
            return;
        }
        canvas.save();
        if (!mCirclePath.isEmpty()) {
            canvas.clipPath(mCirclePath);
        } else {
            canvas.clipRect(getBounds());
        }
        canvas.translate(getBounds().left, getBounds().top);
        int x = 0;
        int y = 0;
        do {
            do {
                canvas.drawRect(x, y, x + mGridSize, y + mGridSize,
                        ((x / mGridSize + y / mGridSize) % 2 == 0) ? mWhitePaint : mGrayPaint);
                x += mGridSize;
            } while (x < mWidth + mGridSize);
            x = 0;
            y += mGridSize;
        } while (y < mHeight + mGridSize);
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
