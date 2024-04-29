package com.example.movie.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

// Serializable : 객체 상태로 입출력

@AllArgsConstructor
@Data
public class UploadResultDto implements Serializable {
    // 폴더, uuid, 실 파일명
    private String folderPath;

    private String uuid;

    private String fileName;

    // 저장된 파일의 위치
    public String getImageURL() {
        String fullPath = "";

        try {
            // 경로에 속한 한글이 깨질 수 있기 때문에 인코딩함
            fullPath = URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
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
            thumbFullPath = URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return thumbFullPath;
    }
}
