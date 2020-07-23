package com.arman.covidtracker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arman.covidtracker.databinding.ItemCountryStatusBinding;
import com.arman.covidtracker.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryStatusAdapter extends RecyclerView.Adapter<CountryStatusAdapter.MyViewHolder> implements Filterable {


    private Context context;

    private List<Country> countries;

    private List<Country> countriesListFiltered;

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

        Country country = countriesListFiltered.get(position);
        holder.binder(country);
    }

    @Override
    public int getItemCount() {
        if (countriesListFiltered == null){
            return 0;
        }
        return countriesListFiltered.size();
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        this.countriesListFiltered = countries;
        notifyDataSetChanged();
    }

    public void updateCountry(int position,Country country) {
        this.countries.set(position,country);
        notifyItemChanged(position);
    }

    public List<Country> getCountries() {
        return countries;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    countriesListFiltered = countries;
                } else {
                    List<Country> filteredList = new ArrayList<>();
                    for (Country row : countries) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCountry().toLowerCase().contains(charString.toLowerCase()) || row.getCountry().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    countriesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countriesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countriesListFiltered = (ArrayList<Country>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
