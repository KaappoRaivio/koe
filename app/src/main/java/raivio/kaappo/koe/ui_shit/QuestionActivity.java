package raivio.kaappo.koe.ui_shit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import raivio.kaappo.koe.MainActivity;
import raivio.kaappo.koe.QuestionManager;
import raivio.kaappo.koe.R;
import raivio.kaappo.koe.Reporter;

import static raivio.kaappo.koe.MainActivity.AMOUNT_OF_OPTIONS;
import static raivio.kaappo.koe.MainActivity.AMOUNT_OF_QUESTIONS;

public class QuestionActivity extends AppCompatActivity {

    private ViewPager2 pager2;
    private Reporter reporter;
    private Reporter reporter2;
    private QuestionManager manager;

    private int amountOfQuestions;
    private int amountOfOptions;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        amountOfQuestions = getIntent().getBundleExtra(Intent.ACTION_ATTACH_DATA).getInt(AMOUNT_OF_QUESTIONS);
        amountOfOptions = getIntent().getBundleExtra(Intent.ACTION_ATTACH_DATA).getInt(AMOUNT_OF_OPTIONS);


        System.out.println("ALIVE");

        reporter = Reporter.getInstance();
        reporter2 = Reporter.getInstance2();
        pager2 = findViewById(R.id.viewPager2);
        manager = new QuestionManager(reporter, reporter2);

        pager2.setAdapter(new QuestionView.Adapter(MainActivity.questions, MainActivity.questions2));


        DotsIndicator indicator = findViewById(R.id.question_dots_indicator);
        indicator.setViewPager2(pager2);
    }

    @SuppressLint("StaticFieldLeak")
    public void getData2 (View view) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    System.out.println("DATA: " + reporter.getData("A20:C22"));
                    System.out.println("MANAGER: " + manager.getBestOptionsM(3, 2));
                } catch (UserRecoverableAuthIOException e) {
                    startActivityForResult(e.getIntent(), Reporter.REQUEST_AUTHORIZATION);
                }
                return null;
            }
        }.execute();
    }

    private List<AsyncTask> tasks = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    public void onButtonClicked (View view) {
        int position = pager2.getCurrentItem();
        QuestionView questionView = ((QuestionView.Adapter) pager2.getAdapter()).getAt(pager2.getCurrentItem());

        if (questionView.isReady() && position < pager2.getAdapter().getItemCount()) {
            List<String> result = questionView.getResult();

            AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        if (result.stream().max((x, y) -> x.charAt(0) - y.charAt(0)).orElse("A").charAt(0) > 'f') {
                            reporter2.reportData(result);
                        } else {
                            reporter.reportData(result);
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (!result) {
                        Toast.makeText(getApplicationContext(), "PROBLEM", Toast.LENGTH_SHORT).show();
                    }
                }

            };
            task.execute();
            tasks.add(task);


            pager2.setCurrentItem(position + 1);

        }

        if (pager2.getCurrentItem() == pager2.getAdapter().getItemCount() - 1) {
            ((Button) findViewById(R.id.question_next)).setText(R.string.finish);
            ((Button) findViewById(R.id.question_next)).setOnClickListener(this::onFinish);
        }

    }

    public void onFinish (View view) {
        int position = pager2.getCurrentItem();
        QuestionView questionView = ((QuestionView.Adapter) pager2.getAdapter()).getAt(pager2.getCurrentItem());

        if (questionView.isReady() && position < pager2.getAdapter().getItemCount()) {
            List<String> result = questionView.getResult();

            @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        if (result.stream().max((x, y) -> x.charAt(0) - y.charAt(0)).orElse("A").charAt(0) > 'f') {
                            reporter2.reportData(result);
                        } else {
                            reporter.reportData(result);
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (!result) {
                        Toast.makeText(getApplicationContext(), "PROBLEM", Toast.LENGTH_SHORT).show();
                    }
                }

            };
            task.execute();
            tasks.add(task);
        }
//        for (AsyncTask task : tasks) {
//            try {
//                System.out.println(task.get());
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        finish();
    }
}
