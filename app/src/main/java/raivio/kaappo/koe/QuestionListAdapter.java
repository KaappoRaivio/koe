package raivio.kaappo.koe;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;

import java.util.Arrays;
import java.util.List;

public class QuestionListAdapter extends VerticalStepperAdapter {
    public QuestionListAdapter (Context context) {
        super(context);
    }

    @NonNull
    @Override
    public CharSequence getTitle (int position) {
        return "Kysymys " + (position + 1);
    }

    @Nullable
    @Override
    public CharSequence getSummary (int position) {
        return "Yhteenveto " + (position + 1);
    }

    @Override
    public boolean isEditable (int position) {
        return false;
    }

    @NonNull
    @Override
    public View onCreateContentView (Context context, int position) {
        View content = new QuestionItemView(context);

        Button actionContinue = content.findViewById(R.id.next);
        actionContinue.setEnabled(position < getCount() - 1);
        actionContinue.setOnClickListener(view -> next());

        Button actionBack = content.findViewById(R.id.previous);
        actionBack.setEnabled(position > 0);
        actionBack.setOnClickListener(view -> previous());

        setDragListeners(content);
        setTouchListeners(content);

        return content;
    }

    @Override
    public int getCount () {
        return 5;
    }

    @Override
    public Object getItem (int position) {
        return null;
    }

    public void setTouchListeners (View content) {
        LinearLayout l = content.findViewById(R.id.view_question_option_holder);
        for (int i = 0; i < l.getChildCount(); i++) {
            View option = l.getChildAt(i);
            option.setOnTouchListener(new MyTouchListener());

        }
    }

    public void setDragListeners (View content) {
        List<View> views = Arrays.asList(content.findViewById(R.id.view_question_option_holder), content.findViewById(R.id.main_one), content.findViewById(R.id.main_two), content.findViewById(R.id.main_three));
        for (View view : views) {
            view.setOnDragListener(new MyDragListener(content));
            System.out.println(view);
        }

//        findViewById(R.id.main_parent).setOnDragListener((v, event) -> {
//            int action = event.getAction();
//
//            if (action == DragEvent.ACTION_DROP) {
//                View view = (View) event.getLocalState();
//                ((ViewGroup) view.getParent()).removeView(view);
//                ((ViewGroup) findViewById(R.id.main_placeholder)).addView(view);
//                view.setVisibility(View.VISIBLE);
//            }
//
//            return true;
//        });
    }
}
