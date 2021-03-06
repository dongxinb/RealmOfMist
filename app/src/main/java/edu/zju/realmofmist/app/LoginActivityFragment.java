package edu.zju.realmofmist.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import edu.zju.realmofmist.R;
import edu.zju.realmofmist.api.APICallback;
import edu.zju.realmofmist.model.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    EditText mPhoneText;
    EditText mAreaCodeText;
    EditText mCaptcha;
    ButtonFlat mGetCapButton;
    ButtonRectangle mSignInButton;
    String mAreaCode;//区域代码
    String mPhoneNumber;//手机号
    String mCaptchaNumber;//验证码
    Timer mcapTimer;
    Handler timeHandler = new Handler() {

        public void handleMessage(Message timeMsg){
            if (timeMsg.what > 0){
                //Integer time = timeMsg.what;
                mGetCapButton.setBackgroundColor(0xff189176);
                mGetCapButton.setText(Integer.toString(timeMsg.what)+"s");
            }
            else {
                mGetCapButton.setEnabled(true);
                mGetCapButton.setText("CAPTCHA");
                mcapTimer.cancel();
            }
        }
    };


    public LoginActivityFragment() {

    }

    public void initSMSSDK(){
        SMSSDK.initSDK(getActivity(), "96cf4fe49b38", "2f3a5163ca2b1145ee6c4bbafc3b2bcd");
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Log.d(mPhoneNumber, "after event");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.d(mPhoneNumber, "提交成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.d(mPhoneNumber, "获取成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    Log.d(mPhoneNumber, "result incomplete");
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initSMSSDK();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        linkView(view);

        return view;
    }

    public void linkView(View view){
        mPhoneText = (EditText)view.findViewById(R.id.phoneNumberText);
        mAreaCodeText = (EditText)view.findViewById(R.id.areaCodeText);
        mCaptcha = (EditText)view.findViewById(R.id.capText);
        mGetCapButton = (ButtonFlat)view.findViewById(R.id.getCaptchaButton);
        mSignInButton = (ButtonRectangle)view.findViewById(R.id.signInButton);
        mGetCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetCapButton.setEnabled(false);
                mcapTimer = new Timer();
                mcapTimer.schedule(new TimerTask() {
                    int waitTime = 60;

                    @Override
                    public void run() {
                        Message timerMsg = new Message();
                        timerMsg.what = waitTime--;
                        timeHandler.sendMessage(timerMsg);
                    }
                }, 100, 1000);

                mAreaCode = mAreaCodeText.getText().toString();
                mPhoneNumber = mPhoneText.getText().toString();
                if ((!mAreaCode.isEmpty())&&(!mPhoneNumber.isEmpty())){
                    SMSSDK.getVerificationCode(mAreaCode, mPhoneNumber);
                    Log.d(mPhoneNumber, "点击发送");
                }
            }
        });
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCaptchaNumber = mCaptcha.getText().toString();
                //验证登陆
                //SMSSDK.submitVerificationCode(mAreaCode,mPhoneNumber,mCaptchaNumber);
                Log.d(mPhoneNumber, "点击登陆");
                User.signIn(mPhoneText.getText().toString(), mAreaCodeText.getText().toString(), mCaptcha.getText().toString(), new APICallback() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        Toast toast = Toast.makeText(getActivity(), "Login Success!", Toast.LENGTH_SHORT);
                        toast.show();
                        getActivity().finish();
                    }

                    @Override
                    public void onSuccess(JSONArray array) {

                    }

                    @Override
                    public void onFailure(String description) {
                        Toast toast = Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
