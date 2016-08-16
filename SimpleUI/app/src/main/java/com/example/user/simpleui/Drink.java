package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/11.
 */
@ParseClassName("Drink")
public class Drink extends ParseObject implements Parcelable {
//    String name;
//    int mPrice;
//    int lPrice;
    static final String NAME_COL = "name";
    static final String MPRICE_COL = "mPrice";
    static final String LPRICE_COL = "lPrice";
    int imageId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeInt(this.getlPrice());
        dest.writeInt(this.getmPrice());
        dest.writeInt(this.imageId);
    }

    public Drink() {
    }

    protected Drink(Parcel in) {
        this.setName(in.readString());
        this.setmPrice(in.readInt());
        this.setlPrice(in.readInt());
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

    public String getName() {
        return getString(NAME_COL);
    }

    public void setName(String name) {
        this.put(NAME_COL, name);
    }

    public int getmPrice() {
        return getInt(MPRICE_COL);
    }

    public void setmPrice(int mPrice) {
        this.put(MPRICE_COL, mPrice);
    }

    public int getlPrice() {
        return getInt(LPRICE_COL);
    }

    public void setlPrice(int lPrice) {
        this.put(LPRICE_COL, lPrice);
    }
}
