package com.layoutxml.applistmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.layoutxml.applistmanagerlibrary.AppList;
import com.layoutxml.applistmanagerlibrary.interfaces.AllAppsListener;
import com.layoutxml.applistmanagerlibrary.interfaces.AllNewAppsListener;
import com.layoutxml.applistmanagerlibrary.interfaces.AllUninstalledAppsListener;
import com.layoutxml.applistmanagerlibrary.objects.AppData;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private Button getAllButton;
    private Button getNewButton;
    private Button getUninstalledButton;
    private TextView getAllText;
    private TextView getNewText;
    private TextView getUninstalledTxt;
    private List<AppData> AllAppsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllButton = findViewById(R.id.getAllBtn);
        getNewButton = findViewById(R.id.getNewBtn);
        getUninstalledButton = findViewById(R.id.getUninstalledBtn);
        getAllText = findViewById(R.id.getAllTxt);
        getNewText = findViewById(R.id.getNewTxt);
        getUninstalledTxt = findViewById(R.id.getUninstalledTxt);

        final AllAppsListener allAppsListener = new AllAppsListener() {
            @Override
            public void allAppsListener(List<AppData> appDataList) {
                getAllText.setText("There are now "+appDataList.size()+" apps installed.");
                AllAppsList = appDataList;
            }
        };

        final AllNewAppsListener allNewAppsListener = new AllNewAppsListener() {
            @Override
            public void allNewAppsListener(List<AppData> appDataList) {
                getNewText.setText(appDataList.size()+" new apps installed.");
                if (AllAppsList!=null) {
                    AllAppsList.addAll(appDataList);
                    getAllText.setText(getAllText.getText()+"\n+ "+appDataList.size()+" ("+AllAppsList.size()+" total).");
                }
            }
        };

        final AllUninstalledAppsListener allUninstalledAppsListener = new AllUninstalledAppsListener() {
            @Override
            public void allUninstalledAppsListener(List<AppData> appDataList) {
                getUninstalledTxt.setText(appDataList.size()+" apps uninstaleld.");
                if (AllAppsList!=null) {
                    AllAppsList.removeAll(appDataList);
                    getAllText.setText(getAllText.getText()+"\n- "+appDataList.size()+" ("+AllAppsList.size()+" total).");
                }
            }
        };

        getAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppList.getAllApps(getApplicationContext(),allAppsListener);
            }
        });

        getNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppList.getAllNewApps(getApplicationContext(), allNewAppsListener, AllAppsList);
            }
        });

        getUninstalledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppList.getAllUninstalledApps(getApplicationContext(),allUninstalledAppsListener, AllAppsList);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppList.stop();
    }
}