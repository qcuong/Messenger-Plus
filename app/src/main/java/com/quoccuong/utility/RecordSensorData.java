package com.quoccuong.utility;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;

import com.quoccuong.messengerplus.ConverstationActivity;
import com.quoccuong.model.DataTrain;
import com.quoccuong.model.PointOfTime;

import java.util.ArrayList;

/**
 * Created by sev_user on 7/29/2015.
 */
public class RecordSensorData extends Thread implements SensorEventListener {
    private ArrayList<PointOfTime> data;
    private Context mContext;


    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyrscope;
    private float acc_x, acc_y, acc_z;
    private float gyr_x, gyr_y, gyr_z;

    private final int M = DataTrain.M;
    private final int v1 = -((M - 1) / 2);
    private final int vm = ((M - 1) / 2);


    double g2 = 2 * 9.80665;
    double acc_g = g2 / ((M - 1) / 2);

    double a = 20;
    double gyr_a = a / ((M - 1) / 2);

    private boolean isStop = false;

    public RecordSensorData(Context context, ArrayList<PointOfTime> data) {
        this.mContext = context;
        this.data = data;

        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);

        sensorGyrscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensorGyrscope, SensorManager.SENSOR_DELAY_FASTEST);
        isStop = false;
        this.data.clear();
    }

    @Override
    public void run() {

        while (!isStop) {
            if (ConverstationActivity.handler != null) {
                Message msg = Message.obtain();

                Bundle bundle = new Bundle();
                bundle.putString("msg", "postInvalidate");
                msg.setData(bundle);
                ConverstationActivity.handler.sendMessage(msg);
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }

            PointOfTime dataPoint = new PointOfTime();
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }

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
        isStop = true;
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

    public void setStop(boolean isStop) {
        this.isStop = isStop;
    }
}
