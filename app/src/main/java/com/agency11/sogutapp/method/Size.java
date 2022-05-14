package com.agency11.sogutapp.method;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Size {

    int dpWidth;
    int dpHeight;
    float density1;
    int designWidth = 375;
    int designHeight = 812;
    Context mContext;

    public Size(Context mContext){
        this.mContext = mContext;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels;
        dpWidth = displayMetrics.widthPixels;
        density1 = displayMetrics.density;
    }

    public void setSize(TextView textView, int size){
        textView.setTextSize(calcSize(size));
    }

    public void setPadding(View view, int left,int top,int right,int bottom){
        view.setPadding(calcWidth(left),calcWidth(top),calcWidth(right),calcWidth(bottom));
    }

    public void setPaddingHeight(View view, int left,int top,int right,int bottom){
        view.setPadding(calcHeight(left),calcHeight(top),calcHeight(right),calcHeight(bottom));
    }

    public void setPadding2(View view, int left,int top,int right,int bottom){
        view.setPadding(calcHeight(left),calcHeight(top),calcHeight(right),calcHeight(bottom));
    }

    public void setMargin2(View view, int left, int top, int right, int bottom){
        ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        marginLayoutParams.setMargins(calcSize(left),calcSize(top),calcSize(right),calcSize(bottom));
    }

    public void setWidth(View view,int width){
        view.getLayoutParams().width = calcWidth(width);
    }

    public void setHeight(View view, int height){
        view.getLayoutParams().height = calcWidth(height);
    }

    public void setRealHeight(View view, int height){
        view.getLayoutParams().height = calcHeight(height);
    }

    public void setMargin(View view, int left, int top, int right, int bottom){
        ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        marginLayoutParams.setMargins(calcWidth(left),calcWidth(top),calcWidth(right),calcWidth(bottom));
    }

    int calcHeight(float value){
        return (int) (dpHeight *(value/designHeight) );
    }

    int calcWidth(float value){
        return (int) ((dpWidth *(value/designWidth) ));
    }

    int calcSize(float value){
        return (int)  ((dpWidth *(value/designWidth) / density1)) ;
    }

    int calcSize2(float value){
        return (int)  ((dpHeight *(value/designHeight) / density1)) ;
    }

}