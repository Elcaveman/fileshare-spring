package com.fileshare.filepool.service;

import com.fileshare.filepool.model.Filepool;
import com.fileshare.filepool.model.FilepoolPage;
import com.fileshare.filepool.model.FilepoolSearchCriteria;
import com.fileshare.filepool.repository.FilepoolCriteriaRepository;
import com.fileshare.filepool.repository.FilepoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilepoolService {

    private final FilepoolRepository filepoolRepository;
    private final FilepoolCriteriaRepository filepoolCriteriaRepository;

    public FilepoolService(FilepoolRepository filepoolRepository,
                           FilepoolCriteriaRepository filepoolCriteriaRepository) {
        this.filepoolRepository = filepoolRepository;
        this.filepoolCriteriaRepository = filepoolCriteriaRepository;
    }

    public Page<Filepool> getFilepools(FilepoolPage filepoolPage,
                                       FilepoolSearchCriteria filepoolSearchCriteria){
        return filepoolCriteriaRepository.findAllWithFilters(filepoolPage, filepoolSearchCriteria);
    }

    public Filepool addFilepool(Filepool filepool){
        if (filepool!=null && filepool.getOwner_id() !=null){
            return filepoolRepository.save(filepool);
        }
        else{
            return null;
        }
    }
    public Optional<Filepool> getFilepool(Long id){
        return filepoolRepository.findById(id);
    }
}
