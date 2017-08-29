package com.setupproject.hitesh.customdialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.setupproject.hitesh.MyApplication;
import com.setupproject.hitesh.R;
import com.setupproject.hitesh.enums.CalenderDateSelection;
import com.setupproject.hitesh.helper.ToastHelper;
import com.setupproject.hitesh.utility.Constants;
import com.setupproject.hitesh.utility.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ${hitesh} on 12/6/2016.
 */
public class CustomDialog {

    private static final int PREV_MONTH = 1;
    private static CustomDialog ourInstance;
    private Dialog mDialog;
    private int mSelectedYear, mSelectedMonth, mSelectedDay;

    private CustomDialog() {
    }

    public static CustomDialog getInstance() {

        if (ourInstance == null) {
            ourInstance = new CustomDialog();
        }
        return ourInstance;
    }

    public void showProgress(Context mContext, String mTitle, boolean mIsCancelable) {

        mDialog = new Dialog(mContext, R.style.dialogStyle);
        // @SuppressLint("InflateParams")
        //  View dataView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_progress_update, null, false);
        mDialog.setContentView(R.layout.custom_dialog_progress_update);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        /* Set Dialog width match parent */
        // mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

       /* TextView mDialogTitle = (TextView) mDialog.findViewById(R.id.tv_customProgressBarTitle);
        mDialogTitle.setTypeface(MyApplication.getInstance().FONT_ROBOTO_REGULAR);
        mDialogTitle.setText(mTitle);*/

        mDialog.setCancelable(mIsCancelable);

        try {
            if (mDialog != null) {
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlert(Context mContext, String mTitle, boolean mIsCancelable) {

        mDialog = new Dialog(mContext, R.style.dialogStyle);
        //  @SuppressLint("InflateParams")
        //  View dataView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_alert, null, false);
        mDialog.setContentView(R.layout.custom_dialog_alert);

         /* Set Dialog width match parent */
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        //mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView mDialogTitle = (TextView) mDialog.findViewById(R.id.tv_alert);
        mDialogTitle.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);
        mDialogTitle.setText(mTitle);

        TextView tvOk = (TextView) mDialog.findViewById(R.id.tv_ok);
        tvOk.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setCancelable(mIsCancelable);

        if (mDialog != null) {
            if (!isDialogShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * This method open Date picker dialog to select Date.
     * This method manages future, past and all Date selection in calender
     * e.g. (1) set future Date limit e.g. event - pass Date[1-31], month [1-12], year[2016-2099]
     * (2) set past Date limit e.g. birthDate, age limit - pass Date[1-31], month [1-12], year[1970-2016]
     *
     * @param context               (Context)  : context
     * @param textView              (TextView)   : to show selected Date
     * @param calenderDateSelection (enum) :  e.g. CALENDER_WITH_PAST_DATE
     * @param year                  (int)     : year e.g. 2016
     * @param month                 (int)     : month e.g. 9
     * @param day                   (int)     : day   e.g. 20
     */
    public void showDatePickerDialog(final Context context, final TextView textView, final CalenderDateSelection calenderDateSelection,
                                     int year, int month, int day) {

        final Calendar mCurrentDate = Calendar.getInstance();

        final Calendar minDate = Calendar.getInstance();

        int mYear = mCurrentDate.get(Calendar.YEAR);
        int mMonth = mCurrentDate.get(Calendar.MONTH);
        int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker = new DatePickerDialog(context, R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        mSelectedYear = selectedyear;
                        mSelectedMonth = selectedmonth;
                        mSelectedDay = selectedday;

                        mCurrentDate.set(mSelectedYear, mSelectedMonth, mSelectedDay);
                        textView.setText(Utils.dateFormat(mCurrentDate.getTimeInMillis(), Constants.DATE_MM_DD_YYYY));
                /* it is used to pass selected Date in millisecond*/
                        textView.setTag(mCurrentDate.getTimeInMillis());
                    }
                }, mYear, mMonth, mDay);


        switch (calenderDateSelection) {

            case CALENDER_WITH_ALL_DATE:

                break;
            case CALENDER_WITH_PAST_DATE:

                minDate.set(Calendar.YEAR, year);
                minDate.set(Calendar.MONTH, month - PREV_MONTH);
                minDate.set(Calendar.DAY_OF_MONTH, day);

                mDatePicker.getDatePicker().setMinDate(minDate.getTimeInMillis());
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                break;
            case CALENDER_WITH_FUTURE_DATE:

                minDate.set(Calendar.YEAR, year);
                minDate.set(Calendar.MONTH, month - PREV_MONTH);
                minDate.set(Calendar.DAY_OF_MONTH, day);

                mCurrentDate.set(Calendar.DAY_OF_MONTH, mDay);

                mDatePicker.getDatePicker().setMinDate(mCurrentDate.getTimeInMillis());
                mDatePicker.getDatePicker().setMaxDate(minDate.getTimeInMillis());
                break;
        }

        mDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                    textView.setText("");
                    //onBackPressed();
                }
            }
        });
       /* mDatePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    dialog.dismiss();
                    mCurrentDate.set(mSelectedYear, mSelectedMonth, mSelectedDay);
                    textView.setText(Utils.dateFormat(mCurrentDate.getTimeInMillis(), Constants.DATE_MM_DD_YYYY));
                *//* it is used to pass selected Date in millisecond*//*
                    textView.setTag(mCurrentDate.getTimeInMillis());

                }
            }
        });*/
//        mDatePicker.setTitle(context.getString(R.string.str_select_date));
        mDatePicker.setCanceledOnTouchOutside(false);

