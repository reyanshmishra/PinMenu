package com.samsung.gallery.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class DemoView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath;

    public DemoView(Context context) {
        super(context);
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void d(Context context) {

        mPath = new Path();
        mPath.addCircle(40, 40, 45, Path.Direction.CCW);
        mPath.addCircle(80, 80, 45, Path.Direction.CCW);
    }

    private void showPath(Canvas canvas, int x, int y, Path.FillType ft,
                          Paint paint) {
        canvas.save();
        canvas.translate(x, y);
        canvas.clipRect(0, 0, 120, 120);
        canvas.drawColor(Color.WHITE);
        mPath.setFillType(ft);
        canvas.drawPath(mPath, paint);
        canvas.restore();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final Path path = new Path();
        final Paint paint = new Paint();

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path.moveTo(0, 0);
        path.lineTo(ReyUtils.getScreenWidth(), 0);
        path.lineTo(ReyUtils.getScreenWidth(), ReyUtils.getScreenHeight());
        path.lineTo(0, ReyUtils.getScreenHeight());

        path.close();

        path.moveTo(200, 200);
        path.lineTo(400, 200);
        path.lineTo(400, 400);
        path.lineTo(200, 400);

        path.close();

        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(path, paint);
    }

}