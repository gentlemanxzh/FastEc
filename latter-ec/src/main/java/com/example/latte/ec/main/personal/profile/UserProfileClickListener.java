package com.example.latte.ec.main.personal.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.latte.ec.R;
import com.example.latte.ec.main.personal.list.ListBean;
import com.example.latter.app.Latte;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.util.callback.CallbackManager;
import com.example.latter.util.callback.CallbackType;
import com.example.latter.util.callback.IGlobalCallback;
import com.example.latter.util.log.LatteLogger;
import com.example.ui.date.DateDialogUtil;

/**
 * @author gentleman
 * @date 2018/8/9
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};


    public UserProfileClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getmId();
        switch (id) {
            case 1:
                //开始照相机或选择图片
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void exeuteCallback(Uri args) {
                        LatteLogger.d("ON_CROP",args);
                    }
                });
                DELEGATE.startCameraWithCheck();

                break;
            case 2:
                final LatteDelegate nameDelegate = bean.getmDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[i]);
                        dialogInterface.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
