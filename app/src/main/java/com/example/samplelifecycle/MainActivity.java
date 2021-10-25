package com.example.samplelifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            // 키패드 숨기기
            InputMethodManager manager = (InputMethodManager)
                    getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(v.getWindowToken(),0);
            printLn("onCreate");
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLn("onPause");
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLn("onResume");
        //앱이 종료되어도 여전히 데이터가 살아있음
        restoreState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        printLn("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printLn("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLn("onDestory");
    }

    public void printLn(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void restoreState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if ((pref != null) && (pref.contains("name"))) {
            String name = pref.getString("name", "");
            editText.setText(name);
        }
    }

    protected void saveState() {
        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", editText.getText().toString());
        editor.commit();
    }

    protected void clearState() {
        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}