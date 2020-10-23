package com.example.comicvine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<MovieDataModel> implements View.OnClickListener {
    private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView imMainPoster;
        TextView txtName;
        TextView txtDeck;
    }

    public MovieAdapter(ArrayList<MovieDataModel> data, Context context) {
        super(context, R.layout.movie_item, data);
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        MovieDataModel dataModel = getItem(position);
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MovieDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_item, parent, false);
            viewHolder.imMainPoster = convertView.findViewById(R.id.imageViewMainPoster);
            viewHolder.txtName = convertView.findViewById(R.id.textViewName);
            viewHolder.txtDeck = convertView.findViewById(R.id.textViewDeck);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        assert dataModel != null;

        BitmapDrawable drawable = (BitmapDrawable) dataModel.getMainImagePoster().getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        viewHolder.imMainPoster.setImageBitmap(bitmap);
        viewHolder.txtName.setText(dataModel.getMovieName());
        viewHolder.txtDeck.setText(dataModel.getMovieDeck());
        // Return the completed view to render on screen
        return convertView;
    }
}
