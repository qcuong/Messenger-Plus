package com.quoccuong.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.quoccuong.data.Data;
import com.quoccuong.model.DataTrain;
import com.quoccuong.model.MyPair;
import com.quoccuong.model.PointOfTime;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by sev_user on 7/29/2015.
 */
public class LoadGestureData extends Thread {
    private Context mContext;

    public LoadGestureData(Context context) {
        mContext = context;
    }

    @Override
    public void run() {
        try {
            File file = new File(mContext.getFilesDir(), "gesture_plus_1.data");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Data.dataTrain = (ArrayList<MyPair<String, ArrayList<DataTrain>>>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            Data.dataTrain = new ArrayList<>();
            e.printStackTrace();
        }

        //--------
//        try {
//            File file = new File(mContext.getFilesDir(), "gesture_tho_plus_1.data");
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
//            Data.dataTrainTho = (ArrayList<MyPair<String, ArrayList<ArrayList<PointOfTime>>>>) objectInputStream.readObject();
//            objectInputStream.close();
//            return;
//        } catch (EOFException e) {
//            Data.dataTrainTho = new ArrayList<>();
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            File file = new File(mContext.getFilesDir(), "gesture_tho_plus_1.data");
//            ObjectOutputStream objectInputStream = new ObjectOutputStream(new FileOutputStream(file));
//            objectInputStream.close();
//            Data.dataTrainTho = new ArrayList<>();
//            return;
//        } catch (Exception e) {
//            Log.e("quoccuong", "create data " + e.toString());
//        }

//        try {
//            String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
//            File file = new File(sdcard + "/gesture.data");
//            Log.e("quoccuong", "save data " + file.getPath());
//            ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
//            Data.dataTrainTho = (ArrayList<MyPair<String, ArrayList<ArrayList<PointOfTime>>>>) o.readObject();
//            o.close();
//
//            File file2 = new File(mContext.getFilesDir(), "gesture_plus_tho.data");
//            ObjectOutputStream objectInputStream = new ObjectOutputStream(new FileOutputStream(file2));
//            objectInputStream.writeObject(Data.dataTrainTho);
//            objectInputStream.flush();
//            objectInputStream.close();
//        } catch (Exception e) {
//            Log.e("quoccuong", "save data " + e.toString());
//        }



        try {
            File file = new File(mContext.getFilesDir(), "gesture_plus_tho.data");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Data.dataTrainTho = (ArrayList<MyPair<String, ArrayList<ArrayList<PointOfTime>>>>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (Data.dataTrainTho == null) {
            Data.dataTrainTho = new ArrayList<>();
        }
    }
}
