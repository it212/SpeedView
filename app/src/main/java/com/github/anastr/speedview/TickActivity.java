package com.github.anastr.speedview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.anastr.speedviewlib.Speedometer;
import com.github.anastr.speedviewlib.util.OnPrintTickLabel;

import java.util.Locale;

public class TickActivity extends AppCompatActivity {

    Speedometer speedometer;
    CheckBox withRotation;
    SeekBar seekBarTickNumbers, seekBarTickPadding;
    TextView textTicks, textTickPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tick);

        speedometer = findViewById(R.id.speedometer);
        withRotation = findViewById(R.id.cb_withRotation);
        seekBarTickNumbers = findViewById(R.id.seekBarStartDegree);
        seekBarTickPadding = findViewById(R.id.seekBarTickPadding);
        textTicks = findViewById(R.id.textTickNumber);
        textTickPadding = findViewById(R.id.textTickPadding);

        speedometer.speedPercentTo(53);

        speedometer.setOnPrintTickLabel(new OnPrintTickLabel() {
            @Override
            public CharSequence getTickLabel(int tickPosition, float tick) {
                if (tick == 0) {
                    SpannableString s = new SpannableString(String.format(Locale.getDefault(), "%.1f", tick));
                    s.setSpan(new ForegroundColorSpan(0xffff1117), 0, 1, 0);
                    return s;
                }
                return null;
            }
        });

        withRotation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                speedometer.setTickRotation(b);
            }
        });

        seekBarTickNumbers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                speedometer.setTickNumber(value);
                textTicks.setText(String.format(Locale.getDefault(), "%d", value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarTickPadding.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                speedometer.setTickPadding((int) speedometer.dpTOpx(value));
                textTickPadding.setText(String.format(Locale.getDefault(), "%d dp", value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
