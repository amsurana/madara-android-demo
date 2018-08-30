package ai.madara.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.lang.reflect.Method;

public class GamsTestsRunner extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gams_tests);

        ((Spinner) findViewById(R.id.tests)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String testName = adapterView.getSelectedItem().toString();
                performTest(testName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void performTest(String testName) {

        //Run main class.
        try {

            String findClass = "ai.gams.tests." + testName;
            Class clz = Class.forName(findClass);
            Object o = clz.newInstance();
            Method mainMethod = clz.getDeclaredMethod("main", String[].class);
            String[] params = null; // init params accordingly
            mainMethod.invoke(o, (Object) params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
