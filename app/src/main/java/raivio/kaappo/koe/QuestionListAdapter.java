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
        View content = new QuestionView(context);

//        Button actionContinue = content.findViewById(R.id.next);
//        actionContinue.setEnabled(position < getCount() - 1);
//        actionContinue.setOnClickListener(view -> next());
//
//        Button actionBack = content.findViewById(R.id.previous);
//        actionBack.setEnabled(position > 0);
//        actionBack.setOnClickListener(view -> previous());

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

}
