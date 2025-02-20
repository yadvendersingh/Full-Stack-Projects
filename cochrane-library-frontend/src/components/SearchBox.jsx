import React from 'react';
import './css/SearchBox.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

const SearchBox = ({ searchInput, setSearchInput, filteredTopics, handleTopicSelect }) => {
  return (
    <div className="search-box">
      <input type="text"
        value={searchInput}
        onChange={(e) => setSearchInput(e.target.value)}
        placeholder="Search by topic..."
      />
      <FontAwesomeIcon icon={faSearch} className="search-icon" />
      {filteredTopics.length > 0 && (
        <div className="suggestions">
          {filteredTopics.map((topic, index) => (
            <div
              key={index}
              className="suggestion-item"
              onClick={() => handleTopicSelect(topic)}
            >
              {topic}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchBox;