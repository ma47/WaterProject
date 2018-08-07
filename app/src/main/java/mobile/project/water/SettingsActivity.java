package mobile.project.water;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import es.dmoral.toasty.Toasty;

import static mobile.project.water.MainActivity.GOAL;
import static mobile.project.water.MainActivity.PATH_GOAL_KEY;
import static mobile.project.water.MainActivity.PATH_GOAL_LOCATION;

public class SettingsActivity extends AppCompatActivity {

    Button help;
    Button feedback;
    Button changeGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.getNavigationIcon().setColorFilter(Color.rgb(255,255,255), PorterDuff.Mode.SRC_IN); // White arrow
        toolbar.setTitle("Settings");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // back to main activity
            }
        });

        /*
        initialisation of elements
         */
        help = findViewById(R.id.help);
        feedback = findViewById(R.id.feedback);
        changeGoal = findViewById(R.id.changegoal);


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "water.project@protonmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Message");
                startActivity(intent);
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        /*
        change goal input dialog
         */
        changeGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SettingsActivity.this)
                        .title(R.string.input)
                        .content(R.string.input_content)
                        .inputType(InputType.TYPE_CLASS_NUMBER )
                        .inputRangeRes(3,5,R.color.warning_stroke_color)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                String a = input.toString();
                                setGoal(a);
                                Toasty.success(SettingsActivity.this, "Goal changed!", Toast.LENGTH_SHORT,true).show();
                                Log.d("SETTINGS", "onInput: CHANGE GOAL ****************************");
                            }
                        }).show();
            }
        });
    }


    /*
    set and get goal from sharedPreferences to manipulate view in mainactivity
     */
    public void setGoal(String goal) {
        SharedPreferences uri = this.getSharedPreferences(PATH_GOAL_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uri.edit();
        editor.putString(PATH_GOAL_KEY, goal);
        editor.apply();
    }

    public String getGoal() {
        SharedPreferences uri = this.getSharedPreferences(PATH_GOAL_LOCATION, Context.MODE_PRIVATE);
        return uri.getString(PATH_GOAL_KEY, "");
    }
}
