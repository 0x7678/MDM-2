package com.igexin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.Consts;
import com.shwootide.mdm.service.MyService;

public class GexinSdkMsgReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	Bundle bundle = intent.getExtras();
	Log.i("Password", "�õ�����");
	switch (bundle.getInt(Consts.CMD_ACTION)) {
	case Consts.GET_MSG_DATA:// ��ȡ������
	    byte[] payload = bundle.getByteArray("payload");// ���ݴ�С ������2k
	    String data = new String(payload);
	    Intent in = new Intent(context, MyService.class);
	    in.putExtra("data", data);
	    context.startService(in);
	    break;
	case Consts.GET_CLIENTID:
	    Log.i("Password", "�õ�����333");
	    String CID = bundle.getString("clientid");
	    Intent intwo = new Intent(context, MyService.class);
	    intwo.putExtra("cid", CID);
	    context.startService(intwo);
	    break;
	case Consts.BIND_CELL_STATUS:
	    Log.i("Password", "�õ�����444");
	    String cell = bundle.getString("cell");
	    break;
	default:
	    break;
	}

    }

}
