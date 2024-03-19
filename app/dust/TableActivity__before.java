package com.websarva.wings.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.asLiveData;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableActivity__before extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // データ表示
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        BondDao bondDao = db.bondDao();

        LiveData<List<Bond>> bondListLiveData = bondDao.getAllBonds().asLiveData();
        bondListLiveData.observe(this, new Observer<List<Bond>>() {
            @Override
            public void onChanged(List<Bond> bonds) {
                SimpleAdapter adapter = new SimpleAdapter(bonds);
                recyclerView.setAdapter(adapter);
            }
        });

        // アダプターを設定


        // 「メニューに戻る」ボタンのクリックイベント
        findViewById(R.id.backToMenuButton).setOnClickListener(view -> {
            // MainActivityに遷移
            Intent intent = new Intent(TableActivity__before.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}