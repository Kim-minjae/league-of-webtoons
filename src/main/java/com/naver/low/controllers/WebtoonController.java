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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/webtoons")
public class WebtoonController {

    WebtoonRepository webtoonRepository;
    WebtoonService webtoonService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_WEBTOONIST')")
    public ResponseEntity<ApiResponse> uploadWebtoon(@ModelAttribute CreateWebtoonRequest createWebtoonRequest,
                                                     @CurrentUser UserPrincipal currentUser) {
        if (createWebtoonRequest.getWebtoonImage() == null || createWebtoonRequest.getWebtoonThumbnail() == null) {
            // when a user doesn't attach a file, which http status code should be returned?
            return ResponseEntity.ok(new ApiResponse(false, "please select a file"));
        }
        System.out.println(createWebtoonRequest);
        try {
            webtoonService.uploadWebtoon(createWebtoonRequest, currentUser);
        } catch (IOException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Successfully uploaded"));
    }

    @PreAuthorize("permitAll") // not working ;<
    @GetMapping("/{id}")
    public WebtoonInfo getOneWebtoon(@PathVariable(value = "id") Long id) {
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

    // need to be refactored
    @PostMapping("/{id}")
    @PreAuthorize("(@webtoonRepository.findById(#webtoonId).get().getWebtoonist().id == #currentUser.getId())")
    public ResponseEntity<ApiResponse> updateWebtoon(@ModelAttribute CreateWebtoonRequest updateWebtoonRequest,
                                                     @CurrentUser UserPrincipal currentUser,
                                                     @PathVariable(value = "id") Long webtoonId) {
        try {
            webtoonService.updateWebtoon(updateWebtoonRequest, webtoonId);
        } catch (IOException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Webtoon updated successfully."));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(@webtoonRepository.findById(#webtoonId).get().getWebtoonist().id == #currentUser.getId()) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteWebtoonById(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "id") Long webtoonId) {
        try {
            webtoonService.deleteWebtoon(webtoonId);
        } catch (IOException e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Webtoon deleted successfully."));
    }

    //getWebtoonsAll
    @GetMapping("/getWebtoonsAll")
    public Page<Webtoon> getAllWebtoonsAll() throws IOException{
        Pageable pageable = new PageRequest(0,5,Sort.unsorted());
        return webtoonService.getAllWebtoonsAll(pageable);
    }

    //getWebtoonsAllOfUser

}
