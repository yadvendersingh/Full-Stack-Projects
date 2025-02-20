import React from 'react';
import './css/ReviewLists.css';

const ReviewList = ({ reviews }) => {
  if (!reviews || reviews.length === 0) {
    return <div className="no-reviews">No reviews found</div>;
  }

  return (
    <div className="review-list">
      {reviews.map((review, index) => (
        <div key={index} className="review-item">
          <h3>
            <a href={review.url} target="_blank" rel="noopener noreferrer">
              {review.title}
            </a>
          </h3>
          <div className="review-details">
            <p className="author">{review.author}</p>
          </div>
          <p className="date">{review.date}</p>
        </div>
      ))}
    </div>
  );
};

export default ReviewList;