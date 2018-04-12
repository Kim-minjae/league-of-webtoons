package com.naver.low.controllers;

import com.naver.low.entities.Webtoon;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.ApiResponse;
import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.payloads.WebtoonInfo;
import com.naver.low.payloads.WebtoonSummary;
import com.naver.low.repositories.WebtoonRepository;
import com.naver.low.security.CurrentUser;
import com.naver.low.security.UserPrincipal;
import com.naver.low.services.WebtoonService;
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
    WebtoonService webtoonService;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadWebtoon(@RequestParam("file") MultipartFile[] files,
                                                     @RequestBody CreateWebtoonRequest createWebtoonRequest,
                                                     @CurrentUser UserPrincipal curentUser) {

        return webtoonService.uploadWebtoon(files,createWebtoonRequest,curentUser);
    }

    //basic crud
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

    @DeleteMapping("/{id}")
    @PreAuthorize("(#currentUser.id == webtoonRepository.findById_(id).webtoonist.id) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteWebtoonById(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long id) {
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoon_id", id));
        webtoonRepository.delete(webtoon);
        return ResponseEntity.ok(new ApiResponse(true, "Webtoon deleted successfully."));
    }

    //getWebtoonsAll

    //getWebtoonsAllOfUser

}
