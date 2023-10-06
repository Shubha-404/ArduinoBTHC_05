package com.example.arduinobthc_05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private final String macAddress = "00:22:12:00:A3:ED";
    private BluetoothAdapter btAdapter;
    private BluetoothDevice btDevice;
    private BluetoothSocket btSocket;
    private OutputStream opStream;
    private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button on = findViewById(R.id.on);
        Button off = findViewById(R.id.off);
        Button r = findViewById(R.id.R);
        Button g = findViewById(R.id.G);
        Button b = findViewById(R.id.B);

        // Set up BT Adapter
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        // To handle devices that do not support BT
        if (btAdapter == null) {
            // Handle the case where Bluetooth is not supported on the device
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if Bluetooth is enabled, and request to enable it if not
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        // Set up BT Device
        btDevice = btAdapter.getRemoteDevice(macAddress);
        try {

            btSocket = btDevice.createRfcommSocketToServiceRecord(BT_UUID);
            btSocket.connect();
            opStream = btSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error connecting to device", Toast.LENGTH_SHORT).show();
            // Handle connection errors here
        }

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    opStream.write("9".getBytes());
                    Toast.makeText(MainActivity.this, "Sending '9'", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error sending '9'", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    opStream.write("0".getBytes());
                    Toast.makeText(MainActivity.this, "Sending '0'", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error sending '0'", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    opStream.write("1".getBytes());
                    Toast.makeText(MainActivity.this, "Sending '1'", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error sending '1'", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    opStream.write("3".getBytes());
                    Toast.makeText(MainActivity.this, "Sending '3'", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error sending '3'", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    opStream.write("5".getBytes());
                    Toast.makeText(MainActivity.this, "Sending '5'", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error sending '5'", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (opStream != null) {
                opStream.close();
            }
            if (btSocket != null) {
                btSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
