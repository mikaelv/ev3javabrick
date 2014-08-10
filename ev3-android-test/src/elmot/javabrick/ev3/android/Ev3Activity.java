package elmot.javabrick.ev3.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import com.example.ev3_android_test.R;

import static elmot.javabrick.ev3.android.Constants.MsgSource;

public class Ev3Activity extends Activity {
    BroadcastReceiver detachReceiver;
    private TextView logView;
    private Button runButton;
    private Button stopButton;
    private Chronometer chronometer;
    private LegoTaskBase runTask = null;

    public void log(int level, MsgSource source, String message) {
        Log.println(level, "EV3/USB", message);
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(source + ": " + message);
        int color = Color.WHITE;
        switch (level) {
            case Log.INFO:
                color = Color.BLUE;
                break;
            case Log.WARN:
                color = Color.YELLOW;
                break;
            case Log.ERROR:
                color = Color.RED;
                break;
            case Log.DEBUG:
                color = Color.GREEN;
                break;
        }
        spannable.setSpan(new ForegroundColorSpan(color), 0, spannable.length(), 0);

        logView.append(spannable);
        logView.append("\n");
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        logView = (TextView) findViewById(R.id.logView);
        runButton = (Button) findViewById(R.id.runButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        log(Log.INFO, MsgSource.ACTIVITY, "Started");
        IntentFilter filter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED);
        detachReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                String name = usbDevice.getDeviceName();
                log(Log.INFO, MsgSource.SYSTEM, "Device detached: " + name);
                updateButtons(Ev3Activity.this.isRunning());
            }
        };
        registerReceiver(detachReceiver, filter);

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTask();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTask.cancel(true);
                chronometer.stop();
                updateButtons(Ev3Activity.this.isRunning());
            }
        });
        if (isAutoStart())
            runTask();
    }

    public boolean isAutoStart() {
        return true;
    }

    private void runTask() {
        runTask = new LegoTask(this);
        runTask.execute();
        chronometer.start();
        updateButtons(this.isRunning());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                log(Log.INFO, MsgSource.SYSTEM, "Device attached: " + usbDevice.getDeviceName());
                updateButtons(isRunning());
            }
        }

    }

    public void updateButtons(boolean running) {
        if (running) {
            stopButton.setEnabled(true);
            runButton.setEnabled(false);
        } else {
            stopButton.setEnabled(false);
            runButton.setEnabled(true);
        }
    }

    private boolean isRunning() {
        return runTask != null &&
                runTask.getStatus() == AsyncTask.Status.RUNNING;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(detachReceiver);
    }

}
