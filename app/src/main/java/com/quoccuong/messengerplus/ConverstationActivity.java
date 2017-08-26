package com.quoccuong.messengerplus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.quoccuong.data.Data;
import com.quoccuong.model.DataTrain;
import com.quoccuong.model.MyMessage;
import com.quoccuong.model.PointOfTime;
import com.quoccuong.utility.Algorithm;
import com.quoccuong.utility.LoadMessage;
import com.quoccuong.utility.MessageItemAdapter;
import com.quoccuong.utility.RecordDataView;
import com.quoccuong.utility.RecordSensorData;

import java.util.ArrayList;

public class ConverstationActivity extends Activity implements OnClickListener {

    private EditText edMsg;
    private ImageView mIvSend;
    private ListView lvListMessage;
    private ArrayList<MyMessage> mListMessages = new ArrayList<>();
    private MessageItemAdapter mMessageItemAdapter;
    private String phoneNumber = "";
    private String name = "";
    public static Handler handler;
    private ConverstationActivity mContext;
    private ImageView imvGesture;
    private boolean isReady = false;
    private boolean isRecord = false;
    private boolean isExecuting = false;
    private LinearLayout linearLayout;
    private RecordSensorData recordSensorData;
    private RecordDataView recordDataView;


    private ArrayList<PointOfTime> senserData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_converstation);

        mContext = this;
        Data.mContext = this;

        Bundle bundle = getIntent().getBundleExtra("bundle");
        name = bundle.getString("name");
        phoneNumber = bundle.getString("phoneNumber");

        if (Data.listConverstations.size() <= 5) {
            LoadMessage loadMessage = new LoadMessage();
            loadMessage.start();
        } else {
            for (int i = 0; i < Data.listConverstations.size(); i++) {
                if (Data.listConverstations.get(i).getmPhoneNumber().equals(phoneNumber)) {
                    name = Data.listConverstations.get(i).getnName();
                    mListMessages.clear();
                    mListMessages.addAll(Data.listConverstations.get(i).getListMessages());
                    break;
                }
            }
        }

        ActionBar actionBar = getActionBar();
        if (name.compareTo(phoneNumber) == 0) {
            actionBar.setTitle(name);
        } else {
            actionBar.setTitle(name + " (" + phoneNumber + ")");
        }

        actionBar.setHomeButtonEnabled(true);

        mMessageItemAdapter = new MessageItemAdapter(this, mListMessages);
        lvListMessage = (ListView) findViewById(R.id.activity_converstation_listview);
        lvListMessage.setAdapter(mMessageItemAdapter);

        edMsg = (EditText) findViewById(R.id.activity_converstation_edit_msg);
        edMsg.requestFocus();

        mIvSend = (ImageView) findViewById(R.id.activity_converstation_imgv_send);
        mIvSend.setOnClickListener(this);

        imvGesture = (ImageView) findViewById(R.id.activity_converstation_imgv_gesture);
        imvGesture.setOnClickListener(this);

        edMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = edMsg.getText().toString();
                if (str.isEmpty()) {
                    mIvSend.setImageResource(R.drawable.send1);
                } else {
                    mIvSend.setImageResource(R.drawable.send2);
                }
            }
        });
    /* fill the background ImageView with the resized image */
        linearLayout = (LinearLayout) findViewById(R.id.activity_converstation_linearlayout);
        recordDataView = new RecordDataView(mContext);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub

                Bundle bundle = msg.getData();

                String ms = bundle.getString("msg");

                if (ms.compareTo("new message") == 0) {
                    for (int i = 0; i < Data.listConverstations.size(); i++) {
                        if (Data.listConverstations.get(i).getmPhoneNumber().equals(phoneNumber)) {
                            name = Data.listConverstations.get(i).getnName();
                            ActionBar actionBar = getActionBar();
                            actionBar.setTitle(name);
                            actionBar.setHomeButtonEnabled(true);
                            mListMessages.clear();
                            mListMessages.addAll(Data.listConverstations.get(i).getListMessages());
                            break;
                        }
                    }
                    mMessageItemAdapter.notifyDataSetChanged();
                    return;
                }

                if (ms.compareTo("message resend") == 0) {
                    long id = bundle.getLong("idsms");
                    for (int i = 0; i < mListMessages.size(); i++) {
                        if (mListMessages.get(i).getId() == id
                                && !mListMessages.get(i).ismInbox()) {
                            final String txt = mListMessages.get(i).getmMsg();
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            // Yes button clicked
                                            sendSMS(phoneNumber, txt);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            // No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    mContext);
                            builder.setMessage("Do you resend sms?")
                                    .setPositiveButton("Yes",
                                            dialogClickListener)
                                    .setNegativeButton("No",
                                            dialogClickListener).show();
                            return;
                        }
                    }
                    return;
                }

                if (ms.compareTo("append") == 0) {
                    String charactor = bundle.getString("charactor");
                    Log.e("quoccuong", "charactor  " + charactor);
                    edMsg.append(charactor);
                    return;
                }

                if (ms.compareTo("executing") == 0) {
                    int dem = bundle.getInt("charactor");
                    if (dem == -1) {
                        imvGesture.setImageResource(R.drawable.toggle_on);
                    } else if (dem % 2 == 0) {
                        imvGesture.setImageResource(R.drawable.wait);
                    } else {
                        imvGesture.setImageResource(R.drawable.wait2);
                    }
                    return;
                }

                if (ms.compareTo("postInvalidate") == 0) {
                    recordDataView.postInvalidate();
                    return;
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendSMS(String phoneNumber, String txt) {
//        String SENT = "SMS_SENT";
//        String DELIVERED = "SMS_DELIVERED";
//
//        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
//                SENT), 0);
//
//        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
//                new Intent(DELIVERED), 0);
//
//        // ---when the SMS has been sent---
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(getBaseContext(), "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(getBaseContext(), "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(getBaseContext(), "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(getBaseContext(), "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                // if (getResultCode() == Activity.RESULT_OK) {
//                // myMessage.setErrorCode(0);
//                // } else {
//                // myMessage.setErrorCode(38);
//                // mMessageItemAdapter.notifyDataSetChanged();
//                // }
//            }
//        }, new IntentFilter(SENT));
//
//        // ---when the SMS has been delivered---
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getBaseContext(), "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));

        // SmsManager sms = SmsManager.getDefault();
        // sms.sendTextMessage(phoneNumber, null, txt, sentPI, deliveredPI);

        SmsManager sm = SmsManager.getDefault();
        ArrayList<String> parts = sm.divideMessage(txt);
        int numParts = parts.size();

        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

//        for (int i = 0; i < numParts; i++) {
//            sentIntents.add(sentPI);
//            deliveryIntents.add(deliveredPI);
//        }

        sm.sendMultipartTextMessage(phoneNumber, null, parts, null,
                null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_converstation_imgv_gesture) {
            if (isReady) {
                if (isExecuting) {
                    isExecuting = false;
                    imvGesture.setImageResource(R.drawable.toggle_on);
                } else {
                    isReady = false;
                    imvGesture.setImageResource(R.drawable.toggle_off);
                    linearLayout.removeAllViews();
                }
            } else {
                isReady = true;
                imvGesture.setImageResource(R.drawable.toggle_on);
                senserData = new ArrayList<PointOfTime>();
                recordDataView = new RecordDataView(mContext);
                linearLayout.addView(recordDataView, LinearLayout.LayoutParams.MATCH_PARENT, 300);
            }
            return;
        }

        if (v.getId() == R.id.activity_converstation_imgv_send) {
            String text = edMsg.getText().toString();
            text = text.trim();

            if (text.isEmpty()) {
                return;
            }

            sendSMS(phoneNumber, text);
            edMsg.setText("");
        }
    }

    class GestureToCharactor extends Thread {
        @Override
        public void run() {
            ArrayList<PointOfTime> mData = new ArrayList<>();
            mData.addAll(senserData);
            if (Data.dataTrainTho == null || Data.dataTrainTho.size() == 0) {
                return;
            }

            isExecuting = true;

            Loading loading = new Loading();
            loading.start();

//            String charactor = "none";
//            double pmax = -1;
//            Log.e("quoccuong", "Data size = " + Data.dataTrain.size());
//            for (int i = 0; i < Data.dataTrain.size(); i++) {
//                for (int j = 0; j < Data.dataTrain.get(i).second.size(); j++) {
//                    double pi = Algorithm.POMODEL(Data.dataTrain.get(i).second.get(j), mData);
//                    if (pi > pmax) {
//                        pmax = pi;
//                        charactor = "" + Data.dataTrain.get(i).first;
//                    }
//                }
//            }

            String charactor = Data.dataTrainTho.get(0).first;

            double minDTW = 0;
            for (int j = 0; j < Data.dataTrainTho.get(0).second.size(); j++) {
                minDTW = minDTW + DTW(Data.dataTrainTho.get(0).second.get(j), mData);
            }
            minDTW = minDTW / Data.dataTrainTho.get(0).second.size();

            for (int i = 1; i < Data.dataTrainTho.size(); i++) {

                double dtw = 0;

                int j = 0;
                for (; j < Data.dataTrainTho.get(i).second.size() / 2; j++) {
                    dtw = dtw + DTW(Data.dataTrainTho.get(i).second.get(j), mData);
                }

                dtw = dtw / j;

                if (dtw < minDTW) {
                    minDTW = dtw;
                    charactor = Data.dataTrainTho.get(i).first;
                }
            }

            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("msg", "append");
            bundle.putString("charactor", charactor);

            msg.setData(bundle);

            ConverstationActivity.handler.sendMessage(msg);
            isExecuting = false;
        }

        public double distance(PointOfTime s, PointOfTime t) {
            double daccx = s.getAccX() - t.getAccX();
            double daccy = s.getAccY() - t.getAccY();
            double daccz = s.getAccZ() - t.getAccZ();
            double dgyrx = s.getGyrX() - t.getGyrX();
            double dgyry = s.getGyrY() - t.getGyrY();
            double dgyrz = s.getGyrZ() - t.getGyrZ();


            return Math.sqrt(daccx * daccx + daccy * daccy + daccz * daccz
                    + dgyrx * dgyrx + dgyry * dgyry + dgyrz * dgyrz);
        }


        public double DTW(ArrayList<PointOfTime> sample, ArrayList<PointOfTime> train) {

            if (train == null || sample == null) {
                return -1;
            }
            if (train.isEmpty() || sample.isEmpty()) {
                return -1;
            }

            int samplesize = sample.size();
            int trainsize = train.size();
            double[][] d = new double[samplesize][trainsize];

            d[0][0] = distance(sample.get(0), train.get(0));

            for (int i = 1; i < trainsize; i++) {
                d[0][i] = distance(sample.get(0), train.get(i)) + d[0][i - 1];
            }

            for (int i = 1; i < samplesize; i++) {
                d[i][0] = distance(sample.get(i), train.get(0)) + d[i - 1][0];

                for (int j = 1; j < trainsize; j++) {
                    double x = distance(sample.get(i), train.get(j));
                    double t = d[i][j - 1];
                    double t1 = d[i - 1][j];
                    double t2 = d[i - 1][j - 1];
                    d[i][j] = x + distanceMin(t, t1, t2);
                }
            }

            return d[samplesize - 1][trainsize - 1];
        }

        public double distanceMin(double d1, double d2, double d3) {
            double dmin = d1;
            if (d2 < dmin) {
                dmin = d2;
            }

            if (d3 < dmin) {
                dmin = d3;
            }
            return dmin;
        }


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    if (isReady && !isExecuting && !isRecord) {
                        isRecord = true;
                        imvGesture.setImageResource(R.drawable.record);
                        recordSensorData = new RecordSensorData(mContext, senserData);
                        recordDataView.setData(senserData);
                        recordSensorData.start();
                    }
                }
                if (action == KeyEvent.ACTION_UP) {
                    if (isRecord) {
                        isRecord = false;
                        recordSensorData.setStop(true);
                        imvGesture.setImageResource(R.drawable.toggle_on);
                        GestureToCharactor gestureToCharactor = new GestureToCharactor();
                        gestureToCharactor.start();
                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    class Loading extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (isExecuting) {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "executing");
                bundle.putInt("charactor", i);
                msg.setData(bundle);
                ConverstationActivity.handler.sendMessage(msg);
                i++;
            }
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("msg", "executing");
            bundle.putInt("charactor", -1);
            msg.setData(bundle);
            ConverstationActivity.handler.sendMessage(msg);
        }
    }
}
