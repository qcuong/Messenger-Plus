package com.quoccuong.messengerplus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quoccuong.data.Data;
import com.quoccuong.model.DataTrain;
import com.quoccuong.model.MyPair;
import com.quoccuong.model.PointOfTime;
import com.quoccuong.utility.Algorithm;
import com.quoccuong.utility.KSpinnerAdaper;
import com.quoccuong.utility.RecordDataSensorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class CreateNewGestueActivity extends Activity implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyrscope;
    private Context mContext;

    private float acc_x, acc_y, acc_z;
    private float gyr_x, gyr_y, gyr_z;


    private Button btnSave;
    private Button btnReset;
    private Button btnCancel;

    private ImageView imageSave;
    private ImageView imageReset;
    private ImageView imageCancel;

    private Spinner spAccK;
    private Spinner spGyrK;

    private TextView tvAcc;
    private TextView tvGyr;

    private EditText edCharactor;
    private boolean isStop = true;
    private boolean isReady = false;
    private ArrayList<PointOfTime> data = new ArrayList<>();
    private final int M = DataTrain.M;
    private final int v1 = -((M - 1) / 2);
    private final int vm = ((M - 1) / 2);

    double g2 = 2 * 9.80665;
    double acc_g = g2 / ((M - 1) / 2);

    double a = 20;
    double gyr_a = a / ((M - 1) / 2);

    private RecordDataSensorView record;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_gestue);
        mContext = this;

        Bundle bundle = getIntent().getBundleExtra("msg");
        String msg = bundle.getString("msg");

        if (msg.compareTo("add") == 0) {
            position = bundle.getInt("position");
        }

        initSensor();
        initView();
    }

    void initSensor() {
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);

        sensorGyrscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensorGyrscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    void initView() {
        record = (RecordDataSensorView) findViewById(R.id.activity_create_new_gesture_record);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        imageCancel = (ImageView) findViewById(R.id.imageCancel);
        imageReset = (ImageView) findViewById(R.id.imgReset);
        imageSave = (ImageView) findViewById(R.id.imageSave);
        spAccK = (Spinner) findViewById(R.id.spiner_acc_k);
        spGyrK = (Spinner) findViewById(R.id.spiner_gyr_k);

        record.setData(data);

        btnSave.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imageCancel.setOnClickListener(this);
        imageReset.setOnClickListener(this);
        imageSave.setOnClickListener(this);

        final ArrayList<Integer> listK = new ArrayList<>();
        listK.add(5);
        listK.add(10);
        listK.add(15);
        listK.add(20);
        listK.add(25);
        listK.add(30);

        KSpinnerAdaper acc_adapter = new KSpinnerAdaper(mContext, listK);
        KSpinnerAdaper gyr_adapter = new KSpinnerAdaper(mContext, listK);

        spAccK.setAdapter(acc_adapter);
        spGyrK.setAdapter(gyr_adapter);

        spAccK.setSelection(2);
        spGyrK.setSelection(3);

        spAccK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                record.setAccK(listK.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGyrK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                record.setGyrK(listK.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvAcc = (TextView) findViewById(R.id.tv_acc);
        tvGyr = (TextView) findViewById(R.id.tv_gyr);

        tvAcc.setTypeface(Data.typeface);
        tvGyr.setTypeface(Data.typeface);

        edCharactor = (EditText) findViewById(R.id.ed_charactor);
        if (position >= 0) {
            edCharactor.setText(Data.dataTrain.get(position).first);
            edCharactor.setEnabled(false);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acc_x = event.values[0];
            acc_y = event.values[1];
            acc_z = event.values[2];
        }
        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyr_x = event.values[0];
            gyr_y = event.values[1];
            gyr_z = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancel || v.getId() == R.id.imageCancel) {
            isStop = true;
            isReady = false;
            finish();
        }

        if (v.getId() == R.id.btnReset || v.getId() == R.id.imgReset) {
            if (isReady) {
                isReady = false;
                imageReset.setImageResource(R.drawable.ready);
                imageSave.setImageResource(R.drawable.save2);
                btnReset.setText("READY");
                data.clear();
            } else {
                isReady = true;
                imageReset.setImageResource(R.drawable.reload);
                btnReset.setText("RESET");
                data.clear();
            }
            record.postInvalidate();
            return;
        }

        if (v.getId() == R.id.btnSave || v.getId() == R.id.imageSave) {
            String charactor = edCharactor.getText().toString();
            if (data.isEmpty()) {
                return;
            }

            if (charactor.isEmpty()) {
                return;
            }

            TrainingData trainingData = new TrainingData(charactor);
            trainingData.execute(0, 0, 0);
        }
    }

    class RecordDataSensor extends Thread {
        @Override
        public void run() {
            while (!isStop) {
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                }

                record.postInvalidate();
                PointOfTime dataPoint = new PointOfTime();
                if (acc_x > g2) {
                    dataPoint.setAccX(vm);
                } else if (acc_x < -g2) {
                    dataPoint.setAccX(v1);
                } else {
                    dataPoint.setAccX((int) Math.round((acc_x / acc_g)));
                }


                if (acc_y > g2) {
                    dataPoint.setAccY(vm);
                } else if (acc_y < -g2) {
                    dataPoint.setAccY(v1);
                } else {
                    dataPoint.setAccY((int) Math.round((acc_y / acc_g)));
                }

                if (acc_z > g2) {
                    dataPoint.setAccZ(vm);
                } else if (acc_z < -g2) {
                    dataPoint.setAccZ(v1);
                } else {
                    dataPoint.setAccZ((int) Math.round((acc_z / acc_g)));
                }

                if (gyr_x > a) {
                    dataPoint.setGyrX(vm);
                } else if (gyr_x < -a) {
                    dataPoint.setGyrX(v1);
                } else {
                    dataPoint.setGyrX((int) Math.round((gyr_x / gyr_a)));
                }

                if (gyr_y > a) {
                    dataPoint.setGyrY(vm);
                } else if (gyr_y < -a) {
                    dataPoint.setGyrY(v1);
                } else {
                    dataPoint.setGyrY((int) Math.round((gyr_y / gyr_a)));
                }

                if (gyr_z > a) {
                    dataPoint.setGyrZ(vm);
                } else if (gyr_z < -a) {
                    dataPoint.setGyrZ(v1);
                } else {
                    dataPoint.setGyrZ((int) Math.round((gyr_z / gyr_a)));
                }

                data.add(dataPoint);


                int maxx = v1 - 1;
                int minx = vm + 1;

                for (int i = data.size() - 1, j = 30; i >= 0 && j > 0; i--, j--) {
                    if (data.get(i).getAccX() > maxx) {
                        maxx = data.get(i).getAccX();
                    }

                    if (data.get(i).getAccX() < minx) {
                        minx = data.get(i).getAccX();
                    }
                }

                if (maxx - minx == 1) {
                    for (int i = data.size() - 1, j = 30; i >= 0 && j > 0; i--, j--) {
                        data.get(i).setAccX(minx);
                    }
                }

                maxx = v1 - 1;
                minx = vm + 1;

                for (int i = data.size() - 1, j = 30; i >= 0 && j > 0; i--, j--) {
                    if (data.get(i).getAccY() > maxx) {
                        maxx = data.get(i).getAccY();
                    }

                    if (data.get(i).getAccY() < minx) {
                        minx = data.get(i).getAccY();
                    }
                }

                if (maxx - minx == 1) {
                    for (int i = data.size() - 1, j = 30; i >= 0 && j > 0; i--, j--) {
                        data.get(i).setAccY(minx);
                    }
                }

                maxx = v1 - 1;
                minx = vm + 1;

                for (int i = data.size() - 1, j = 30; i >= 0 && j > 0; i--, j--) {
                    if (data.get(i).getAccZ() > maxx) {
                        maxx = data.get(i).getAccZ();
                    }

                    if (data.get(i).getAccZ() < minx) {
                        minx = data.get(i).getAccZ();
                    }
                }

                if (maxx - minx == 1) {
                    for (int i = data.size() - 1, j = 30; i >= 0 && j > 0; i--, j--) {
                        data.get(i).setAccZ(minx);
                    }
                }
            }
        }
    }

    class TrainingData extends AsyncTask<Integer, Integer, Boolean> {
        private ProgressDialog pd = null;
        private String charactor;
        int NX = 8;
        int NY = 8;
        int NZ = 8;
        int NGX = 8;
        int NGY = 8;
        int NGZ = 8;

        DataTrain dataTrainX = null;
        DataTrain dataTrainGY = null;
        DataTrain dataTrainGX = null;
        DataTrain dataTrainGZ = null;
        DataTrain dataTrainY = null;
        DataTrain dataTrainZ = null;
        long time = 0;


        public TrainingData(String charactor) {
            this.charactor = charactor;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setTitle("Processing...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
            time = System.currentTimeMillis();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            Algorithm.Tranning tranning = new Algorithm.Tranning(data);
            DataTrain newTrainingData = tranning.execute();

            if (newTrainingData == null) {
                try {
                    boolean c = true;
                    for (int i = 0; i < Data.dataTrainTho.size(); i++) {
                        if (charactor.equals(Data.dataTrainTho.get(i).first)) {
                            position = i;
                            Data.dataTrainTho.get(position).second.add(data);
                            c = false;
                            break;
                        }
                    }

                    if (c) {
                        position = Data.dataTrainTho.size();
                        ArrayList<ArrayList<PointOfTime>> x = new ArrayList<>();
                        x.add(data);
                        Data.dataTrainTho.add(new MyPair<>(charactor, x));
                    }

                    File file = new File(mContext.getFilesDir(), "gesture_plus_tho.data");
                    ObjectOutputStream objectInputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectInputStream.writeObject(Data.dataTrainTho);
                    objectInputStream.flush();
                    objectInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            if (position >= 0) {
                Data.dataTrain.get(position).second.add(newTrainingData);
            } else {
                boolean c = true;
                for (int i = 0; i < Data.dataTrain.size(); i++) {
                    if (charactor.equals(Data.dataTrain.get(i).first)) {
                        position = i;
                        Data.dataTrain.get(position).second.add(newTrainingData);
                        c = false;
                        break;
                    }
                }

                if (c) {
                    position = Data.dataTrain.size();
                    ArrayList<DataTrain> x = new ArrayList<>();
                    x.add(newTrainingData);
                    Data.dataTrain.add(new MyPair<>(charactor, x));
                }
            }


            try {
                File file = new File(mContext.getFilesDir(), "gesture_plus_1.data");
                ObjectOutputStream objectInputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectInputStream.writeObject(Data.dataTrain);
                objectInputStream.flush();
                objectInputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Boolean result) {


            long t = System.currentTimeMillis();
            if (t - time < 8000) {
                try {
                    Thread.sleep(8000 - t + time);
                } catch (Exception e) {
                }
            }


            if (pd != null) {
                pd.dismiss();
            }


            if (result) {
                Toast.makeText(mContext, "Training successful!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "Training successful!", Toast.LENGTH_LONG).show();
            }

            data = new ArrayList<>();
            record.setData(data);

            isReady = false;
            btnReset.setText("READY");
            imageReset.setImageResource(R.drawable.ready);
            imageSave.setImageResource(R.drawable.save2);
            record.postInvalidate();
            edCharactor.setEnabled(false);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    if (isReady && isStop) {
                        isStop = false;
                        data.clear();
                        RecordDataSensor recordDataSensor = new RecordDataSensor();
                        recordDataSensor.start();
                        imageSave.setImageResource(R.drawable.save);
                    }
                }
                if (action == KeyEvent.ACTION_UP) {
                    isStop = true;
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

}

