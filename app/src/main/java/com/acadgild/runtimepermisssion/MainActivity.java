package com.acadgild.runtimepermisssion;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
 private static final int PERM_REQ_CODE=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //finding button in java
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasPermission()){
                    makeFolder();

                }else { requestPermission();

                }
            }
        });
    }
         //METHOD TO CHECK THE PERMISSION
        private boolean hasPermission(){
        int res=0;
            String[]permisson= new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for(String perm:permisson){
                res=checkCallingOrSelfPermission(perm);
                if(!(res== PackageManager.PERMISSION_GRANTED)){
                    return false;
                }
            }
            return true;
    }
    //Method to request for the permission
    private void requestPermission(){
        String[]permisson= new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permisson,PERM_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode) {
            case PERM_REQ_CODE:
                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                //if user does not granted the permission
                allowed = false;
                break;
        }
        if (allowed) {
            //user granted all the permission we can perform our work
            makeFolder();
        }
        else {
            //giving warning to the user that permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
                Toast.makeText(getApplicationContext(),"User permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }
      //Method that would make folder if all permissions has been granted
    private void makeFolder(){
                File file= new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"MYFILE");
                if(!file.exists()){
                    boolean ff=  file.mkdir();
                    if(ff){
                        Toast.makeText(getApplicationContext(),"Folder created sucessfully",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Folder not created", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Folder alredy exist",Toast.LENGTH_LONG).show();

                }
            }
            }




