package com.mysite.sbb.repository;

import com.mysite.sbb.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findBySubject(String subject);

    List<Question> findBySubjectLike(String subject);

    boolean existsById(Integer id);
}
