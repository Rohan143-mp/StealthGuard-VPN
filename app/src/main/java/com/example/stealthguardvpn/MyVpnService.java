package com.example.stealthguardvpn;

import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyVPNService extends VpnService {
    @Override
    protected void onConfigure(VpnService.Builder builder) {
        builder.setSession(getString(R.string.myvpnsession))
                .addAddress("10.0.0.2", 24)
                .addDnsServer("8.8.8.8")
                .addDnsServer("8.8.4.4")
                .setMtu(1500)
                .setBlocking(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start the VPN service
        startVpn();
        return START_STICKY;
    }

    private void startVpn() {
        try {
            // Open the OpenVPN configuration file from the assets folder
            InputStream inputStream = getAssets().open("userrrr.ovpn");

            // Start a new thread to handle the VPN connection
            new Thread(() -> {
                // Create a new ParcelFileDescriptor to handle the VPN interface
                try (ParcelFileDescriptor vpnInterface = establishVpn()) {
                    // Copy the OpenVPN configuration file to the VPN interface
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        vpnInterface.getFileDescriptor().write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ParcelFileDescriptor establishVpn() {
        // Start the VPN service
        Builder builder = new Builder();
        return builder.establish();
    }
}
