package com.example.demo_skill;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText m_hexInput, m_redInput, m_greenInput, m_blueInput, m_hueInput, m_satInput, m_lumInput;
    ConstraintLayout m_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_hexInput = (EditText) findViewById(R.id.hexInput);
        m_hueInput = (EditText) findViewById(R.id.hueInput);
        m_redInput = (EditText) findViewById(R.id.redInput);
        m_greenInput = (EditText) findViewById(R.id.greenInput);
        m_blueInput = (EditText) findViewById(R.id.blueInput);
        m_satInput = (EditText) findViewById(R.id.satInput);
        m_lumInput = (EditText) findViewById(R.id.lumInput);
        m_background = (ConstraintLayout) findViewById(R.id.background);
    }

    private boolean invalidRange(String input, int min, int max){
        return false;
    }
    private String decToHex(String dec) {
        int converter = Integer.parseInt(dec);
        String result = Integer.toHexString(converter);
        if (result.length() == 1) {
            return "0" + result;
        } else {
            return result;
        }
    }
    private String hexToDec(String hex){
        int result = Integer.parseInt(hex, 16);
        return Integer.toString(result);
    }
    private int maxOfThree(int a, int b, int c){
        if(a>b)
            return Integer.max(a,c);
        else
            return Integer.max(b,c);
    }
    private int minOfThree(int a, int b, int c){
        if(a<b)
            return Integer.min(a,c);
        else
            return Integer.min(b,c);
    }
    private String luminance(int red, int green, int blue){
        int min = minOfThree(red, green, blue);
        int max = maxOfThree(red, green, blue);
        double lum = (max+min)/5.1;
        int result = (int) Math.round(lum);
        return Integer.toString(result);
    }
    private String saturation(int red, int green, int blue){
        int min = minOfThree(red, green, blue);
        int max = maxOfThree(red, green, blue);
        if(min==max)
            return "0";
        else if(Integer.parseInt(luminance(red,green,blue))<50){
            double result = ((max-min)*100.0)/(max+min);
            int sat = (int) Math.round(result);
            return Integer.toString(sat);
        }
        else{
            double result = ((max-min)*100.0)/(510-max-min);
            int sat = (int) Math.round(result);
            return Integer.toString(sat);
        }
    }
    private String hue(int red, int green, int blue){
        int min = minOfThree(red, green, blue);
        int max = maxOfThree(red, green, blue);
        if(min==max)
            return "0";
        else if(max==red){
            double result = ((green-blue)*60.0)/(max-min);
            int h = (int) Math.round(result);
            if(h<0)
                h+=360;
            return Integer.toString(h);
        }else if(max==green){
            double result = ((blue-red)*60.0)/(max-min) + 120;
            int h = (int) Math.round(result);
            if(h<0)
                h+=360;
            return Integer.toString(h);
        } else{
            double result = ((red-green)*60.0)/(max-min) + 240;
            int h = (int) Math.round(result);
            if(h<0)
                h+=360;
            return Integer.toString(h);
        }
    }
    private double hslBasesToRGB(double rgb, double conv1, double conv2){
        while(rgb < 0.0){
            rgb++;
        }
        while(rgb > 1.0){
            rgb--;
        }
        if(6.0*rgb < 1.0){
            return conv2 + (conv1-conv2)*6.0*rgb;
        } else if (2.0*rgb < 1.0) {
            return conv1;
        } else if (3.0*rgb < 2.0){
            return conv2 + (conv1-conv2)*((2.0/3)-rgb)*6.0;
        } else {
            return conv2;
        }
    }
    public void changeColorHexadecimal(View view){
        String color = m_hexInput.getText().toString();
        String red = hexToDec(color.substring(1, 3));
        String green = hexToDec(color.substring(3, 5));
        String blue = hexToDec(color.substring(5, 7));
        m_redInput.setText(red);
        m_greenInput.setText(green);
        m_blueInput.setText(blue);

        int r = Integer.parseInt(red);
        int g = Integer.parseInt(green);
        int b = Integer.parseInt(blue);
        m_lumInput.setText(luminance(r, g, b));
        m_hueInput.setText(hue(r, g, b));
        m_satInput.setText(saturation(r, g, b));

        //        Log.i("MainActivity", "test");
        m_background.setBackgroundColor(Color.parseColor(color));
    }
    public void changeColorRGB(View view){
        String red = m_redInput.getText().toString();
        String green = m_greenInput.getText().toString();
        String blue = m_blueInput.getText().toString();
        String color = "#"+decToHex(red)+decToHex(green)+decToHex(blue);
        int r = Integer.parseInt(red);
        int g = Integer.parseInt(green);
        int b = Integer.parseInt(blue);
        m_lumInput.setText(luminance(r, g, b));
        m_hueInput.setText(hue(r, g, b));
        m_satInput.setText(saturation(r, g, b));
        m_hexInput.setText(color);
        m_background.setBackgroundColor(Color.parseColor(color));
    }
    public void changeColorHSL(View view){
//        String color = m_hslInput.getText().toString();
        String hue = m_hueInput.getText().toString();
        String sat = m_satInput.getText().toString();
        String lum = m_lumInput.getText().toString();
        if(invalidRange(hue, 0, 360) || invalidRange(sat, 0, 100)|| invalidRange(lum, 0, 100)){
            return;
        }
        String color;
        if(Integer.parseInt(sat)==0){
            int s = (int) Math.round(Integer.parseInt(lum)*2.55);
            String scale = Integer.toString(s);
            m_redInput.setText(scale);
            m_greenInput.setText(scale);
            m_blueInput.setText(scale);
            color = "#"+decToHex(scale)+decToHex(scale)+decToHex(scale);
        } else {
            double temp1;
            double n_lum = Integer.parseInt(lum)/100.0;
            double n_sat = Integer.parseInt(sat)/100.0;
            if(n_lum<50){
                temp1 = (n_lum*(1.0+ n_sat));
            } else {
                temp1 = (n_lum + n_sat) - (n_lum *n_sat);
            }
            double temp2 = 2*n_lum - temp1;
            double n_hue = Integer.parseInt(hue)/360.0;
            double base_r = n_hue + (1.0/3);
            double base_g = n_hue;
            double base_b = n_hue - (1.0/3);
            int r = (int) Math.round(255.0*(hslBasesToRGB(base_r, temp1, temp2)));
            int g = (int) Math.round(255.0*(hslBasesToRGB(base_g, temp1, temp2)));
            int b = (int) Math.round(255.0*(hslBasesToRGB(base_b, temp1, temp2)));
            color = "#"+decToHex(Integer.toString(r))+decToHex(Integer.toString(g))+decToHex(Integer.toString(b));
            m_redInput.setText(Integer.toString(r));
            m_greenInput.setText(Integer.toString(g));
            m_blueInput.setText(Integer.toString(b));
        }
        m_hexInput.setText(color);
        m_background.setBackgroundColor(Color.parseColor(color));
    }
}
