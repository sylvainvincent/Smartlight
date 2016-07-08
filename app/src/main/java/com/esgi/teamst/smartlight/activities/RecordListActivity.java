package com.esgi.teamst.smartlight.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.adapters.LogAdapter;
import com.esgi.teamst.smartlight.models.Record;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by sylvainvincent on 02/07/16.
 */
public class RecordListActivity extends AppCompatActivity{

    private ListView mListRecord;
    private List<Record> mRealmRecords;
    private Realm mRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs_list);
        initView();
        mRealm = Realm.getDefaultInstance();

        mRealmRecords = mRealm.where(Record.class)
                .findAll();
        if(mRealmRecords != null){

            mListRecord.setAdapter(new LogAdapter(this, mRealmRecords));
        }

    }

    private void initView(){
        mListRecord = (ListView) findViewById(R.id.list_records);
    }
}
