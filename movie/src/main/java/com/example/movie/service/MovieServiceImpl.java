package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {

        Page<Object[]> result = movieImageRepository.getTotalList(pageRequestDto.getType(), pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("mno").descending()));

        Function<Object[], MovieDto> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]),
                (Long) en[2], (Double) en[3]));

        return new PageResultDto<>(result, function);
    }

    @Override
    public MovieDto getRow(Long mno) {
        List<Object[]> result = movieImageRepository.getMovieRow(mno);

        // result[0] [Movie(mno=3, title=Movie3), MovieImage(inum=9,
        // uuid=38a306e7-8d84-4ebe-83f7-57343a82bc9e, imgName=img4.jpg, path=null), 2,
        // 4.0]
        Movie movie = (Movie) result.get(0)[0];

        // result 길이만큼 반복
        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImages.add(movieImage);
        });

        // List<MovieImage> movieImages = result.stream().map(en ->
        // (MovieImage)en[1]).collect(Collectors.toList());

        Long reviewCnt = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        return entityToDto(movie, movieImages, reviewCnt, avg);

    }

    @Transactional
    @Override
    public void movieRemove(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();

        // 이미지 삭제
        movieImageRepository.deleteByMovie(movie);

        // 리뷰 삭제
        reviewRepository.deleteByMovie(movie);

        // 영화 삭제
        movieRepository.delete(movie);
    }

    @Transactional
    @Override
    public Long movieInsert(MovieDto movieDto) {
        // 영화정보 : title => Movie Entity
        // 이미지 : MovieImage Entity

        // dto ==> entity 변환
        Map<String, Object> entityMap = dtoToEntity(movieDto);

        // movie 삽입
        Movie movie = (Movie) entityMap.get("movie");
        movieRepository.save(movie);

        // movieImage 삽입
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("imgList");
        movieImages.forEach(image -> movieImageRepository.save(image));

        return movie.getMno();
    }

    @Transactional
    @Override
    public Long movieUpdate(MovieDto movieDto) {

        // dto ==> entity 변환
        Map<String, Object> entityMap = dtoToEntity(movieDto);

        // movie 기존 image 제거 후 삽입
        Movie movie = (Movie) entityMap.get("movie");
        movieImageRepository.deleteByMovie(movie);

        // 새로운 movieImage 삽입
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("imgList");
        movieImages.forEach(image -> movieImageRepository.save(image));

        return movie.getMno();

    }

}