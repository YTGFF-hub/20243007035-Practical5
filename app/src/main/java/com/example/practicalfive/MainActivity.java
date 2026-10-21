package com.example.practicalfive;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Light status-bar icons on the dark title bar, dark navigation icons on the light page.
        EdgeToEdge.enable(this,
                SystemBarStyle.dark(Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.main);
        View title = findViewById(R.id.text_title);
        int titlePaddingTop = title.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
            // Keep the title bar coloured behind the status bar and lift the form above the keyboard.
            v.setPadding(bars.left, 0, bars.right, Math.max(bars.bottom, ime.bottom));
            title.setPadding(title.getPaddingLeft(), titlePaddingTop + bars.top,
                    title.getPaddingRight(), title.getPaddingBottom());
            return insets;
        });
    }
}
