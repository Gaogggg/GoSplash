package edu.pku.gg.gosplash.photo.adapter;

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
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.ui.AdaptiveImageView;
import edu.pku.gg.gosplash.common.ui.activity.PhotoDetailActivity;
import edu.pku.gg.gosplash.common.utils.ColorUtils;
import edu.pku.gg.gosplash.common.utils.TypefaceUtils;

/**
 * Created by gaoge
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context mContext;
    private List<Photo> mPhotos;

    public PhotoAdapter(Context context, List<Photo> list) {
        mContext = context;
        mPhotos = list;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mPhotos.get(position).urls.small)
                .crossFade(1000)
                .into(holder.image);
        holder.background.setBackgroundColor(
                ColorUtils.calcCardBackgroundColor(
                        mContext,
                        mPhotos.get(position).color));
        holder.title.setText("By " + mPhotos.get(position).user.name + ", On "
                + mPhotos.get(position).created_at.split("T")[0]);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @Override
    public long getItemId(int position) {
        return mPhotos.get(position).urls.regular.hashCode();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RelativeLayout background;
        public AdaptiveImageView image;
        public TextView title;

        public ViewHolder(View itemView, int position) {
            super(itemView);
            this.background = (RelativeLayout) itemView.findViewById(R.id.item_photo_background);
            background.setOnClickListener(this);

            this.image = (AdaptiveImageView) itemView.findViewById(R.id.item_photo_image);
            this.title = (TextView) itemView.findViewById(R.id.item_photo_title);
            TypefaceUtils.setTypeface(itemView.getContext(), title);

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_photo_background:
                    Intent intent = new Intent();
                    intent.putExtra("id",mPhotos.get(getAdapterPosition()).id);
                    intent.putExtra("avatar",mPhotos.get(getAdapterPosition()).user.profile_image.large);
                    intent.setClass(mContext,PhotoDetailActivity.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}
