import React, { useState, useEffect } from "react";
import SearchBox from "./components/SearchBox";
import ReviewList from "./components/ReviewList";
import FilterSection from "./components/FilterSection";
import reviewsData from "./data/cochrane_reviews.json";
import './App.css';

const App = () => {
  const [searchInput, setSearchInput] = useState("");
  const [filteredTopics, setFilteredTopics] = useState([]);
  const [selectedTopic, setSelectedTopic] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [totalFilteredReviews, setTotalFilteredReviews] = useState(0);
  const [page, setPage] = useState(1);
  const ITEMS_PER_PAGE = 10;

  const flatReviews = reviewsData.flat().filter(review => review && review.topic);

  // Initial reviews load
  useEffect(() => {
    setReviews(flatReviews.slice(0, ITEMS_PER_PAGE));
  }, []);

  // Search functionality
  useEffect(() => {
    if (searchInput.trim()) {
      const uniqueTopics = [...new Set(
        flatReviews
          .filter(review => review && review.topic)
          .map(review => review.topic)
      )];

      const filtered = uniqueTopics.filter(topic =>
        topic.toLowerCase().includes(searchInput.toLowerCase())
      );

      setFilteredTopics(filtered);
    } else {
      setFilteredTopics([]);
    }
  }, [searchInput, flatReviews]);

  // Scroll handler
  useEffect(() => {
    const handleScroll = () => {
      if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight - 100) {
        setPage(prevPage => {
          const nextPage = prevPage + 1;
          const currentReviews = selectedTopic
            ? flatReviews.filter(review => review.topic === selectedTopic)
            : flatReviews;
          const newReviews = currentReviews.slice(0, nextPage * ITEMS_PER_PAGE);
          setReviews(newReviews);
          return nextPage;
        });
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, [selectedTopic, flatReviews]);

  const handleTopicSelect = (topic) => {
    setSelectedTopic(topic);
    setPage(1);
    const filtered = flatReviews.filter(review => review.topic === topic);
    setReviews(filtered); // Override the limit to show all results under the selected topic
    setTotalFilteredReviews(filtered.length); // Set the total number of filtered reviews
    setFilteredTopics([]);
    setSearchInput("");
  };

  const clearFilter = () => {
    setSelectedTopic(null);
    setPage(1);
    setReviews(flatReviews.slice(0, ITEMS_PER_PAGE));
    setTotalFilteredReviews(0);
  };

  return (
    <div className="app">
      <div className="search-section">
        <div className="search-container">
          <SearchBox
            searchInput={searchInput}
            setSearchInput={setSearchInput}
            filteredTopics={filteredTopics}
            handleTopicSelect={handleTopicSelect}
          />
        </div>
      </div>
      {selectedTopic && (
        <FilterSection
          selectedTopic={selectedTopic}
          totalFilteredReviews={totalFilteredReviews}
          clearFilter={clearFilter}
        />
      )}
      <div className="content-section">
        <ReviewList reviews={reviews} />
      </div>
    </div>
  );
};

export default App;