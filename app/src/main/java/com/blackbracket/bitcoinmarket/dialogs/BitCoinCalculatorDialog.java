package com.blackbracket.bitcoinmarket.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blackbracket.bitcoinmarket.AppApplication;
import com.blackbracket.bitcoinmarket.R;
import com.blackbracket.bitcoinmarket.apis.Services;
import com.blackbracket.bitcoinmarket.helper.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ldoublem.loadingviewlib.view.LVGhost;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anish on 9/2/2017.
 */

public class BitCoinCalculatorDialog extends Dialog {
    private Context context;
    private android.widget.EditText edtAmount;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spinnerSearchable;
    private android.widget.Button btnConvert;
    private android.widget.TextView txtResult;
    private List<String> currencies;
    private Services services = AppApplication.getRetrofit().create(Services.class);

    public BitCoinCalculatorDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
        initListeners();
    }

    private void initListeners() {
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndCallService();
            }
        });
    }

    private void checkAndCallService() {
        String amount = edtAmount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(context, "Please enter proper amount", Toast.LENGTH_SHORT).show();
            return;
        }
        String selectedCurrency = spinnerSearchable.getSelectedItem().toString();
        if (TextUtils.isEmpty(selectedCurrency)) {
            Toast.makeText(context, "Please select currency", Toast.LENGTH_SHORT).show();
            return;
        }
        double enterdAmount = Double.parseDouble(amount);
        BigDecimal bigDecimal = BigDecimal.valueOf(enterdAmount);

        System.out.println("Amount: " + amount);
        services.calculateCurrency(selectedCurrency, amount).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String value = response.body();
                    txtResult.setText(value.toString());
                    System.out.println("Value: " + value.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Failed ", Toast.LENGTH_SHORT).show();
                Log.e("err", t.getMessage());
            }
        });

    }


    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bitcoin_calculator, null, false);
        txtResult = (TextView) view.findViewById(R.id.txtResult);
        btnConvert = (Button) view.findViewById(R.id.btnConvert);
        spinnerSearchable = (SearchableSpinner) view.findViewById(R.id.spinnerSearchable);
        edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        setDialogProps(view);

        spinnerSearchable.setTitle("Select Currency");
        spinnerSearchable.setPositiveButton("OK");

        currencies = new ArrayList<>();
        addCurrencies();
        setSearchableSpinner();

        show();

    }

    private void setSearchableSpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, currencies); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearchable.setAdapter(spinnerArrayAdapter);
    }

    private void addCurrencies() {
        currencies.add(AppConstants.mUSD);
        currencies.add(AppConstants.mINR);
        currencies.add(AppConstants.mAUD);
        currencies.add(AppConstants.mBRL);
        currencies.add(AppConstants.mCAD);
        currencies.add(AppConstants.mCHF);
        currencies.add(AppConstants.mCLP);
        currencies.add(AppConstants.mCNY);
        currencies.add(AppConstants.mDKK);
        currencies.add(AppConstants.mEUR);
        currencies.add(AppConstants.mGBP);
        currencies.add(AppConstants.mHKD);
        currencies.add(AppConstants.mISK);
        currencies.add(AppConstants.mJPY);
        currencies.add(AppConstants.mKRW);
        currencies.add(AppConstants.mNZD);
        currencies.add(AppConstants.mPLN);
        currencies.add(AppConstants.mRUB);
        currencies.add(AppConstants.mSEK);
        currencies.add(AppConstants.mSGD);
        currencies.add(AppConstants.mTHB);
        currencies.add(AppConstants.mTWD);

    }

    private void setDialogProps(View view) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
        getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        this.setCanceledOnTouchOutside(true);
    }
}

