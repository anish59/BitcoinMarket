package com.blackbracket.bitcoinmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackbracket.bitcoinmarket.R;
import com.blackbracket.bitcoinmarket.model.Countries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anish on 29-08-2017.
 */

public class CurrencyItemAdapter extends RecyclerView.Adapter<CurrencyItemAdapter.CurrencyHolder> {
    private Context context;
    private int counter = 0;
    private List<Countries> countries = new ArrayList<>();

    public CurrencyItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_currency_symbol, parent, false);
        return new CurrencyHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyHolder holder, int position) {
        holder.txtSymbol.setText(countries.get(position).getClass().getSimpleName());
        switch (counter) {
            case 0:
                holder.viewSideLine.setBackgroundColor(context.getResources().getColor(R.color.colorSunset));
                counter++;
                break;
            case 1:
                holder.viewSideLine.setBackgroundColor(context.getResources().getColor(R.color.colorBlueSky));
                counter++;
                break;
            case 2:
                holder.viewSideLine.setBackgroundColor(context.getResources().getColor(R.color.colorLime));
                counter = 0;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return countries.size() > 0 && countries != null ? countries.size() : 0;
    }

    public class CurrencyHolder extends RecyclerView.ViewHolder {
        View viewSideLine;
        TextView txtSymbol;

        public CurrencyHolder(View itemView) {
            super(itemView);
            txtSymbol = itemView.findViewById(R.id.txtSymbol);
            viewSideLine = itemView.findViewById(R.id.viewSideLine);
        }
    }

    public void setItemsAndNotify(List<Countries> countries) {
        this.countries = new ArrayList<>();
        this.countries = countries;
        notifyDataSetChanged();
    }
}
