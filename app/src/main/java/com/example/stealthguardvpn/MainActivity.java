package com.example.stealthguardvpn;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button connectButton;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = findViewById(R.id.connect_button);
        statusText = findViewById(R.id.status_text);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Connecting to StealthGuard VPN...", Toast.LENGTH_SHORT).show();
                startVpnService();
            }
        });
    }

    private void startVpnService() {
        Intent intent = new Intent(MainActivity.this, MyVPNService.class);
        startService(intent);
    }

    
    public void updateStatus(String status) {
        statusText.setText(status);
    }
}
