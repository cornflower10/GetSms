package com.example.cornflower.getsms;

/**
 * Created by xiejingbao on 2015/11/24.
 */


import android.app.Activity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *获取短信
 */
class SmsContent extends ContentObserver {

    private Cursor cursor = null;

    private Activity activity;
    private EditText editText;

    public SmsContent(Handler handler,Activity activity,EditText editText) {
        super(handler);
        this.activity = activity;
        this.editText = editText;
    }

    @Override
    public void onChange(boolean selfChange) {

        super.onChange(selfChange);

        cursor = activity.managedQuery(Uri.parse("content://sms/inbox"), new String[]{"_id", "address", "read", "body"},
                 " address=? and read=?", new String[]{"15555215556", "0"}, "_id desc");//��id�������date����Ļ����޸��ֻ�ʱ��󣬶�ȡ�Ķ��žͲ�׼��
        Log.e("cursor.isBeforeFirst() ", cursor.isBeforeFirst() + " cursor.getCount()  " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("read", "1");
            cursor.moveToNext();
            int smsbodyColumn = cursor.getColumnIndex("body");
            String smsBody = cursor.getString(smsbodyColumn);
            Log.e("smsBody...........",smsBody);

            editText.setText(getDynamicPassword(smsBody));

        }

        if(Build.VERSION.SDK_INT < 14) {
            cursor.close();
        }
    }


    public static String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while(m.find()){
            if(m.group().length() == 4) {
                Log.e("================", m.group());
                dynamicPassword = m.group();
            }
        }

        return dynamicPassword;
    }
}
