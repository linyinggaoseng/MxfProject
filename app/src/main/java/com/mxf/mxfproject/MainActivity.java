package com.mxf.mxfproject;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mxf.mxfproject.Model.UserEntity;
import com.mxf.mxfproject.Model.UserModel;
import com.mxf.mxfproject.server.HttpManager;
import com.mxf.mxfproject.server.LogInRequest;
import com.mxf.mxfproject.server.ServerCallBack;

public class MainActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    private  ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInRequest request = new LogInRequest(UserEntity.class);
        request.userName = "13989826414";
        request.passwdWord = "123456";
        HttpManager manager = new HttpManager(request, new ServerCallBack() {
            @Override
            public void onSucess(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        UserModel user = ((UserEntity) object).data;
                        Log.d("MainActivity", String.valueOf(user._id));
                        Log.d("MainActivity",user.user_name);
                    }
                });
            }

            @Override
            public void onFail(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        showProgressDialog();
        manager.performRequest(MainActivity.this);
    }
}
