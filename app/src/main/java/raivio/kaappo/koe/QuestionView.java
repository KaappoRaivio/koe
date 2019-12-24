package raivio.kaappo.koe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


//        setOptions(Arrays.asList("A", "B", "C", "D", "E", "F"));
//        setOptions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H"));
//        setOptions(Arrays.asList("A", "B"));
        setTouchListeners();
        setDragListeners();

//        parent.findViewById(R.id.next).setOnClickListener(this::onNextPress);
    }

    public void setOptions (List<String> options) {
        setAmountOfAnswerPlaces(options.size());
        setAnswerOptions(options);

        setTouchListeners();
        setDragListeners();
    }

    public List<String> getResult () throws Exception {
        List<String> result = new ArrayList<>();

        for (View view : IntStream.range(0, answer.getChildCount()).mapToObj(answer::getChildAt).collect(Collectors.toList())) {
            if (view instanceof ViewGroup) {
                ConstraintLayout child;
                try {
                    child = (ConstraintLayout) ((ViewGroup) view).getChildAt(0);
                    TextView text = (TextView) child.getChildAt(0);
                    result.add(text.getText().toString());
                } catch (ClassCastException | IndexOutOfBoundsException | NullPointerException e) {
                    new Exception("Not yet ready!").printStackTrace();
                    result.add(" ");
                }
            }
        }
        System.out.println("Result: " + result);
        return result;
    }

    public void onNextPress (View view) {
        System.out.println("Trying!");
        try {
            Toast.makeText(context, String.join("\n", getResult()), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Pair<Integer, Integer> size = getAnswerOptionSize();
//        for (View view : views) {
//            System.out.println("Class: " + view.getClass());
//            view.setLayoutParams(new LayoutParams(size.first, size.second));
//        }
        Toast.makeText(context, size.toString(), Toast.LENGTH_LONG).show();
    }

    private Pair<Integer, Integer> getAnswerOptionSize () {
//        moveToAnswerPlace();
//        ((ViewGroup) answer.getChildAt(0)).getChildAt(0).measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
//        int width = ((ViewGroup) answer.getChildAt(0)).getChildAt(0).getMeasuredWidth();
//        int height = ((ViewGroup) answer.getChildAt(0)).getChildAt(0).getMeasuredHeight();
        int width = ((ViewGroup) answer.getChildAt(0)).getMeasuredWidth();
        int height = ((ViewGroup) answer.getChildAt(0)).getMeasuredHeight();
//        moveBack();

        return new Pair<>(width, height);
    }

    private void moveToAnswerPlace () {
        View view = optionHolder.getChildAt(0);
        optionHolder.removeView(view);
        ((ViewGroup) answer.getChildAt(0)).addView(view);
    }

    private void moveBack () { ;
        View view = ((ViewGroup) answer.getChildAt(0)).getChildAt(0);
        ((ViewGroup) view.getParent()).removeView(view);
        optionHolder.addView(view);
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
            if (view instanceof ViewGroup) {
                view.setOnDragListener(new MyDragListener(parent));
            }

            System.out.println("View " + view);
        }

        parent.setOnDragListener((v, event) -> {
            int action = event.getAction();

            if (action == DragEvent.ACTION_DROP) {
                View view = (View) event.getLocalState();
                ((ViewGroup) view.getParent()).removeView(view);
                optionHolder.addView(view);
                view.setVisibility(View.VISIBLE);
            }

            return true;
        });
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private QuestionView questionView;

        public Holder (@NonNull View itemView) {
            super(itemView);

            questionView = (QuestionView) itemView;
        }

        public void bind (List<String> options) {
            questionView.setOptions(options);
        }

        /*class CategoryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
            constructor(parent: ViewGroup) :
                this(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))

            fun bind(category: Category) {
                itemView.categoryName.text = category.name
            }
        }*/
    }

    public static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<List<String>> questions;

        public Adapter (List<List<String>> questions) {

            this.questions = questions;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            QuestionView itemView = new QuestionView(parent.getContext());
            itemView.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
            return new Holder(itemView);
        }

        @Override
        public void onBindViewHolder (@NonNull Holder holder, int position) {
            holder.bind(questions.get(position));
        }

        @Override
        public int getItemCount () {
            return questions.size();
        }
    }
}
