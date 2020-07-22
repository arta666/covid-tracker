package com.arman.covidtracker;

import android.content.Context;

import com.arman.covidtracker.utils.DateConverter;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CheckDateTest {

    private static final String TODAY_DATE = "2020-07-22T00:00:00Z";
    private static final String YESTERDAY_DATE = "2020-07-20T00:00:00Z";
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void isSameDateTest() {

        boolean result = DateConverter.isToday(context,TODAY_DATE);

        assertTrue(result);
    }

    @Test
    public void isNotSameDate() {

        boolean result = DateConverter.isToday(context,YESTERDAY_DATE);

        assertTrue(result);
    }

}
