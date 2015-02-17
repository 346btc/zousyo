package jp.co.funai.zousyo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    ViewFlipper viewflipper;
    Animation in_from_left;
    Animation out_to_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewflipper = (ViewFlipper)findViewById(R.id.viewFlipper1);
        in_from_left = AnimationUtils.loadAnimation(this, R.anim.in_from_left);
        out_to_left = AnimationUtils.loadAnimation(this, R.anim.out_to_left);
        viewflipper.setInAnimation(in_from_left);
        viewflipper.setOutAnimation(out_to_left);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_Button1(View view) {
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        RadioButton radiobutton = (RadioButton)findViewById(radiogroup.getCheckedRadioButtonId());
        if (radiobutton != null) {
            Toast.makeText(this, radiobutton.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick_Button2(View view) {
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        RadioButton radiobutton = (RadioButton)findViewById(radiogroup.getCheckedRadioButtonId());
        if (radiobutton != null) {
            radiobutton.setChecked(false);
        }
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        RadioButton newradio = new RadioButton(this);
        newradio.setText(String.valueOf(time));
        radiogroup.addView(newradio);

        viewflipper.showNext();
/*
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.relativeLayout1);
		ObjectAnimator animation = ObjectAnimator.ofFloat(layout, "x", 100);
		animation.setStartDelay(1000);
		animation.start();
		*/
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        boolean ischecked = false;
        int checked_id = -1;
        int cnt = radiogroup.getChildCount();
        outState.putInt("radiobutton_cnt", cnt);
        for (int i = 0; i < cnt; i++) {
            RadioButton radiobutton = (RadioButton)radiogroup.getChildAt(i);
            outState.putString("radiobutton" + i, radiobutton.getText().toString());
            if (radiobutton.isChecked()) {
                ischecked = true;
                checked_id = i;
            }
        }
        outState.putBoolean("radiobutton_checked", ischecked);
        if (ischecked) {
            outState.putInt("radiobutton_check_id", checked_id);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedinstanceState) {
        super.onRestoreInstanceState(savedinstanceState);
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        int cnt = savedinstanceState.getInt("radiobutton_cnt");
        for (int i = 0; i < cnt; i++) {
            RadioButton newradio = new RadioButton(this);
            newradio.setText(savedinstanceState.getString("radiobutton" + i));
            radiogroup.addView(newradio);
        }
        if (savedinstanceState.getBoolean("radiobutton_checked")) {
            RadioButton radiobutton = (RadioButton)radiogroup.getChildAt(
                    savedinstanceState.getInt("radiobutton_check_id"));
            radiobutton.setChecked(true);
        }
    }
}
