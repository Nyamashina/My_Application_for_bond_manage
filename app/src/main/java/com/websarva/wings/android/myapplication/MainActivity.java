package com.websarva.wings.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューリソースを使用してメニューをインフレートする
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    /*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // アイテムのクリックイベントを処理
        switch (item.getItemId()) {
            case R.id.action_settings:
                // 設定アクション
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */


    public void launchAddBondActivity(View view) {
        Intent intent = new Intent(this, AddBondActivity.class);
        startActivity(intent);
    }

    public void launchTableActivity(View view) {
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
    }

    public void launchCalendarActivity(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}