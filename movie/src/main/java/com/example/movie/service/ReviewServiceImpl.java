package com.example.movie.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> reviews = repository.findByMovie(movie);

        // List<Review> => List<ReviewDto>
        Function<Review, ReviewDto> fn = review -> entityToDto(review);
        return reviews.stream().map(fn).collect(Collectors.toList());
    }

    @Override
    public Long addReview(ReviewDto reviewDto) {
        Review review = dtoToEntity(reviewDto);
        return repository.save(review).getReviewNo();
    }

    @Override
    public void removeReview(Long reviewNo) {
        repository.deleteById(reviewNo);
    }

    @Override
    public ReviewDto getReview(Long reviewNo) {
        return entityToDto(repository.findById(reviewNo).get());
    }

    @Override
    public Long updateReview(ReviewDto reviewDto) {
        // save() =>
        // 1) select 2) insert or update 결정
        // return repository.save(dtoToEntity(reviewDto)).getReviewNo();

        Optional<Review> result = repository.findById(reviewDto.getReviewNo());

        if (result.isPresent()) {
            Review review = result.get();
            review.setText(reviewDto.getText());
            review.setGrade(reviewDto.getGrade());
            repository.save(dtoToEntity(reviewDto));
        }
        return reviewDto.getReviewNo();
    }

}