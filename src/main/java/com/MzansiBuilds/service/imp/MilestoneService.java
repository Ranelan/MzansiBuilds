package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Milestone;
import com.MzansiBuilds.repository.MilestoneRepository;
import com.MzansiBuilds.service.IMilestone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneService implements IMilestone {

     @Autowired
        private MilestoneRepository milestoneRepository;

    @Override
    public Milestone create(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    @Override
    public Milestone read(Integer integer) {
        return milestoneRepository.findById(integer).orElse(null);
    }

    @Override
    public Milestone update(Milestone milestone) {
        if(milestone == null || milestone.getMilestoneId() == 0){
            return null;
        }
        Optional<Milestone> existingMilestone = milestoneRepository.findById(milestone.getMilestoneId());
        if(existingMilestone.isPresent()){
            Milestone updateMilestone = new Milestone.MilestoneBuilder().copy(existingMilestone.get())
                    .setTitle(milestone.getTitle())
                    .setDescription(milestone
                            .getDescription()).build();
            return milestoneRepository.save(updateMilestone);
        }
        return null;
    }

     @Override
     public List<Milestone> findByProjectId(Integer projectId) {
         return milestoneRepository.findByProjectProjectId(projectId);
     }

     @Override
     public List<Milestone> findAll() {
         return milestoneRepository.findAll();
     }

     @Override
    public void delete(Integer id) {
        milestoneRepository.deleteById(id);
    }
}
