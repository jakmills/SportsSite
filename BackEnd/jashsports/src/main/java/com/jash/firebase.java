package com.jash;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import io.github.cdimascio.dotenv.Dotenv;

import com.google.firebase.FirebaseApp;

public class firebase {

    public firebase() throws Exception {

        // Tested this and it works
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = null;
            if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") == null) {
                Dotenv dotnev = Dotenv.load();
                serviceAccount = new FileInputStream(dotnev.get("GOOGLE_APPLICATION_CREDENTIALS"));
            } else {
                serviceAccount = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }

    // tested it and it works
    public String getUidByEmail(String email) throws FirebaseAuthException {
        if (email == null || email.isEmpty())
            return null;
        UserRecord user = FirebaseAuth.getInstance().getUserByEmail(email);
        return user.getUid();
    }

}
