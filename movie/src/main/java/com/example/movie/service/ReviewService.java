package com.example.movie.service;

import java.util.List;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewService {

    // 특정 영화의 모든 리뷰 가져오기
    List<ReviewDto> getListOfMovie(Long mno);

    public default ReviewDto entityToDto(Review review) {
        ReviewDto reviewDto = ReviewDto.builder()
                .nickname(review.getMember().getNickname())
                .grade(review.getGrade())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .reviewNo(review.getReviewNo())
                .createdDate(review.getCreatedDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .text(review.getText())
                .mno(review.getMovie().getMno())
                .build();

        return reviewDto;

    }

    public default Review dtoToEntity(ReviewDto reviewDto) {

        Member member = Member.builder().mid(reviewDto.getMid()).build();
        Movie movie = Movie.builder().mno(reviewDto.getMno()).build();

        Review review = Review.builder()
                .grade(reviewDto.getGrade())
                .text(reviewDto.getText())
                .reviewNo(reviewDto.getReviewNo())
                .member(member)
                .movie(movie)
                .build();

        return review;
    }
}
