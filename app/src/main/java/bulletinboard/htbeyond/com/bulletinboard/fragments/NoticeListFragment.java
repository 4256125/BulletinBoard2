package bulletinboard.htbeyond.com.bulletinboard.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import bulletinboard.htbeyond.com.bulletinboard.NoticeEditActivity;
import bulletinboard.htbeyond.com.bulletinboard.R;
import bulletinboard.htbeyond.com.bulletinboard.models.Notice;
import bulletinboard.htbeyond.com.bulletinboard.models.NoticeStorage;
import bulletinboard.htbeyond.com.bulletinboard.network.NoticeService;
import bulletinboard.htbeyond.com.bulletinboard.network.PageRepo;
import bulletinboard.htbeyond.com.bulletinboard.network.RetrofitService;
import bulletinboard.htbeyond.com.bulletinboard.recyclerviewhelpers.NoticeAdapter;
import bulletinboard.htbeyond.com.bulletinboard.recyclerviewhelpers.PaginationScrollListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeListFragment extends Fragment {

    private static final String TAG = "NoticeListFragment";
    private static final String KEY_PAGE_NUM
            = "bulletinboard.htbeyond.com.bulletinboard.fragments.page_num";
    private static final String KEY_LAST
            = "bulletinboard.htbeyond.com.bulletinboard.fragments.last";
    private static final int PAGE_START = 0;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private NoticeAdapter mAdapter;
    private ProgressBar mProgressBar;
    private int mCurrentPageNumber = PAGE_START;
    private int mTotalPage;
    private boolean mIsLoading = false;
    private boolean mIsLastPage;
    private List<Notice> mNotices;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE_NUM, mCurrentPageNumber);
        outState.putBoolean(KEY_LAST, mIsLastPage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_progress_bar);
        mAdapter = new NoticeAdapter(NoticeStorage.getInstance(getActivity()).getNotices());
        mLinearLayoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false);

        Log.d("NoticeListFragment", mLinearLayoutManager.toString());

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        loadFirstPage();
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.d(TAG, "loadMoreItems() called");
                mIsLoading = true;
                loadNextPage();
                mCurrentPageNumber++;
            }

            @Override
            public int getTotalPageCount() {
                // TODO 토털 페이지 개수
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return mIsLastPage;
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }
        });

        if (savedInstanceState != null) {
            mIsLastPage = savedInstanceState.getBoolean(KEY_LAST);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        loadFirstPage();
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsLastPage = false;
        mCurrentPageNumber = 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_notice_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notice_list_item_post:
                Intent i = NoticeEditActivity.newIntent(getActivity(), null);
                startActivity(i);
                return true;

            case R.id.menu_notice_list_item_refresh:
                loadNextPage();

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void loadFirstPage() {
        mCurrentPageNumber = PAGE_START;

        Call<PageRepo> res = RetrofitService.getInstance(getActivity()).getService()
                .getNotices(getActivity().getString(R.string.access_token) , NoticeService.NORMAL_SIZE, mCurrentPageNumber, NoticeService.MODE_FIND_ALL);

        res.enqueue(new Callback<PageRepo>() {
            @Override
            public void onResponse(Call<PageRepo> call, Response<PageRepo> response) {
                Log.d(TAG, "getNotices() called" + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_LONG);
                    PageRepo page = response.body();
                    Log.d(TAG, "getNotices() called" + response.body().toString());

                    mProgressBar.setVisibility(View.GONE);
                    mAdapter.setNotices(page.getNotices());
                    mIsLoading = false;
                    mTotalPage = page.getTotalPages();

                    if (page.getLast()) {
                        mAdapter.addLoadingFooter();
                    } else {
                        mIsLastPage = true;
                    }

                }
            }

            @Override
            public void onFailure(Call<PageRepo> call, Throwable t) {
                Log.e(TAG, "getNotices() called" + t.getMessage());
                Toast.makeText(getActivity(), R.string.toast_failure, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadNextPage() {

        mCurrentPageNumber++;

        Call<PageRepo> res = RetrofitService.getInstance(getActivity()).getService()
                .getNotices(getActivity().getString(R.string.access_token) , NoticeService.NORMAL_SIZE, mCurrentPageNumber, NoticeService.MODE_FIND_ALL);

        res.enqueue(new Callback<PageRepo>() {
            @Override
            public void onResponse(Call<PageRepo> call, Response<PageRepo> response) {
                Log.d(TAG, "getNotices() called" + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_LONG);
                    PageRepo page = response.body();

                    mAdapter.removeLoadingFooter();
                    mIsLoading = false;
                    mAdapter.addAll(page.getNotices());

                    if (page.getLast()) {
                        mAdapter.addLoadingFooter();
                    } else {
                        mIsLastPage = true;
                    }

                }
            }

            @Override
            public void onFailure(Call<PageRepo> call, Throwable t) {
                Log.e(TAG, "getNotices() called" + t.getMessage());
                Toast.makeText(getActivity(), R.string.toast_failure, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
