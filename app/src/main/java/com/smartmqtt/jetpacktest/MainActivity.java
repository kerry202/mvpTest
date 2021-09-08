package com.smartmqtt.jetpacktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.smartmqtt.basemodel.PathConfig;
import com.smartmqtt.basemodel.RouterHelper;

/**
 * @author kerry
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.testIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.startActivity(PathConfig.TEXT_ACTIVITY);

            }
        });

    }

    public static void sort(int[] arr) {

        int length = arr.length;

        for (int i = 0; i < length; i++) {

            for (int j = 0; j < length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }

    public static void main(String[] args) {

//        int[] arr = {1, 2, 4, 11, 5, 6, 0};
//
//        sort(arr);

    }

}