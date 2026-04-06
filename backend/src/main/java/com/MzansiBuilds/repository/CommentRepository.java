package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByProjectProjectId(Integer projectId);
    List<Comment> findByDeveloperDeveloperId(Integer developerId);
}
