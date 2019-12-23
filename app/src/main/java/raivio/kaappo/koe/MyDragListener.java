package raivio.kaappo.koe;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyDragListener implements View.OnDragListener {
    private View content;

    public MyDragListener (View content) {
        this.content = content;
    }

    public boolean onDrag (View view, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DROP:
                View viewInQuestion = (View) event.getLocalState();

                LinearLayout originParent = (LinearLayout) viewInQuestion.getParent();

                LinearLayout destination = (LinearLayout) view;
                if (((ViewGroup) view).getChildCount() == 0 || view.getId() == R.id.view_question_option_holder) {
                    originParent.removeView(viewInQuestion);
                    destination.addView(viewInQuestion);
                } else if (((ViewGroup) view).getChildCount() == 1) {
                    RelativeLayout otherView = (RelativeLayout) ((ViewGroup) view).getChildAt(0);

                    destination.removeView(otherView);
                    originParent.addView(otherView);
                    originParent.removeView(viewInQuestion);
                    destination.addView(viewInQuestion);

                }

                viewInQuestion.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                System.out.println("moi");
                if (!event.getResult()) {
                    View origin = (View) event.getLocalState();
                    ((ViewGroup) origin.getParent()).removeView(origin);
                    ((ViewGroup) content.findViewById(R.id.view_question_option_holder)).addView(origin);
                    origin.setVisibility(View.VISIBLE);
                }
                System.out.println("hei");
                break;
            default:
                return true;
        }
        return true;
    }
}
