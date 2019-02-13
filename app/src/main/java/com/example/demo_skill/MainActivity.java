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

    private String decToHex(String dec){
        int converter = Integer.parseInt(dec);
        return Integer.toHexString(converter);
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
//        String color = m_hslInput.getText().toString();
        String hue = m_hueInput.getText().toString();
        String sat = m_satInput.getText().toString();
        String lum = m_lumInput.getText().toString();
//        m_background.setBackgroundColor(Color.parseColor(color));
    }


}
