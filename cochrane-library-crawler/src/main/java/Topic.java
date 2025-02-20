import java.util.ArrayList;
import java.util.List;

public class Topic{
    private String topicName;
    private String url;
    private List<Review> reviews;

    public Topic(String topicName, String url) {
        this.topicName = topicName;
        this.url = url;
        this.reviews = new ArrayList<>();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReviews(Review review) {
        this.reviews.add(review);
    }
    
    public void clearReviews() {
        this.reviews.clear();
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
}