package com.example.phpor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	    final TelephonyManager tm = (TelephonyManager) MyActivity.this.getSystemService(TELEPHONY_SERVICE);
	    Log.d("phpor", tm.toString());
	    ((Button) findViewById(R.id.btn_sysinfo)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				startActivity(new Intent(MyActivity.this, SysInfoActivity.class));
		    }
	    });

    }
}
