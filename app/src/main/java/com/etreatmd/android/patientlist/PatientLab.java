package com.etreatmd.android.patientlist;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PatientLab {
    private static PatientLab sPatientLab;

    private Context mContext;
    private List<Patient> mPatients;

    public static PatientLab get(Context context) {
        if (sPatientLab == null) {
            sPatientLab = new PatientLab(context);
        }
        return sPatientLab;
    }

    private PatientLab(Context context) {
        mPatients = new ArrayList<>();
        mContext = context.getApplicationContext();
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

    public File getPhotoFile(Patient patient) {
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, patient.getPhotoFilename());
    }

    public void addPatients(List<Patient> patients) {
        mPatients.addAll(patients);
    }
}
