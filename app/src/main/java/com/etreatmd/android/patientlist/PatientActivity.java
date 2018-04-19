package com.etreatmd.android.patientlist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PatientActivity extends SingleFragmentActivity {

    public static final String EXTRA_PATIENT_ID =
            "com.etreatmd.android.patientlist.patient_id";

    public static Intent newIntent(Context packageContext, String patientId) {
        Intent intent = new Intent(packageContext, PatientActivity.class);
        intent.putExtra(EXTRA_PATIENT_ID, patientId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String patientId = getIntent().getStringExtra(EXTRA_PATIENT_ID);
        return PatientFragment.newInstance(patientId);
    }
}
