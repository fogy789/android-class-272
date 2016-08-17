package com.example.user.simpleui;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
public class Order {
    String note;
    String storeInfo;
//    String drink;
    List<DrinkOrder> drinkOrderList;

    public int getTotal()
    {
        int total = 0;
        for (DrinkOrder drinkOrder : drinkOrderList)
        {
            total += drinkOrder.getLNumber()*drinkOrder.getDrink().getlPrice() + drinkOrder.getmNumber()*drinkOrder.getDrink().getmPrice();
        }

        return total;
    }
}
