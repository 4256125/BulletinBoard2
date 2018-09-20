package bulletinboard.htbeyond.com.bulletinboard.network;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bulletinboard.htbeyond.com.bulletinboard.models.Notice;

public class PageRepo {

    @SerializedName("content")
    @Expose
    private List<Content> content = null;
    @SerializedName("last")
    @Expose
    private Boolean last;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("sort")
    @Expose
    private List<Sort> sort = null;
    @SerializedName("first")
    @Expose
    private Boolean first;
    @SerializedName("numberOfElements")
    @Expose
    private Integer numberOfElements;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<Notice> getNotices() {
        List<Notice> notices = new ArrayList<>();
        List<Content> contents = getContent();
        int numberOfElements = getNumberOfElements();
        for (int i = 0; i < numberOfElements; i++) {
            notices.add(contents.get(i).getNotice());
        }

        return notices;
    }

    class Content {

        class Chronology {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("calendarType")
            @Expose
            private String calendarType;

            public String getId() {
                return id;
            }

            public String getCalendarType() {
                return calendarType;
            }

        }

        class NoticeEditDate {

            @SerializedName("month")
            @Expose
            private String month;
            @SerializedName("year")
            @Expose
            private Integer year;
            @SerializedName("dayOfMonth")
            @Expose
            private Integer dayOfMonth;
            @SerializedName("dayOfWeek")
            @Expose
            private String dayOfWeek;
            @SerializedName("dayOfYear")
            @Expose
            private Integer dayOfYear;
            @SerializedName("monthValue")
            @Expose
            private Integer monthValue;
            @SerializedName("hour")
            @Expose
            private Integer hour;
            @SerializedName("minute")
            @Expose
            private Integer minute;
            @SerializedName("nano")
            @Expose
            private Integer nano;
            @SerializedName("second")
            @Expose
            private Integer second;
            @SerializedName("chronology")
            @Expose
            private NoticeRepo.Chronology chronology;

            public String getMonth() {
                return month;
            }

            public Integer getYear() {
                return year;
            }

            public Integer getDayOfMonth() {
                return dayOfMonth;
            }

            public String getDayOfWeek() {
                return dayOfWeek;
            }

            public Integer getDayOfYear() {
                return dayOfYear;
            }

            public Integer getMonthValue() {
                return monthValue;
            }

            public Integer getHour() {
                return hour;
            }

            public Integer getMinute() {
                return minute;
            }

            public Integer getNano() {
                return nano;
            }

            public Integer getSecond() {
                return second;
            }

            public NoticeRepo.Chronology getChronology() {
                return chronology;
            }

            public Date getDate() {
                Date date = new GregorianCalendar(year, monthValue, dayOfMonth, hour, minute)
                        .getTime();
                return date;
            }
        }

        class NoticePostDate {

            @SerializedName("month")
            @Expose
            private String month;
            @SerializedName("year")
            @Expose
            private Integer year;
            @SerializedName("dayOfMonth")
            @Expose
            private Integer dayOfMonth;
            @SerializedName("dayOfWeek")
            @Expose
            private String dayOfWeek;
            @SerializedName("dayOfYear")
            @Expose
            private Integer dayOfYear;
            @SerializedName("monthValue")
            @Expose
            private Integer monthValue;
            @SerializedName("hour")
            @Expose
            private Integer hour;
            @SerializedName("minute")
            @Expose
            private Integer minute;
            @SerializedName("nano")
            @Expose
            private Integer nano;
            @SerializedName("second")
            @Expose
            private Integer second;
            @SerializedName("chronology")
            @Expose
            private NoticeRepo.Chronology chronology;

            public String getMonth() {
                return month;
            }

            public Integer getYear() {
                return year;
            }

            public Integer getDayOfMonth() {
                return dayOfMonth;
            }

            public String getDayOfWeek() {
                return dayOfWeek;
            }

            public Integer getDayOfYear() {
                return dayOfYear;
            }

            public Integer getMonthValue() {
                return monthValue;
            }

            public Integer getHour() {
                return hour;
            }

            public Integer getMinute() {
                return minute;
            }

            public Integer getNano() {
                return nano;
            }

            public Integer getSecond() {
                return second;
            }

            public NoticeRepo.Chronology getChronology() {
                return chronology;
            }

            public Date getDate() {
                Date date = new GregorianCalendar(year, monthValue, dayOfMonth, hour, minute)
                        .getTime();

                return date;
            }
        }

        @SerializedName("noticeNum")
        @Expose
        private Integer noticeNum;
        @SerializedName("noticeTitle")
        @Expose
        private String noticeTitle;
        @SerializedName("noticeContent")
        @Expose
        private String noticeContent;
        @SerializedName("noticePostDate")
        @Expose
        private NoticeRepo.NoticePostDate noticePostDate;
        @SerializedName("noticeEditDate")
        @Expose
        private NoticeRepo.NoticeEditDate noticeEditDate;
        @SerializedName("noticeViewNum")
        @Expose
        private Integer noticeViewNum;
        @SerializedName("noticeMemId")
        @Expose
        private String noticeMemId;
        @SerializedName("noticeAttachedFile")
        @Expose
        private String noticeAttachedFile;
        @SerializedName("highlight")
        @Expose
        private Boolean highlight;

        public Integer getNoticeNum() {
            return noticeNum;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public NoticeRepo.NoticePostDate getNoticePostDate() {
            return noticePostDate;
        }

        public NoticeRepo.NoticeEditDate getNoticeEditDate() {
            return noticeEditDate;
        }

        public Integer getNoticeViewNum() {
            return noticeViewNum;
        }

        public String getNoticeMemId() {
            return noticeMemId;
        }

        public String getNoticeAttachedFile() {
            return noticeAttachedFile;
        }

        public Boolean getHighlight() {
            return highlight;
        }

        @Override
        public String toString() {
            return "NoticeRepo{" +
                    "noticeNum=" + noticeNum +
                    ", noticeTitle='" + noticeTitle + '\'' +
                    ", noticeContent='" + noticeContent + '\'' +
                    ", noticePostDate=" + noticePostDate +
                    ", noticeEditDate=" + noticeEditDate +
                    ", noticeViewNum=" + noticeViewNum +
                    ", noticeMemId=" + noticeMemId +
                    ", noticeAttachedFile=" + noticeAttachedFile +
                    ", highlight=" + highlight +
                    '}';
        }

        public Notice getNotice() {
            Notice notice = new Notice();

            notice.setNoticeId(noticeNum)
                    .setTitle(noticeTitle)
                    .setContent(noticeContent)
                    .setFirstDate(noticePostDate.getDate())
                    .setModifiedDate(noticeEditDate.getDate())
                    .setViews(noticeViewNum)
                    .setWriter(noticeMemId)
                    .setHighlighted(highlight);

            return notice;
        }

    }

    class Sort {

        @SerializedName("direction")
        @Expose
        private String direction;
        @SerializedName("property")
        @Expose
        private String property;
        @SerializedName("ignoreCase")
        @Expose
        private Boolean ignoreCase;
        @SerializedName("nullHandling")
        @Expose
        private String nullHandling;
        @SerializedName("ascending")
        @Expose
        private Boolean ascending;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Boolean getIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(Boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public String getNullHandling() {
            return nullHandling;
        }

        public void setNullHandling(String nullHandling) {
            this.nullHandling = nullHandling;
        }

        public Boolean getAscending() {
            return ascending;
        }

        public void setAscending(Boolean ascending) {
            this.ascending = ascending;
        }

    }

}