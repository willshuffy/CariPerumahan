package cariperumahan.rini.com.cariperumahan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cariperumahan.rini.com.cariperumahan.R;
import cariperumahan.rini.com.cariperumahan.activities.RumahListActivity;
import cariperumahan.rini.com.cariperumahan.interfaces.PaginationAdapterCallback;
import cariperumahan.rini.com.cariperumahan.interfaces.RecyClerviewClick;
import cariperumahan.rini.com.cariperumahan.model.Rumah;

public class RumahAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Rumah> list;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    RecyClerviewClick listener;

    public RumahAdapter(Context context, RecyClerviewClick listener) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        list = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_rumah, parent,
                        false);

                viewHolder = new ViewHolder(viewItem);

                final RecyclerView.ViewHolder finalViewHolder = viewHolder;
                viewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(v, finalViewHolder
                                .getAdapterPosition());

                    }
                });

                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    /**
     * Main list's content ViewHolder
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivRumah;
        private CardView cvRoot;
        private TextView tvName;
        private TextView tvAlamat;



        public ViewHolder(View itemView) {
            super(itemView);
            ivRumah = itemView.findViewById(R.id.iv_rumah);
            cvRoot = itemView.findViewById(R.id.cvRoot);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);


            tvAlamat.setPaintFlags(0);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        final Rumah result = list.get(position);

        switch (getItemViewType(position)) {

            case ITEM:
                final ViewHolder viewHolder = (ViewHolder) holder;

                //String id = "SA"+result.getId()+result.getCreated_at().replaceAll("[^\\d]", "");

                //viewHolder.tvName.setText(result.getName());
                //viewHolder.tvAlamat.setText(result.getAddress());

                viewHolder.tvName.setText(result.getNama());
                viewHolder.tvAlamat.setText(result.getAlamat());

                Glide.with(context)
                        .load(result.getFoto())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .into(((ViewHolder) holder).ivRumah);

                viewHolder.tvAlamat.setTextIsSelectable(false);

                viewHolder.tvAlamat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.cvRoot.performClick();
                    }
                });

                viewHolder.cvRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((RumahListActivity)context).intentToRumahData(result);
                    }
                });
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Rumah r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<Rumah> moveResults) {
        for (Rumah result : moveResults) {
            add(result);
        }

    }

    public void remove(Rumah r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clearAll() {
        isLoadingAdded = false;
        if (!list.isEmpty()){
            list.clear();
            notifyDataSetChanged();
        }

    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Rumah());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = list.size() - 1;
        Rumah result = getItem(position);

        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }
    public Rumah getItem(int position) {

        if (list !=null){
            return list.get(position);
        }

        return null;

    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View
    .OnClickListener{
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */

    private void showRetry(boolean show, String errorMsg) {
        retryPageLoad=show;
        notifyItemChanged(list.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }
}
