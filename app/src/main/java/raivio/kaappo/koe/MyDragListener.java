package raivio.kaappo.koe;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyDragListener implements View.OnDragListener {
    private View context;

    public MyDragListener (View content) {
        this.context = content;
    }

    public boolean onDrag (View view, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DROP:
                View viewInQuestion = (View) event.getLocalState();

                LinearLayout originParent = (LinearLayout) viewInQuestion.getParent();

                LinearLayout destination = (LinearLayout) view;
                if (((ViewGroup) view).getChildCount() == 0 || view.getId() == R.id.main_placeholder) {
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
                if (!event.getResult()) {
                    View origin = (View) event.getLocalState();
                    ((ViewGroup) origin.getParent()).removeView(origin);
                    ((ViewGroup) context.findViewById(R.id.main_placeholder)).addView(origin);
                    origin.setVisibility(View.VISIBLE);
                }
                break;
            default:
                return true;
        }
        return true;
    }
}
