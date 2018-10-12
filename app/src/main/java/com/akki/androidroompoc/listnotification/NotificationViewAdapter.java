package com.akki.androidroompoc.listnotification;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akki.androidroompoc.R;
import com.akki.androidroompoc.db.NotificationItemModel;
import com.akki.androidroompoc.db.NotificationViewModel;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by v-akhilesh.chaudhary on 11/20/2017.
 */

public class NotificationViewAdapter  extends RecyclerView.Adapter<NotificationViewAdapter.ItemRecyclerViewHolder> {

    private final String TAG = NotificationViewAdapter.class.getName();

    private Context mContext;
    private List<NotificationItemModel> notificationItemModelList;

    public class ItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView urlImageView, beImageView;
        private TextView titleTextView;
        private TextView detailTextView;

        private LinearLayout promoCodeLayout;
        private TextView promoCodeTextView;
        private TextView copyPromoTextView;
        private TextView promoCodeValidityTextView;

        private RelativeLayout viewBackground;
        public LinearLayout viewForeground;
        private CardView mCardView;

        ItemRecyclerViewHolder(View view) {
            super(view);
            urlImageView = view.findViewById(R.id.url_image_view);
            beImageView = view.findViewById(R.id.be_thumbnail);
            titleTextView = view.findViewById(R.id.txt_title);
            detailTextView = view.findViewById(R.id.txt_description);
            promoCodeLayout = view.findViewById(R.id.view_promo_code_layout);
            promoCodeTextView = view.findViewById(R.id.txt_promo_code);
            copyPromoTextView = view.findViewById(R.id.txt_copy_code);
            promoCodeValidityTextView = view.findViewById(R.id.txt_code_validity);

            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

            mCardView = view.findViewById(R.id.card_view);
        }
    }

    public NotificationViewAdapter(Context context, List<NotificationItemModel> notificationItemModelList) {
        this.mContext = context;
        this.notificationItemModelList = notificationItemModelList;
    }

    @Override
    public ItemRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*return new ItemRecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false));*/

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false);

        return new ItemRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemRecyclerViewHolder holder, final int position) {
        NotificationItemModel notificationItemModel = notificationItemModelList.get(position);

        if(notificationItemModel.getImage_url().equalsIgnoreCase("Image Url")) {
            holder.urlImageView.setVisibility(View.GONE);
        } else {
            holder.urlImageView.setVisibility(View.VISIBLE);
            holder.urlImageView.setMaxHeight(30);

            /*Glide.with(mContext)
                    .load(notificationItemModel.getImage_url())
                    .into(holder.urlImageView);*/

            /*String imageUrl = "https://res.cloudinary.com/demo/image/upload/Sample.jpg";
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.urlImageView);*/

            String imageUrl = "https://res.cloudinary.com/demo/image/upload/Sample.jpg";
            holder.urlImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.urlImageView);

        }

        holder.titleTextView.setText(notificationItemModel.getTitle());
        holder.detailTextView.setText(notificationItemModel.getMessage());

        if(notificationItemModel.getPromo_code().isEmpty()) {
            holder.promoCodeLayout.setVisibility(View.GONE);
        } else {
            holder.promoCodeLayout.setVisibility(View.VISIBLE);
            holder.promoCodeTextView.setText(notificationItemModel.getPromo_code());
            holder.promoCodeValidityTextView.setText(notificationItemModel.getValidity());

            holder.copyPromoTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = (String) v.getTag();
                    copyCouponCodeToClipboard(v.getContext(), code);
                }
            });
        }

        holder.itemView.setTag(notificationItemModel); // ??
    }

    @Override
    public int getItemCount() {
        return notificationItemModelList.size();
    }

    public void addItems(List<NotificationItemModel> notificationItemModelList) {
        this.notificationItemModelList = notificationItemModelList;
        notifyDataSetChanged();
    }


    public void removeItem(int position) {
        notificationItemModelList.remove(position);

        // remove the item from recycler view
        notificationItemModelList.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void removeItem(int position, NotificationViewModel viewModel, RecyclerView recyclerView) {
        notificationItemModelList.remove(position);

        // remove the item from recycler view
        NotificationItemModel notificationItemModel = (NotificationItemModel)recyclerView.findViewHolderForAdapterPosition(position).itemView.getTag();
        viewModel.deleteNotification(notificationItemModel);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }


    public void restoreItem(NotificationItemModel notificationItemModel, int position, NotificationViewModel viewModel) {
        notificationItemModelList.add(position, notificationItemModel);
        viewModel.addNotification(notificationItemModel);

        // notify item added by position
        notifyItemInserted(position);
    }

    /**
     * Copies the specified promo code to the Clipboard.
     *
     * @param mContext   An instance of the application Context
     * @param promoCode The promo code to be added to the Clipboard
     */
    public static void copyCouponCodeToClipboard(Context mContext,
                                                 String promoCode) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            copyToClipboardHoneyLess(mContext, promoCode);
        } else {
            copyToClipboardHoney(mContext, promoCode);
        }
        Toast.makeText(mContext, "Promo code copied to clipboard!", Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void copyToClipboardHoney(Context mContext,
                                             String coupon_code) {
        final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);
        final android.content.ClipData clipData = android.content.ClipData
                .newPlainText("coupon code", coupon_code);
        clipboardManager.setPrimaryClip(clipData);
    }

    @SuppressWarnings("deprecation")
    private static void copyToClipboardHoneyLess(Context mContext,
                                                 String coupon_code) {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(coupon_code);
    }

}
