package com.blackbracket.bitcoinmarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackbracket.bitcoinmarket.adapter.CurrencyItemAdapter;
import com.blackbracket.bitcoinmarket.apis.Services;
import com.blackbracket.bitcoinmarket.dialogs.BitCoinCalculatorDialog;
import com.blackbracket.bitcoinmarket.dialogs.SwipeIntroDialog;
import com.blackbracket.bitcoinmarket.helper.AppConstants;
import com.blackbracket.bitcoinmarket.helper.FunctionHelper;
import com.blackbracket.bitcoinmarket.helper.PrefsUtil;
import com.blackbracket.bitcoinmarket.model.Countries;
import com.blackbracket.bitcoinmarket.model.CurrencyResponse;
import com.blackbracket.bitcoinmarket.model.USD;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

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
    private android.support.v7.widget.Toolbar toolbar;
    private LinearLayout layoutContainer;
    private android.support.v4.widget.SwipeRefreshLayout layoutSwipe;
    private int selectedPosition = 0;
    private String selectedCurrency = USD.class.getSimpleName();
    InterstitialAd mInterstitialAd;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (PrefsUtil.isSplashShown(context)) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            initViews();
            init();
            initListeners();
        } else {
            startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
            finish();
        }
    }

    private void initListeners() {
        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (FunctionHelper.isConnectedToInternet(context)) {
                    initApiCalling();
                } else {
                    FunctionHelper.showAlertDialogWithOneOpt(context, "Please connect to internet", new FunctionHelper.DialogOptionsSelectedListener() {
                        @Override
                        public void onSelect(boolean isYes) {
                            onResume();
                        }

                        @Override
                        public void onDialogBackClick() {
                            finish();
                        }
                    }, "Try Again!");
                }
            }
        });

        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.BlackBracketBlog));
                startActivity(browserIntent);*/

                if (FunctionHelper.isConnectedToInternet(context)) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    builder.setToolbarColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    builder.enableUrlBarHiding();
                    builder.setStartAnimations(context, R.anim.slide_in_top, R.anim.slide_out_top);
                    builder.setExitAnimations(context, R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    customTabsIntent.launchUrl(MainActivity.this, Uri.parse(AppConstants.BlackBracketBlog));
                } else {
                    FunctionHelper.showAlertDialogWithOneOpt(context, getString(R.string.connect_to_iternet), new FunctionHelper.DialogOptionsSelectedListener() {
                        @Override
                        public void onSelect(boolean isYes) {
                            //nothing yet
                        }

                        @Override
                        public void onDialogBackClick() {
                        }
                    }, "Ok");
                }
            }
        });
    }

    private void init() {
        initAdapter();
//        services.calculateCurrency("USD", 5000).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful() && response.body() != null) {
////                    Toast.makeText(context, "Value:-> " + response.body(), Toast.LENGTH_SHORT).show();
//                }
//            }

//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, "Failed ", Toast.LENGTH_SHORT).show();
//                Log.e("err", t.getMessage());
//            }
//        });
        mInterstitialAd = new InterstitialAd(this);
    }

    private void initAdapter() {
        currencyItemAdapter = new CurrencyItemAdapter(context, new CurrencyItemAdapter.OnCountryItemClickedListener() {
            @Override
            public void onClick(Countries country, String currencyName, int position) {
                txtAskValue.setText(getString(R.string.one_bitcoin_in, currencyName));
                txtAnsValue.setText("Value:\n " + country.get15m() + " " + country.getSymbol());
                txtAnsBuyRate.setText("Buy rate:\n " + country.getBuy() + " " + country.getSymbol());
                txtAnsSaleRate.setText("Sale rate:\n " + country.getSell() + " " + country.getSymbol());
                toolbar.setTitle(country.getFullForm());
                selectedPosition = position;
                selectedCurrency = currencyName;
            }
        });
        rvCurrencyItems.setLayoutManager(new LinearLayoutManager(context));
        rvCurrencyItems.setAdapter(currencyItemAdapter);
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        layoutSwipe = (SwipeRefreshLayout) findViewById(R.id.layoutSwipe);
        layoutContainer = (LinearLayout) findViewById(R.id.layoutContainer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        FunctionHelper.initToolbar(MainActivity.this, toolbar, "United States Dollars ", "");
        applyFontForToolbarTitle(context, toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FunctionHelper.isConnectedToInternet(context)) {
            if (PrefsUtil.isIntroShown(context)) {
                initApiCalling();
            } else {

                new SwipeIntroDialog(context, new SwipeIntroDialog.OnIntroDismissListener() {
                    @Override
                    public void onDismissed() {
                        PrefsUtil.setIntroStatus(context, true);
                        initApiCalling();
                    }
                });

            }
        } else {
            FunctionHelper.showAlertDialogWithOneOpt(context, getString(R.string.connect_to_iternet), new FunctionHelper.DialogOptionsSelectedListener() {
                @Override
                public void onSelect(boolean isYes) {
                    onResume();
                }

                @Override
                public void onDialogBackClick() {
                    finish();
                }
            }, "Try Again!");
        }
    }

    private void initApiCalling() {


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                callService();
            }
        };

        handler.postDelayed(runnable, 0);

    }

    private void callService() {
        final List<Countries> countries = new ArrayList<>();
        layoutSwipe.setRefreshing(true);
        services.getAllBitcoinInfo().enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                layoutSwipe.setRefreshing(false);
                Countries usd, aud, brl, cad, chf, clp, cny, dkk, eur, gbp, hkd, inr, isk, jpy, krw, nzd, pln, rub, sek, sgd, thb, twd;

                if (response.isSuccessful() && response.body() != null) {
                    usd = response.body().getUSD();
                    usd.setFullForm(AppConstants.USD);

                    inr = response.body().getiNR();
                    inr.setFullForm(AppConstants.INR);

                    aud = response.body().getAUD();
                    aud.setFullForm(AppConstants.AUD);

                    brl = response.body().getBRL();
                    brl.setFullForm(AppConstants.BRL);

                    cad = response.body().getCAD();
                    cad.setFullForm(AppConstants.CAD);

                    chf = response.body().getCHF();
                    chf.setFullForm(AppConstants.CHF);

                    clp = response.body().getCLP();
                    clp.setFullForm(AppConstants.CLP);

                    cny = response.body().getCNY();
                    cny.setFullForm(AppConstants.CNY);

                    dkk = response.body().getDKK();
                    dkk.setFullForm(AppConstants.DKK);

                    eur = response.body().getEUR();
                    eur.setFullForm(AppConstants.EUR);

                    gbp = response.body().getGBP();
                    gbp.setFullForm(AppConstants.GBP);

                    hkd = response.body().getHKD();
                    hkd.setFullForm(AppConstants.HKD);

                    isk = response.body().getISK();
                    isk.setFullForm(AppConstants.ISK);

                    jpy = response.body().getJPY();
                    jpy.setFullForm(AppConstants.JPY);

                    krw = response.body().getKRW();
                    krw.setFullForm(AppConstants.KRW);

                    nzd = response.body().getNZD();
                    nzd.setFullForm(AppConstants.NZD);

                    pln = response.body().getPLN();
                    pln.setFullForm(AppConstants.PLN);

                    rub = response.body().getRUB();
                    rub.setFullForm(AppConstants.RUB);

                    sek = response.body().getSEK();
                    sek.setFullForm(AppConstants.SEK);

                    sgd = response.body().getSGD();
                    sgd.setFullForm(AppConstants.SGD);

                    thb = response.body().getTHB();
                    thb.setFullForm(AppConstants.THB);

                    twd = response.body().getTWD();
                    twd.setFullForm(AppConstants.TWD);

                    countries.add(usd);
                    countries.add(inr);
                    countries.add(aud);
                    countries.add(brl);
                    countries.add(cad);
                    countries.add(chf);
                    countries.add(clp);
                    countries.add(cny);
                    countries.add(dkk);
                    countries.add(eur);
                    countries.add(gbp);
                    countries.add(hkd);
                    countries.add(isk);
                    countries.add(jpy);
                    countries.add(krw);
                    countries.add(nzd);
                    countries.add(pln);
                    countries.add(rub);
                    countries.add(sek);
                    countries.add(sgd);
                    countries.add(thb);
                    countries.add(twd);

                    if (!countries.isEmpty()) {
                        rvCurrencyItems.setItemViewCacheSize(countries.size());
                        currencyItemAdapter.setItemsAndNotify(countries);
                    }
                    txtAskValue.setText(getString(R.string.one_bitcoin_in, selectedCurrency));
                   txtAnsValue.setText("Value:\n " + countries.get(selectedPosition).get15m() + " " + countries.get(selectedPosition).getSymbol());
                    txtAnsBuyRate.setText("Buy rate:\n " + countries.get(selectedPosition).getBuy() + " " + countries.get(selectedPosition).getSymbol());
                    txtAnsSaleRate.setText("Sale rate:\n " + countries.get(selectedPosition).getSell() + " " + countries.get(selectedPosition).getSymbol());

                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 10000);
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                layoutSwipe.setRefreshing(false);
                FunctionHelper.showAlertDialogWithOneOpt(context, "Oops!, there seems to be some issue with the server. Please try again later..!", new FunctionHelper.DialogOptionsSelectedListener() {
                    @Override
                    public void onSelect(boolean isYes) {
                        finish();
                    }

                    @Override
                    public void onDialogBackClick() {
                        //
                    }
                }, "Okay");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionCalculateBitCoin:
                new BitCoinCalculatorDialog(context);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void applyFontForToolbarTitle(Context context, Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.
                        createFromAsset(context.getAssets(), "ConcertOneRegular.ttf");
                if (tv.getText().equals(toolbar.getTitle())) {
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("F9A8C7909D2B12226B0AEA937B0B8EAC")/*please remove me when you want to upload app*/
                .build();
        // Load ads into Interstitial Adssss
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
//            finish();
        }
    }
}

/*
      //the time is in miliseconds*/
