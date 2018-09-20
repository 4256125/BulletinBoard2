package bulletinboard.htbeyond.com.bulletinboard.models;

import java.util.ArrayList;

import bulletinboard.htbeyond.com.bulletinboard.network.NoticeRepo;

public class NoticeList {
    private ArrayList<NoticeRepo> noticeReposList = new ArrayList<>();

    public ArrayList<NoticeRepo> getNoticeRepoList() {
        return noticeReposList;
    }

    public void setNoticeRepoList(ArrayList<NoticeRepo> noticeReposList){
        this.noticeReposList = noticeReposList;
    }
}
