package com.example.latter.wechat.templates;

import android.widget.Toast;

import com.example.latter.activity.ProxyActivity;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.wechat.BaseWXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;

/**
 * @author gentleman
 * @date 2018/6/6
 */

public class WPayXEntryTemplate extends BaseWXPayEntryActivity {
    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);//取消动画

    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);//取消动画
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);//取消动画
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
