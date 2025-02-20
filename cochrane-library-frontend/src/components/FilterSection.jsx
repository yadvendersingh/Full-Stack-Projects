import React from 'react';
import './css/FilterSection.css';

const FilterSection = ({ selectedTopic, totalFilteredReviews, clearFilter }) => {
  return (
    <div className="filter-section">
      <div className="topic-wrapper">
        <p className="topic-label">Topic:</p>
        <div className="topic-container">
          <p className="topic">{selectedTopic}</p>
          <button className="clear-button" onClick={clearFilter}>&times;</button>
        </div>
      </div>
      <p className="count"><b>{totalFilteredReviews}</b> Cochrane Reviews Matching <b>{selectedTopic}</b></p>
    </div>
  );
};

export default FilterSection;