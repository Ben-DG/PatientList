package com.etreatmd.android.patientlist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 4/18/2018.
 */

public class PatientLab {
    private static PatientLab sPatientLab;

    private List<Patient> mPatients;

    public static PatientLab get(Context context) {
        if (sPatientLab == null) {
            sPatientLab = new PatientLab(context);
        }
        return sPatientLab;
    }

    private PatientLab(Context context) {
        mPatients = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Patient patient = new Patient("John", Integer.toString(i));
            mPatients.add(patient);
        }
    }

    public List<Patient> getPatients() {
        return mPatients;
    }

    public Patient getPatient(String id) {
        for (Patient patient : mPatients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }

        return null;
    }
}
