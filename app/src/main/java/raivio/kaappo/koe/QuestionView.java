package raivio.kaappo.koe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.List;

public class QuestionView extends LinearLayout {
    private ConstraintLayout parent;
    private LinearLayout placeholder;
    private ConstraintLayout answer;
    private Context context;

    public QuestionView (Context context) {
        super(context);
        initialize(context);
    }

    public QuestionView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public QuestionView (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public QuestionView (Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }


    private void initialize (Context context) {
        setClipChildren(true);
        setOrientation(VERTICAL);
        parent = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.view_question, this, true);
        placeholder = parent.findViewById(R.id.main_placeholder);
        answer = parent.findViewById(R.id.main_answer);
        this.context = context;

    }

    public void setOptions (List<String> options) throws Exception {
        setAmountOfAnswerPlaces(options.size());
        throw new Exception("Not implemented");
    }

    public List<String> getResult () throws Exception {
        throw new Exception("Not implemented");
    }

    private void setAmountOfAnswerPlaces (int amount) {
        answer.removeAllViews();


        List<View> views = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            LinearLayout child = new LinearLayout(context);
            views.add(child);
            if (i < amount - 1) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.less_than);
                views.add(imageView);
            }
        }

        ConstraintSet set = new ConstraintSet();
        for (int i = 1; i < views.size() - 1; i++) {
            set.addToHorizontalChain(views.get(i).getId(), views.get(i - 1).getId(), views.get(i + 1).getId());
        }
        set.applyTo(parent);
    }
}
