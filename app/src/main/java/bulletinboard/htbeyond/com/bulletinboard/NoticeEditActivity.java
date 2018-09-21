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

    private static final String EXTRA_NOTICE =
            "bulletinboard.htbeyond.com.bulletinboard.notice_num";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_HIGHLIGHT = "highlight";
    public static final int CREATE_NOTICE = -1;
    public static final int RESULT_DELETE = 10;

    private static final String TAG = "NoticeEditActivity";

    private Notice mNotice;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private CheckBox mHighlightedCheckBox;
    private boolean mCreating;


    public static Intent newIntent(Context packageContext, Notice notice) {
        Intent i = new Intent(packageContext, NoticeEditActivity.class);
        i.putExtra(EXTRA_NOTICE, notice);

        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_edit);

        mNotice = (Notice) getIntent().getSerializableExtra(EXTRA_NOTICE);
        if (mNotice == null) {
            mCreating = true;
        }

        mTitleTextView = (TextView) findViewById(R.id.notice_edit_title);
        mContentTextView = (TextView) findViewById(R.id.notice_edit_content);
        mHighlightedCheckBox = (CheckBox) findViewById(R.id.notice_edit_highlight);

        // savedIntanceState에 저장된 값이 있으면 해당 값을, 저장된 값이 없으면 Notice의 값을 보여줌

        if (savedInstanceState == null) {
            if (mCreating) {
                mNotice = new Notice();
            } else {
                mTitleTextView.setText(mNotice.getTitle());
                mContentTextView.setText(mNotice.getContent());
                mHighlightedCheckBox.setChecked(mNotice.isHighlighted());
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
                Intent i = new Intent();
                i.putExtra(EXTRA_NOTICE, mNotice);
                setResult(RESULT_OK, i);
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
        Call<NoticeRepo> res = RetrofitService.getInstance(NoticeEditActivity.this).getService()
                .deleteNotice(getString(R.string.access_token), mNotice.getNoticeId());
        res.enqueue(new Callback<NoticeRepo>() {
            @Override
            public void onResponse(Call<NoticeRepo> call, Response<NoticeRepo> response) {
                Log.d(TAG, "deleteNotice() called" + response.toString());

            }

            @Override
            public void onFailure(Call<NoticeRepo> call, Throwable t) {
                Log.e(TAG, "deleteNotice() called" + t.getMessage());
                Toast.makeText(NoticeEditActivity.this, R.string.toast_delete_success, Toast.LENGTH_SHORT).show();
                setResult(RESULT_DELETE);
                finish();
            }
        });

    }

        private void showFailToast() {
            Toast.makeText(NoticeEditActivity.this, R.string.toast_failure, Toast.LENGTH_SHORT).show();
        }
}