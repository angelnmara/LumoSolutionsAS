package com.cepadem.lumosolutionsas.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cepadem.lumosolutionsas.Models.MenuImg;
import com.cepadem.lumosolutionsas.R;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public void guardaShared(Activity activity, int variable, String valor){
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(variable), valor);
        editor.commit();
    }
    public void removeShared(Activity activity, int variable){
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sharedPref.edit().remove(activity.getString(variable)).commit();
    }
    public List<MenuImg> GetMenuImg(){
        List<MenuImg> menus = new ArrayList<>();
        menus.add(new MenuImg(152,R.drawable.ic_face_agent));
        menus.add(new MenuImg(157,R.drawable.ic_file_check));
        menus.add(new MenuImg(153,R.drawable.ic_cash_usd));
        menus.add(new MenuImg(156,R.drawable.ic_file_account));
        menus.add(new MenuImg(159,R.drawable.ic_seatbelt));
        menus.add(new MenuImg(160,R.drawable.ic_gavel));
        menus.add(new MenuImg(155,R.drawable.ic_file_document));
        menus.add(new MenuImg(209,R.drawable.ic_file_chart));
        menus.add(new MenuImg(169,R.drawable.ic_crosshairs_gps));

        return menus;
    }
}
