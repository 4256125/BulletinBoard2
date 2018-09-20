package bulletinboard.htbeyond.com.bulletinboard.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import bulletinboard.htbeyond.com.bulletinboard.models.Notice;

public class NoticeListJSONWrapper {

    private static final String TAG = "NoticeListJSONWrapper";

    protected JSONObject mJSONObject;
    protected List<Notice> mNotices;

    private final String PAGE = "content";
    private final String TOTAL_ELEMENTS = "totalElements";
    private final String TOTAL_PAGES = "totalPages";
    private final String FIRST = "first";
    private final String LAST = "last";
    private final String PAGE_NUMBER = "number";
    private final String SIZE_OF_PAGE = "size";

    private final String NOTICE_ID = "noticeNum";
    private final String TITLE = "noticeTitle";
    private final String CONTENT = "noticeContent";
    private final String FIRST_DATE = "noticePostDate";
    private final String MODIFIED_DATE = "noticeEditDate";
    private final String VIEWS = "noticeViewNum";
    private final String WRITER = "noticeMemId";
    private final String HIGHLIGHTED = "highlight";

    private final String YEAR = "year";
    private final String MONTH = "month";
    private final String DAY_OF_MONTH = "monthValue";
    private final String HOUR = "hour";
    private final String MINUTE = "minute";


    public NoticeListJSONWrapper(JSONObject jsonObject) {
        mJSONObject = jsonObject;
    }


    public boolean isFirst() {
        boolean first = false;
        try {
            first = mJSONObject.getBoolean(FIRST);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from FIRST :\n" + getExceptionToString(e));
        }
        return first;
    }

    public boolean isLast() {
        boolean last = false;
        try {
            last = mJSONObject.getBoolean(LAST);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from LAST :\n" + getExceptionToString(e));
        }
        return last;
    }

    public Notice getNotice(JSONObject jsonObject) {
        Notice notice = new Notice();

        int noticeId = 0;
        String title = null;
        String content = null;
        Date firstDate = null;
        Date modifiedDate = null;
        int views = 0;
        String writer = null;
        boolean highlighted = false;

        try {
            noticeId = jsonObject.getInt(NOTICE_ID);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from NOTICE_ID :\n" + getExceptionToString(e)); }
        try {
            title = jsonObject.getString(TITLE);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from TITLE :\n" + getExceptionToString(e)); }
        try {
            content = jsonObject.getString(CONTENT);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from CONTENT :\n" + getExceptionToString(e)); }
        try {
            firstDate = getDate(
                    jsonObject.getJSONObject(FIRST_DATE));
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from FIRST_DATE :\n" + getExceptionToString(e)); }
        try {
            modifiedDate = getDate(
                    jsonObject.getJSONObject(MODIFIED_DATE));
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from MODIFIED_DATE :\n" + getExceptionToString(e));
        }
        try {
            views = jsonObject.getInt(VIEWS);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from VIEWS :\n" + getExceptionToString(e));
        }
        try {
            writer = jsonObject.getString(WRITER);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from WRITER :\n" + getExceptionToString(e));
        }
        try {
            highlighted = jsonObject.getBoolean(HIGHLIGHTED);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from HIGHLIGHTED :\n" + getExceptionToString(e));
        }

        notice.setNoticeId(noticeId)
                .setTitle(title)
                .setContent(content)
                .setFirstDate(firstDate)
                .setModifiedDate(modifiedDate)
                .setViews(views)
                .setWriter(writer)
                .setHighlighted(highlighted);

        return notice;
    }

    public List<Notice> getNotices() {
        if (mNotices != null) {
            return mNotices;
        }
        mNotices = new ArrayList<>();
        JSONArray page = null;
        int length = 0;
        try {
            page = mJSONObject.getJSONArray(PAGE);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from PAGE :\n" + getExceptionToString(e));
        }

        length = page.length();
        if (length != 0) {
            for (int i = 0; i < length; i++) {
                JSONObject jsonNotice = null;
                try {
                    jsonNotice = page.getJSONObject(i);
                } catch (JSONException e) {
                    Log.w(TAG, "catched JSONException from 'Notice' JSON file :\n" + getExceptionToString(e));
                }
                mNotices.add(getNotice(jsonNotice));
            }
        }

        return mNotices;
    }

    public int getPageNumber() {
        int number = 0;
        try {
            number = mJSONObject.getInt(PAGE_NUMBER);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from PAGE_NUMBER :\n" + getExceptionToString(e));
        }
        return number;
    }

    public int getTotalNumberOfPages() {
        int num = 0;
        try {
            num = mJSONObject.getInt(TOTAL_PAGES);
        } catch (JSONException e) {
            Log.w(TAG, "catched JSONException from TOTAL_PAGES :\n" + getExceptionToString(e));
        }
        return num;
    }

    private Date getDate(JSONObject localDateTime) throws JSONException {
        int year = localDateTime.getInt(YEAR);
        int month = localDateTime.getInt(MONTH);
        int dayOfMonth = localDateTime.getInt(DAY_OF_MONTH);
        int hour = localDateTime.getInt(HOUR);
        int minute = localDateTime.getInt(MINUTE);

        return new GregorianCalendar(year, month, dayOfMonth, hour, minute)
                .getTime();
    }

    private String getExceptionToString(JSONException e) {
        Writer writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

}
