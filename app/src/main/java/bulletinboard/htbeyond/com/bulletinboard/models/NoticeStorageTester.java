package bulletinboard.htbeyond.com.bulletinboard.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class NoticeStorageTester {
    private static NoticeStorageTester sNoticeStorage;

    private List<Notice> mNotices;
    private Context mContext;
    // private HTTP가지고 노는 거 변수

    public static NoticeStorageTester getInstance(Context context) {
        if (sNoticeStorage == null) {
            sNoticeStorage = new NoticeStorageTester(context);
        }
        return sNoticeStorage;
    }

    NoticeStorageTester(Context context) {
        mNotices = new ArrayList<>();
        mContext = context.getApplicationContext();
    }

    // 서버에 Notice 추가
    public boolean addNotice(Notice notice) {
        mNotices.add(notice);
        return false;
    }

    public boolean updateNotice(Notice notice) {
        int index = mNotices.indexOf(notice);
        if (index == -1) {
            return false;
        }

        mNotices.set(index, notice);
        return true;
    }

    public boolean deleteNotice(Notice notice) {
        return mNotices.remove(notice);
    }

    public Notice getNotice(int noticeNum) {
        for (int i = 0; i < mNotices.size(); i++) {
            if (mNotices.get(i).getNoticeId() == noticeNum) {
                return mNotices.get(i);
            }
        }
        return null;
    }

    public List<Notice> getNotices() {
        return mNotices;
    }

}
