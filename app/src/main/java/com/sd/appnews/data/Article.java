package com.sd.appnews.data;

import java.io.Serializable;

public class Article  implements Serializable {

    private String id;
    private String source_name; //  "TechCrunch",
    private String author;      //  "Anna Heim",
    private String title;       //  "Stocks with friends",
    private String description; //  "Several startups want to make it easier and friendlier for individuals to buy stocks. But isn't pandemic-era stock picking just a bad habit that'd better be left behind?",
    private String url;         //  "https://techcrunch.com/2022/07/30/stocks-with-friends/",
    private String urlToImage;  //  "https://techcrunch.com/wp-content/uploads/2020/06/NSussman_Techcrunch_Exchange-multicolor.jpg?w=533",
    private String publishedAt; //  "2022-07-30T14:00:43Z",
    private String content;     //  "Welcome to The TechCrunch Exchange, a weekly startups-and-markets newsletter. Its inspired by the daily TechCrunch+ column where it gets its name. Want it in your inbox every Saturday? Sign up here.\r… [+440 chars]"

    public Article(String id,
            String source_name,
            String author,
            String title,
            String description,
            String url,
            String urlToImage,
            String publishedAt,
            String content)
    {
        this.id             = id;
        this.source_name    = source_name;
        this.author         = author;
        this.title          = title;
        this.description    = description;
        this.url            = url;
        this.urlToImage     = urlToImage;
        this.publishedAt    = publishedAt;
        this.content        = content;
    }

    public String getId() {
        return id;
    }
    //_________________________________________________________________________

    public void setId(String id) {
        this.id = id;
    }
    //_________________________________________________________________________

    public String getSource_name() {
        return source_name;
    }
    //_________________________________________________________________________

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }
    //_________________________________________________________________________

    public String getAuthor() {
        return author;
    }
    //_________________________________________________________________________

    public void setAuthor(String author) {
        this.author = author;
    }
    //_________________________________________________________________________

    public String getTitle() {
        return title;
    }
    //_________________________________________________________________________

    public void setTitle(String title) {
        this.title = title;
    }
    //_________________________________________________________________________

    public String getDescription() {
        return description;
    }
    //_________________________________________________________________________

    public void setDescription(String description) {
        this.description = description;
    }
    //_________________________________________________________________________

    public String getUrl() {
        return url;
    }
    //_________________________________________________________________________

    public void setUrl(String url) {
        this.url = url;
    }
    //_________________________________________________________________________

    public String getUrlToImage() {
        return urlToImage;
    }
    //_________________________________________________________________________

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
    //_________________________________________________________________________

    public String getPublishedAt() {
        return publishedAt;
    }
    //_________________________________________________________________________

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    //_________________________________________________________________________

    public String getContent() {
        return content;
    }
    //_________________________________________________________________________

    public void setContent(String content) {
        this.content = content;
    }
    //_________________________________________________________________________

}
//_____________________________________________________________________________


























