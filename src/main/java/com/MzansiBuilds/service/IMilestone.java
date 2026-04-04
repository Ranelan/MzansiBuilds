package com.MzansiBuilds.service;

import com.MzansiBuilds.domain.Milestone;

import java.util.List;

public interface IMilestone extends IService<Milestone, Integer> {

    List<Milestone> findByProjectId(Integer projectId);
    List<Milestone> findAll();
}
