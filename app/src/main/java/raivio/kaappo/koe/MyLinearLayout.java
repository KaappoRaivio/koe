package raivio.kaappo.koe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout (Context context) {
        super(context);
    }

    public MyLinearLayout (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
