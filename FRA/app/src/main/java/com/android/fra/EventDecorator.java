package com.android.fra;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new BackgroundSpan());
    }

    public class BackgroundSpan extends BaseActivity implements LineBackgroundSpan {

        @Override
        public void drawBackground(Canvas canvas, Paint paint,
                                   int left, int right, int top, int baseline, int bottom,
                                   CharSequence charSequence,
                                   int start, int end, int lineNum) {
            canvas.save();
            Paint p = new Paint();
            p.setColor(Color.parseColor("#009B59"));
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(3f);
            p.setAntiAlias(true);
            canvas.drawCircle((left + right) / 2, (top + bottom) / 2, 36, p);
            canvas.restore();
        }
    }
}
