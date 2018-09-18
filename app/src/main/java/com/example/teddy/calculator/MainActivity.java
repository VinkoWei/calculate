package com.example.teddy.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17;

    private TextView textView1;
    private TextView textView2;
    private static StringBuffer currentResult = new StringBuffer();
    private static String beforeResult = "";
    private static String operate = "";
    private static String newOperate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get textViews
        textView1 = findViewById(R.id.show1);
        textView2 = findViewById(R.id.show2);

        //get buttons
        btn0 = findViewById(R.id.number_0);
        btn1 = findViewById(R.id.number_1);
        btn2 = findViewById(R.id.number_2);
        btn3 = findViewById(R.id.number_3);
        btn4 = findViewById(R.id.number_4);
        btn5 = findViewById(R.id.number_5);
        btn6 = findViewById(R.id.number_6);
        btn7 = findViewById(R.id.number_7);
        btn8 = findViewById(R.id.number_8);
        btn9 = findViewById(R.id.number_9);
        btn10 = findViewById(R.id.point);
        btn11 = findViewById(R.id.and);
        btn12 = findViewById(R.id.minus);
        btn13 = findViewById(R.id.times);
        btn14 = findViewById(R.id.divide);
        btn15 = findViewById(R.id.equal);
        btn16 = findViewById(R.id.ac);
        btn17 = findViewById(R.id.del);

        // number_buttons set Listener
        btn0.setOnTouchListener(new NumberButtonListener());
        btn1.setOnTouchListener(new NumberButtonListener());
        btn2.setOnTouchListener(new NumberButtonListener());
        btn3.setOnTouchListener(new NumberButtonListener());
        btn4.setOnTouchListener(new NumberButtonListener());
        btn5.setOnTouchListener(new NumberButtonListener());
        btn6.setOnTouchListener(new NumberButtonListener());
        btn7.setOnTouchListener(new NumberButtonListener());
        btn8.setOnTouchListener(new NumberButtonListener());
        btn9.setOnTouchListener(new NumberButtonListener());
        btn10.setOnTouchListener(new NumberButtonListener());

        // operate_buttons set Listener
        btn11.setOnTouchListener(new OperateButtonListener());
        btn12.setOnTouchListener(new OperateButtonListener());
        btn13.setOnTouchListener(new OperateButtonListener());
        btn14.setOnTouchListener(new OperateButtonListener());
        btn15.setOnTouchListener(new OperateButtonListener());
        btn16.setOnTouchListener(new OperateButtonListener());
        btn17.setOnTouchListener(new OperateButtonListener());


    }

    public class NumberButtonListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Button button = findViewById(v.getId());
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    button.setBackground(getDrawable(R.drawable.btn_round_number));
                    button.setElevation(20);
                    break;
                case MotionEvent.ACTION_DOWN:
                    button.setBackground(getDrawable(R.drawable.btn_round_number_pressed));
                    button.setElevation(2);
                    // Add content to currentResult
                    add(button.getText());
                    break;
            }
            return false;
        }

        // function add()
        private void add(CharSequence text) {
            if (text.equals(".") && currentResult.toString().contains(".")) {
                // Nothing to do
                return;
            }
            if (currentResult.length() != 0 || !text.equals("0")) {
                if (beforeResult.equals("")) {
                    currentResult.append(text);
                    textView1.setText(currentResult.toString());
                } else {
                    currentResult.append(text);
                    String s;
                    if (!operate.equals("")) {
                        s = beforeResult + operate + currentResult.toString();
                    } else {
                        s = currentResult.toString();
                    }
                    textView1.setText(s);
                }
            }
        }
    }

    public class OperateButtonListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Button button = findViewById(v.getId());
            // Get operation type
            newOperate = button.getText().toString();

            if (R.id.equal == v.getId()) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        button.setBackground(getDrawable(R.drawable.btn_equal));
                        button.setElevation(20);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        button.setBackground(getDrawable(R.drawable.btn_equal_pressed));
                        button.setElevation(2);
                        // execute
                        execute();
                        break;
                }
            } else {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        button.setBackground(getDrawable(R.drawable.btn_color_operate));
                        button.setTextColor(getColor(R.color.white));
                        button.setElevation(20);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        button.setBackground(getDrawable(R.drawable.btn_color_operate_pressed));
                        button.setTextColor(getColor(R.color.white_pressed));
                        button.setElevation(2);
                        execute();
                        break;
                }
            }
            return false;
        }

        private void execute() {
            switch (newOperate) {
                case "+": case "-": case "*": case "/":
                    if (operate.equals("")) {
                        beforeResult = currentResult.toString();
                        currentResult.delete(0, currentResult.length());
                        String show = beforeResult + newOperate;
                        textView1.setText(show);
                        textView2.setText(beforeResult);
                        operate = newOperate;
                    } else {
                        if (!operate.equals(newOperate) && !beforeResult.equals("")) {
                            String show = beforeResult + newOperate;
                            textView1.setText(show);
                            operate = newOperate;
                        } else {
                            // work out
                            if (!currentResult.toString().equals("") && !beforeResult.equals("")) {
                                beforeResult = getResult();
                                currentResult.delete(0, currentResult.length());
                                String show = beforeResult + newOperate;
                                textView1.setText(show);
                                textView2.setText(beforeResult);
                                operate = newOperate;
                            } else {
                                if (currentResult.length() != 0) {
                                    operate = newOperate;
                                    beforeResult = currentResult.toString();
                                    currentResult.delete(0, currentResult.length());
                                    String show = beforeResult + newOperate;
                                    textView1.setText(show);
                                } else {
                                    operate = newOperate;
                                    String show = beforeResult + newOperate;
                                    textView1.setText(show);
                                }
                            }
                        }
                    }
                    break;
                case "AC":
                    init();
                    break;
                case "DEL":

                    StringBuffer text = new StringBuffer(textView1.getText());

                    if (!text.toString().equals("0")) {

                        text.deleteCharAt(text.length() - 1);

                        if (currentResult.length() != 0) {
                            currentResult.deleteCharAt(currentResult.length() - 1);
                        } else {

                        }
                        if (text.length() == 0) {
                            textView1.setText("0");
                        } else {
                            textView1.setText(text);
                        }
                    } else {

                        currentResult.delete(0, currentResult.length());
                        beforeResult = "";

                    }

                    /*if(text.length() != 1){
                        text.deleteCharAt(text.length() - 1);

                        if (currentResult.length() != 0) {
                            currentResult.deleteCharAt(currentResult.length() - 1);
                        } else {

                        }

                        textView1.setText(text);
                    } else if (text.length() == 1) {

                        if (currentResult.length() != 0) {
                            currentResult.deleteCharAt(currentResult.length()-1);
                        }

                        textView1.setText("0");
                    }

                    */
                    break;
                case "=":
                    if (!currentResult.toString().equals("")) {
                        String result = getResult();
                        operate = "";
                        newOperate = "";
                        currentResult.delete(0, currentResult.length());
                        beforeResult = result;
                        textView1.setText("0");
                        textView2.setText(beforeResult);
                    }
            }
        }

        public String getResult() {

            String result = "";
            switch (operate) {
                case "+":
                    result = "" + (Float.parseFloat(currentResult.toString()) + Float.parseFloat(beforeResult));
                    break;
                case "-":
                    result = "" + (Float.parseFloat(beforeResult) - Float.parseFloat(currentResult.toString()));
                    break;
                case "/":
                    result = "" + (Float.parseFloat(beforeResult) / Float.parseFloat(currentResult.toString()));
                    break;
                case "*":
                    result = "" + (Float.parseFloat(currentResult.toString()) * Float.parseFloat(beforeResult));
                    break;
            }
            return result;
        }

        public void init() {
            beforeResult ="";
            currentResult.delete(0, currentResult.length());
            operate = "";
            newOperate = "";
            textView1.setText("0");
            textView2.setText("0");
        }
    }
}
