package raivio.kaappo.koe;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MyConstraintLayout extends ConstraintLayout {
    public MyConstraintLayout (Context context) {
        super(context);
    }

    public MyConstraintLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyConstraintLayout (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.max(width, height);
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }
}
