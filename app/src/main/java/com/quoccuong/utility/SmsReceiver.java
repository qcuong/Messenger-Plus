package com.quoccuong.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;

import com.quoccuong.data.Data;
import com.quoccuong.messengerplus.ConverstationActivity;
import com.quoccuong.messengerplus.R;
import com.quoccuong.model.Contact;
import com.quoccuong.model.MyMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;

        HashMap<String, ArrayList<MyMessage>> listAllNewMessages = new HashMap<String, ArrayList<MyMessage>>();
        if (bundle != null) {
            // ---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            String phone = "";
            MyMessage myMsg = new MyMessage();
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                phone = msgs[i].getOriginatingAddress();

                phone = phone.trim();
                if (phone.startsWith("+84")) {
                    phone = phone.substring(3);
                    phone = "0" + phone;
                }

                phone = phone.replaceAll("\\s", "");


                myMsg.setmMsg(msgs[i].getMessageBody().toString());
                myMsg.setmTime(new Date(System.currentTimeMillis()));
                myMsg.setmInbox(true);
                myMsg.setReadState(0);

                if (listAllNewMessages.containsKey(phone)) {
                    listAllNewMessages.get(phone).add(myMsg);
                } else {
                    ArrayList<MyMessage> mylistMsgs = new ArrayList<MyMessage>();
                    mylistMsgs.add(myMsg);
                    listAllNewMessages.put(phone, mylistMsgs);
                }
            }

            String name = "";
            boolean c = true;
            if (Data.listContacts.isEmpty()) {
                Data.listContacts.clear();

                // TODO Auto-generated method stub
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String[] projection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor people = context.getContentResolver().query(uri, projection,
                        null, null, null);

                int indexName = people
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int indexNumber = people
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                if (people == null || people.getCount() <= 0) {
                    return;
                }
                if (!people.moveToFirst()) {
                    return;
                }
                do {
                    String name1 = people.getString(indexName);
                    String phone1 = people.getString(indexNumber);

                    phone1 = phone1.trim();
                    if (phone1.startsWith("+84")) {
                        phone1 = phone1.substring(3);
                        phone1 = "0" + phone1;
                    }
                    phone1 = phone1.replaceAll("\\s", "");
                    System.out.println("phone= "+phone1+"| name= "+name1);

                    Data.listContacts.add(new Contact(name1, phone1));

                } while (people.moveToNext());
            }

            for (int i = 0; i < Data.listContacts.size(); i++) {
                if (phone.compareTo(Data.listContacts.get(i).getPhoneNumber()) == 0) {
                    name = Data.listContacts.get(i).getName();
                    c = false;
                    break;
                }
            }

            if (c) {
                name = phone;
            }

            Intent intent2 = new Intent(context, ConverstationActivity.class);

            Bundle bundle2 = new Bundle();
            bundle2.putString("phoneNumber", phone);
            bundle2.putString("name", name);
            intent2.putExtra("bundle", bundle2);


            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification mNotification = new Notification.Builder(context)
                    .setContentTitle(name)
                    .setContentText(myMsg.getmMsg())
                    .setSmallIcon(R.mipmap.icon)
                    .setColor(context.getResources().getColor(R.color.myblue))
                    .setContentIntent(contentIntent)
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis() % 100000, mNotification);

            Intent intent3 = new Intent();

            intent3.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
            intent3.putExtra("com.sonyericsson.home.intent.extra.badge.MainActivity", "com.quoccuong.messenger.MainActivity");
            intent3.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true);
            intent3.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", "99");
            intent3.putExtra("com.sonyericsson.home.intent.extra.badge.messenger", "com.quoccuong.messenger");

            context.sendBroadcast(intent3);


            while (true) {
                if (!Data.isAddNewMessageRunning) {
                    AddNewMessage addNewMessage = new AddNewMessage(listAllNewMessages);
                    addNewMessage.start();
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
