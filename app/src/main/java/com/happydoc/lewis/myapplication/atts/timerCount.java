package com.happydoc.lewis.myapplication.atts;

import android.os.CountDownTimer;
import android.widget.TextView;
import com.happydoc.lewis.myapplication.R;
/**
 * Created by Lewis on 2016/7/12.
 */
public class TimerCount extends CountDownTimer {
    public static final int TIME_COUNT = 61000;//时间防止从119s开始显示（以倒计时120s为例子）
    private TextView btn;
    private int endStrRid;
    private int normalColor, timingColor;//未计时的文字颜色，计时期间的文字颜色

    /**
     * 参数 millisInFuture         倒计时总时间（如60S，120s等）
     * 参数 countDownInterval    渐变时间（每次倒计1s）

     * 参数 btn               点击的按钮(因为Button是TextView子类，为了通用我的参数设置为TextView）

     * 参数 endStrRid   倒计时结束后，按钮对应显示的文字
     */
    public TimerCount(TextView btn) {
        super(TIME_COUNT, 1000);
        this.btn = btn;
        this.endStrRid = R.string.get_code;
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {
        if(normalColor > 0){
            btn.setTextColor(normalColor);
        }
        btn.setText(endStrRid);
        btn.setEnabled(true);
    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        if(timingColor > 0){
            btn.setTextColor(timingColor);
        }
        btn.setEnabled(false);
        btn.setText(millisUntilFinished / 1000 + "秒");
    }
}
