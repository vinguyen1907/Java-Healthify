package com.example.javahealthify.ui.screens.onboarding;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.OnboardingAdapter;
import com.example.javahealthify.databinding.FragmentOnboardingBinding;

public class OnboardingFragment extends Fragment {
    private FragmentOnboardingBinding binding;
    private OnboardingVM viewModel;

    private int currentPosition = 0;
    private TextView[] dots;
    private OnboardingAdapter onboardingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false);

        // Initialize the ViewModel
        viewModel = new OnboardingVM();
        binding.setOnboardingVM(viewModel);

        // Set pager adapter for onboarding images
        onboardingAdapter = new OnboardingAdapter(requireContext(), viewModel);
        binding.onboardingViewPager.setAdapter(onboardingAdapter);

        setOnClick();

        setUpIndicatorIndex(0);

        binding.onboardingViewPager.addOnPageChangeListener(onPageChangeListener);

        // Observe title and subtitle LiveData from ViewModel
        viewModel.getTitleLiveData().observe(getViewLifecycleOwner(), title -> binding.titleTxtView.setText(getString(title)));
        viewModel.getSubtitleLiveData().observe(getViewLifecycleOwner(), subtitle -> binding.subtitleTv.setText(getString(subtitle)));

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.skipBtn.setOnClickListener(v -> navigateToSignUpScreen());

        binding.nextBtn.setOnClickListener(v ->
                binding.onboardingViewPager.setCurrentItem(currentPosition + 1, true));

        binding.submitBtn.setOnClickListener(v -> navigateToSignUpScreen());
    }

    private void setUpIndicatorIndex(int index) {
        dots = new TextView[3];
        binding.indicatorLayout.removeAllViews();

        currentPosition = index;

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(requireContext());
            dots[i].setText(HtmlCompat.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35f);
            dots[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.indicatorInactiveColor));
            binding.indicatorLayout.addView(dots[i]);
        }

        dots[index].setTextColor(ContextCompat.getColor(requireContext(), R.color.indicatorActiveColor));
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Empty implementation
        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicatorIndex(position);

            viewModel.updateTitleAndSubtitle(position);

            currentPosition = position;

            if (position == onboardingAdapter.getCount() - 1) {
                binding.nextBtn.setVisibility(View.INVISIBLE);
                binding.skipBtn.setVisibility(View.INVISIBLE);
                binding.submitBtn.setVisibility(View.VISIBLE);
            } else {
                binding.nextBtn.setVisibility(View.VISIBLE);
                binding.skipBtn.setVisibility(View.VISIBLE);
                binding.submitBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Empty implementation
        }
    };

    private void navigateToSignUpScreen() {
        NavHostFragment.findNavController(this).navigate(R.id.signUpFragment);
    }
}