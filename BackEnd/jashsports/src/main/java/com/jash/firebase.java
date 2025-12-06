package com.jash;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.FirebaseApp;

public class firebase {

    public firebase() throws Exception {

        // Tested this and it works
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = new FileInputStream("BackEnd/jashsports/jashsports-firebase-adminsdk-fbsvc-94fb012fce.json");

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
        }
    }

    // tested it and it works
    public String getUidByEmail(String email) throws FirebaseAuthException {
        if (email == null || email.isEmpty()) return null;
        UserRecord user = FirebaseAuth.getInstance().getUserByEmail(email);
        return user.getUid();
    }


}
