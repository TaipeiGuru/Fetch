package com.example.fetch;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OkHttpClient client = new OkHttpClient();

        // Executor documentation: https://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/ExecutorService.html
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(() -> {
            try {
                /* Get data string and convert to Items
                 * GSON Documentation:
                 * https://github.com/google/gson
                 * https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/Gson.html
                 */
                Gson gson = new Gson();
                String jsonString = getJSONFromUrl(client);
                Item[] items = gson.fromJson(jsonString, Item[].class);

                /* Define custom comparator
                 * Comparator documentation:
                 * https://docs.oracle.com/javase/8/docs/api/?java/util/Comparator.html
                */
                Comparator<Item> compareName = Comparator
                        .comparingInt(Item::getListId)
                        .thenComparingInt(Item::getNameNumber);
                // sort Items in list
                List<Item> filteredItems = Arrays.stream(items)
                        .filter(Item::nameIsValid)
                        .sorted(compareName)
                        .collect(Collectors.toList());

                // set recycler view to Items list
                runOnUiThread(() -> {
                    itemAdapter = new ItemAdapter(filteredItems);
                    recyclerView.setAdapter(itemAdapter);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }

    // Function that fetches JSON string from URL
    public static String getJSONFromUrl(OkHttpClient client) throws Exception {
        // Request documentation: https://square.github.io/okhttp/3.x/okhttp/okhttp3/Request.Builder.html
        Request request = new Request.Builder().url("https://fetch-hiring.s3.amazonaws.com/hiring.json").build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}
