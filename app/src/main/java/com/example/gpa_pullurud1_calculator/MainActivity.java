package com.example.gpa_pullurud1_calculator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText[] gradeInputs = new EditText[5];
    private TextView gpaDisplay;
    private Button computeButton;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        gradeInputs[0] = findViewById(R.id.editTextMobileComputing);
        gradeInputs[1] = findViewById(R.id.editTextHCI);
        gradeInputs[2] = findViewById(R.id.editTextJava);
        gradeInputs[3] = findViewById(R.id.editTextDataMining);
        gradeInputs[4] = findViewById(R.id.editTextCompNetworks);
        gpaDisplay = findViewById(R.id.textViewGPA);
        computeButton = findViewById(R.id.buttonComputeGPA);

        computeButton.setOnClickListener(v -> {
            if ("Compute GPA".equals(computeButton.getText().toString())) {
                if (validateInputs()) {
                    computeGPA();
                }
            } else {
                resetForm();
            }
        });

        for (EditText editText : gradeInputs) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    computeButton.setText("Compute GPA");
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private boolean validateInputs() {
        boolean allValid = true;
        for (EditText editText : gradeInputs) {
            String input = editText.getText().toString();
            if (input.isEmpty() || !input.matches("\\d+(\\.\\d+)?")) {
                editText.setBackgroundColor(Color.RED);
                allValid = false;
            } else {
                editText.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        return allValid;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void computeGPA() {
        float sum = 0;
        for (EditText editText : gradeInputs) {
            sum += Float.parseFloat(editText.getText().toString());
        }
        float gpa = sum / gradeInputs.length;
        gpaDisplay.setText(String.format("GPA: %.2f", gpa));
        int backgroundColor = getBackgroundColorForGPA(gpa);
        mainLayout.setBackgroundColor(backgroundColor);
        computeButton.setText("Clear form");
    }

    private int getBackgroundColorForGPA(float gpa) {
        if (gpa < 60) {
            return Color.RED;
        } else if (gpa < 80) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }

    @SuppressLint("SetTextI18n")
    private void resetForm() {
        for (EditText editText : gradeInputs) {
            editText.setText("");
            editText.setBackgroundColor(Color.TRANSPARENT);
        }
        gpaDisplay.setText("Display GPA");
        mainLayout.setBackgroundColor(Color.WHITE);
        computeButton.setText("Compute GPA");

        Toast.makeText(getApplicationContext(), "pullurud1 gpa calculator!", Toast.LENGTH_LONG).show();
    }
}
