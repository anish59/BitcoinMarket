package com.blackbracket.bitcoinmarket.apis;

import com.blackbracket.bitcoinmarket.helper.AppConstants;
import com.blackbracket.bitcoinmarket.model.CurrencyResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by anish on 29-08-2017.
 */

public interface Services {
    @GET(AppConstants.TICKER)
    Call<CurrencyResponse> getAllBitcoinInfo();
}
