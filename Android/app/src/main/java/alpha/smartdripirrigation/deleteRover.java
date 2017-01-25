package alpha.smartdripirrigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class deleteRover extends AppCompatActivity {

    private EditText ip;
    private Button clearAllBtn,deleteRoverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_rover);

        clearAllBtn = (Button)findViewById(R.id.clearAllBtn);
        deleteRoverBtn = (Button)findViewById(R.id.deleteRoverBtn);
        ip = (EditText)findViewById(R.id.deleteIP);

        ipFilter();

        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clearAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rovers.deleteFile(getApplicationContext());
            }
        });

        deleteRoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rovers.deleteRover(ip.getText().toString(),getApplicationContext());
            }
        });

    }

    //Back button function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //IP TEXT BOX FILTERING
    private void ipFilter(){
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        ip.setFilters(filters);
    }
}
