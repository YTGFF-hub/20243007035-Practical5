package com.example.practicalfive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

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

        EditText editMunicipality = findViewById(R.id.edit_municipality);
        editMunicipality.setOnEditorActionListener((v, actionId, event) -> {
            boolean enterKey = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
            boolean imeAction = actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE;
            if (!enterKey && !imeAction) {
                return false;
            }
            // A hardware Enter key is reported twice (key down and key up): act on the press only.
            if (!enterKey || event.getAction() == KeyEvent.ACTION_DOWN) {
                openMunicipality(editMunicipality);
            }
            return true;
        });
    }

    /**
     * Validates the typed name with TextProcessor. An invalid name shows an error and keeps the user
     * on this screen; a valid name is passed to the Municipality activity with an explicit Intent.
     */
    private void openMunicipality(EditText editMunicipality) {
        String input = editMunicipality.getText().toString();
        String municipality = TextProcessor.processMunicipality(input);
        if (municipality == null) {
            editMunicipality.setError(getString(TextProcessor.isBlank(input)
                    ? R.string.error_empty_input
                    : R.string.error_unsupported_municipality));
            return;
        }
        Intent intent = new Intent(this, Municipality.class);
        intent.putExtra(Municipality.EXTRA_MUNICIPALITY_NAME, municipality);
        startActivity(intent);
    }
}
