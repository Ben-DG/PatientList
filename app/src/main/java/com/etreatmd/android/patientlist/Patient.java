package com.etreatmd.android.patientlist;

/**
 * Created by Ben on 4/18/2018.
 */

public class Patient {

    private String mId;
    private String mName;

    public Patient(String name, String id) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId() + ".jpg";
    }
}
