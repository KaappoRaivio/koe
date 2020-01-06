package raivio.kaappo.koe.sheets;

import android.util.Pair;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reporter {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY, SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String APPLICATION_NAME = "Koe";

    private Sheets service;
    private String spreadsheetId;

//    public Reporter (String spreadsheetId) {
//        this.spreadsheetId = spreadsheetId;
//
//        final NetHttpTransport HTTP_TRANSPORT;
//        try {
//            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
//            @Override
//            public void initialize(HttpRequest request) throws IOException {
//                request.setHe
//            }
//        })
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }

    public void reportResult (List<String> result) {
        List<Pair<String, String>> flattened = flattenResult(result);

        List<List<Object>> data = flattened.stream().map(pair -> new ArrayList<Object>(Arrays.asList(pair.first, "<", pair.second))).collect(Collectors.toList());


        AppendValuesResponse response;
        try {
            response = service.spreadsheets()
                .values()
                .append(spreadsheetId, "B2:D", new ValueRange()
                    .setRange("B2:D")
                    .setValues(data)
                )
                .setValueInputOption("USER_ENTERED")
                .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response);
    }

    private List<Pair<String, String>> flattenResult (List<String> result) {
        List<Pair<String, String>> flattened = new ArrayList<>();

        for (int right = result.size() - 1; right >= 0; right--) {
            for (int left = 0; left < right; left++) {
                flattened.add(new Pair<>(result.get(left), result.get(right)));
            }
        }

        return flattened;
    }

    private static Credential getCredentials (final NetHttpTransport HTTP_TRANSPORT) {
        InputStream in = Optional.ofNullable(Reporter.class.getResourceAsStream(CREDENTIALS_FILE_PATH)).orElseThrow(RuntimeException::new);

        GoogleClientSecrets clientSecrets = null;
        try {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        try {
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
