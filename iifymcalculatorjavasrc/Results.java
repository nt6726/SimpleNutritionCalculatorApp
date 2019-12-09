package nathantsai.iifymcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.content.Intent;

/**
 * Created by nathantsai on 12/18/15.
 */
public class Results extends AppCompatActivity{

    private EditText caloriesoutput, proteinoutput, fatoutput, carbsoutput;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        //output
        caloriesoutput = (EditText) findViewById(R.id.caloriesoutput);
        proteinoutput = (EditText) findViewById(R.id.proteinoutput);
        fatoutput = (EditText) findViewById(R.id.fatoutput);
        carbsoutput = (EditText) findViewById(R.id.carbsoutput);

        Intent intent = getIntent();
        double tdee = intent.getDoubleExtra("TDEE", 0.0);
        double proteinresults = intent.getDoubleExtra("PROTEIN", 0.0);
        double fatresults = intent.getDoubleExtra("FAT", 0.0);
        double carbsresults = intent.getDoubleExtra("CARBS", 0.0);

        caloriesoutput.setText(String.format("%6.2f Calories", tdee));
        proteinoutput.setText(String.format("%5.2f g", proteinresults));
        fatoutput.setText(String.format("%5.2f g", fatresults));
        carbsoutput.setText(String.format("%5.2f g", carbsresults));
    }

}
