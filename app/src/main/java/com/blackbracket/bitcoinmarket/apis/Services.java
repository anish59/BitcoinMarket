package com.blackbracket.bitcoinmarket.apis;

import com.blackbracket.bitcoinmarket.helper.AppConstants;
import com.blackbracket.bitcoinmarket.model.CurrencyResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anish on 29-08-2017.
 */

public interface Services {
    @GET(AppConstants.TICKER)
    Call<CurrencyResponse> getAllBitcoinInfo();

    @GET(AppConstants.TO_BTC)
    Call<String> calculateCurrency(@Query("currency") String currency, @Query("value") long value);

}
