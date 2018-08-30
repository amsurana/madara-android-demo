package ai.madara.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private StringBuilder readLogs() {
        StringBuilder logBuilder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("ViewRootImpl") || line.contains("InputEventReceiver") || line.contains("InputMethodManager") || line.contains("OpenGLRenderer")) {
                    continue;
                }
                logBuilder.append(line + "\n");
            }
        } catch (IOException e) {
        }
        return logBuilder;
    }


    /**
     * Other classes can create a textview logcat and all logs will be filled in that view.
     *
     * @param view
     */
    public void showLogs(View view) {
        StringBuilder builder = readLogs();
        ((TextView) findViewById(R.id.logcat)).setText(builder.toString());
    }


}
