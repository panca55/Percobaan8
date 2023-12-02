package com.panca_nugraha.percobaan8;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {
    private ArrayList<String> dataNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        dataNama = new ArrayList<>();

        ListView listView = findViewById(R.id.list_view);

        firestore.collection("pengguna").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // Pastikan bahwa field "nama" dan "stambuk" ada dalam dokumen sebelum mengaksesnya
                    if (document.contains("nama") && document.contains("stambuk")) {
                        dataNama.add(document.getString("nama") + " - " + document.getString("stambuk"));
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataNama);
                listView.setAdapter(adapter);
            } else {
                Log.w("Lihat", "Error getting documents.", task.getException());
            }
        });
    }
}
