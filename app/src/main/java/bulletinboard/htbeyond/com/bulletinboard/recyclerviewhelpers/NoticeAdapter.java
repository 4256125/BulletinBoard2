package bulletinboard.htbeyond.com.bulletinboard.recyclerviewhelpers;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bulletinboard.htbeyond.com.bulletinboard.NoticeActivity;
import bulletinboard.htbeyond.com.bulletinboard.R;
import bulletinboard.htbeyond.com.bulletinboard.models.Notice;

public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NOTICE = 0;
    private static final int VIEW_TYPE_LOADING = 111;

    private List<Notice> mNotices;
    private boolean mIsLoadingAdded = false;

    protected class NoticeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Notice mNotice;
        public TextView mTitle;
        public TextView mWriter;
        public TextView mDate;
        public TextView mViews;


        public NoticeHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.list_item_title);
            mWriter = (TextView) itemView.findViewById(R.id.list_item_writer_name);
            mDate = (TextView) itemView.findViewById(R.id.list_item_edit_date);
            mViews = (TextView) itemView.findViewById(R.id.list_item_views);
        }

        @Override
        public void onClick(View v) {
            Intent i = NoticeActivity.newIntent(itemView.getContext(), mNotice.getNoticeId());
            itemView.getContext().startActivity(i);
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {

        public ProgressHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public NoticeAdapter(List<Notice> notices) {
        mNotices = notices;
    }


    public void add(Notice notice) {
        mNotices.add(notice);
        notifyItemInserted(mNotices.size() - 1);
    }

    public void addAll(List<Notice> noticeList) {
        mNotices.addAll(noticeList);
    }

    public void clear() {
        mIsLoadingAdded = false;
        mNotices.clear();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public Notice getItem(int position) {
        if (mNotices == null) {
            return null;
        }
        return mNotices.get(position);
    }

    public void addLoadingFooter() {
        mIsLoadingAdded = true;
        add(new Notice());
    }

    public void removeLoadingFooter() {
        mIsLoadingAdded = false;

        int position = mNotices.size() - 1;
        Notice item = mNotices.get(position);

        if (item != null) {
            mNotices.remove(position);
            notifyItemRemoved(position);
        }
    }

    private NoticeHolder getViewHolder(ViewGroup viewGroup, LayoutInflater inflater) {
        NoticeHolder noticeHolder;
        View v = inflater.inflate(R.layout.list_item_notice, viewGroup, false);
        return new NoticeHolder(v);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (i) {
            case VIEW_TYPE_NOTICE:
                viewHolder = getViewHolder(viewGroup, inflater);
                break;
            case VIEW_TYPE_LOADING:
                View v = inflater.inflate(R.layout.list_item_progress_bar, viewGroup,false);
                viewHolder = new ProgressHolder(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Notice notice = mNotices.get(i);

        switch (getItemViewType(i)) {
            case VIEW_TYPE_NOTICE:
                NoticeHolder holder = (NoticeHolder) viewHolder;
                holder.mNotice = notice;
                holder.mTitle.setText(notice.getTitle());
                holder.mWriter.setText(notice.getWriter());
                holder.mDate.setText(notice.getModifiedDateToString());
                holder.mViews.setText("views" + notice.getNoticeId());
                if (notice.isHighlighted()) {
                    holder.mTitle.setTypeface(null, Typeface.BOLD);
                }
                break;

            case VIEW_TYPE_LOADING:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mNotices == null ? 0 : mNotices.size();
    }

    public void setNotices(List<Notice> notices) {
        mNotices = notices;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mNotices.size() - 1 && mIsLoadingAdded) ?
                VIEW_TYPE_LOADING : VIEW_TYPE_NOTICE;
    }
}
