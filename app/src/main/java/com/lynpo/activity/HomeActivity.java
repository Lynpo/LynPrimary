package com.lynpo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.util.JniMethods;
import com.lynpo.model.User;
import com.lynpo.network.LynpoHttpMethods;

import java.util.HashMap;

import rx.Subscriber;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(JniMethods.stringFromJNI(/*this*/));

        getUserInfo();
    }

    private void getUserInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("u", "lynpo");
        LynpoHttpMethods.getInstance().getUsrInfo(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {

            }
        }, params);
    }
}
