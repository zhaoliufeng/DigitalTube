package com.zhaoliufeng.digitaltube;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener,NumberPicker.OnValueChangeListener{
    private CodeView codeViews[] = new CodeView[8];
    private LinearLayout linearLayouts[] = new LinearLayout[8];
    private RadioGroup radioGroup;
    private RadioButton YangRbtn, YinRbtn, A_DPRbtn,DP_ARbtn;
    private NumberPicker numberPicker;

    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private TextView tv_resualt_out,tv_Binary,tv_Hex;
    private Button btn_copy;
    private boolean isYangMode;
    private boolean isDP_AMode;
    CodeTurned codeTurned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

        isYangMode = true;
        isDP_AMode = true;
    }

    void initView(){
        codeViews[0] = (CodeView) findViewById(R.id.SegmentCode_A);
        codeViews[1] = (CodeView) findViewById(R.id.SegmentCode_B);
        codeViews[2] = (CodeView) findViewById(R.id.SegmentCode_C);
        codeViews[3] = (CodeView) findViewById(R.id.SegmentCode_D);
        codeViews[4] = (CodeView) findViewById(R.id.SegmentCode_E);
        codeViews[5] = (CodeView) findViewById(R.id.SegmentCode_F);
        codeViews[6] = (CodeView) findViewById(R.id.SegmentCode_G);
        codeViews[7] = (CodeView) findViewById(R.id.SegmentCode_DP);

        linearLayouts[0] = (LinearLayout)findViewById(R.id.SegmentCode_A_View);
        linearLayouts[1] = (LinearLayout)findViewById(R.id.SegmentCode_B_View);
        linearLayouts[2] = (LinearLayout)findViewById(R.id.SegmentCode_C_View);
        linearLayouts[3] = (LinearLayout)findViewById(R.id.SegmentCode_D_View);
        linearLayouts[4] = (LinearLayout)findViewById(R.id.SegmentCode_E_View);
        linearLayouts[5] = (LinearLayout)findViewById(R.id.SegmentCode_F_View);
        linearLayouts[6] = (LinearLayout)findViewById(R.id.SegmentCode_G_View);
        linearLayouts[7] = (LinearLayout)findViewById(R.id.SegmentCode_DP_View);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        YangRbtn = (RadioButton)findViewById(R.id.YangRbtn);
        YinRbtn = (RadioButton)findViewById(R.id.YinRbtn);
        A_DPRbtn = (RadioButton)findViewById(R.id.A_DPRbtn);
        DP_ARbtn = (RadioButton)findViewById(R.id.DP_ARbtn);

        YangRbtn.setOnClickListener(this);
        YinRbtn.setOnClickListener(this);
        A_DPRbtn.setOnClickListener(this);
        DP_ARbtn.setOnClickListener(this);
        initRadioGroup();

        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setOnValueChangedListener(this);
        initNumberPicker();

        tv_resualt_out = (TextView)findViewById(R.id.tv_Resualt_Out);
        tv_Binary = (TextView)findViewById(R.id.tv_Binary);
        tv_Hex = (TextView)findViewById(R.id.tv_Hex);
        codeTurned = new CodeTurned(tv_Binary, tv_Hex);
        btn_copy = (Button)findViewById(R.id.btn_copy);
        btn_copy.setOnClickListener(this);

        for (int i = 0; i < 8; i++){
            codeViews[i].setOnClickListener(this);
            linearLayouts[i].setOnClickListener(this);
            //默认为0
            if (i == 6 || i == 7){
                codeViews[i].setTrulyState(false);
            }else {
                codeViews[i].setTrulyState(true);
            }
        }
    }

    private  void initRadioGroup(){
        YangRbtn.setChecked(true);
        DP_ARbtn.setChecked(true);
    }

    private void initNumberPicker(){
        String[] num = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        numberPicker.setDisplayedValues(num);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(num.length - 1);
        numberPicker.setValue(0);
    }

    private void copyText(){
        String text = tv_resualt_out.getText().toString();
        clipData = ClipData.newPlainText("text", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "输出结果已拷贝到剪贴板", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        System.out.print(Integer.toBinaryString(codeTurned.getYangCode(newVal)));
        for (int i = 0; i < 8; i++){
            if (Integer.toBinaryString(codeTurned.getYangCode(newVal)).charAt(i) == '1'){
                codeViews[7-i].setTrulyState(false);
            }else {
                codeViews[7-i].setTrulyState(true);
            }
        }
        codeTurned.codeChange(isYangMode,isDP_AMode,codeViews);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_copy:
                copyText();
                break;
            case R.id.YangRbtn:
                isYangMode = true;
                //codeTurned.codeChange(isYangMode,isDP_AMode,codeViews);
                tv_resualt_out.setText(codeTurned.getResualtOut(isYangMode, isDP_AMode,codeViews).toString());
                break;
            case R.id.YinRbtn:
                isYangMode = false;
                //codeTurned.codeChange(isYangMode,isDP_AMode,codeViews);
                tv_resualt_out.setText(codeTurned.getResualtOut(isYangMode, isDP_AMode,codeViews).toString());
                break;
            case R.id.A_DPRbtn:
                isDP_AMode = false;
                //codeTurned.codeChange(isYangMode,isDP_AMode,codeViews);
                tv_resualt_out.setText(codeTurned.getResualtOut(isYangMode, isDP_AMode,codeViews).toString());
                break;
            case R.id.DP_ARbtn:
                isDP_AMode = true;
                //codeTurned.codeChange(isYangMode,isDP_AMode,codeViews);
                tv_resualt_out.setText(codeTurned.getResualtOut(isYangMode, isDP_AMode,codeViews).toString());
                break;
            default:
                try {
                    CodeView codeView = (CodeView) v;
                    if (codeView.isTrulyState()) {
                        codeView.setTrulyState(false);
                    } else {
                        codeView.setTrulyState(true);
                    }
                } catch (ClassCastException cce) {
                    for (int i = 0; i < 8; i++) {
                        if (linearLayouts[i] == v) {
                            if (codeViews[i].isTrulyState()) {
                                codeViews[i].setTrulyState(false);
                            } else {
                                codeViews[i].setTrulyState(true);
                            }
                        }
                    }
                }
                codeTurned.codeChange(isYangMode,isDP_AMode,codeViews);
                break;
        }
    }
}
