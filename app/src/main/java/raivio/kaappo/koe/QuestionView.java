package raivio.kaappo.koe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionView extends ConstraintLayout {
    private ConstraintLayout parent;
    private LinearLayout optionHolder;
    private LinearLayout answer;
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


    private void initialize (Context context) {
        parent = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.view_question, this, true);
        optionHolder = parent.findViewById(R.id.view_question_option_holder);
        answer = parent.findViewById(R.id.main_answer);
        this.context = context;


        setOptions(Arrays.asList("A", "B", "C", "D", "E", "F"));
        setTouchListeners();
        setDragListeners();
    }

    public void setOptions (List<String> options) {
        setAmountOfAnswerPlaces(options.size());
        setAnswerOptions(options);
    }

    public List<String> getResult () throws Exception {
        throw new Exception("Not implemented");
    }

    private void setAnswerOptions (List<String> options) {
        optionHolder.removeAllViews();

        List<View> views = new ArrayList<>();

        for (String string : options) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_answer, optionHolder, false);
            view.setId(generateViewId());
            ((TextView) view.findViewById(R.id.view_answer_text)).setText(string);
//            view.setLayoutParams(new ConstraintLayout.LayoutParams(getOptionSlotWidth(), getOptionSlotWidth()));

//            view.setMinimumHeight(getOptionSlotWidth());
            views.add(view);
        }

        views.forEach(optionHolder::addView);
    }


    private void setAmountOfAnswerPlaces (int amount) {
        answer.removeAllViews();


        List<View> views = new ArrayList<>();

        System.out.println("Adding views!");

        for (int i = 0; i < amount; i++) {
            LinearLayout child = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_option, answer, false);
            child.setId(generateViewId());
            views.add(child);
            if (i < amount - 1) {
                ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.view_less_than, answer, false);
                imageView.setId(generateViewId());
                views.add(imageView);
            }
        }

        System.out.println("Added views!");
        views.forEach(answer::addView);

//        ConstraintSet set = new ConstraintSet();
//        set.clone(answer);
//
//        View previousItem = null;
//        for (View view : views) {
//            boolean lastItem = views.indexOf(view) == views.size() - 1;
//            if(previousItem == null) {
//                set.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END);
//            } else {
//                set.connect(view.getId(), ConstraintSet.START, previousItem.getId(), ConstraintSet.END);
//                if(lastItem) {
//                    set.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
//                }
//            }
//            previousItem = view;
//        }
//        //constraintSet.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, viewIds, null, ConstraintSet.CHAIN_SPREAD);
//        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END, views.stream().mapToInt(View::getId).toArray(), null, ConstraintSet.CHAIN_SPREAD);
//        set.applyTo(answer);

    }

    public int getOptionSlotWidth () {
        return answer.getChildAt(0).getWidth();
    }

    public void setTouchListeners () {
        for (int i = 0; i < optionHolder.getChildCount(); i++) {
            View option = optionHolder.getChildAt(i);
            option.setOnTouchListener(new MyTouchListener());

        }
    }

    public void setDragListeners () {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < answer.getChildCount(); i++) {
            views.add(answer.getChildAt(i));
        }
        for (View view : views) {
            view.setOnDragListener(new MyDragListener(parent));
            System.out.println("View " + view);
        }
    }
}
