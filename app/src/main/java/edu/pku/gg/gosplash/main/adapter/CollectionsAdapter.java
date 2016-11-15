package edu.pku.gg.gosplash.main.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.pku.gg.gosplash.R;
import edu.pku.gg.gosplash.collection.view.CollectionActivity;
import edu.pku.gg.gosplash.common.data.Collection;
import edu.pku.gg.gosplash.common.ui.AdaptiveImageView;
import edu.pku.gg.gosplash.common.utils.ColorUtils;
import edu.pku.gg.gosplash.common.utils.TypefaceUtils;

/**
 * Created by gaoge
 */
public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.ViewHolder> {

    private Context mContext;
    private List<Collection> mCollection;

    public CollectionsAdapter(Context context, List<Collection> collections) {
        mContext = context;
        mCollection = collections;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Glide
                    .with(mContext)
                    .load(mCollection.get(position).cover_photo.urls.regular)
                    .crossFade(1000)
                    .into(holder.image);
            holder.background.setBackgroundColor(
                    ColorUtils.calcCardBackgroundColor(
                            mContext,
                            mCollection.get(position).cover_photo.color));
            holder.title.setText(mCollection.get(position).title.toUpperCase());
            holder.descrption.setText(mCollection.get(position).description);
            holder.subtitle.setText(mCollection.get(position).total_photos + (mCollection.get(position).total_photos > 1 ? " photos" : " photo"));
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    @Override
    public long getItemId(int position){
        return mCollection.get(position).cover_photo.urls.regular.hashCode();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RelativeLayout background;
        public AdaptiveImageView image;
        public TextView title;
        public TextView subtitle;
        public TextView descrption;

        public ViewHolder(View itemView, int position) {
            super(itemView);
            this.background = (RelativeLayout) itemView.findViewById(R.id.item_collection_background);
            background.setOnClickListener(this);

            this.image = (AdaptiveImageView) itemView.findViewById(R.id.item_collection_cover);
            this.title = (TextView) itemView.findViewById(R.id.item_collection_title);
            this.subtitle = (TextView) itemView.findViewById(R.id.item_collection_subtitle);
            this.descrption = (TextView) itemView.findViewById(R.id.item_collection_descrption);
            TypefaceUtils.setTypeface(itemView.getContext(), descrption);
            TypefaceUtils.setTypeface(itemView.getContext(), subtitle);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_collection_background:
                    Intent intent = new Intent();
                    intent.putExtra("id",mCollection.get(getAdapterPosition()).id);
                    intent.putExtra("curated",mCollection.get(getAdapterPosition()).curated);
                    intent.putExtra("avatar",mCollection.get(getAdapterPosition()).user.profile_image.large);
                    intent.putExtra("description",mCollection.get(getAdapterPosition()).description);
                    intent.putExtra("title",mCollection.get(getAdapterPosition()).title.toUpperCase());
                    intent.putExtra("user",mCollection.get(getAdapterPosition()).user.name);
                    intent.setClass(mContext, CollectionActivity.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}
