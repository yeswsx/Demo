package com.example.wsx.demo;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewContextActivity extends AppCompatActivity implements OnClickListener {

    private TextView mText;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_context);

        findViewById(R.id.btn_default).setOnClickListener(this);
        findViewById(R.id.btn_mdpi).setOnClickListener(this);
        findViewById(R.id.btn_hdpi).setOnClickListener(this);
        findViewById(R.id.btn_xhdpi).setOnClickListener(this);
        findViewById(R.id.btn_xxhdpi).setOnClickListener(this);
        mText = (TextView)findViewById(R.id.text);
    }

    @Override
    public void onClick(View v) {
        Configuration configuration = getResources().getConfiguration();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            switch (v.getId()) {
                case R.id.btn_default:
                    break;
                case R.id.btn_mdpi:
                    configuration.densityDpi = 160;
                    break;
                case R.id.btn_hdpi:
                    configuration.densityDpi = 240;
                    break;
                case R.id.btn_xhdpi:
                    configuration.densityDpi = 320;
                    break;
                case R.id.btn_xxhdpi:
                    configuration.densityDpi = 480;
                    break;
                default:
                    break;
            }

            context = createConfigurationContext(configuration);
            if (context != null) {
                mText.setText(getResources().getText(R.string.resource_name));
            }
        }
    }
}
