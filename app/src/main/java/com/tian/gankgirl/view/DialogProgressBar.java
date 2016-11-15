package com.tian.gankgirl.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.tian.gankgirl.R;

/**
 * Created by 1363655717 on 2016/8/30.
 */
public class DialogProgressBar {
    private Activity activity;
    Dialog dialog;
    private float densi;
    public DialogProgressBar(Activity activity) {
        this.activity=activity;
        init();

    }

    private void init() {
        densi = activity.getResources().getDisplayMetrics().density;
        dialog = new Dialog(
                activity, R.style.mystyle);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupWindow_view = layoutInflater.inflate(
                R.layout.dialog_progressbar,
                null);
        dialog.setContentView(popupWindow_view);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog
                .getWindow();
        dialogWindow.setLayout(
                (int) (150 * densi),
                (int) (150 * densi));
    }
    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
