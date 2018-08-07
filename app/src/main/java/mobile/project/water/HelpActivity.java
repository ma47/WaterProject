package mobile.project.water;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    TextView info1;
    TextView info2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        info1 = findViewById(R.id.first_info);
        info2 = findViewById(R.id.second_info);


        info1.setText("Icons made by https://www.flaticon.com/authors/freepik from www.flaticon.com" + "\n" + " This application tracks how much water you drink everyday" + "\n");
        info2.setText("Usage:" + "\n" + "Enter goal" + "\n" + "Upload new data about your water intake" + "\n" + "Press end your day if you've finished" + "\n" + "You are Done");
    }
}
