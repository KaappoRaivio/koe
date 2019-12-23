package raivio.kaappo.koe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.liefery.android.vertical_stepper_view.VerticalStepperView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((VerticalStepperView) findViewById(R.id.main_stepper)).setStepperAdapter(new QuestionListAdapter(this));

//        setTouchListeners();
//        setDragListeners();
    }

//    public void setTouchListeners () {
//        LinearLayout l = findViewById(R.id.main_placeholder);
//        for (int i = 0; i < l.getChildCount(); i++) {
//            View option = l.getChildAt(i);
//            option.setOnTouchListener(new MyTouchListener());
//
//        }
//    }
//
//    public void setDragListeners () {
//        List<View> views = Arrays.asList(findViewById(R.id.main_placeholder), findViewById(R.id.main_one), findViewById(R.id.main_two), findViewById(R.id.main_three));
//        for (View view : views) {
//            view.setOnDragListener(new MyDragListener(this));
//            System.out.println(view);
//        }
//
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
//    }
}
