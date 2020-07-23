package com.arman.covidtracker.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {

    public static void load(ImageView view,String path){
        Picasso.get().load(path).into(view);
    }
}
