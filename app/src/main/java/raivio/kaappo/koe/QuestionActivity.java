package raivio.kaappo.koe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.Arrays;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ViewPager2 pager2 = findViewById(R.id.viewPager2);
        pager2.setAdapter(new QuestionView.Adapter(Arrays.asList(
                Arrays.asList("A", "B"),
                Arrays.asList("B", "C"),
                Arrays.asList("A", "C"),
                Arrays.asList("A", "B", "C")
        )));
    }
}
