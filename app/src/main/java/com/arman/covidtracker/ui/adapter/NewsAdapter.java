package com.arman.covidtracker.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.ItemCountryStatusBinding;
import com.arman.covidtracker.databinding.ItemNewsBinding;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.utils.ImageLoader;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private Context context;

    private List<News> newsList;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news = newsList.get(position);
        holder.binder(news);
    }

    @Override
    public int getItemCount() {
        if (newsList == null){
            return 0;
        }
        return newsList.size();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void updateNews(int position,News news) {
        this.newsList.set(position,news);
        notifyItemChanged(position,newsList);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ItemNewsBinding binding;

        public NewsViewHolder(@NonNull ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void binder(News news){

            if (news !=null){
                binding.vTitle.setText(news.getTitle());
                binding.vDescription.setText(news.getDescription());
                if (news.getUrlToImage() !=null && !TextUtils.isEmpty(news.getUrlToImage())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.ivPoster.setTransitionName(context.getString(R.string.name_thumbnail_transaction)+getAdapterPosition());
                    }
                    ImageLoader.load(binding.ivPoster,news.getUrlToImage());
                }
            }

        }
    }
}