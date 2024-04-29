package com.example.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

public interface MovieService {

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    MovieDto getRow(Long mno);

    void movieRemove(Long mno);

    Long movieInsert(MovieDto movieDto);

    // [Movie(mno=99, title=Movie99), MovieImage(inum=296,
    // uuid=98fc2592-6d21-4015-a9fe-65a21ec35236, imgName=img0.jpg, path=null), 1,
    // 3.0]
    public default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Long reviewCnt, Double avg) {

        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .title(movie.getTitle())
                .avg(avg != null ? avg : 0.0d)
                .reviewCnt(reviewCnt)
                .build();
        // 영화 상세 조회 => 이미지를 모두 보여주기
        List<MovieImageDto> movieImageDtos = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getImgName())
                    .build();
        }).collect(Collectors.toList());

        movieDto.setMovieImageDtos(movieImageDtos);
        return movieDto;
    }

    // dto => entity
    public default Map<String, Object> dtoToEntity(MovieDto dto) {

        // 생성된 movie entity 를 Map 에 담기 : put()
        Map<String, Object> entityMap = new HashMap<>();

        // Movie Entity 생성
        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();
        entityMap.put("movie", movie);
        // List<MovieIMageDto> movieImageDtos 를
        // List<Movie> 로 변환
        List<MovieImageDto> movieImageDtos = dto.getMovieImageDtos();
        // List<MovieImage> movieImages = new ArrayList<>();
        // if (movieImageDtos != null && movieImageDtos.size() > 0) {
        // for (MovieImageDto mDto : movieImageDtos) {
        // MovieImage movieImage = MovieImage.builder()
        // .imgName(mDto.getImgName())
        // .uuid(mDto.getUuid())
        // .path(mDto.getPath())
        // .build();

        // movieImages.add(movieImage);
        // }

        // }
        // movieImageDtos 리스트가 Null 이 아니고 비어있지 않으면 실행
        if (movieImageDtos != null && movieImageDtos.size() > 0) {
            // movieImageDtos 리스트의 각 요소를 처리하기 위해 스트림 사용
            List<MovieImage> movieImages = movieImageDtos.stream().map(mDto -> {
                // 각 MovieImageDto 객체를 MovieImage 객체로 변혼
                MovieImage movieImage = MovieImage.builder()
                        .imgName(mDto.getImgName()) // MovieImage의 imgName 필드를 설정
                        .uuid(mDto.getUuid()) // MovieImage 의 uuid 필드를 설정
                        .path(mDto.getPath()) // MovieImage 의 path 필드를 설정
                        .movie(movie) // 현재 movie 객체를 MovieImage의 Movie 필드에 설정
                        .build(); // MovieImage 객체를 생성
                return movieImage; // 변환된 MovieImage 객체를 반환
            }).collect(Collectors.toList());

            // 반환된 MovieImage 객체들을 entityMap 에 추가
            entityMap.put("imgList", movieImages);
        }
        // 변환이 끝난 entity list 를 Map 에 담기 : put()
        return entityMap;
    }

}