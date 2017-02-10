package com.lynpo.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lynpo.util.AndroidAPIUtil;
import com.lynpo.util.GlideUtil;

/**
 * Created by fujw on 16-2-24.
 *
 * RecyclerView's common used ViewHolder
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArrayCompat<View> mView;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArrayCompat<>();
    }

    private View getView(int resId) {
        View view = mView.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mView.put(resId, view);
        }
        return view;
    }

    public void setText(int resId, String text) {
        ((TextView) getView(resId)).setText(text);
    }

    public void setTextSize(int resId, float textSize) {
        ((TextView) getView(resId)).setTextSize(textSize);
    }

    public void setHtmlText(int resId, String text) {
        ((TextView) getView(resId)).setText(AndroidAPIUtil.fromHtml(text));
    }

    public void setText(int resId, String text, int maxLines, TextUtils.TruncateAt where) {
        TextView tv = (TextView) getView(resId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(AndroidAPIUtil.fromHtml(text));
        } else {
            tv.setText("");
        }
        tv.setMaxLines(maxLines);
        tv.setEllipsize(where);
    }

    public void postRunnable(int resId, final int targetResId) {
        final TextView tv = (TextView) getView(resId);
        tv.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = tv.getLayout();
                if (layout != null) {
                    int lines = layout.getLineCount();
                    if (lines > 0) {
                        if (layout.getEllipsisCount(lines - 1) > 0) {
                            getView(targetResId).setVisibility(View.VISIBLE);
                        } else {
                            getView(targetResId).setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    public void setTextColor (int resId, int color) {
        ((TextView) getView(resId)).setTextColor(color);
    }

    public void setTextStyle (int resId, Typeface type) {
        ((TextView) getView(resId)).setTypeface(type);
    }

//    public void setTextViewDrawableResource(int resId, int drawableResId) {
//        TextView tv = (TextView) getView(resId);
//        tv.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(tv.getContext(), drawableResId), null, null, null);
//    }

    public void setTextShadowColorBlack(int resId) {
        ((TextView) getView(resId)).setShadowLayer(2F, 1F, 1F, Color.BLACK);
    }

    public void setBackground(int resId, int drawableRes) {
        getView(resId).setBackgroundResource(drawableRes);
    }

    public void setImageSrc(int ivId, int resId) {
        ((ImageView) getView(ivId)).setImageResource(resId);
    }

    public void loadImageUrl (final Context context, int ivId, String url) {
        ImageView iv = ((ImageView) getView(ivId));
        Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Glide.clear(target);
                return true;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (!AndroidAPIUtil.isContextValid(context)) {
                    Glide.clear(target);
                    return true;
                }
                return false;
            }
        }).into(iv);
    }

    public void loadRoundImage (final Context context, int ivId, String url, String tagForTransform) {
        ImageView iv = ((ImageView) getView(ivId));
        Glide.with(context).load(url).transform(
                new GlideUtil.CircleTransform(
                        iv.getContext(), tagForTransform)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Glide.clear(target);
                return true;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (!AndroidAPIUtil.isContextValid(context)) {
                    Glide.clear(target);
                    return true;
                }
                return false;
            }
        }).into(iv);
    }

    public void setVisibility(int resId, int visibility) {
        getView(resId).setVisibility(visibility);
    }

    public void setInnerViewClickListener(final int innerResId, final OnViewClickListener listener) {
        getView(innerResId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onViewClick(v);
                }
            }
        });
    }

    public interface OnViewClickListener {
        void onViewClick(View view);
    }
}
