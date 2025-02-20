# Cochrane Library Frontend React Application

This project is a frontend application for the Cochrane Library, built using React, HTML and CSS3. The application allows users to search for reviews by topic, filter the results, and view detailed information about each review.

## Table of Contents
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Available Scripts](#available-scripts)
- [Application Flow](#application-flow)
- [How to Run the Program](#how-to-run-the-program)

## Getting Started

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

### Prerequisites

- Node.js and npm

### Application Structure

```
cochrane-library-frontend/
├── public/
├── src/
│   ├── components/
│   │   ├── FilterSection.jsx
│   │   ├── ReviewList.jsx
│   │   ├── SearchBox.jsx
│   │   ├── css/
│   │   │   ├── FilterSection.css
│   │   │   ├── ReviewLists.css
│   │   │   ├── SearchBox.css
│   ├── data/
│   │   └── cochrane_reviews.json
│   ├── App.jsx
│   ├── App.css
│   ├── index.js
├── README.md
├── package.json
```

### Application Flow
#### App Component:

Manages the state of the application, including search input, selected topic, and reviews.
Handles the initial load of reviews, search and filter functionality, and scrolling.

#### SearchBox Component:

Allows users to search for reviews by topic.
Displays a list of filtered topics based on the search input.
Users can select a topic from the suggestions to filter the reviews.

#### FilterSection Component:

Displays the selected topic and the count of filtered results.
Includes a clear button to remove the filter and show all reviews.

#### ReviewList Component:

Displays a list of reviews based on the selected topic or search input.
Each review includes a title, author, and date.

### Installation

1. In the project directory, open terminal and run following commands:
```
cd cochrane-library-frontend
npm install
npm start
```
These commands will install all the dependencies and start the server

2. Go to following link in your browser to access the application
```
http://localhost:3000
```
