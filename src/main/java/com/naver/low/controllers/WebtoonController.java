package com.naver.low.controllers;

import com.naver.low.payloads.ApiResponse;
import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.repositories.WebtoonRepository;
import com.naver.low.security.CurrentUser;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webtoons")
public class WebtoonController {

    WebtoonRepository webtoonRepository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_WEBTOONIST')")
    public ResponseEntity<ApiResponse> uploadWebtoon (@RequestParam("file") MultipartFile[] files,
                                                      @RequestBody CreateWebtoonRequest createWebtoonRequest,
                                                      @CurrentUser UserPrincipal curentUser) {
        if (files == null) {
            // when a user doesn't attach a file, which http status code should be returned?
            return ResponseEntity.ok(new ApiResponse(false, "please select a file"));
        }

        try {
            saveFiles(Arrays.asList(files));
        } catch (IOException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new ApiResponse(true, "Successfully uploaded"));
    }

    private List<String> saveFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            byte[] bytes = file.getBytes();
            Path path = Paths.get("/Users/augustine/webtoons/" + file.getOriginalFilename());
            uploadedFiles.add(path.toString());
            Files.write(path, bytes);
        }
        return uploadedFiles;
    }


}
