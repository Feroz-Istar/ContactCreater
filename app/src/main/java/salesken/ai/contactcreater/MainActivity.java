package salesken.ai.contactcreater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllpermission();
    }



    public boolean requestAllpermission() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : PERMISSIONS) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                //Initial
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);


                //Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                //Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                        perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
                      ) {
                    // All Permissions Granted
                   startActivity(new Intent(MainActivity.this,CreateContact.class));
                   finish();
                } else {
                    Toast.makeText(MainActivity.this, "Some permissions were denied", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }



}