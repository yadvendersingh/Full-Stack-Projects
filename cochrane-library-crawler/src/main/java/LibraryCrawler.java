import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LibraryCrawler {

    public static void main(String[] args) {

        //Reading Property file to store paths
        Properties dictionary = new Properties();
        try (FileReader reader = new FileReader("src/main/resources/paths.properties")) {
            dictionary.load(reader);
        }
        catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            System.exit(1);
        }
        
        System.setProperty("webdriver.chrome.driver", dictionary.getProperty("webdriver.chrome.driver"));
        String outputFile = dictionary.getProperty("output.file");
        
        // Clear output file and create the directory if it doesn't exist
        Utils.clearOutputDirectory(outputFile);
        Utils.createOutputFile(outputFile);

        // Set up Chrome options and initialize WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        // Get the Cochrane Library URL
        String libraryUrl = "https://www.cochranelibrary.com/cdsr/reviews/topics";
        
        int maxLimit = 100;
        try{
            maxLimit = Integer.parseInt(dictionary.getProperty("max.reviews"));
        } catch (NumberFormatException e) {
            System.err.println("Invalid max limit. Using default limit of 100");
        }

        try {
            driver.get(libraryUrl);
            System.out.println("HTML successfully retrieved!");
            List<Topic> topics = getTopics(driver);
            System.out.println("Found " + topics.size() + " topics.");
            for (Topic topic : topics) {
                getReviews(driver, topic, maxLimit);
                writeReviewToCSV(outputFile, topic);
                topic.clearReviews();       // Clear reviews to save memory
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static List<Topic> getTopics(WebDriver driver) {
        List<Topic> topics = new ArrayList<>();
        List<WebElement> listItems = driver.findElements(By.cssSelector("li.browse-by-list-item"));

        for (WebElement listItem : listItems) {
            WebElement link = listItem.findElement(By.tagName("a"));
            if (link != null) {
                String url = link.getDomAttribute("href");
                WebElement button = link.findElement(By.tagName("button"));
                if (button != null) {
                    String topicName = button.getText();
                    topics.add(new Topic(topicName, url));
                }
            }
        }
        return topics;
    }

    private static void getReviews(WebDriver driver, Topic topic, int maxLimit) {
        System.out.println("Getting reviews for topic: " + topic.getTopicName());
        String nextPageUrl = topic.getUrl();
        while (nextPageUrl != null && maxLimit>0) {
            /*if(reviewCount >= 25 && !confirmLimit){
                System.out.println("Review count is greater than "+reviewCount+". Do you want to continue? (y/n/custom limit)");
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("n")) {
                    break;
                } else if (userInput.equalsIgnoreCase("y")) {
                    confirmLimit = true;
                } else {
                    try {
                        int customLimit = Integer.parseInt(userInput);
                        maxLimit = customLimit;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Continuing with default limit");
                    }
                    confirmLimit = true;
                }
                
            }*/
            // Iterating over the review pages and add reviews to the topic object
            driver.get(nextPageUrl);
            String baseUrlString = Utils.getBaseUrl(nextPageUrl);
            List<WebElement> reviewElements = driver.findElements(By.cssSelector("div.search-results-item-body"));
            for (WebElement reviewElement : reviewElements) {
                String reviewTitle = reviewElement.findElement(By.cssSelector("h3.result-title a")).getText();
                String reviewUrl = baseUrlString+reviewElement.findElement(By.cssSelector("h3.result-title a")).getDomAttribute("href");
                String authors = reviewElement.findElement(By.cssSelector("div.search-result-authors div")).getText();
                String date = Utils.formatDate(reviewElement.findElement(By.cssSelector("div.search-result-date div")).getText());
                topic.addReviews(new Review(reviewTitle, reviewUrl, authors, date));
                maxLimit--;
            }
            // Check for the next page
            WebElement nextPageElement;
            try {
                nextPageElement = driver.findElement(By.cssSelector("div.pagination-page-links li.pagination-page-list-item.active + li a"));
            } catch (Exception e) {
                nextPageElement = null;
            }

            nextPageUrl = (nextPageElement != null) ? nextPageElement.getDomAttribute("href") : null;
        }
    }

    private static void writeReviewToCSV(String outputFile, Topic topic) {
        try (FileWriter csvWriter = new FileWriter(outputFile, true)) {
            for (Review review : topic.getReviews()) {
                    csvWriter.append(review.getUrl())
                            .append('|')
                            .append(topic.getTopicName())
                            .append('|')
                            .append(review.getTitle())
                            .append('|')
                            .append(review.getAuthors())
                            .append('|')
                            .append(review.getDate())
                            .append('\n');
                }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    
}
