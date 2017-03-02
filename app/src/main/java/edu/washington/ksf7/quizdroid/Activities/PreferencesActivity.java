package edu.washington.ksf7.quizdroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.washington.ksf7.quizdroid.R;
import edu.washington.ksf7.quizdroid.Repositories.TopicRepository;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        initializeViewData();
    }

    private void initializeViewData() {
        ((EditText) findViewById(R.id.question_data_url)).setText(TopicRepository.getQuestionDataURL());
        ((EditText) findViewById(R.id.question_data_refresh_rate)).setText(String.valueOf(TopicRepository.getQuestionDataRefreshRate()));

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChangesAndExit();
            }
        });
    }

    private void saveChangesAndExit() {
        // Extract user inputted data
        String newQuestionDataURL = ((EditText) findViewById(R.id.question_data_url)).getText().toString();
        String newQuestionDataRefreshRate = ((EditText) findViewById(R.id.question_data_refresh_rate)).getText().toString();

        int parsedRefreshRate;

        // Validate
        try {
            parsedRefreshRate = Integer.parseInt(newQuestionDataRefreshRate);

            if (parsedRefreshRate < 1) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid refresh rate, please enter a positive whole number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update
        TopicRepository.getInstance().setQuestionDataSettings(this, newQuestionDataURL, parsedRefreshRate);

        // Peace out
        finish();
    }
}
