package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // DELETE FROM MOVIE_IMAGE mi WHERE movie_mno=1; ==> 메소드 생성필요
    // delele(), deleteById() ==> Review 의 review_no 기준임
    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 번호를 이용해 리뷰 가져오기
    // 이 메소드 실행 시 join 구문으로 처리해줘
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // @Query(value = "delete from Review r where r.member = :member", nativeQuery =
    // true)

    // 멤버를 기준으로 삭제
    // Review 엔티티에 member 가 들어가 있기 때문에 member 호출
    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(Member member); // review_no 를 기준으로 동작함(리뷰 작성이 많은 사람일 수록 delete 구문 여러번 실행됨)
}