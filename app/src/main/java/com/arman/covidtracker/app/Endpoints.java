package com.arman.covidtracker.app;

public class Endpoints {
    public static final String BASE_URL = "http://api.coronatracker.com";

    public static final String GET_GLOBAL_STATUS = "/v3/stats/worldometer/global";

    public static final String GET_COUNTRIES = "/v2/analytics/country";

    public static final String GET_LIVE_BY_COUNTRY= "/total/country/{countryName}";
}
