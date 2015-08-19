package com.example.dominik.photouploader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HeaderAdapter extends HeaderViewRecyclerAdapeter<HeaderAdapter.HeaderViewHolder, HeaderAdapter.FooterViewHolder, HeaderAdapter.ContentViewHolder> {
    private ItemData[] itemsData;


    public HeaderAdapter(ItemData[] itemsData) {
        this.itemsData = itemsData;
    }
    @Override
    protected int getHeaderItemCount() {
        return 1;
    }

    @Override
    protected int getFooterItemCount() {
        return 1;
    }

    @Override
    protected int getContentItemCount() {
        return itemsData.length;
    }

    @Override
    protected HeaderViewHolder onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header, null);

        // create ViewHolder

        HeaderViewHolder viewHolder = new HeaderViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    protected FooterViewHolder onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, null);

        // create ViewHolder

        FooterViewHolder viewHolder = new FooterViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    protected ContentViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);

        // create ViewHolder

        ContentViewHolder viewHolder = new ContentViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    protected void onBindHeaderItemViewHolder(HeaderViewHolder headerViewHolder, int position) {

    }

    @Override
    protected void onBindFooterItemViewHolder(FooterViewHolder footerViewHolder, int position) {

    }

    @Override
    protected void onBindContentItemViewHolder(ContentViewHolder contentViewHolder, int position) {
        contentViewHolder.itemTitle.setText(itemsData[position].getTitle());
        contentViewHolder.imgIcon.setImageResource(itemsData[position].getImageUrl());



    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {


        public HeaderViewHolder(View itemView) {
            super(itemView);


        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);

        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public ImageView imgIcon;
        public ContentViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            imgIcon = (ImageView) itemView.findViewById(R.id.item_icon);
        }
    }


}
