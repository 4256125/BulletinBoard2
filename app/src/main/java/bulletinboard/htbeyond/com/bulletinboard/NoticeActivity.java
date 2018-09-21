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

import bulletinboard.htbeyond.com.bulletinboard.models.Notice;
import bulletinboard.htbeyond.com.bulletinboard.models.NoticeStorage;

public class NoticeActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT_ACTIVITY = 0;
    private static final String TAG = "NoticeActivity";
    private static final String EXTRA_NOTICE =
            "bulletinboard.htbeyond.com.bulletinboard.notice_num";

    private Notice mNotice;
    private TextView mTitle;
    private TextView mContent;
    private CheckBox mHighlight;
    private TextView mFirstDate;
    private TextView mModifiedDate;


    public static Intent newIntent(Context packageContext, Notice notice) {
        Intent i = new Intent(packageContext, NoticeActivity.class);
        i.putExtra(EXTRA_NOTICE, notice);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        mNotice = (Notice) getIntent().getSerializableExtra(EXTRA_NOTICE);
        if (mNotice == null) {
            Log.e(TAG, "onCreate() called. There is no noticeNum in Intent.");
            finish();
            return;
        }

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_notice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notice_item_edit :
                Intent i = NoticeEditActivity.newIntent(NoticeActivity.this, mNotice);
                startActivityForResult(i, REQUEST_EDIT_ACTIVITY); //TODO 작성 값 돌려주기
                return true;
            case R.id.menu_notice_item_highlight:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDIT_ACTIVITY:
                    mNotice = (Notice) data.getSerializableExtra(EXTRA_NOTICE);
                    updateUI();
            }
        } else if (resultCode == NoticeEditActivity.RESULT_DELETE) {
            finish();
        }
    }

    private void updateUI() {
        mTitle = (TextView) findViewById(R.id.notice_title);
        mTitle.setText(mNotice.getTitle());

        mContent = (TextView) findViewById(R.id.notice_content);
        mContent.setText(mNotice.getContent());

        mHighlight = (CheckBox) findViewById(R.id.notice_highlight);
        mHighlight.setChecked(mNotice.isHighlighted());

        mFirstDate = (TextView) findViewById(R.id.notice_first_date);
        mFirstDate.setText(mNotice.getFirstDateToString());

        mModifiedDate = (TextView) findViewById(R.id.notice_modified_date);
        mModifiedDate.setText(mNotice.getModifiedDateToString());
    }

}
