package com.example.javahealthify.ui.screens.community_user_profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.AchievementsListAdapter;
import com.example.javahealthify.data.models.Achievement;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.databinding.FragmentCommunityUserProfileBinding;
import com.example.javahealthify.ui.interfaces.ActionOnAchievementMenu;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CommunityUserProfileFragment extends Fragment implements ActionOnAchievementMenu {
    private FragmentCommunityUserProfileBinding binding;
    private CommunityUserProfileVM viewModel;
    private AchievementsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityUserProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CommunityUserProfileVM.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        String uidFromPreviousFragment = CommunityUserProfileFragmentArgs.fromBundle(getArguments()).getUid();
        viewModel.loadUser(uidFromPreviousFragment);
        viewModel.loadAchievements(uidFromPreviousFragment);

        // Set up user avatar: if user already has avatar, use Glide to load it, else display default image
        viewModel.getUser().observe(getViewLifecycleOwner(), new Observer<NormalUser>() {
            @Override
            public void onChanged(NormalUser user) {
                if (user != null) {
                    if (user.getImageUrl() == null || user.getImageUrl().isEmpty()) {
                        binding.userProfileAvatarImg.setImageResource(R.drawable.default_profile_image);
                    } else {
                        Glide.with(requireContext()).load(user.getImageUrl()).into(binding.userProfileAvatarImg);
                    }

                    if (user.getFollowers().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        binding.followBtn.setText("Followed");
                    } else {
                        binding.followBtn.setText("Follow");
                    }
                }
            }
        });

        // Set up achievement list
        adapter = new AchievementsListAdapter(requireContext(), new ArrayList<>(), NavHostFragment.findNavController(this), this);
        binding.profileUserAchievementLst.setAdapter(adapter);
        binding.profileUserAchievementLst.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getAchievements().observe(getViewLifecycleOwner(), new Observer<List<Achievement>>() {
            @Override
            public void onChanged(List<Achievement> achievements) {
                adapter.setData(achievements);
            }
        });

        setOnClick();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.appBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(CommunityUserProfileFragment.this);
            }
        });
    }

    @Override
    public void showPopupMenu(Achievement achievement, View button) {

    }
}