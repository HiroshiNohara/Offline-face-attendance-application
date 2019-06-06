package com.android.fra;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CalendarViewFragment extends DialogFragment {

    private DialogFragment mContext = this;
    private View view;
    private MaterialCalendarView mcv;
    private SharedPreferences pref;

    public CalendarViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calender_dialog, container, false);
        mcv = (MaterialCalendarView) view.findViewById(R.id.mcv);
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grayPrimary)));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        setCancelable(false);
    }

    public void initData() {
        mcv.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        mcv.setSelectedDate(new Date());
        Locale locale = mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (pref.getInt("language", 0) == 3 || !(language.endsWith("zh") || language.endsWith("zh-rTW"))) {
            mcv.state().edit()
                    .setFirstDayOfWeek(Calendar.SUNDAY)
                    .setMinimumDate(CalendarDay.from(1970, 1, 1))
                    .setMaximumDate(CalendarDay.from(2099, 12, 31))
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();
            mcv.setWeekDayLabels(new String[]{this.getString(R.string.sunday), this.getString(R.string.monday), this.getString(R.string.tuesday),
                    this.getString(R.string.wednesday), this.getString(R.string.thursday), this.getString(R.string.friday), this.getString(R.string.saturday)});
            mcv.setTitleFormatter(new TitleFormatter() {
                @Override
                public CharSequence format(CalendarDay day) {
                    StringBuffer buffer = new StringBuffer();
                    int yearOne = day.getYear();
                    int monthOne = day.getMonth() + 1;
                    String showMonth;
                    if (monthOne == 1) {
                        showMonth = "January";
                    } else if (monthOne == 2) {
                        showMonth = "February";
                    } else if (monthOne == 3) {
                        showMonth = "March";
                    } else if (monthOne == 4) {
                        showMonth = "April";
                    } else if (monthOne == 5) {
                        showMonth = "May";
                    } else if (monthOne == 6) {
                        showMonth = "June";
                    } else if (monthOne == 7) {
                        showMonth = "July";
                    } else if (monthOne == 8) {
                        showMonth = "August";
                    } else if (monthOne == 9) {
                        showMonth = "September";
                    } else if (monthOne == 10) {
                        showMonth = "October";
                    } else if (monthOne == 11) {
                        showMonth = "November";
                    } else {
                        showMonth = "December";
                    }
                    buffer.append(showMonth).append(" ").append(yearOne);
                    return buffer;
                }
            });
        } else {
            mcv.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setMinimumDate(CalendarDay.from(1970, 1, 1))
                    .setMaximumDate(CalendarDay.from(2099, 12, 31))
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();
            mcv.setWeekDayLabels(new String[]{this.getString(R.string.sunday), this.getString(R.string.monday), this.getString(R.string.tuesday), this.getString(R.string.wednesday),
                    this.getString(R.string.thursday), this.getString(R.string.friday), this.getString(R.string.saturday)});
            mcv.setTitleFormatter(new TitleFormatter() {
                @Override
                public CharSequence format(CalendarDay day) {
                    StringBuffer buffer = new StringBuffer();
                    int yearOne = day.getYear();
                    int monthOne = day.getMonth() + 1;
                    buffer.append(yearOne).append(getString(R.string.year)).append(monthOne).append(getString(R.string.month));
                    return buffer;
                }
            });
        }
        HashSet<CalendarDay> dates = new HashSet<CalendarDay>();
        CalendarViewActivity calendarViewActivity = (CalendarViewActivity) getActivity();
        String uid = calendarViewActivity.getUid();
        List<com.android.fra.db.Date> date = LitePal.where("uid = ?", uid).find(com.android.fra.db.Date.class);
        String january = date.get(0).getJanuary();
        if (january != null) {
            String[] januaryArray = january.split(String.valueOf(" "));
            for (int i = 0; i < januaryArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(januaryArray[i], "yyyy-MM-dd"))));
            }
        }
        String february = date.get(0).getFebruary();
        if (february != null) {
            String[] februaryArray = february.split(String.valueOf(" "));
            for (int i = 0; i < februaryArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(februaryArray[i], "yyyy-MM-dd"))));
            }
        }
        String march = date.get(0).getMarch();
        if (march != null) {
            String[] marchArray = march.split(String.valueOf(" "));
            for (int i = 0; i < marchArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(marchArray[i], "yyyy-MM-dd"))));
            }
        }
        String april = date.get(0).getApril();
        if (april != null) {
            String[] aprilArray = april.split(String.valueOf(" "));
            for (int i = 0; i < aprilArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(aprilArray[i], "yyyy-MM-dd"))));
            }
        }
        String may = date.get(0).getMay();
        if (may != null) {
            String[] mayArray = may.split(String.valueOf(" "));
            for (int i = 0; i < mayArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(mayArray[i], "yyyy-MM-dd"))));
            }
        }
        String june = date.get(0).getJune();
        if (june != null) {
            String[] juneArray = june.split(String.valueOf(" "));
            for (int i = 0; i < juneArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(juneArray[i], "yyyy-MM-dd"))));
            }
        }
        String july = date.get(0).getJuly();
        if (july != null) {
            String[] julyArray = july.split(String.valueOf(" "));
            for (int i = 0; i < julyArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(julyArray[i], "yyyy-MM-dd"))));
            }
        }
        String august = date.get(0).getAugust();
        if (august != null) {
            String[] augustArray = august.split(String.valueOf(" "));
            for (int i = 0; i < augustArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(augustArray[i], "yyyy-MM-dd"))));
            }
        }
        String september = date.get(0).getSeptember();
        if (september != null) {
            String[] septemberArray = september.split(String.valueOf(" "));
            for (int i = 0; i < septemberArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(septemberArray[i], "yyyy-MM-dd"))));
            }
        }
        String october = date.get(0).getOctober();
        if (october != null) {
            String[] octoberArray = october.split(String.valueOf(" "));
            for (int i = 0; i < octoberArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(octoberArray[i], "yyyy-MM-dd"))));
            }
        }
        String november = date.get(0).getNovember();
        if (november != null) {
            String[] novemberArray = november.split(String.valueOf(" "));
            for (int i = 0; i < novemberArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(novemberArray[i], "yyyy-MM-dd"))));
            }
        }
        String december = date.get(0).getDecember();
        if (december != null) {
            String[] decemberArray = december.split(String.valueOf(" "));
            for (int i = 0; i < decemberArray.length; i++) {
                dates.add(new CalendarDay(new Date(str2long(decemberArray[i], "yyyy-MM-dd"))));
            }
        }
        mcv.setSelectionColor(getResources().getColor(R.color.faceDetector));
        mcv.addDecorators(new HighlightWeekendsDecorator(), new EventDecorator(R.color.colorAccent, dates));

    }

    private static long str2long(String content, String spf_Value) {
        long l = 0;
        SimpleDateFormat spf = new SimpleDateFormat(spf_Value);
        Date date;
        try {
            date = spf.parse(content);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

}
