package com.websarva.wings.android.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private Drawable eventDrawable;


    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューリソースを使用してメニューをインフレートする
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        eventDrawable = ContextCompat.getDrawable(this, R.drawable.my_event_icon);
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        BondDao bondDao = db.bondDao();

        // ViewModelFactoryのインスタンスを生成
        BondViewModelFactory factory = new BondViewModelFactory(bondDao);

        // ViewModelの取得
        BondViewModel bondViewModel = new ViewModelProvider(this, factory).get(BondViewModel.class);
        bondViewModel = new ViewModelProvider(this).get(BondViewModel.class);
        bondViewModel.getBondListLiveData().observe(this, new Observer<List<Bond>>() {
            @Override
            public void onChanged(List<Bond> bonds) {
                for (Bond bond : bonds) {
                    addEventForBond(bond);
                }
            }
        });

        findViewById(R.id.backToMenuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void addEventForBond(Bond bond) {
        // 利払い予定日にイベントを追加
        if (bond.getInterestPaymentDate() != null) {
            CalendarDay interestPaymentDay = CalendarDay.from(bond.getInterestPaymentDate());
            calendarView.addDecorator(new EventDecorator(eventDrawable, interestPaymentDay));
        }

        // 償還予定日にイベントを追加
        if (bond.getRedemptionDate() != null) {
            CalendarDay redemptionDay = CalendarDay.from(bond.getRedemptionDate());
            calendarView.addDecorator(new EventDecorator(eventDrawable, redemptionDay));
        }
    }

    class EventDecorator implements DayViewDecorator {
        private final Drawable drawable;
        private final CalendarDay date;

        public EventDecorator(Drawable drawable, CalendarDay date) {
            this.drawable = drawable;
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
        }
    }
}