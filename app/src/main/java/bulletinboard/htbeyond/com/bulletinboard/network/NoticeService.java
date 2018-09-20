package bulletinboard.htbeyond.com.bulletinboard.network;

import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import bulletinboard.htbeyond.com.bulletinboard.R;
import bulletinboard.htbeyond.com.bulletinboard.models.Notice;
import bulletinboard.htbeyond.com.bulletinboard.models.NoticeStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NoticeService {

    private static final String TAG = "NoticeService";
    public static final int NORMAL_SIZE = 30;
    public static final int MODE_FIND_ALL = 0;
    public static final int MODE_FIND_BY_TITLE = 1;
    public static final int MODE_FIND_BY_CONTENT = 2;
    public static final int MODE_FIND_BY_WRITER = 3;
    public static final int MODE_FIND_BY_HIGHLIGHT = 4;


    private Context mContext;

    public NoticeService(Context context){
        mContext = context;
    }


    public void deleteNotice(int noticeId) {

        final Call<NoticeRepo> res = RetrofitService.getInstance(mContext).getService()
                .deleteNotice("access_token", noticeId);
        res.enqueue(new Callback<NoticeRepo>() {
            @Override
            public void onResponse(Call<NoticeRepo> call, Response<NoticeRepo> response) {
                Log.d(TAG, "deleteNotice() called" + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_delete_success, Toast.LENGTH_SHORT).show();

                    onDo();

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

    public void getNotice(int noticeId) {

        Call<NoticeRepo> res = RetrofitService.getInstance(mContext).getService()
                .getNotice("access_token", noticeId);
        res.enqueue(new Callback<NoticeRepo>() {
            @Override
            public void onResponse(Call<NoticeRepo> call, Response<NoticeRepo> response) {
                Log.d(TAG, "getNoticePage() called" + response.toString());
                if (response.isSuccessful()) {

                    onDo();

                } else {
                    showFailToast();
                }
            }

            @Override
            public void onFailure(Call<NoticeRepo> call, Throwable t) {
                Log.e(TAG, "getNotice() called" + t.getMessage());
                showFailToast();

            }
        });
    }

    public void postNotice(Notice notice) {

        Call<NoticeRepo> res = RetrofitService.getInstance(mContext).getService()
                .postNotice("access_token", notice.getPostBody(Notice.POST));
        res.enqueue(new Callback<NoticeRepo>() {
            @Override
            public void onResponse(Call<NoticeRepo> call, Response<NoticeRepo> response) {
                Log.d(TAG, "postNotice() called" + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.toast_post_success, Toast.LENGTH_SHORT).show();
                    onDo();

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

    public void getNotices(int pageSize, int pageNum) {

        Call<JSONObject> res = RetrofitService.getInstance(mContext).getService()
                .getNotices(mContext.getString(R.string.access_token) , pageSize, pageNum, MODE_FIND_ALL);
        res.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d(TAG, "getNotices() called" + response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_LONG);
                    NoticeListJSONWrapper jsonWrapper = new NoticeListJSONWrapper(response.body());
                    NoticeStorage storage = NoticeStorage.getInstance(mContext);
                    storage.appendNotices(jsonWrapper.getNotices());
                    storage.setLast(jsonWrapper.isLast());
                    onDo();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e(TAG, "getNotices() called" + t.getMessage());
                showFailToast();
            }
        });
    }

    public void updateNotice(Notice notice) {


    }

    private void showFailToast() {
        Toast.makeText(mContext, R.string.toast_failure, Toast.LENGTH_SHORT).show();
    }

    public abstract void onDo();

}
