package com.MzansiBuilds.factory;

import com.MzansiBuilds.domain.Comment;
import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.util.Helper;

import java.time.LocalDateTime;

public class CommentFactory {

    public static Comment createComment(String content, LocalDateTime createdAt, Developer developer, Project project){
        if (Helper.isNullOrEmpty(content)
                || !Helper.isValidTimeStamp(createdAt)
                || developer == null
                || project == null
        ) {
            return null;
        }

        return new Comment.CommentBuilder()
                .setContent(content)
                .setDeveloper(developer)
                .setProject(project)
                .build();
    }
}
