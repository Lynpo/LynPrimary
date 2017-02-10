package com.lynpo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lynpo.R;

/**
 * Created by fujw on 16-2-24.
 *
 * RecyclerView's loading progress view
 */
public class LoadViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar mProgressBar;

    public LoadViewHolder(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
    }
}
