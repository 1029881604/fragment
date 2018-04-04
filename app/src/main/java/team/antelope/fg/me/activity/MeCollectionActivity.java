package team.antelope.fg.me.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import team.antelope.fg.R;
import team.antelope.fg.me.adapter.MeCollectionAdapter;
import team.antelope.fg.ui.base.BaseActivity;

public class MeCollectionActivity extends BaseActivity implements View.OnClickListener {
    Toolbar mToolbar;
    FloatingActionButton fab;//悬浮按钮
    private List<ImagePicture> imagePictureList = new ArrayList<>();
    private MeCollectionAdapter meCollectionAdapter;


    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fab = findViewById(R.id.collection_fab);
        fab.setOnClickListener(this);
        initImage();

        RecyclerView recyclerView = findViewById(R.id.me_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        meCollectionAdapter = new MeCollectionAdapter(imagePictureList);
        recyclerView.setAdapter(meCollectionAdapter);
    }

    private void initImage() {

        ImagePicture head1 = new ImagePicture("还行", R.drawable.me_user_head5);
        imagePictureList.add(head1);
        ImagePicture head2 = new ImagePicture("Html5", R.drawable.me_user_head3);
        imagePictureList.add(head2);
        ImagePicture head3 = new ImagePicture("手工", R.drawable.me_user_head4);
        imagePictureList.add(head3);
        ImagePicture head4 = new ImagePicture("跑腿", R.drawable.me_user_head2);
        imagePictureList.add(head4);
        ImagePicture head5 = new ImagePicture("ppt", R.drawable.me_user_head1);
        imagePictureList.add(head5);


    }


    @Override
    public int getLayout() {
        return R.layout.me_collection_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collection_fab: {
                Intent intent = new Intent(MeCollectionActivity.this, MeAddCollectActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String heading = data.getStringExtra("heading");

                }
        }
    }
}
