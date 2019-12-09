package nathantsai.iifymcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class IIFYMCalculator extends AppCompatActivity implements View.OnClickListener {

    public static final double lbsToKgConversionFactor = 2.2046;
    public static final double inToCmConversionFactor = 0.393701;
    public static final double proteinRatio = 0.825;
    public static final double fatPercentageTDEE = 0.25;
    public static final int caloriesPerGFat = 9;
    public static final int GtoCaloriesProtein = 4;
    public static final int GtoCaloriesCarbs = 4;
    private EditText name, age, height, weight;
    private RadioButton male, female, inches, cm, lb, kg, lose, maintain, gain;
    private Spinner actLevel;
    private Button calculateMacros;
    private boolean isAMale;
    private boolean lweight;
    private boolean mweight;
    private boolean gweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iifymcalculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void init() {

        //input
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        inches = (RadioButton) findViewById(R.id.inches);
        cm = (RadioButton) findViewById(R.id.cm);
        lb = (RadioButton) findViewById(R.id.lb);
        kg = (RadioButton) findViewById(R.id.kg);
        lose = (RadioButton) findViewById(R.id.lose);
        maintain = (RadioButton) findViewById(R.id.maintain);
        gain = (RadioButton) findViewById(R.id.gain);
        actLevel = (Spinner) findViewById(R.id.actLevel);
        calculateMacros = (Button) findViewById(R.id.calculateMacros);

        //onClickListeners
        calculateMacros.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iifymcalculator, menu);
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

    @Override
    public void onClick(View v) {
        String useHeight = height.getText().toString();
        String useWeight = weight.getText().toString();
        String useAge = age.getText().toString();
        double height = 0.0;
        double weight = 0.0;
        double REE = 0.0;
        double tdee = 0.0;
        double proteinresults = 0.0;
        double fatresults = 0.0;
        double carbsresults = 0.0;
        int actLevelSelection = actLevel.getSelectedItemPosition();

        if (male.isChecked()) {
            isAMale = true;
        }
        if (inches.isChecked()) {
            height = (Double.parseDouble(useHeight) / inToCmConversionFactor);
        } else {
            height = (Double.parseDouble(useHeight));
        }
        if (lb.isChecked()) {
            weight = (Double.parseDouble(useWeight) / lbsToKgConversionFactor);
        } else {
            weight = (Double.parseDouble(useWeight));
        }
        if (lose.isChecked()) {
            lweight = true;
            mweight = false;
            gweight = false;
        } else if (maintain.isChecked()) {
            lweight = false;
            mweight = true;
            gweight = false;
        } else {
            lweight = false;
            mweight = false;
            gweight = true;
        }
        if (isAMale) {
            REE = (10*weight+6.25*height-5*(Integer.parseInt(useAge))+5);
            if (actLevelSelection == 1) {
                tdee = REE*1.2;
            } else if (actLevelSelection == 2) {
                tdee = REE*1.375;
            } else if (actLevelSelection == 3) {
                tdee = REE*1.55;
            } else if (actLevelSelection == 4) {
                tdee = REE*1.725;
            }
            if (lweight) {
                tdee = (tdee-(tdee*0.2));
            } else if (mweight) {
                tdee = tdee;
            } else if (gweight){
                tdee = (tdee+(tdee*0.2));
            }
        } else if (!isAMale) {
            REE = 10*weight+6.25*height-5*(Integer.parseInt(useAge))-161;
            if (actLevelSelection == 1) {
                tdee = REE*1.2;
            } else if (actLevelSelection == 2) {
                tdee = REE*1.375;
            } else if (actLevelSelection == 3) {
                tdee = REE*1.55;
            } else if (actLevelSelection == 4) {
                tdee = REE*1.725;
            }
            if (lweight) {
                tdee = (tdee-(tdee*0.2));
            } else if (mweight) {
                tdee = tdee;
            } else if (gweight){
                tdee = (tdee+(tdee*0.2));
            }
        }
        proteinresults = ((weight*lbsToKgConversionFactor)*proteinRatio);
        fatresults = ((tdee*fatPercentageTDEE)/caloriesPerGFat);
        carbsresults = ((tdee-(GtoCaloriesProtein*proteinresults)-(caloriesPerGFat*fatresults))/GtoCaloriesCarbs);

        Intent intent = new Intent(this, Results.class);
        intent.putExtra("TDEE", tdee);
        intent.putExtra("PROTEIN", proteinresults);
        intent.putExtra("FAT", fatresults);
        intent.putExtra("CARBS", carbsresults);
        startActivity(intent);
    }
}
