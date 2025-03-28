package com.ash.teacheron.viewmodel.teacherVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ash.teacheron.repository.teacherRep.saveStep5Repo;
import com.ash.teacheron.retrofit.model.ErrorData;
import com.ash.teacheron.retrofit.model.saveResponse;


public class step5VModel extends AndroidViewModel {

    private saveStep5Repo repository;
    public step5VModel(@NonNull Application application) {
        super(application);
        repository = new saveStep5Repo();
    }
    public LiveData<saveResponse> startLogin(String user_id, String fee_schedule, String fee_amount, String total_experience, String willing_to_travel, String available_for_online, String help_with_homework, String currently_employed, String interested_association, String fee_details, String currency_id,String token) {
        return repository.saveData(user_id,   fee_schedule,   fee_amount,   total_experience,   willing_to_travel,   available_for_online,   help_with_homework,   currently_employed,   interested_association,   fee_details,   currency_id, token);
    }
    public LiveData<ErrorData> getErrorMessage() {

        return repository.getErrorMessage();
    }
}
