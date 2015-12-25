package com.appgranula.mvvmsample;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgranula.mvvmsample.utils.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Belozerow on 11.10.2015.
 */
public class BindingAttributes {
    private static HashMap<String, Typeface> typefaceHashMap = new HashMap<>();

    @BindingAdapter({"bind:circleUrl"})
    public static void setCircleUrl(ImageView imageView, String url) {
        if (url == null)
            return;
        Picasso.with(imageView.getContext()).load(url).transform(new CircleTransformation()).into(imageView);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        if (url == null)
            return;
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("bind:customFont")
    public static void setCustomFont(TextView textView, String font) {
        Typeface tf = typefaceHashMap.get(font);
        if (tf == null) {
            tf = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + font);
            typefaceHashMap.put(font, tf);
        }
        textView.setTypeface(tf);
    }
}
