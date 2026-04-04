package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Comment;
import com.MzansiBuilds.repository.CommentRepository;
import com.MzansiBuilds.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment read(Integer integer) {
        return commentRepository.findById(integer).orElse(null);
    }

    @Override
    public Comment update(Comment comment) {
        if(comment == null || comment.getCommentId() == 0){
            return null;
        }
        Optional<Comment> existingComment = commentRepository.findById(comment.getCommentId());
        if(existingComment.isPresent()){
            Comment updateComment = new Comment.CommentBuilder().copy(comment)
                    .setContent(comment.getContent())
                    .build();
            return commentRepository.save(updateComment);
        }

        return null;
    }

    @Override
    public List<Comment> findByProjectId(Integer projectId) {
        return commentRepository.findByProjectProjectId(projectId);
    }

    @Override
    public List<Comment> findByDeveloperId(Integer developerId) {
        return commentRepository.findByDeveloperDeveloperId(developerId);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
