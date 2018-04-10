package com.naver.low.repositories;

import com.naver.low.entities.Webtoon;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    //Optional 이 아닌 Webtoon Type 의 반환형을 만들기 위해 만듦
    @Query("SELECT a FROM Webtoon a where a.id = : id")
    public Webtoon findById_(Long id);

}
