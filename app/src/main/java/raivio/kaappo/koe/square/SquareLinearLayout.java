package raivio.kaappo.koe.square;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SquareLinearLayout extends LinearLayout {
    private int parentHeight;

    private int selfWidth;

    public SquareLinearLayout (Context context) {
        super(context);
        initialize();
    }

    public SquareLinearLayout (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public SquareLinearLayout (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public SquareLinearLayout (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize () {
        post(() -> {
            parentHeight = ((ViewGroup) getParent()).getHeight();


            System.out.println("PARENTHEIGHT: " + parentHeight + ", SELFHEIGHT: " + getHeight());
            setLayoutParams(new LinearLayout.LayoutParams(Math.min(parentHeight, selfWidth), Math.min(parentHeight, selfWidth)));
        });
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        System.out.println("HEight: " + selfWidth + ", parentheight: " + parentHeight);
        int size = Math.min(selfWidth, parentHeight);
//        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
//                        MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