        try {
            if (!mDatePicker.isShowing()) {
                mDatePicker.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void showTimePickerDialog(final Context context, final TextView textView, final int timeFormat) {

        Calendar mCurrentTime = Calendar.getInstance();

        final int[] hour = new int[1];
        final int[] minute = new int[1];
        final int[] seconds = new int[1];

        hour[0] = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        minute[0] = mCurrentTime.get(Calendar.MINUTE);
        seconds[0] = mCurrentTime.get(Calendar.SECOND);

        final TimePickerDialog mTimePicker = new TimePickerDialog(context, R.style.DatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        //hour[0] = selectedHour;
                        //minute[0] = selectedMinute;


                        if (timeFormat == 1) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("hh:mm");

                                Date Date1 = format.parse(hour[0] + ":" + minute[0]);
                                Date Date2 = format.parse(selectedHour + ":" + selectedMinute);
                                long mills = Date2.getTime() - Date1.getTime();

                                //Debug.trace("currentTime", "" +);

                                int diffHours = (int) (mills / (1000 * 60 * 60));
                                int diffMinutes = (int) (mills % (1000 * 60 * 60));
                                if (selectedHour < hour[0]) {
                                    ToastHelper.getInstance().showMessage("");
                                } else {
                                    textView.setText(String.valueOf(selectedHour - hour[0]));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (timeFormat == 2) {
                            hour[0] = selectedHour;
                            minute[0] = selectedMinute;

                            textView.setText(String.format("%02d:%02d:%02d", hour[0], minute[0], seconds[0]));
                        }

                    }
                }, hour[0], minute[0], true);//Yes 24 hour time

        // mTimePicker.setTitle("Select Time");

        /*mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, "SET", new DialogInterface.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == DialogInterface.BUTTON_POSITIVE) {
                    mTimePicker.dismiss();
                    textView.setText(String.format("%02d:%02d", hour[0], minute[0]));
                    // textView.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                }
            }
        });*/
        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            mTimePicker.dismiss();
                            textView.setText("");
                        }
                    }
                });

        mTimePicker.setCanceledOnTouchOutside(false);
        try {
            if (!mTimePicker.isShowing()) {
                mTimePicker.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dismiss() {
        try {
            if (mDialog != null) {
                if (isDialogShowing()) {
                    mDialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return (boolean) : return true or false, if the dialog is showing or not
     */
    public boolean isDialogShowing() {

        return mDialog != null && mDialog.isShowing();
    }

}
