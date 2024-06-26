package com.example.movie.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieImageDto {
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 저장된 파일의 위치
    public String getImageURL() {
        String fullPath = "";

        try {
            // 경로에 속한 한글이 깨질 수 있기 때문에 인코딩함
            fullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fullPath;
    }

    // 저장된 파일의 위치
    public String getThumbImageURL() {
        String thumbFullPath = "";

        try {
            // 경로에 속한 한글이 깨질 수 있기 때문에 인코딩함
            thumbFullPath = URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return thumbFullPath;
    }
}