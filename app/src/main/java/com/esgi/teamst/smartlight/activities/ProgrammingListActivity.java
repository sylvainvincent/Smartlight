package com.esgi.teamst.smartlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.adapters.ProgrammingAdapter;
import com.esgi.teamst.smartlight.models.Programming;

import java.util.ArrayList;

import io.realm.Realm;

public class ProgrammingListActivity extends AppCompatActivity {

    public static final String TAG = ProgrammingListActivity.class.getSimpleName();
    public static final int REQUEST_PROGRAMMING = 1;
    private ListView mListProgramming;
    private ProgrammingAdapter mProgrammingAdapter;
    private ArrayList<Programming> mProgrammingArrayList;
    private Realm mRealm;
    private Programming mRealmProgramming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_programming_list);
        initView();

        mProgrammingArrayList = new ArrayList<>();
        mRealm = Realm.getDefaultInstance();
        mRealmProgramming = mRealm.where(Programming.class)
                .findFirst();
        if(mRealmProgramming != null){
            mProgrammingArrayList.add(mRealmProgramming);
            mProgrammingAdapter = new ProgrammingAdapter(this, mProgrammingArrayList);
            mListProgramming.setAdapter(mProgrammingAdapter);
            mListProgramming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivityForResult(new Intent(ProgrammingListActivity.this, ProgrammingActivity.class), REQUEST_PROGRAMMING);
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PROGRAMMING){
            if(resultCode == RESULT_OK){
                Log.i(TAG, "onActivityResult: Refresh");
                ArrayList<Programming> programmingList = new ArrayList<>();
                Programming programming = mRealmProgramming = mRealm.where(Programming.class)
                        .findFirst();
                if(programming != null){
                    programmingList.add(programming);
                    mProgrammingAdapter.refreshList(programmingList);
                }
            }
        }
    }

    private void initView() {
        mListProgramming = (ListView) findViewById(R.id.list_programming);
        //fab = (FloatingActionButton) findViewById(R.id.fab_list_alarm);
    }

}
