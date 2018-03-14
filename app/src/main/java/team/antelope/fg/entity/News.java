package team.antelope.fg.entity;

/**
 * Created by ericliu on 12/10/16.
 */

public class News {
    private String newsTitle;
    private String newsBody;

    public News() {
    }

    public News(String newsTitle, String newsBody) {
        this.newsTitle = newsTitle;
        this.newsBody = newsBody;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsTitle='" + newsTitle + '\'' +
                ", newsBody='" + newsBody + '\'' +
                '}';
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }
}
