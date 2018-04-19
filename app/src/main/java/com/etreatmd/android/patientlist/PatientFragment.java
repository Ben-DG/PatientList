package com.etreatmd.android.patientlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ben on 4/18/2018.
 */

public class PatientFragment extends Fragment {

    private static final String ARG_PATIENT_ID = "patient_id";

    private Patient mPatient;
    private Button mPictureButton;

    public static PatientFragment newInstance(String patientId) {
        Bundle args = new Bundle();
        args.putCharSequence(ARG_PATIENT_ID, patientId);

        PatientFragment fragment = new PatientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPatient = new Patient("TEMP", "TEMP");
        String patientId = getArguments().getString(ARG_PATIENT_ID);
        mPatient = PatientLab.get(getActivity()).getPatient(patientId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient, container, false);

        TextView nameTextView = (TextView) v.findViewById(R.id.name_text_view);
        TextView idTextView = (TextView) v.findViewById(R.id.id_text_view);
        nameTextView.setText(mPatient.getName());
        idTextView.setText(mPatient.getId());

        mPictureButton = (Button) v.findViewById(R.id.picture_button);
        mPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });

        return v;
    }
}
