package com.naver.low.repositories;

import com.naver.low.entities.Webtoonist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonistRepository extends JpaRepository<Webtoonist, Long> {
}
