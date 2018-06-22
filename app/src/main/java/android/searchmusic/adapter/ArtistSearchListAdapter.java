package android.searchmusic.adapter;

/*Created by venkareddy on 26/12/17.*/

import android.content.Context;
import android.searchmusic.R;
import android.searchmusic.dto.ArtistSearchResponse;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ArtistSearchListAdapter extends RecyclerView.Adapter<ArtistSearchListAdapter.ViewHolder> {

    final Context context;

    private List<ArtistSearchResponse> artistList;

    private OnArtistItemClickListener listener;

    public ArtistSearchListAdapter(Context context, List<ArtistSearchResponse> artistList,OnArtistItemClickListener listener) {
        super();

        this.context = context;
        this.artistList = artistList;
        this.listener = listener;

    }

    // inflating the recyler item to the view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.artist_search_row_item, parent, false);

            return new ViewHolder(itemView);
    }

    //Interface for forums items click listener
    public interface OnArtistItemClickListener{
        void onClickArtistListItem(ArtistSearchResponse artistSearchResponse);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        bindDataToUI(position,holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClickArtistListItem(artistList.get(position));
            }
        });

    }

    // Bind data to UI
    private void bindDataToUI(int i, ViewHolder holder) {

        final String profileName = artistList.get(i).getArtistName();

        final String profileImageUrl = artistList.get(i).getImageArray().get(2).getImageUrl();

        final String isStreaming = artistList.get(i).getStreamable();

        if(!TextUtils.isEmpty(isStreaming)){

            if(isStreaming.equals("0")){
                holder.ivOnOffLine.setImageResource(R.drawable.ic_gray_circle);
                holder.tvStreaming.setText("Streaming: No");
            }else {
                holder.ivOnOffLine.setImageResource(R.drawable.ic_green_circle);
                holder.tvStreaming.setText("Streaming: Yes");
            }
        }

        if(!TextUtils.isEmpty(profileName))
       holder.tvArtistName.setText(profileName);

        if (!TextUtils.isEmpty(profileImageUrl)) {

            Glide.with(context)
                    .load(profileImageUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.ivArtist);

        } else {

            holder.ivArtist.setImageResource(R.drawable.ic_artist_dummy);
        }
    }

    @Override
    public int getItemCount() {

        return artistList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return artistList.get(position) != null ? 1 : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivArtist;
        private ImageView ivOnOffLine;
        private TextView tvArtistName;
        private TextView tvStreaming;

        public ViewHolder(View itemView) {
            super(itemView);

            tvArtistName =  itemView.findViewById(R.id.tvArtistName);
            tvStreaming =  itemView.findViewById(R.id.tvStreaming);
            ivArtist =  itemView.findViewById(R.id.ivArtistProfile);
            ivOnOffLine =  itemView.findViewById(R.id.ivOnOffline);
        }
    }

}