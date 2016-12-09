package com.barrondata.android.api;

public interface HttpUrlManager {
    String ACCEPT       = "application/vnd.snizl+json; version=1.0.0-dev";
    String X_API_KEY    = "738214yxn1358345b435";
    String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=utf-8";
    String CLIENT_ID    = "9nClMh72c6uTrdk8lWSZUu0Pndll1lE2";

//  Base API urls
    String SERVER_URL          = "https://v5.snizl.com";
//    String SERVER_URL          = "https://area51.snizl.com";
    String FORGOT_PASSWORD_URL = "https://www.snizl.com#forgotPassword";

    String USERS_URL       = SERVER_URL + "/api/users";
    String HUBS_URL        = SERVER_URL + "/api/hubs";
    String BUSINESSES_URL  = SERVER_URL + "/api/businesses";
    String WALLET_URL      = SERVER_URL + "/api/wallet";
    String FEED_OFFERS_URL = SERVER_URL + "/api/feeds/offers";
    String FEED_EVENTS_URL = SERVER_URL + "/api/feeds/events";
    String OFFERS_URL      = SERVER_URL + "/api/offers";
    String SEARCH_URL      = SERVER_URL + "/api/search";
    String CLAIMS_URL      = SERVER_URL + "/api/claims";

//    API URLs
    String API_URL_OAUTH_ACCESS_TOKEN  = SERVER_URL + "/oauth/access_token";
    String API_URL_ONE_SIGNAL_TOKEN    = SERVER_URL + "/api/notifications/devicetoken";

    String API_URL_GET_CURRENT_USER    = USERS_URL  + "/current";
    String API_URL_GET_USER_BUSINESSES = USERS_URL  + "/user_id/businesses";
    String API_URL_GET_USER_CATEGORIES = USERS_URL  + "/user_id/categories";
    String API_URL_PUT_USER_HUB        = USERS_URL  + "/user_id/hub/hub_id";

    String API_URL_GET_CLOSEST_HUB     = HUBS_URL   + "/closest";

    String API_URL_DELETE_WALLET       = WALLET_URL + "/wallet_id";

    String API_URL_CLAIM               = CLAIMS_URL + "/claim";

    String API_URL_GET_CATEGORIES      = SERVER_URL + "/api/categories";

    String API_URL_GET_BUSINESS_OFFERS = BUSINESSES_URL + "/business_id/offers?type=offer|promo|comp";
    String API_URL_GET_BUSINESS_EVENTS = BUSINESSES_URL + "/business_id/events";
    String API_URL_FOLLOW_BUSINESS     = BUSINESSES_URL + "/business_id/follow";

    String API_URL_POST_PICTURE        = OFFERS_URL + "/offer_id/picture";

    // Request Method
    int POST_METHOD   = 0;
    int GET_METHOD    = 1;
    int PUT_METHOD    = 2;
    int DELETE_METHOD = 3;

//    Header Key
    String HEADER_ACCEPT = "ACCEPT";
    String HEADER_X_API_KEY = "X-API-KEY";
    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_AUTHORIZATION = "Authorization";

//    Body Key
    String BODY_USERNAME   = "username";
    String BODY_FIRST_NAME = "first_name";
    String BODY_LAST_NAME  = "last_name";
    String BODY_EMAIL      = "email";
    String BODY_PASSWORD   = "password";
    String BODY_CLIENT_ID  = "client_id";
    String BODY_GRANT_TYPE = "grant_type";
    String BODY_TYPE       = "type";
    String BODY_PREF       = "pref";
    String BODY_HUB        = "hub";
    String BODY_LAT        = "lat";
    String BODY_LONG       = "long";
    String BODY_OFFER_ID   = "offer_id";
    String BODY_EVENT_ID   = "event_id";
    String BODY_RANGE      = "range";
    String BODY_TOKEN      = "token";
    String BODY_FACEBOOK_ID = "facebook_id";
    String BODY_FACEBOOK_TOKEN = "facebook_token";
}
