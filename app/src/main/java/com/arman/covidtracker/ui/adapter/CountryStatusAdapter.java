package com.arman.covidtracker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arman.covidtracker.databinding.ItemCountryStatusBinding;
import com.arman.covidtracker.model.Country;

import java.util.List;

public class CountryStatusAdapter extends RecyclerView.Adapter<CountryStatusAdapter.MyViewHolder> {


    private Context context;

    private List<Country> countries;

    public CountryStatusAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryStatusBinding binding = ItemCountryStatusBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Country country = countries.get(position);
        holder.binder(country);
    }

    @Override
    public int getItemCount() {
        if (countries == null){
            return 0;
        }
        return countries.size();
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    public void updateCountry(int position,Country country) {
        this.countries.set(position,country);
        notifyItemChanged(position);
    }

    public List<Country> getCountries() {
        return countries;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemCountryStatusBinding  itemBinding;

        public MyViewHolder(@NonNull ItemCountryStatusBinding  itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void binder(Country country){
            if (country != null){
                itemBinding.vTitle.setText(country.getCountry());
                itemBinding.tvConfirmedNumber.setText(String.valueOf(country.getTotalConfirmed()));
                itemBinding.tvRecoveredNumber.setText(String.valueOf(country.getTotalRecovered()));
                itemBinding.tvDeathsNumber.setText(String.valueOf(country.getTotalDeaths()));
            }
        }
    }
}
