package com.blackbracket.bitcoinmarket.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackbracket.bitcoinmarket.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

/**
 * Created by Anish on 9/2/2017.
 */

public class BitCoinCalculatorDialog extends Dialog {
    private Context context;
    private android.widget.EditText edtAmount;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spinnerSearchable;
    private android.widget.Button btnConvert;
    private android.widget.TextView txtResult;

    public BitCoinCalculatorDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
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

        //todo: remains from here add currency class name

        show();

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
