package com.example.javahealthify.ui.screens;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.POST_NOTIFICATIONS
    };

    boolean permission_post_notification=false;
    private static final String CHANNEL_ID = "my_channel";
    private static final String CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESC = "This is my notification channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_CODE = 123;

    private NotificationManager notificationManager;
    private AlarmManager alarmManager;
    private PendingIntent notificationIntent;

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void scheduleNotification() {
        // Set the desired time for the notification
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21); // Hour in 24-hour format
        calendar.set(Calendar.MINUTE, 49);
        calendar.set(Calendar.SECOND, 0);

        // Create an explicit intent for the notification receiver
        Intent intent = new Intent(this, NotificationReceiver.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.i("test1","test");
            notificationIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            Log.i("test2","test");
            notificationIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        // Schedule the notification
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationIntent);
    }

//    private void cancelNotification() {
//        if (notificationIntent != null) {
//            alarmManager.cancel(notificationIntent);
//        }
//    }

    private ActivityMainBinding binding;
    private MainVM viewModel;
    private NavController navController;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private BeginSignInRequest signInRequest;
    static private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        viewModel = new ViewModelProvider(this).get(MainVM.class);
        binding.setMainVM(viewModel);


        if (firebaseAuth.getCurrentUser() == null) {
            navController.navigate(R.id.signUpFragment);
        } else {
            viewModel.loadUser();
            navController.navigate(R.id.homeFragment);
        }

        setUpNavbar();

        if(!permission_post_notification) {
            requestPermissionNotification();
        }
        else {
            Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show();
        }

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        scheduleNotification();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();


//        Button scheduleButton = findViewById(R.id.scheduleButton);
//        scheduleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scheduleNotification(); //gọi hàm set thông báo
//            }
//        });

//        Button cancelButton = findViewById(R.id.cancelButton);
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelNotification();
//            }
//        });
    }

    private void requestPermissionNotification() {
        if(ContextCompat.checkSelfPermission(this,permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            permission_post_notification = true;
        }
        else {
            if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Log.d("Permission ","inside else 1 time don't allow");
            }
            else {
                Log.d("Permission ","inside else 2 time don't allow");
            }
            requestPermissiongLauncherNotification.launch(permissions[0]);
        }
    }

    private ActivityResultLauncher<String> requestPermissiongLauncherNotification =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{
                if(isGranted) {
                    permission_post_notification=true;
                }
                else
                {
                    permission_post_notification=false;
                    showPermissiongDialog("Notification Permission");
                }
            });

    public void  showPermissiongDialog(String permissiong_desc) {
        new AlertDialog.Builder(
                this
        ).setTitle("Alert for Permission")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent rintent = new Intent();
                                rintent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",getPackageName(),null);
                                rintent.setData(uri);
                                startActivity(rintent);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
    }

    private void setNavbarItem(int itemId) {
        binding.navBar.setItemSelected(itemId, true);
    }

    private void setUpNavbar() {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination,
                                             @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.homeFragment:
                        setNavbarItem(R.id.nav_home);
                        binding.navBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.menuFragment:
                        setNavbarItem(R.id.nav_menu);
                        binding.navBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.workoutFragment:
                        setNavbarItem(R.id.nav_workout);
                        binding.navBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.communityFragment:
                        setNavbarItem(R.id.nav_community);
                        binding.navBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.profileFragment:
                        setNavbarItem(R.id.nav_profile);
                        binding.navBar.setVisibility(View.VISIBLE);
                        break;
                    default:
                        binding.navBar.setVisibility(View.GONE);
                }
            }
        });
        binding.navBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch(i) {
                    case R.id.nav_home:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.nav_menu:
                        navController.navigate(R.id.menuFragment);
                        break;
                    case R.id.nav_workout:
                        navController.navigate(R.id.workoutFragment);
                        break;
                    case R.id.nav_community:
                        navController.navigate(R.id.communityFragment);
                        break;
                    case R.id.nav_profile:
                        navController.navigate(R.id.profileFragment);
                        break;
                }
            }
        });
    }

    public static FirebaseFirestore getDb() {
        return db;
    }
}