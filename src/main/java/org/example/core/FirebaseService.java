package org.example.core;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FirebaseService {
    private static volatile boolean initialized = false;

    public static Firestore getDb() {
        if (!initialized){
            initWithEnvOrFile();
            initialized = true;
        }
        return FirestoreClient.getFirestore();
    }

    private static void initWithEnvOrFile() {
        try {
            // Doc: inicialização com credenciais de aplicativo/arquivo JSON.
            // https://firebase.google.com/docs/firestore/query-data/get-data
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            try(InputStream in = new FileInputStream("secrets/serviceAccountKey.json")){
                GoogleCredentials credentials = GoogleCredentials.fromStream(in);
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .build();
                FirebaseApp.initializeApp(options);
            }catch (IOException ex){
                throw new RuntimeException("Falha ao inicializar Firebase Admin SDK. " +
                        "Defina GOOGLE_APPLICATION_CREDENTIALS ou coloque secrets/serviceAccountKey.json na raiz.", ex);
            }
        }

    }
}
