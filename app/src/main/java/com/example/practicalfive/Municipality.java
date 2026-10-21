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

public class Municipality extends AppCompatActivity {

    /** Key of the Intent extra that carries the validated municipality name from MainActivity. */
    public static final String EXTRA_MUNICIPALITY_NAME = "com.example.practicalfive.EXTRA_MUNICIPALITY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this,
                SystemBarStyle.dark(Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_municipality);

        View root = findViewById(R.id.main);
        View nameBar = findViewById(R.id.text_municipality_name);
        int nameBarPaddingTop = nameBar.getPaddingTop();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            // Keep the name bar coloured behind the status bar; keep the button above the navigation bar.
            v.setPadding(bars.left, 0, bars.right, bars.bottom);
            nameBar.setPadding(nameBar.getPaddingLeft(), nameBarPaddingTop + bars.top,
                    nameBar.getPaddingRight(), nameBar.getPaddingBottom());
            return insets;
        });
    }
}
