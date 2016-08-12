package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/8/11.
 */
public class Drink implements Parcelable {
    String name;
    int mPrice;
    int lPrice;
    int imageId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.mPrice);
        dest.writeInt(this.lPrice);
        dest.writeInt(this.imageId);
    }

    public Drink() {
    }

    protected Drink(Parcel in) {
        this.name = in.readString();
        this.mPrice = in.readInt();
        this.lPrice = in.readInt();
        this.imageId = in.readInt();
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel source) {
            return new Drink(source);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };
}
