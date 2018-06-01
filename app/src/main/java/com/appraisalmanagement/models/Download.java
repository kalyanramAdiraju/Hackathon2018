package com.appraisalmanagement.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nineleaps on 31/5/18.
 */

public class Download implements Parcelable {

    private double progress;
    private int currentFileSize;
    private double totalFileSize;

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public double getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(double totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public static Creator<Download> getCREATOR() {
        return CREATOR;
    }

    public Download() {
        // do nothing

    }

    protected Download(Parcel in) {
        progress=in.readDouble();
        currentFileSize=in.readInt();
        totalFileSize= in.readDouble();
    }

    public static final Creator<Download> CREATOR = new Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel in) {
            return new Download(in);
        }

        @Override
        public Download[] newArray(int size) {
            return new Download[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(progress);
        parcel.writeInt(currentFileSize);
        parcel.writeDouble(totalFileSize);
    }
}
