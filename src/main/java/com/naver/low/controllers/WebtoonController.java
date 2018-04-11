package com.naver.low.controllers;

import com.naver.low.entities.Webtoon;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.ApiResponse;
import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.payloads.WebtoonInfo;
import com.naver.low.repositories.WebtoonRepository;
import com.naver.low.security.CurrentUser;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    public ResponseEntity<ApiResponse> uploadWebtoon (@RequestParam("webtoonfile") MultipartFile webtoonFile,
                                                      @RequestParam("thumbnailfile") MultipartFile thumbnailFile,
                                                      @RequestBody CreateWebtoonRequest createWebtoonRequest,
                                                      @CurrentUser UserPrincipal curentUser) {
        /*if (files == null) {
            // when a user doesn't attach a file, which http status code should be returned?
            return ResponseEntity.ok(new ApiResponse(false, "please select a file"));
        }*/

        /*try {
            saveFiles(Arrays.asList(files));
        } catch (IOException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }*/

        return ResponseEntity.ok(new ApiResponse(true, "Successfully uploaded"));
    }



    //기본 crud
    @GetMapping("/{id}")
    public WebtoonInfo getWebtoonById(@PathVariable(value = "id") Long id) {

        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoon_id", id));
        return new WebtoonInfo(
                webtoon.getId(),
                webtoon.getWebtoonTitle(),
                webtoon.getWebtoonDescription(),
                webtoon.getWebtoonImage(),
                webtoon.getWebtoonHits(),
                webtoon.getWebtoonLikedByAccounts().size(),
                webtoon.getWebtoonist().getUserName());
    }

    //작가만 자신의 웹툰을 업데이트 할 수 있도록 하려함,
    //webtoonRepository 에 webtoon_id 로 User id뽑아오는 로직을 만들어서 대입
    @PatchMapping("/{id}")
    @PreAuthorize("(#currentUser.id == webtoonRepository.findById_(id).webtoonist.id)")
    public ResponseEntity<ApiResponse> updateWebtoonThisId(@Valid CreateWebtoonRequest updateWebtoonRequest, @CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long id) {
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoon_id", id));
        webtoon.setWebtoonDescription(updateWebtoonRequest.getWebtoonDescription());
        webtoon.setWebtoonImage(updateWebtoonRequest.getWebtoonImage());
        webtoon.setWebtoonThumbnail(updateWebtoonRequest.getWebtoonThumbnail());
        webtoon.setWebtoonTitle(updateWebtoonRequest.getWebtoonTitle());

        webtoonRepository.save(webtoon);
        return ResponseEntity.ok(new ApiResponse(true, "Webtoon updated successfully."));
    }

    //작가와 ADMIN만 웹툰을 지울 수 있도록 함.
    @DeleteMapping("/{id}")
    @PreAuthorize("(#currentUser.id == webtoonRepository.findById_(id).webtoonist.id) or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteWebtoonById(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long id) {
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoon_id", id));
        webtoonRepository.delete(webtoon);
        return ResponseEntity.ok(new ApiResponse(true, "Webtoon deleted successfully."));
    }

    //모든 웹툰을 보여줘


    //나의 모든 웹툰을 조회



}
