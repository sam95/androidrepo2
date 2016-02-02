package com.example.pavan.justjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView t;
    private TextView p;
    private TextView osum;
    private Button b;
    private String summary;
    private Button plus;
    private TextView osummary;
    private Button minus;
    private EditText edt;
    private boolean whc;
    private boolean choclate;
    private int pdt;
    private int var = 0;
    private float sum;
    private String myname;
    private CheckBox chk, choc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.txt);
        b = (Button) findViewById(R.id.button);
        p = (TextView) findViewById(R.id.price);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        chk = (CheckBox) findViewById(R.id.chk);
        edt = (EditText) findViewById(R.id.name);
        choc = (CheckBox) findViewById(R.id.choc);
        osum = (TextView) findViewById(R.id.osum);
        osummary = (TextView) findViewById(R.id.ordersummary);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (var != 0) {
                    if (chk.isChecked()) {
                        pdt = 45;
                        whc = true;
                    } else {
                        whc = false;
                        pdt = 40;
                    }
                    if (choc.isChecked()) {
                        pdt += 5;
                        choclate = true;
                    } else {
                        choclate = false;
                    }
                    myname = edt.getText().toString();
                    if(myname.isEmpty()){
                        myname = "Superman!";
                    }
                    osummary.setVisibility(View.VISIBLE);
                    sum = var * pdt;
                    p.setText("Rs : " + String.valueOf(sum));
                    myname = myname.toUpperCase();
                    summary = "Name : " + myname + "\n\n";
                    summary = summary + "Whipped Cream Topping : " + whc + "\n\n";
                    summary = summary + "Choclate Topping : " + choclate + "\n\n";
                    summary = summary + "Total Number of Orders : " + var + "\n\n";
                    summary = summary + "Total Price : " + sum + "\n\n";
                    osum.setText(summary);
                    //t.setText(String.valueOf(var))
                } else {
                    Toast.makeText(getApplicationContext(), "Please order something", Toast.LENGTH_SHORT).show();
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                var++;
                t.setText(String.valueOf(var));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (var > 0) {
                    var--;
                    t.setText(String.valueOf(var));
                } else {
                    Toast.makeText(getApplicationContext(), "Already 0", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
