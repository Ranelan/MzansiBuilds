package com.MzansiBuilds.factory;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Milestone;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.util.Helper;

import java.time.LocalDateTime;

public class MilestoneFactory {

    public static Milestone createMilestone(String title, String description, LocalDateTime createdAt, Project project) {
        if (Helper.isNullOrEmpty(title)
                || Helper.isNullOrEmpty(description)
                || !Helper.isValidTimeStamp(createdAt)
                || project == null) {
            return null;
        }

        return new Milestone.MilestoneBuilder()
                .setTitle(title)
                .setDescription(description)
                .setProject(project)
                .build();
    }
}
