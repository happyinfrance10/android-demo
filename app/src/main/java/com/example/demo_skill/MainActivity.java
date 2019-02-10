package com.example.demo_skill;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText m_hexInput, m_rgbInput, m_hslInput;
    ConstraintLayout m_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_hexInput = (EditText) findViewById(R.id.hexInput);
        m_hslInput = (EditText) findViewById(R.id.hslInput);
        m_rgbInput = (EditText) findViewById(R.id.rgbInput);
        m_background = (ConstraintLayout) findViewById(R.id.background);
    }


    public void changeColorHexadecimal(View view){
        String color = m_hexInput.getText().toString();
//        Log.i("MainActivity", "test");
        m_background.setBackgroundColor(Color.parseColor(color));
    }
    public void changeColorRGB(View view){
//        String color = m_rgbInput.getText().toString();
//        String rgbColor = color.substring(1, color.length()-1);
//        String values[] = rgbColor.split(" ,");
//        int r = Integer.parseInt(values[0]);
//        int g = Integer.parseInt(values[1]);
//        int b = Integer.parseInt(values[2]);
////        Log.i("MainActivity", "test");
//        String hexResult="#"+Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
//        m_hexInput.setText(hexResult);
//        m_background.setBackgroundColor(Color.parseColor(hexResult));
    }
    public void changeColorHSL(View view){
        String color = m_hslInput.getText().toString();
//        Log.i("MainActivity", "test");
        m_background.setBackgroundColor(Color.parseColor(color));
    }


}
