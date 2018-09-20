package bulletinboard.htbeyond.com.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import bulletinboard.htbeyond.com.bulletinboard.models.Notice;
import bulletinboard.htbeyond.com.bulletinboard.models.NoticeStorage;
import bulletinboard.htbeyond.com.bulletinboard.network.NoticeRepo;
import bulletinboard.htbeyond.com.bulletinboard.network.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeEditActivity extends AppCompatActivity {

    private static final String EXTRA_NOTICE_NUM =
            "bulletinboard.htbeyond.com.bulletinboard.notice_num";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_HIGHLIGHT = "highlight";
    public static final int CREATE_NOTICE = -1;

    private static final String TAG = "NoticeEditActivity";

    private Notice mNotice;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private CheckBox mHighlightedCheckBox;
    private boolean mCreating;


    /**
     *
     * @param packageContext
     * @param noticeNum
     * 만약 새로 Notice를 만들 시, <br/>
     * NoticeEditActivity.CREATE_NOTICE 를 넣으면 된다.
     * @return
     */
    public static Intent newIntent(Context packageContext, int noticeNum) {
        Intent i = new Intent(packageContext, NoticeEditActivity.class);
        i.putExtra(EXTRA_NOTICE_NUM, noticeNum);

        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_edit);

        int noticeNum = getIntent().getIntExtra(EXTRA_NOTICE_NUM, CREATE_NOTICE);
        if (noticeNum == CREATE_NOTICE) {
            mCreating = true;
        }

        mTitleTextView = (TextView) findViewById(R.id.notice_edit_title);
        mContentTextView = (TextView) findViewById(R.id.notice_edit_content);
        mHighlightedCheckBox = (CheckBox) findViewById(R.id.notice_edit_highlight);

        // savedIntanceState에 저장된 값이 있으면 해당 값을, 저장된 값이 없으면 Notice의 값을 보여줌


        if (savedInstanceState == null) {
            if (noticeNum != CREATE_NOTICE) {
                Notice notice =
                        NoticeStorage.getInstance(NoticeEditActivity.this).getNotice(noticeNum);
                mNotice = notice;
                mTitleTextView.setText(notice.getTitle());
                mContentTextView.setText(notice.getContent());
                mHighlightedCheckBox.setChecked(notice.isHighlighted());
            } else {
                mNotice = new Notice();
            }
        } else {
            mTitleTextView.setText(
                    savedInstanceState.getString(KEY_TITLE, mNotice.getTitle()));
            mContentTextView.setText(
                    savedInstanceState.getString(KEY_CONTENT, mNotice.getTitle()));
            mHighlightedCheckBox.setChecked(
                    savedInstanceState.getBoolean(KEY_HIGHLIGHT, mNotice.isHighlighted()));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_notice_edit, menu);
        MenuItem item = (MenuItem) menu.findItem(R.id.menu_notice_edit_item_delete);
        item.setVisible(!mCreating);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notice_edit_item_edit:
                sendNotice();
                finish();
                return true;
            case R.id.menu_notice_edit_item_delete:
                deleteNotice();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, mNotice.getTitle());
        outState.putString(KEY_CONTENT, mNotice.getContent());
        outState.putBoolean(KEY_HIGHLIGHT, mNotice.isHighlighted());
    }


    //context 찾아 넣기
    private void sendNotice() {

        Call<NoticeRepo> res = null;

        mNotice.setTitle(mTitleTextView.getText().toString())
                .setContent(mContentTextView.getText().toString())
                .setHighlighted(mHighlightedCheckBox.isChecked())
                .setWriter("tester");

        if (mCreating) {
            res = RetrofitService.getInstance(NoticeEditActivity.this).getService()
                    .postNotice(getString(R.string.access_token), mNotice.getPostBody(Notice.POST));
        } else {
            res = RetrofitService.getInstance(NoticeEditActivity.this).getService()
                    .postNotice(getString(R.string.access_token), mNotice.getPostBody(Notice.UPDATE));
        }
            res.enqueue(new Callback<NoticeRepo>() {
                @Override
                public void onResponse(Call<NoticeRepo> call, Response<NoticeRepo> response) {
                    Log.d(TAG, "postNotice() called" + response.toString());
                    if (response.isSuccessful()) {
                        Toast.makeText(NoticeEditActivity.this, R.string.toast_post_success, Toast.LENGTH_SHORT).show();

                    } else {
                        showFailToast();
                    }
                }

                @Override
                public void onFailure(Call<NoticeRepo> call, Throwable t) {
                    Log.e(TAG, "postNotice() called" + t.getMessage());
                    showFailToast();
                }
            });
    }

    //context 찾아 넣기
    private void deleteNotice(){
        mNotice.setNoticeId(getIntent().getIntExtra(EXTRA_NOTICE_NUM, CREATE_NOTICE));

        Call<NoticeRepo> res = RetrofitService.getInstance(NoticeEditActivity.this).getService()
                .deleteNotice(getString(R.string.access_token), mNotice.getNoticeId());
        res.enqueue(new Callback<NoticeRepo>() {
            @Override
            public void onResponse(Call<NoticeRepo> call, Response<NoticeRepo> response) {
                Log.d(TAG, "deleteNotice() called" + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(NoticeEditActivity.this, R.string.toast_delete_success, Toast.LENGTH_SHORT).show();

                } else {
                    showFailToast();
                }
            }

            @Override
            public void onFailure(Call<NoticeRepo> call, Throwable t) {
                Log.e(TAG, "deleteNotice() called" + t.getMessage());
                showFailToast();
            }
        });

    }

        private void showFailToast() {
            Toast.makeText(NoticeEditActivity.this, R.string.toast_failure, Toast.LENGTH_SHORT).show();
        }
}