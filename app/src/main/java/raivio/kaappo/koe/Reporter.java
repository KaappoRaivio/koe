package raivio.kaappo.koe;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Reporter {
    private GoogleAccountCredential credential;

    public static final int REQUEST_AUTHORIZATION = 1001;


    public Reporter (GoogleAccountCredential credential) {
        this.credential = credential;
    }

    public List<String> getDataFromApi () throws UserRecoverableAuthIOException {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Sheets service = new Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Testi")
                .build();

        String spreadsheetId = "1tUQ9kKtYC3K3IDGzoS8s8h50NtjYaZ4x2uq02DZMLVs";
        String range = "B2:D4";

        ValueRange response;

        try {
            response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//            return getDataFromApi(credential);


        System.out.println("RESPONSE:" + response);


        return response
                .getValues()
                .stream()
                .map(list -> list
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.toList());
    }
}
