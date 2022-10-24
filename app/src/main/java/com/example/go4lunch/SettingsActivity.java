package com.example.go4lunch;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.ViewModel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    private SettingsViewModel viewModel;
    private SwitchCompat switchCompat;
    private ImageButton backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(SettingsViewModel.class);

        notifClickListener();
        getSwitchState();
        setBackpress();
    }

    private void notifClickListener() {
        switchCompat = findViewById(R.id.switch_notif);
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> viewModel.changeNotificationState(getApplicationContext(), b));
    }

    private void getSwitchState() {
        switchCompat.setChecked(viewModel.areNotificationsAllowed(getApplicationContext()));
    }

    private void setBackpress() {
        backpress = findViewById(R.id.backpress);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
