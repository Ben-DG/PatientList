package com.etreatmd.android.patientlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientListFragment extends Fragment {

    private static final String TAG = "PatientListFragment";

    private ListView mPatientListView;
    private PatientAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestQueue myRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.myjson.com/bins/uj26j";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        List<Patient> patients = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Patient patient = new Patient(jsonObject.getString("name"),
                                        jsonObject.getString("id"));
                                patients.add(patient);
                            } catch (JSONException e) {
                                Log.d(TAG, "Failed to parse data", e);
                            }

                        }
                        PatientLab.get(getActivity()).addPatients(patients);
                        updateUI();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Failed to GET data", error);
                    }
                });

        myRequestQueue.add(jsonArrayRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);

        mPatientListView = (ListView) view.findViewById(R.id.patient_list_view);

        updateUI();

        mPatientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient) parent.getItemAtPosition(position);
                Intent intent = PatientActivity.newIntent(getActivity(), patient.getId());
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateUI() {
        PatientLab patientLab = PatientLab.get(getActivity());
        List<Patient> patients = patientLab.getPatients();

        mAdapter = new PatientAdapter(getActivity(), patients);
        mPatientListView.setAdapter(mAdapter);
    }

    private class PatientAdapter extends ArrayAdapter<Patient> {
        private final Context mContext;
        private List<Patient> mPatients;

        public PatientAdapter(Context context, List<Patient> patients) {
            super(context, -1, patients);
            mContext = context;
            mPatients = patients;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_patient_item, parent, false);
            TextView nameTextView = (TextView) rowView.findViewById(R.id.patient_name);
            TextView idTextView = (TextView) rowView.findViewById(R.id.patient_id);
            nameTextView.setText(mPatients.get(position).getName());
            idTextView.setText(mPatients.get(position).getId());

            return rowView;
        }
    }
}
