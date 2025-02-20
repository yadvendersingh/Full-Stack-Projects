# Library Crawler Project Documentation
## Overview
The Library Crawler project is a Java-based web scraping tool that uses Selenium WebDriver to extract information about topics and reviews from the Cochrane Library website as per the provided requirements

## Table of Contents
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Reasoning Behind these choice of libraries](#reasoning-behind-these-libraries)
- [Setup](#setup)
- [Code Explanation](#code-explanation)

## Project Structure
The project consists of the following main components:

- LibraryCrawler.java: The main class that initializes the web driver, fetches topics, and retrieves reviews.
- Utils.java: A utility class that provides helper methods for file operations and date formatting.
- Topic.java: A class representing a topic with its associated reviews.
- Review.java: A class representing a review with its details.

## Dependencies
The project uses the following dependencies:

Selenium WebDriver
ChromeDriver
Maven Shade Plugin (for creating a fat JAR)

## Reasoning behind these libraries:
I tried with Jsoup but it was working fine for first page reviews indexing for each topic. Next page could not be loaded using Jsoup even with the link to next page as it was dynamically generated.
So I went with Selenium with a tradeoff in speed of crawling the website.
Maven Shade Plugin is included so that selenium libraries are packed with the executable jar.
Currently, the limit for reviews is set to 100 reviews per topic but it can be configured in 'path.properties' file available in 'resources' directory.

## Setup
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven
- Chrome browser
- ChromeDriver (compatible with your Chrome browser version)

### Update Properties
```
webdriver.chrome.driver=path to chromedriver
output.file=path to output directory
max.reviews=100
```

### How to build and run 
Go to project directory and build the project using Maven
```
cd cochrane-library-crawler
mvn clean
mvn clean install
```

Run the program
```
java -jar target/library-crawler-1.0-SNAPSHOT.jar
```


## Code Explanation
1. LibraryCrawler.java
This is the main class that initializes the web driver and the website crawling. Following are the methods available in this class:
    - main(String[] args): This method initializes the web driver, properties and creates the output directory. After these initialization steps, the website crawling is started.
    - getTopics(WebDriver driver): This method fetches the list of all the Topic objects with properties - TopicName and URL
    - getReviews(WebDriver driver, Topic topic, int maxLimit): This method retrieves all the reviews upto the maxLimit and add the reviews to respective Topic object
    - writeReviewToCSV(String outputFile, Topic topic): This method writes the collected reviews and respective Topic to a CSV file

2. Topic.java
This class represents the Topic object with properties such as topicName, url, and List of Review Objects along with the getter and setter methods

3. Review.java
This class represents the Review object with properties such as title, url, authors, and date along with the getter and setter methods

4. Utils.java
This class provides utility methods for file operations, date formatting and base url.