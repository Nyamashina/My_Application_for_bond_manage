package com.websarva.wings.android.myapplication;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;
import java.util.List;

public class BondEventDecorator implements DayViewDecorator {
    private Drawable drawable;
    private HashSet<CalendarDay> dates;

    public BondEventDecorator(Drawable drawable, List<CalendarDay> dates) {
        this.drawable = drawable;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        // 予定金額などのテキストを追加するロジックをここに実装
    }
}
