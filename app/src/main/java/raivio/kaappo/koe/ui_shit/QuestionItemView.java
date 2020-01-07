package raivio.kaappo.koe.ui_shit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import raivio.kaappo.koe.R;

public class QuestionItemView extends LinearLayout {
    public QuestionItemView (Context context) {
        super(context, null);
        initialize(context);
    }

    public QuestionItemView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public QuestionItemView (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public QuestionItemView (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize (Context context) {
        setClipChildren(true);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_question, this, true);
    }
}
