package raivio.kaappo.koe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Arrays;

public class QuestionActivity extends AppCompatActivity {

    private ViewPager2 pager2;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        pager2 = findViewById(R.id.viewPager2);
        pager2.setAdapter(new QuestionView.Adapter(Arrays.asList(
                Arrays.asList("A"),
                Arrays.asList("B", "C"),
                Arrays.asList("A", "B", "C"),
                Arrays.asList("A", "B", "C", "D"))
        ));

        DotsIndicator indicator = findViewById(R.id.question_dots_indicator);
        indicator.setViewPager2(pager2);
    }


    public void onButtonClicked (View view){
        int position = pager2.getCurrentItem();

        switch(view.getId()){
            case R.id.question_next:
                pager2.setCurrentItem(position + 1);
                break;
            case R.id.question_previous:
                pager2.setCurrentItem(position - 1);
                break;
        }

        findViewById(R.id.question_next).setEnabled(pager2.getCurrentItem() < pager2.getAdapter().getItemCount() - 1);
        findViewById(R.id.question_previous).setEnabled(pager2.getCurrentItem() > 0);

    }
}
