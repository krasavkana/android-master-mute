package com.krasavkana.android.mastermute;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ReflectionSample";

    private Class<AudioManager> clazz;
    private AudioManager am;

    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AudioManager)getSystemService(AUDIO_SERVICE);
        //  http://www.ne.jp/asahi/hishidama/home/tech/java/reflection.html
        clazz = AudioManager.class;

        sw = (Switch)findViewById(R.id.mute_switch);

//        if(isMasterMuteOn()) {
//            findViewById(R.id.mute_switch).setActivated(true);
//        }else{
//            findViewById(R.id.mute_switch).setActivated(false);
//        }

        sw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG,"Master Mute Toggle...");
                if(isMasterMuteOn()) {
                    Log.d(TAG,"Now On, so set MasterMute Off");
                    MasterMuteOff();
                    sw.setChecked(false);
                }else{
                    Log.d(TAG,"Now Off, so set MasterMute On");
                    MasterMuteOn();
                    sw.setChecked(true);
                }
            }
        });

    }

    private boolean isMasterMuteOn(){
        boolean ret = false;
        Method[] aClassMethods = clazz.getDeclaredMethods();
        for(Method method : aClassMethods){
            if(method.getName().equals("isMasterMute")){
                method.setAccessible(true);
                Log.d(TAG, "isMasterMute()");
                try {
                    Log.d(TAG, "invokeMethod");
                    ret = (boolean)method.invoke(am);
//                    Log.d(TAG, "isMasterMute:" + obj.toString());
                } catch (IllegalArgumentException|IllegalAccessException|InvocationTargetException e) {
                    //呼び出し：引数が異なる
                    //呼び出し：アクセス違反、保護されている
                    //ターゲットとなるメソッド自身の例外処理
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    private void MasterMuteOn(){
        Method[] aClassMethods = clazz.getDeclaredMethods();
        for(Method method : aClassMethods){
            // Found a method m
//            Log.d("Reflection","method: " + method.getName());
            if (method.getName().equals("setMasterMute")) {
                method.setAccessible(true);
                Log.d(TAG, "setMasterMute()");
                try {
                    Log.d(TAG, "invokeMethod");
                    Object obj = method.invoke(am,true,0);
                } catch (IllegalArgumentException|IllegalAccessException|InvocationTargetException e) {
                    //呼び出し：引数が異なる
                    //呼び出し：アクセス違反、保護されている
                    //ターゲットとなるメソッド自身の例外処理
                    e.printStackTrace();
                }
            }
        }
    }

    private void MasterMuteOff(){
        Method[] aClassMethods = clazz.getDeclaredMethods();
        for(Method method : aClassMethods){
            // Found a method m
//            Log.d("Reflection","method: " + method.getName());
            if (method.getName().equals("setMasterMute")) {
                method.setAccessible(true);
                Log.d(TAG, "setMasterMute()");
                try {
                    Log.d(TAG, "invokeMethod");
                    Object obj = method.invoke(am,false,0);
                } catch (IllegalArgumentException|IllegalAccessException|InvocationTargetException e) {
                    //呼び出し：引数が異なる
                    //呼び出し：アクセス違反、保護されている
                    //ターゲットとなるメソッド自身の例外処理
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
//        isFinished = true;
    }
    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
//        isFinished = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()");
        if(isMasterMuteOn()) {
            sw.setChecked(true);
        }else{
            sw.setChecked(false);
        }
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

}

