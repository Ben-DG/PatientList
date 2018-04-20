package com.etreatmd.android.patientlist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by Ben on 4/18/2018.
 */

public class PatientFragment extends Fragment {

    private static final String ARG_PATIENT_ID = "patient_id";
    private static final int REQUEST_PHOTO = 0;

    private Patient mPatient;
    private File mPhotoFile;
    private ImageButton mPictureButton;

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
        mPhotoFile = PatientLab.get(getActivity()).getPhotoFile(mPatient);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient, container, false);

        TextView nameTextView = (TextView) v.findViewById(R.id.name_text_view);
        TextView idTextView = (TextView) v.findViewById(R.id.id_text_view);
        String nameText = "Name: " + mPatient.getName();
        nameTextView.setText(nameText);
        String idText = "Id: " + mPatient.getId();
        idTextView.setText(idText);

        PackageManager packageManager = getActivity().getPackageManager();

        mPictureButton = (ImageButton) v.findViewById(R.id.picture_button);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPictureButton.setEnabled(canTakePhoto);

        mPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.etreatmd.android.patientlist.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        updatePictureButton();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.etreatmd.android.patientlist.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePictureButton();
        }
    }

    private void updatePictureButton() {
        if (mPhotoFile != null && mPhotoFile.exists()) {
            Bitmap bitmap = PictureUtils.getScaledBitMap(
                    mPhotoFile.getPath(), getActivity());
            mPictureButton.setImageBitmap(bitmap);
            mPictureButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

    }
}
