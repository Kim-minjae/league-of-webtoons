package com.naver.low.services;

import com.naver.low.entities.User;
import com.naver.low.entities.Webtoon;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.repositories.UserRepository;
import com.naver.low.repositories.WebtoonRepository;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@AllArgsConstructor
@Service
public class WebtoonService {

    private WebtoonRepository webtoonRepository;
    private UserRepository userRepository;

    public void uploadWebtoon(CreateWebtoonRequest createWebtoonRequest, UserPrincipal userPrincipal) throws IOException {
        MultipartFile[] files = new MultipartFile[2];
        files[0] = createWebtoonRequest.getWebtoonImage();
        files[1] = createWebtoonRequest.getWebtoonThumbnail();
        String[] uploadedFiles = saveFiles(files, userPrincipal.getId());
        createWebtoonRequest.setWebtoonImageFileName(uploadedFiles[0]);
        createWebtoonRequest.setWebtoonThumbnailFileName(uploadedFiles[1]);
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "userid", userPrincipal.getId()));
        Webtoon webtoon = new Webtoon(createWebtoonRequest.getWebtoonTitle(), createWebtoonRequest.getWebtoonDescription(),
                createWebtoonRequest.getWebtoonThumbnailFileName(), createWebtoonRequest.getWebtoonImageFileName(), user);

        try {
            webtoonRepository.save(webtoon);
        } catch (Exception e) {
            log.error(e.getMessage());
            deleteFiles(uploadedFiles);
        }

    }

    public void updateWebtoon(CreateWebtoonRequest updateWebtoonRequest, UserPrincipal userPrincipal, Long webtoonId) {
        updateWebtoonRequest.getWebtoonTitle();
        updateWebtoonRequest.getWebtoonDescription();
        updateWebtoonRequest.getWebtoonImage();
        updateWebtoonRequest.getWebtoonThumbnail();
    }

    public void deleteWebtoon(Long webtoonId) throws IOException {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoonid", webtoonId));
        String[] paths = {webtoon.getWebtoonImage(), webtoon.getWebtoonThumbnail()};
        deleteFiles(paths);
        webtoonRepository.deleteById(webtoonId);
    }

    private String[] saveFiles(MultipartFile[] files, Long id) throws IOException {
        String[] uploadedFiles = new String[2];
        for (int i = 0; i < 2; i++) {
            if (files[i].isEmpty()) continue;
            byte[] bytes = files[i].getBytes();
            int indexOfDot = files[i].getOriginalFilename().lastIndexOf('.');
            String ext = files[i].getOriginalFilename().substring(indexOfDot);
            Path path = Paths.get("/Users/augustine/webtoons/" + "webtoonist_" + id + (i == 0 ? "_webtoon" : "_thumbnail") + ext);
            uploadedFiles[i] = path.toString();
            log.info("Uploading - " + uploadedFiles[i]);
            Files.write(path, bytes);
        }
        return uploadedFiles;
    }

    private void deleteFiles(String[] path) throws IOException {
        for (String s : path) {
            log.info("Deleting - " + s);
            Files.delete(Paths.get(s));
        }
    }

}
