package com.naver.low.services;

import com.naver.low.entities.User;
import com.naver.low.entities.Webtoon;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.repositories.UserRepository;
import com.naver.low.repositories.WebtoonRepository;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class WebtoonService {

    private WebtoonRepository webtoonRepository;
    private UserRepository userRepository;

    public void uploadWebtoon(MultipartFile webtoonFile, MultipartFile thumbnailFile, CreateWebtoonRequest createWebtoonRequest, UserPrincipal userPrincipal) throws IOException {
        
        List<String> uploadedFiles = saveFiles(Arrays.asList(files));
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "userid", userPrincipal.getId()));
        Webtoon webtoon = new Webtoon(createWebtoonRequest.getWebtoonTitle(), createWebtoonRequest.getWebtoonDescription(),
                createWebtoonRequest.getWebtoonThumbnail(), createWebtoonRequest.getWebtoonImage(), user);

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
