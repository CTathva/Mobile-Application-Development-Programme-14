package com.akash.cp.vtu.vtupartb_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Base{
    Button mButtonStart, mButtonStop;
    private MyTask myTask;
    Integer count = 1;
    TextView banner;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    @Override
    public void init() {
        banner = (TextView) findViewById(R.id.banner);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mButtonStart = (Button) findViewById(R.id.start);
        mButtonStop = (Button) findViewById(R.id.stop);
    }

    @Override
    public void listener() {
        myTask = new MyTask();
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!myTask.isCancelled()) {
                        Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
                        count = 1;
                        myTask.execute(50);
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "stop", Toast.LENGTH_SHORT).show();
                myTask.cancel(true);
            }
        });
    }
    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, "onPostExecute" + result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "Task Starting...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            params.setMargins(values[0] *5, 0, 0, 0);
            banner.setLayoutParams(params);
            Log.i("onProgressUpdate", String.valueOf(values[0]));
        }
    }
}
