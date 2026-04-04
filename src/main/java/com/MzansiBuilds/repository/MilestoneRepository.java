package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {

    List<Milestone> findMilestoneByProjectId(Integer projectId);
}
