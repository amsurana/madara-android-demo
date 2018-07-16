package ai.madara.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Amit S on 11/07/18.
 */
public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("zmq");
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void runMadara(View view) {
        startActivity(new Intent(this, MadaraTestsRunner.class));
    }

    public void runGams(View view) {
        startActivity(new Intent(this, GamsTestsRunner.class));
    }
}
