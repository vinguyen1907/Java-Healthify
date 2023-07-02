package com.example.javahealthify.ui.screens.admin_community_screen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Report;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminCommunityVM extends ViewModel {
    MutableLiveData<Integer> reportCount;
    MutableLiveData<ArrayList<Report>> pendingReportList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdminCommunityVM() {
        reportCount = new MutableLiveData<>(0);
        pendingReportList = new MutableLiveData<>();

        fetchReportCount();
        fetchPendingReportList();

    }


    public MutableLiveData<Integer> getReportCount() {
        return reportCount;
    }

    public void setReportCount(MutableLiveData<Integer> reportCount) {
        this.reportCount = reportCount;
    }

    public MutableLiveData<ArrayList<Report>> getPendingReportList() {
        return pendingReportList;
    }

    public void setPendingReportList(MutableLiveData<ArrayList<Report>> pendingReportList) {
        this.pendingReportList = pendingReportList;
    }
    private void fetchPendingReportList() {
    }
    public void fetchReportCount() {
        db.collection("count").document("reports_count").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Long count = documentSnapshot.getLong("count");
                    if(count != null) {
                        reportCount.postValue(count.intValue());
                    }
                }
            }
        });
    }
}
