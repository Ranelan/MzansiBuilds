package com.MzansiBuilds.service;

import com.MzansiBuilds.domain.Comment;

import java.util.List;

public interface ICommentService extends IService<Comment, Integer>{

    List<Comment> findByProjectId(Integer projectId);
    List<Comment> findByDeveloperId(Integer developerId);
    List<Comment> findAll();

}
