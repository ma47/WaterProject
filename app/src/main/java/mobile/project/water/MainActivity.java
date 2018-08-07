package mobile.project.water;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;
    CircularFillableLoaders circle;
    Toolbar toolbar;

    private final static String TAG = MainActivity.class.getSimpleName();
    public final static String GOAL = "GOAL : ";
    public final static String TODAY = "TODAY : ";
    public final static String PATH_GOAL_LOCATION = "goal";
    public final static String PATH_GOAL_KEY = "goalFile";
    public final static String PATH_TODAY_LOCATION = "today";
    public final static String PATH_TODAY_KEY = "todayFile";
    public final static String PATH_PROGRESS_LOCATION = "progress";
    public final static String PATH_PROGRESS_KEY = "progressFile";



    Button endDay;
    Button settings;
    TextView goal;
    TextView today;
    int progress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        elements initialise
         */
        toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_waterdrop);

        settings = findViewById(R.id.toolbarSettings);
        toolbar.setTitle("  Water Project");

        goal = findViewById(R.id.goal);
        today = findViewById(R.id.today);
        endDay = findViewById(R.id.button3);

        goal.setText(GOAL + getGoal() + "ml");
        today.setText(TODAY + getToday() + "ml");

        /*
         Floating menu button init
          */
        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = findViewById(R.id.material_design_floating_action_menu_item5);

        circle = findViewById(R.id.circle);
        animateString(getGoal(),getToday());

        /*
        buttons on click
         */
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                materialDesignFAM.close(true);
                startActivity(intent);
            }
        });

        /*
        end the day  alert dialog + button + animate on change
         */
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Day will be ended")
                        .setCancelText("Cancel")
                        .setConfirmText("I'm sure")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        setTodayPref(0);
                                        setTodayTextFromInt(0);
                                        animateString(getGoal(),getToday());
                                        sweetAlertDialog.setTitleText("Success")
                                                .setConfirmText("OK")
                                                .setContentText("Day ended!")
                                                .setConfirmClickListener(null)
                                                .showCancelButton(false)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });

        /*
        floating buttons listeners
         */
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempToday = Integer.parseInt(getToday());
                int tempGoal = Integer.parseInt(getGoal());

                tempToday = tempToday + 250;

                setTodayTextFromInt(tempToday);
                setTodayPref(tempToday);

                Log.d(TAG, "onClick: **************** tempToday" + tempToday);
                animate(tempGoal, tempToday);

            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempToday = Integer.parseInt(getToday());
                int tempGoal = Integer.parseInt(getGoal());

                tempToday = tempToday + 500;

                setTodayTextFromInt(tempToday);
                setTodayPref(tempToday);

                Log.d(TAG, "onClick: **************** tempToday" + tempToday);
                animate(tempGoal, tempToday);

            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempToday = Integer.parseInt(getToday());
                int tempGoal = Integer.parseInt(getGoal());

                tempToday = tempToday + 1000;

                setTodayTextFromInt(tempToday);
                setTodayPref(tempToday);

                Log.d(TAG, "onClick: **************** tempToday" + tempToday);
                animate(tempGoal, tempToday);

            }
        });

        /*
        CUSTOM user input for todays intake
         */

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.inputCustomAmount)
                        .content(R.string.input_content)
                        .inputType(InputType.TYPE_CLASS_NUMBER )
                        .inputRangeRes(3,5,R.color.warning_stroke_color)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                int tempToday = Integer.parseInt(getToday());
                                int tempGoal = Integer.parseInt(getGoal());

                                tempToday = tempToday + Integer.parseInt(input.toString());
                                setTodayTextFromInt(tempToday);
                                setTodayPref(tempToday);

                                Log.d(TAG, "onClick: **************** tempToday CUSTOM" + tempToday);
                                animate(tempGoal, tempToday);

                                Toasty.success(MainActivity.this, "Added " + Integer.parseInt(input.toString()), Toast.LENGTH_SHORT,true).show();
                            }
                        }).show();


