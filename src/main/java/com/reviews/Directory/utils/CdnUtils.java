package com.reviews.Directory.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
public class CdnUtils {
    private static Optional<File> convertFile(MultipartFile multipartFile) {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

//            File file = new File(String.valueOf(nonNull(multipartFile.getOriginalFilename())));
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
            return Optional.of(file);
        } catch (IOException e) {
            log.error("{} found while converting File : {}", e.getClass().getSimpleName(), e);
            return Optional.empty();

        }
    }

    @Data
    public static class CDNResponse {
        private String message;
        private List<String> data;
    }

    public static Optional<String> uploadFile(MultipartFile multipartFile) {
        try {
            Optional<File> optionalFile = convertFile(multipartFile);
            if (!optionalFile.isPresent()) {
                return Optional.empty();
            }

            File file = optionalFile.get();
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("files", multipartFile.getOriginalFilename(), RequestBody.create(file.getAbsoluteFile(), MediaType.parse("application/octet-stream"))).build();
            Request request = new Request.Builder()
                    .addHeader("user", "richard.robert@fasthub.co.tz")
                    .addHeader("pass", "2WIHR3Q9")
                    .addHeader("account", "fasthub_cms")
                    .post(body)
                    .url("http://cdn.fasthub.co.tz/api/user/files")
//                    .url("http://192.168.66.12:9696/api/user/files")

                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            Response response = okHttpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                log.error("We failed tp upload the file");
                return Optional.empty();
            }

            String string = response.body().string();
            CDNResponse cdnResponse = new ObjectMapper().readValue(string, CDNResponse.class);
            log.info("{}",cdnResponse);
            return Optional.of(cdnResponse.data.get(0));

        } catch (Exception e) {
            log.error("{} found while uploading file : ", e.getClass().getSimpleName(), e);
            return Optional.empty();
        }
    }
}
