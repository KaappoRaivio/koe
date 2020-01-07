package raivio.kaappo.koe;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.EasyPermissions;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY, SheetsScopes.SPREADSHEETS);
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORISATION = 1001;

    public static final String AMOUNT_OF_QUESTIONS = "amount_of_questions";
    public static final String AMOUNT_OF_OPTIONS = "amount_of_options";

    GoogleAccountCredential credentials;
    private Reporter reporter;

    private EditText amountOfQuestions;
    private EditText amountOfOptionsInAQuestion;

    private QuestionManager manager;

    private AsyncTask<Integer, Void, List<List<String>>> task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        amountOfQuestions = findViewById(R.id.main_amount_of_questions);
        amountOfOptionsInAQuestion = findViewById(R.id.main_amount_of_options_in_a_question);

        credentials = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), SCOPES)
                .setBackOff(new ExponentialBackOff());
        getPerms();


    }

    public static List<List<String>> questions;

    public void onStartButtonPress (View view) {
        manager = new QuestionManager(reporter);
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AMOUNT_OF_QUESTIONS, Integer.parseInt(amountOfQuestions.getText().toString()));
        bundle.putInt(AMOUNT_OF_OPTIONS, Integer.parseInt(amountOfQuestions.getText().toString()));
        try {
            questions = task.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        intent.putExtra(Intent.ACTION_ATTACH_DATA, bundle);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    public void onParamUpdate (View view) {
        int amountOfQuestions = Integer.parseInt(this.amountOfQuestions.getText().toString());
        int amountOfOptions = Integer.parseInt(this.amountOfOptionsInAQuestion.getText().toString());

        task = new AsyncTask<Integer, Void, List<List<String>>>() {
            @Override
            protected List<List<String>> doInBackground(Integer... ints) {
//                return manager.getBestOptions(ints[0], ints[1]);

                try {
                    List<List<String>> bestOptions = manager.getBestOptions(ints[0], ints[1]);
                    MainActivity.this.runOnUiThread(() -> {
                        findViewById(R.id.main_start).setEnabled(true);
                    });
                    return bestOptions;
                } catch (UserRecoverableAuthIOException e) {
                    startActivityForResult(e.getIntent(), REQUEST_AUTHORISATION);
                    return null;
                }
            }

//            @Override
//            protected void onPreExecute () {
//
//            }
//
//            @Override
//            protected void onPostExecute (List<List<String>> lists) {
//                findViewById(R.id.main_start).setEnabled(true);
//            }
        };

        task.execute(amountOfQuestions, amountOfOptions);
    }

    public void getPerms () {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        } else {
            chooseAccount();
        }

    }

    public void chooseAccount () {
        System.out.println("CHOOSING");
        if (credentials.getSelectedAccount() == null) {
            startActivityForResult(credentials.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK) {
                    credentials.setSelectedAccountName(Objects.requireNonNull(data).getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                }

                reporter = new Reporter(credentials);
                manager = new QuestionManager(reporter);
//                task.execute(Integer.parseInt(amountOfQuestions.getText().toString()), Integer.parseInt(amountOfOptionsInAQuestion.getText().toString()));

                break;

            case REQUEST_AUTHORISATION:
                System.out.println("Authorized");
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        chooseAccount();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
