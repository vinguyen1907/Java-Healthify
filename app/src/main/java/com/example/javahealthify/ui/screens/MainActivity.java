package com.example.javahealthify.ui.screens;

import static android.provider.Settings.System.getString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainVM viewModel;
    private NavController navController;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}