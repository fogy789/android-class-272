package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
@ParseClassName("Order")
public class Order extends ParseObject implements Parcelable{
//    String note;
//    String storeInfo;
    static final String NOTE_COL = "note";
    static final String STOREINFO_COL ="storeInfo";
    final static String DRINKORDERS_COL = "drinkOrderList";

//    String drink;
//    List<DrinkOrder> drinkOrderList;
    public Order()
    {

    }

    public int getTotal()
    {
        int total = 0;
        for (DrinkOrder drinkOrder : getDrinkOrderList())
        {
            total += drinkOrder.getLNumber()*drinkOrder.getDrink().getlPrice() + drinkOrder.getmNumber()*drinkOrder.getDrink().getmPrice();
        }

        return total;
    }

    public String getNote() {
        return getString(NOTE_COL);
    }

    public void setNote(String note) {
        this.put(NOTE_COL, note);
    }

    public String getStoreInfo() {
        return getString(STOREINFO_COL);
    }

    public void setStoreInfo(String storeInfo) {
        this.put(STOREINFO_COL, storeInfo);
    }

    public List<DrinkOrder> getDrinkOrderList() {
        return getList(DRINKORDERS_COL);
    }

    public void setDrinkOrderList(List<DrinkOrder> drinkOrderList) {
        this.put(DRINKORDERS_COL, drinkOrderList);
    }

    public static ParseQuery<Order> getQuery()
    {
        return ParseQuery.getQuery(Order.class).include(DRINKORDERS_COL).include(DRINKORDERS_COL + "." + DrinkOrder.DRINK_COL);
    }

    public static void getOrderFromLocalThenRemote(final FindCallback<Order> callback)
    {
        getQuery().fromLocalDatastore().findInBackground(callback);
        getQuery().findInBackground(new FindCallback<Order>() {
            @Override
            public void done(List<Order> list, ParseException e) {
                if (e == null)
                    pinAllInBackground("Order", list);
                callback.done(list, e);
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if(getObjectId() == null)
        {
            dest.writeInt(0);
            dest.writeString(this.getNote());
            dest.writeString(this.getStoreInfo());
//            dest.writeParcelableArray((Parcelable[]) getDrinkOrderList().toArray(), flags);
            dest.writeTypedList(getDrinkOrderList());
        }
        else
        {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }
    }

    protected Order(Parcel in) {
        super();
        this.setNote(in.readString());
        this.setStoreInfo(in.readString());
        ArrayList<DrinkOrder> drinkOrders = new ArrayList<>();
        in.readTypedList(drinkOrders, DrinkOrder.CREATOR);
//        this.setDrinkOrderList(Arrays.asList((DrinkOrder[]) in.readArray(DrinkOrder.class.getClassLoader())));
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {

            int isfromRemote = source.readInt();
            if(isfromRemote == 0)
            {
                return new Order(source);
            }
            else
            {
                String objectId = source.readString();
                return getOrderFromCahe(objectId);
            }
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public static Order getOrderFromCahe(String objectId)
    {
        try {
            return getQuery().fromLocalDatastore().get(objectId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Order.createWithoutData(Order.class, objectId);
    }
}
