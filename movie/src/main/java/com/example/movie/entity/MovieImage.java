package com.example.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class MovieImage {

    // inum(id, 시퀀스)
    // uuid, imgName, path - (String)
    @SequenceGenerator(name = "movie_image_seq_gen", sequenceName = "movie_image_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_image_seq_gen")
    @Id
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
