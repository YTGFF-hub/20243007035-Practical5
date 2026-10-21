package com.example.practicalfive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class Municipality extends AppCompatActivity {

    /** Key of the Intent extra that carries the validated municipality name from MainActivity. */
    public static final String EXTRA_MUNICIPALITY_NAME = "com.example.practicalfive.EXTRA_MUNICIPALITY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read the name sent by MainActivity and validate it again; missing or invalid data ends this screen.
        String municipality = TextProcessor.processMunicipality(
                getIntent().getStringExtra(EXTRA_MUNICIPALITY_NAME));
        if (municipality == null) {
            Toast.makeText(this, R.string.error_invalid_municipality, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

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

        showMunicipality(municipality);
    }

    /** Selects the resources of the validated municipality and applies them to the screen. */
    private void showMunicipality(String municipality) {
        int summaryIndex;      // position in municipalities_array: Beijing, Shanghai, Chongqing, Tianjin
        int imageRes;
        int accentColorRes;    // name bar, information text and Visit site button
        int textOnAccentRes;   // text on the coloured bar and button must stay readable
        int websiteRes;        // official website opened by the Visit site button
        switch (municipality) {
            case TextProcessor.BEIJING:
                summaryIndex = 0;
                imageRes = R.drawable.beijing;
                accentColorRes = R.color.municipality_beijing;
                textOnAccentRes = R.color.color_text_light;
                websiteRes = R.string.url_beijing;
                break;
            case TextProcessor.SHANGHAI:
                summaryIndex = 1;
                imageRes = R.drawable.shanghai;
                accentColorRes = R.color.municipality_shanghai;
                textOnAccentRes = R.color.color_text_light;
                websiteRes = R.string.url_shanghai;
                break;
            case TextProcessor.CHONGQING:
                summaryIndex = 2;
                imageRes = R.drawable.chongqing;
                accentColorRes = R.color.municipality_chongqing;
                textOnAccentRes = R.color.color_text_dark; // white on bright orange is hard to read
                websiteRes = R.string.url_chongqing;
                break;
            case TextProcessor.TIANJIN:
                summaryIndex = 3;
                imageRes = R.drawable.tianjin;
                accentColorRes = R.color.municipality_tianjin;
                textOnAccentRes = R.color.color_text_light;
                websiteRes = R.string.url_tianjin;
                break;
            default:
                Toast.makeText(this, R.string.error_invalid_municipality, Toast.LENGTH_LONG).show();
                finish();
                return;
        }

        int accentColor = ContextCompat.getColor(this, accentColorRes);
        int textOnAccent = ContextCompat.getColor(this, textOnAccentRes);

        TextView nameView = findViewById(R.id.text_municipality_name);
        nameView.setText(municipality);
        nameView.setBackgroundColor(accentColor);
        nameView.setTextColor(textOnAccent);

        ImageView imageView = findViewById(R.id.image_municipality);
        imageView.setImageResource(imageRes);

        String[] summaries = getResources().getStringArray(R.array.municipalities_array);
        TextView infoView = findViewById(R.id.text_municipality_info);
        infoView.setText(summaries[summaryIndex]);
        infoView.setTextColor(accentColor);

        Button visitButton = findViewById(R.id.button_visit_site);
        visitButton.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        visitButton.setTextColor(textOnAccent);

        // The URL belongs to the municipality that is displayed right now.
        String websiteUrl = getString(websiteRes);
        visitButton.setOnClickListener(v -> openWebsite(websiteUrl));

        // Dark status-bar icons when the name bar is light enough to need dark text.
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(textOnAccentRes == R.color.color_text_dark);
    }

    /** Asks Android to open the URL with a suitable application (normally a web browser). */
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.error_no_browser, Toast.LENGTH_LONG).show();
        }
    }
}
