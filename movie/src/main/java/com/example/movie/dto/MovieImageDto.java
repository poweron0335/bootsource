package com.example.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class MovieImageDto {

    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

}
