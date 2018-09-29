package com.example.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latter.delegates.LatteDelegate;
import com.example.ui.recycler.ItemType;
import com.example.ui.recycler.MultipleFields;
import com.example.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by on 2018-09-29 下午 4:39.
 *
 * @author gentleman
 */

public class ImageDelegate extends LatteDelegate {

    private static final String APG_PICTURES = "APG_PICTURES";
    @BindView(R2.id.rv_image_container)
    RecyclerView mRecyclerView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    private void initImages(){
        final Bundle arguments = getArguments();
        if (arguments!=null){
            final ArrayList<String> pictures = arguments.getStringArrayList(APG_PICTURES);
            final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
            final int size;
            if (pictures != null) {
                size = pictures.size();
                for (int i = 0; i < size; i++) {
                    final String imageUrl = pictures.get(i);
                    final MultipleItemEntity entity = MultipleItemEntity
                            .builder()
                            .setItemType(ItemType.SINGLE_BIG_IMAGE)
                            .setField(MultipleFields.IMAGE_URL, imageUrl)
                            .build();
                    entities.add(entity);
                }
                final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
                mRecyclerView.setAdapter(adapter);
            }
        }

    }

    public static ImageDelegate create(ArrayList<String> pictures) {
        final Bundle args = new Bundle();
        args.putStringArrayList(APG_PICTURES, pictures);
        final ImageDelegate detailDelegate = new ImageDelegate();
        detailDelegate.setArguments(args);
        return detailDelegate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImages();
    }
}
