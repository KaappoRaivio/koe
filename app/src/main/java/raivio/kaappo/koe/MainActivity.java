package raivio.kaappo.koe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.liefery.android.vertical_stepper_view.VerticalStepperView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY, SheetsScopes.SPREADSHEETS);

//    @Override
//    protected void onCreate (Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ((QuestionView) findViewById(R.id.question_view_1)).setOptions(Arrays.asList("A", "B", "C"));
//        ((QuestionView) findViewById(R.id.question_view_2)).setOptions(Arrays.asList("A", "B", "C", "D"));
//
////        ((VerticalStepperView) findViewById(R.id.main_stepper_view)).setStepperAdapter(new QuestionListAdapter(this));
//
////        setTouchListeners();
////        setDragListeners();
//        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        // Google Accounts
//        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, SCOPES)
//                .setSelectedAccountName("kaappo.raivio")
//                .setBackOff(new ExponentialBackOff());
//
//        final NetHttpTransport HTTP_TRANSPORT;
//        HTTP_TRANSPORT = new NetHttpTransport();
//
//        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, new JacksonFactory(), credential)
//                .setApplicationName("Koe")
//                .build();
//
//        try {
//            service.spreadsheets().values()
//                    .append("1tUQ9kKtYC3K3IDGzoS8s8h50NtjYaZ4x2uq02DZMLVs", "B2:D", new ValueRange().setRange("B2:D").setValues(Arrays.asList(Arrays.asList("a", "<", "b"))))
//                    .setValueInputOption("USER_ENTERED")
//                    .execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
//        credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
//        // Tasks client
//        service =
//                new com.google.api.services.tasks.Tasks.Builder(httpTransport, jsonFactory, credential)
//                        .setApplicationName("Google-TasksAndroidSample/1.0").build();
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
