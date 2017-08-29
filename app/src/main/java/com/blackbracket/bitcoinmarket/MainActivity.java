package com.blackbracket.bitcoinmarket;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackbracket.bitcoinmarket.adapter.CurrencyItemAdapter;
import com.blackbracket.bitcoinmarket.apis.Services;
import com.blackbracket.bitcoinmarket.model.Countries;
import com.blackbracket.bitcoinmarket.model.CurrencyResponse;
import com.blackbracket.bitcoinmarket.model.USD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.RecyclerView rvCurrencyItems;
    private android.widget.LinearLayout layoutCountries;
    private android.widget.TextView txtAskValue;
    private android.widget.TextView txtAnsValue;
    private android.widget.TextView txtAskBuyRate;
    private android.widget.TextView txtAnsBuyRate;
    private android.widget.LinearLayout layoutBuy;
    private android.widget.TextView txtAnsSaleRate;
    private android.widget.ImageView imgQuestion;
    private android.widget.TextView txtBitCoinInfo;
    private android.support.design.widget.FloatingActionButton fabSend;
    private android.widget.LinearLayout layoutQuestion;
    private android.widget.LinearLayout layoutInfo;
    private Context context;
    private CurrencyItemAdapter currencyItemAdapter;
    private Services services = AppApplication.getRetrofit().create(Services.class);
    private CurrencyItemAdapter.OnCountryItemClickedListener clickedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initViews();
        init();
        initListeners();
    }

    private void initListeners() {
       /* clickedListener = new CurrencyItemAdapter.OnCountryItemClickedListener() {
            @Override
            public void onClick(Countries country, String currencyName) {
                txtAskValue.setText(getString(R.string.one_bitcoin_in, currencyName));
                txtAnsValue.setText("Value:\n " + country.get15m() + " " + country.getSymbol());
                txtAnsBuyRate.setText("Buy rate:\n " + country.getBuy() + " " + country.getSymbol());
                txtAnsSaleRate.setText("Sale rate:\n " + country.getSell() + " " + country.getSymbol());
            }
        };*/
    }

    private void init() {
        initAdapter();
    }

    private void initAdapter() {
        currencyItemAdapter = new CurrencyItemAdapter(context, new CurrencyItemAdapter.OnCountryItemClickedListener() {
            @Override
            public void onClick(Countries country, String currencyName) {
                txtAskValue.setText(getString(R.string.one_bitcoin_in, currencyName));
                txtAnsValue.setText("Value:\n " + country.get15m() + " " + country.getSymbol());
                txtAnsBuyRate.setText("Buy rate:\n " + country.getBuy() + " " + country.getSymbol());
                txtAnsSaleRate.setText("Sale rate:\n " + country.getSell() + " " + country.getSymbol());
            }
        });
        rvCurrencyItems.setLayoutManager(new LinearLayoutManager(context));
        rvCurrencyItems.setAdapter(currencyItemAdapter);
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        layoutInfo = (LinearLayout) findViewById(R.id.layoutInfo);
        layoutQuestion = (LinearLayout) findViewById(R.id.layoutQuestion);
        fabSend = (FloatingActionButton) findViewById(R.id.fabSend);
        txtBitCoinInfo = (TextView) findViewById(R.id.txtBitCoinInfo);
        imgQuestion = (ImageView) findViewById(R.id.imgQuestion);
        txtAnsSaleRate = (TextView) findViewById(R.id.txtAnsSaleRate);
        layoutBuy = (LinearLayout) findViewById(R.id.layoutBuy);
        txtAnsBuyRate = (TextView) findViewById(R.id.txtAnsBuyRate);
        txtAskBuyRate = (TextView) findViewById(R.id.txtAskBuyRate);
        txtAnsValue = (TextView) findViewById(R.id.txtAnsValue);
        txtAskValue = (TextView) findViewById(R.id.txtAskValue);
        layoutCountries = (LinearLayout) findViewById(R.id.layoutCountries);
        rvCurrencyItems = (RecyclerView) findViewById(R.id.rvCurrencyItems);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callService();
    }

    private void callService() {
        final List<Countries> countries = new ArrayList<>();
        services.getAllBitcoinInfo().enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    countries.add(response.body().getUSD());
                    countries.add(response.body().getiNR());
                    countries.add(response.body().getAUD());
                    countries.add(response.body().getBRL());
                    countries.add(response.body().getCAD());
                    countries.add(response.body().getCHF());
                    countries.add(response.body().getCLP());
                    countries.add(response.body().getCNY());
                    countries.add(response.body().getDKK());
                    countries.add(response.body().getEUR());
                    countries.add(response.body().getGBP());
                    countries.add(response.body().getHKD());
                    countries.add(response.body().getISK());
                    countries.add(response.body().getJPY());
                    countries.add(response.body().getKRW());
                    countries.add(response.body().getNZD());
                    countries.add(response.body().getPLN());
                    countries.add(response.body().getPLN());
                    countries.add(response.body().getRUB());
                    countries.add(response.body().getSEK());
                    countries.add(response.body().getSGD());
                    countries.add(response.body().getTHB());
                    countries.add(response.body().getTWD());

                    if (!countries.isEmpty()) {
                        rvCurrencyItems.setItemViewCacheSize(countries.size());
                        currencyItemAdapter.setItemsAndNotify(countries);
                    }
                    txtAskValue.setText(getString(R.string.one_bitcoin_in, USD.class.getSimpleName()));
                    txtAnsValue.setText("Value:\n " + response.body().getUSD().get15m() + " " + response.body().getUSD().getSymbol());
                    txtAnsBuyRate.setText("Buy rate:\n " + response.body().getUSD().getBuy() + " " + response.body().getUSD().getSymbol());
                    txtAnsSaleRate.setText("Sale rate:\n " + response.body().getUSD().getSell() + " " + response.body().getUSD().getSymbol());
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {

            }
        });
    }
}
