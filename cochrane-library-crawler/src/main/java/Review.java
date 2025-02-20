public class Review {
    private String title;
    private String url;
    private String authors;
    private String date;

    public Review(String title, String url, String authors, String date) {
        this.title = title;
        this.url = url;
        this.authors = authors;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
}
