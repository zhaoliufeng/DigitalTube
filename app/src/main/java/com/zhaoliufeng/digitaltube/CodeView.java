package com.zhaoliufeng.digitaltube;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zhao Liufeng on 2016/11/1.
 */

public class CodeView extends View {
    private boolean trulyState;//选择或是未选中状态
    public CodeView(Context context) {
        super(context);
        this.trulyState = false;
    }

    public CodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isTrulyState() {
        return trulyState;
    }

    public void setTrulyState(boolean trulyState) {
        this.trulyState = trulyState;
        if (trulyState){
            this.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }else {
            this.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
    }
}
