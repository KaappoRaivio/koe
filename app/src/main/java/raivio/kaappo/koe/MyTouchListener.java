package raivio.kaappo.koe;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public final class MyTouchListener implements View.OnTouchListener {

    public boolean onTouch (View view, MotionEvent motionEvent) {
        System.out.println("Touch: " + motionEvent);

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                view.setTag(MainActivity.getId((View) view.getParent()));

                ClipData data = ClipData.newPlainText("", "");

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view) {

                    private static final float SCALING_FACTOR = 1.2f;

                    @Override
                    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                        View v = getView();
                        final int actualWidth = (int) (v.getWidth() * SCALING_FACTOR);
                        final int actualHeight = (int) (v.getHeight() * SCALING_FACTOR);
                        Toast.makeText(view.getContext(), actualWidth + ", " + actualHeight, Toast.LENGTH_LONG).show();
                        shadowSize.set(actualWidth, actualHeight);
                        shadowTouchPoint.set(actualWidth / 2, actualHeight);
                    }

                    @Override
                    public void onDrawShadow(Canvas canvas) {
                        canvas.scale(SCALING_FACTOR, SCALING_FACTOR);
                        getView().draw(canvas);
                    }
                };

                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;

            case MotionEvent.ACTION_UP:
                view.performClick();
                view.setVisibility(View.VISIBLE);
                return true;
            default:
                return false;
        }
    }
}