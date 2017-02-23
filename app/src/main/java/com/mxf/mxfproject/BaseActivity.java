package com.mxf.mxfproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);

        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
