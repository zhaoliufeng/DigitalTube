package com.zhaoliufeng.digitaltube;

import android.widget.TextView;

/**
 * Created by Zhao Liufeng on 2016/11/2.
 */

public class CodeTurned {
    private TextView tv_Binary,tv_Hex;
    String[] yangDP_ACode = {
            "0xc0","0xf9","0xa4","0xb0","0x99",
            "0x92","0x82","0xf8","0x80","0x90",
            "0x88","0x83","0xc6","0xa1","0x86",
            "0x8e"
    };
    public CodeTurned(TextView tv_Binary,TextView tv_Hex){
        this.tv_Binary = tv_Binary;
        this.tv_Hex = tv_Hex;
    }
    public String b2h(StringBuffer sb){
        sb.delete(4,5);
        int res = 0;
        int CountX = 1;
        for (int i = 7; i >= 0; i--){
            if (sb.charAt(i) == '1'){
                res += CountX;
            }
            CountX *= 2;
        }
        if (Integer.toHexString(res).length() == 1){
            return "0" + Integer.toHexString(res);
        }
        return Integer.toHexString(res);
    }

    public int getYangCode(int i){
        return Integer.valueOf(yangDP_ACode[i].substring(2,4), 16);
    }

    public void ModeCodePack(StringBuffer sb, boolean isYangMode,boolean isDP_AMode, CodeView codeViews[]){
        if (!isDP_AMode) {
            for (int i = 7; i >= 0; i--) {
                if (i == 3) {
                    sb.append(" ");
                }
                if (isYangMode) {
                    if (codeViews[i].isTrulyState()) {
                        sb.append("0");
                    } else {
                        sb.append("1");
                    }
                } else {
                    if (codeViews[i].isTrulyState()) {
                        sb.append("1");
                    } else {
                        sb.append("0");
                    }
                }
            }
        }else {
            for (int i = 7; i >= 0; i--) {
                if (i == 3) {
                    sb.append(" ");
                }
                if (isYangMode) {
                    if (codeViews[i].isTrulyState()) {
                        sb.append("0");
                    } else {
                        sb.append("1");
                    }
                } else {
                    if (codeViews[i].isTrulyState()) {
                        sb.append("1");
                    } else {
                        sb.append("0");
                    }
                }
            }
        }
    }

    public StringBuffer getResualtOut(boolean isYangMode, boolean isDP_AMode, CodeView[] codeViews){
        StringBuffer sb = new StringBuffer();
        if (isYangMode){
            sb.append("共阳0-F ");
        }else {
            sb.append("共阴0-F ");
        }
        if (isDP_AMode){
            sb.append("dp-a ");
        }else {
            sb.append("a-dp ");
        }
        for (int i = 0; i <= 15; i++){
            StringBuffer sb_temp = new StringBuffer();
            for (int j = 0; j < 8; j++){
                System.out.print(Integer.toBinaryString(getYangCode(i)));
                if (Integer.toBinaryString(getYangCode(i)).charAt(j) == '1'){
                    sb_temp.append("1");
                }else {
                    sb_temp.append("0");
                }
            }
            sb.append(resualtCheck(sb_temp, isYangMode, isDP_AMode));
        }
        StringBuffer sb_temp = new StringBuffer();
        for (int j = 7; j >= 0; j--){
            if (codeViews[j].isTrulyState()){
                sb_temp.append("0");
            }else {
                sb_temp.append("1");
            }
        }
        //sb.append("+" + resualtCheck(sb_temp, isYangMode, isDP_AMode));
        tv_Hex.setText(resualtCheck(sb_temp, isYangMode, isDP_AMode));
        return sb;
    }

    private String resualtCheck(StringBuffer sb, boolean isYangMode, boolean isDP_AMode){
        StringBuffer sb_temp = new StringBuffer();
        if (!isDP_AMode) {
            for (int i = 7; i >= 0; i--) {
                if (i == 3) {
                    sb_temp.append(" ");
                }
                if (isYangMode) {
                    if (sb.charAt(i) == '1') {
                        sb_temp.append("1");
                    } else {
                        sb_temp.append("0");
                    }
                } else {
                    if (sb.charAt(i) == '1') {
                        sb_temp.append("0");
                    } else {
                        sb_temp.append("1");
                    }
                }
            }
        }else {
            for (int i = 0; i < 8; i++) {
                if (i == 4) {
                    sb_temp.append(" ");
                }
                if (isYangMode) {
                    if (sb.charAt(i) == '1') {
                        sb_temp.append("1");
                    } else {
                        sb_temp.append("0");
                    }
                } else {
                    if (sb.charAt(i) == '1') {
                        sb_temp.append("0");
                    } else {
                        sb_temp.append("1");
                    }
                }
            }
        }
        tv_Binary.setText(sb_temp);
        return "0x" + b2h(sb_temp) + " ";
    }

    public void codeChange(boolean isYangMode, boolean isDP_AMode, CodeView[] codeViews){
        StringBuffer sb = new StringBuffer();
        ModeCodePack(sb,isYangMode,isDP_AMode,codeViews);
        tv_Binary.setText(sb);
        tv_Hex.setText("0x" + b2h(sb));
    }
}
