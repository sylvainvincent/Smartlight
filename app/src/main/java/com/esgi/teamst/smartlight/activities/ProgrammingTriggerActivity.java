package com.esgi.teamst.smartlight.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esgi.teamst.smartlight.R;
import com.esgi.teamst.smartlight.models.Day;
import com.esgi.teamst.smartlight.models.Programming;
import com.esgi.teamst.smartlight.models.ProgrammingResponse;
import com.esgi.teamst.smartlight.rest.ApiClient;
import com.esgi.teamst.smartlight.rest.ProgrammingServiceInterface;
import com.esgi.teamst.smartlight.services.ProgrammingService;

import java.io.IOException;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sylvainvincent on 10/07/16.
 */
public class ProgrammingTriggerActivity extends Activity {

    private static final String TAG = ProgrammingTriggerActivity.class.getSimpleName();
    private Button mButtonDisableLight;
    private Realm mRealm;
    private Programming mRealmProgramming;
    private Programming mProgramming;
    private ProgrammingServiceInterface mProgrammingServiceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_trigger);
        mButtonDisableLight = (Button) findViewById(R.id.button_disable_light);
        mButtonDisableLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgramming.setmTrigger(false);
                Call<ProgrammingResponse> programmingResponseCall = mProgrammingServiceInterface.updateProgramming(mRealmProgramming.getmId(),mProgramming);
                programmingResponseCall.enqueue(new Callback<ProgrammingResponse>() {
                    @Override
                    public void onResponse(Call<ProgrammingResponse> call, Response<ProgrammingResponse> response) {
                        if(response.code() == 200){
                            Log.e(TAG, "onResponse: on response OK" );
                            mRealm.beginTransaction();
                            mRealmProgramming.setmTrigger(false);
                            mRealm.commitTransaction();
                            Intent intent = new Intent(ProgrammingTriggerActivity.this, ProgrammingService.class);
                            intent.putExtra("id",response.body().getProgramming().getmId());
                            startService(intent);
                            finish();

                        }else{
                            Log.e(TAG, "onResponse: on response NON OK" + response.errorBody());
                            Toast.makeText(ProgrammingTriggerActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ProgrammingResponse> call, Throwable t) {
                        if(t instanceof IOException){
                            Log.i(TAG, "onResponse: IOException  ");
                            Toast.makeText(ProgrammingTriggerActivity.this, "Problème de connexion", Toast.LENGTH_SHORT).show();

                        }else{
                            Log.i(TAG, "onResponse: autre probleme ");
                        }
                    }
                });
            }
        });
        mProgrammingServiceInterface = ApiClient.getClient().create(ProgrammingServiceInterface.class);
        mRealm = Realm.getDefaultInstance();
        mRealmProgramming = mRealm.where(Programming.class).findFirst();
        mProgramming = new Programming();
        mProgramming.setmTrigger(true);
        mProgramming.setmEnabled(mRealmProgramming.ismEnabled());
        mProgramming.setmBrightnessValue(mRealmProgramming.getmBrightnessValue());
        mProgramming.setmGradual(mRealmProgramming.ismGradual());
        Call<ProgrammingResponse> programmingResponseCall = mProgrammingServiceInterface.updateProgramming(mRealmProgramming.getmId(), mProgramming);
        programmingResponseCall.enqueue(new Callback<ProgrammingResponse>() {
            @Override
            public void onResponse(Call<ProgrammingResponse> call, Response<ProgrammingResponse> response) {
                if(response.code() == 200){
                    Log.e(TAG, "onResponse: on response OK" );
                    mRealm.beginTransaction();
                    mRealmProgramming.setmTrigger(true);
                    mRealm.commitTransaction();
                }else{
                    Log.e(TAG, "onResponse: on response NON OK" + response.errorBody());
                    Toast.makeText(ProgrammingTriggerActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProgrammingResponse> call, Throwable t) {
                if(t instanceof IOException){
                    Log.i(TAG, "onResponse: IOException  ");
                    Toast.makeText(ProgrammingTriggerActivity.this, "Problème de connexion", Toast.LENGTH_SHORT).show();

                }else{
                    Log.i(TAG, "onResponse: autre probleme ");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mProgramming.setmTrigger(false);
        Call<ProgrammingResponse> programmingResponseCall = mProgrammingServiceInterface.updateProgramming(mRealmProgramming.getmId(), mProgramming);
        programmingResponseCall.enqueue(new Callback<ProgrammingResponse>() {
            @Override
            public void onResponse(Call<ProgrammingResponse> call, Response<ProgrammingResponse> response) {
                if(response.code() == 200){
                    Log.e(TAG, "onResponse: on response OK" );
                    mRealm.beginTransaction();
                    mRealmProgramming.setmTrigger(true);
                    mRealm.commitTransaction();
                }else{
                    Log.e(TAG, "onResponse: on response NON OK" + response.errorBody());
                    Toast.makeText(ProgrammingTriggerActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProgrammingResponse> call, Throwable t) {
                if(t instanceof IOException){
                    Log.i(TAG, "onResponse: IOException  ");
                    Toast.makeText(ProgrammingTriggerActivity.this, "Problème de connexion", Toast.LENGTH_SHORT).show();

                }else{
                    Log.i(TAG, "onResponse: autre probleme ");
                }
            }
        });
    }
}
