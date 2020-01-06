package raivio.kaappo.koe;

import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reporter {
    private GoogleAccountCredential credential;

    public static final int REQUEST_AUTHORIZATION = 1001;

    public static Reporter instance;

    private Sheets service;
    private String spreadsheetId;

    public static Reporter getInstance () {
        return Optional.of(instance).orElseThrow(RuntimeException::new);
    }

    public Reporter (GoogleAccountCredential credential) {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Already instance");
        }
        this.credential = credential;
        spreadsheetId = "1tUQ9kKtYC3K3IDGzoS8s8h50NtjYaZ4x2uq02DZMLVs";

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        service = new Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Testi")
                .build();
    }

    public List<List<String>> getData (String range) throws UserRecoverableAuthIOException {

        ValueRange response;

        try {
            response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
        } catch (IOException e) {
            if (e instanceof UserRecoverableAuthIOException) {
                throw (UserRecoverableAuthIOException) e;
            } else {
                throw new RuntimeException(e);
            }
        }

        System.out.println("RESPONSE:" + response);

        return response
                .getValues()
                .stream().map(list -> list.stream().map(Object::toString).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void reportData (List<String> unNormalizedData) {
        List<Pair<String, String>> normalizedData = normalize(unNormalizedData);

        List<List<Object>> data = normalizedData.stream().map(pair -> new ArrayList<Object>(Arrays.asList(pair.first, "<", pair.second))).collect(Collectors.toList());

        try {
            String range = "A20:C";
            AppendValuesResponse response = service.spreadsheets().values().append(spreadsheetId, range, new ValueRange()
                        .setValues(data)
                        .setRange(range)
                        )
                    .setValueInputOption("USER_ENTERED")
//                    .setInsertDataOption("OVERWRITE")
                    .setRange(range).execute();
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        service.spreadsheets().values().ap
//        System.out.println(response);
    }

    public static List<Pair<String, String>> normalize (List<String> unNormalizedData) {
        List<Pair<String, String>> normalizedData = new ArrayList<>();

        for (int right = unNormalizedData.size() - 1; right >= 0; right--) {
            for (int left = 0; left < right; left++) {
                normalizedData.add(new Pair<>(unNormalizedData.get(left), unNormalizedData.get(right)));
            }
        }

        return normalizedData;
    }

    public static void main(String[] args) {
        System.out.println(normalize(Arrays.asList("A", "B", "C")));
    }
}
