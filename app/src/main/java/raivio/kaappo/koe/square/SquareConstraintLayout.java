package raivio.kaappo.koe.square;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SquareConstraintLayout extends ConstraintLayout {

    public SquareConstraintLayout (Context context) {
        super(context);
    }

    public SquareConstraintLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareConstraintLayout (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
////        super.onSizeChanged(w, h, oldw, oldh);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
//        params.width = params.height;
//        setLayoutParams(params);
//    }


}
