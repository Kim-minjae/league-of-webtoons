package com.naver.low.services;

import com.naver.low.entities.User;
import com.naver.low.entities.Webtoon;
import com.naver.low.exceptions.ResourceNotFoundException;
import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.payloads.WebtoonSummary;
import com.naver.low.repositories.UserRepository;
import com.naver.low.repositories.WebtoonRepository;
import com.naver.low.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class WebtoonService {


    private WebtoonRepository webtoonRepository;
    private UserRepository userRepository;

    @Transactional
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
        webtoonRepository.save(webtoon);

    }

    // need to delete the previous files
    @Transactional
    public void updateWebtoon(CreateWebtoonRequest updateWebtoonRequest, Long webtoonId) throws IOException {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoon_id", webtoonId));
        System.out.println("original one: " + webtoon.getWebtoonTitle() + ", " + webtoon.getWebtoonDescription() + ", " + webtoon.getWebtoonImage() + ", " + webtoon.getWebtoonThumbnail());
        System.out.println("request: " + updateWebtoonRequest.toString());
        webtoon.setWebtoonTitle(updateWebtoonRequest.getWebtoonTitle());
        webtoon.setWebtoonDescription(updateWebtoonRequest.getWebtoonDescription());
        MultipartFile[] files = new MultipartFile[2];
        files[0] = updateWebtoonRequest.getWebtoonImage();
        files[1] = updateWebtoonRequest.getWebtoonThumbnail();
        String[] uploadedFiles = saveFiles(files, webtoon);
        if (uploadedFiles[0] != null) webtoon.setWebtoonImage(uploadedFiles[0]);
        if (uploadedFiles[1] != null) webtoon.setWebtoonThumbnail(uploadedFiles[1]);
        System.out.println("updated one: " + webtoon.getWebtoonTitle() + ", " + webtoon.getWebtoonDescription() + ", " + webtoon.getWebtoonImage() + ", " + webtoon.getWebtoonThumbnail());
        webtoonRepository.save(webtoon);
    }

    @Transactional
    public void deleteWebtoon(Long webtoonId) throws IOException {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(() -> new ResourceNotFoundException("Webtoon", "webtoonid", webtoonId));
        String[] paths = {webtoon.getWebtoonImage(), webtoon.getWebtoonThumbnail()};
        deleteFiles(paths);
        webtoonRepository.deleteById(webtoonId);
    }

    //test 해봐야함. 페이지 리퀘스트도 만들어야함
    public Page<Webtoon> getAllWebtoonsAll(Pageable pageable) throws IOException {
        Page<Webtoon> webtoonPage = webtoonRepository.findAll(pageable);
        return webtoonPage;
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

    private String[] saveFiles(MultipartFile[] files, Webtoon webtoon) throws IOException {
        String[] uploadedFiles = new String[2];
        for (int i = 0; i < 2; i++) {
            if (files[i] == null) continue;
            byte[] bytes = files[i].getBytes();
            int indexOfDot = files[i].getOriginalFilename().lastIndexOf('.');
            String ext = files[i].getOriginalFilename().substring(indexOfDot);
            Path path = Paths.get("/Users/augustine/webtoons/" + "webtoonist_" + webtoon.getWebtoonist().getId() + (i == 0 ? "_webtoon" : "_thumbnail") + ext);
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