//                setTodayTextFromInt(tempToday);
//                setTodayPref(tempToday);
//
//                Log.d(TAG, "onClick: **************** tempToday" + tempToday);
//                animate(tempGoal, tempToday);

            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.inputCustomAmountDelete)
                        .content(R.string.input_content)
                        .inputType(InputType.TYPE_CLASS_NUMBER )
                        .inputRangeRes(3,5,R.color.warning_stroke_color)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                int tempToday = Integer.parseInt(getToday());
                                int tempGoal = Integer.parseInt(getGoal());

                                if(Integer.parseInt(input.toString()) <= tempToday) {
                                    tempToday = tempToday - Integer.parseInt(input.toString());
                                    setTodayTextFromInt(tempToday);
                                    setTodayPref(tempToday);
                                    Log.d(TAG, "onClick: **************** tempToday CUSTOM" + tempToday);
                                    animate(tempGoal, tempToday);
                                    Toasty.success(MainActivity.this, "Discarded " + Integer.parseInt(input.toString()), Toast.LENGTH_SHORT, true).show();
                                }
                                else {
                                    Toasty.error(MainActivity.this, "Amount is too big", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }).show();
            }
        });
    }

    /*
    retrieve goal or empty
     */
    public String getGoal() {
        SharedPreferences uri = this.getSharedPreferences(PATH_GOAL_LOCATION, Context.MODE_PRIVATE);
        return uri.getString(PATH_GOAL_KEY, "2000");
    }

    /*
    store goal
     */
    public void setGoal(String goal) {
        SharedPreferences uri = this.getSharedPreferences(PATH_GOAL_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uri.edit();
        editor.putString(PATH_GOAL_KEY, goal);
        editor.apply();
    }

    /*
    retrieve today value if there is one
     */
    public String getToday() {
        SharedPreferences uri = this.getSharedPreferences(PATH_TODAY_LOCATION, Context.MODE_PRIVATE);
        return uri.getString(PATH_TODAY_KEY, "0");
    }

    /*
    set today value
     */
    public void setTodayPref(int value) {
        String today = String.valueOf(value);
        SharedPreferences uri = this.getSharedPreferences(PATH_TODAY_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uri.edit();
        editor.putString(PATH_TODAY_KEY, today);
        editor.apply();
    }

    /*
   retrieve circle value if there is one
    */
    public String getProgress() {
        SharedPreferences uri = this.getSharedPreferences(PATH_PROGRESS_LOCATION, Context.MODE_PRIVATE);
        return uri.getString(PATH_PROGRESS_KEY, "100");
    }

    /*
    set circle value
     */
    public void setProgress(String progress) {
        SharedPreferences uri = this.getSharedPreferences(PATH_PROGRESS_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uri.edit();
        editor.putString(PATH_PROGRESS_KEY, progress);
        editor.apply();
    }


    /*
    set text field of today
     */
    public void setTodayTextFromInt(int val) {
        String i = String.valueOf(val);
        today.setText(TODAY + i + "ml");
    }

    /*
      What percentage of goal is the value of today consumed water, if more than 100% - keep the jar filled at 100%
                 */
    public void animate(int goal, int today) {

        int percent = today * 100 / goal;

        if(percent > 100) {
            circle.setProgress(0);
        }
        else {
            circle.setProgress(100-percent);
        }
    }

    /*
    same animation, but params are string
     */
    public void animateString(String goalSt, String todaySt) {

        int goal = Integer.parseInt(goalSt);
        int today = Integer.parseInt(todaySt);

        int percent = today * 100 / goal;

        if(percent > 100) {
            circle.setProgress(0);
        }
        else {
            circle.setProgress(100-percent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        goal.setText(GOAL + getGoal() + "ml");
        animateString(getGoal(),getToday());
    }
}
