package com.fileshare.filepool.controller;

import com.fileshare.filepool.model.Filepool;
import com.fileshare.filepool.model.FilepoolPage;
import com.fileshare.filepool.model.FilepoolSearchCriteria;
import com.fileshare.filepool.service.FilepoolService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filepools")
public class FilepoolController {

    private final FilepoolService filepoolService;

    public FilepoolController(FilepoolService filepoolService) {
        this.filepoolService = filepoolService;
    }

    @GetMapping
    public ResponseEntity<List<Filepool>> getFilepools(@RequestParam(required = false) String owner_id,
                                                       @RequestParam(required = false) String path,
                                                       FilepoolPage filepoolPage,
                                                       FilepoolSearchCriteria filepoolSearchCriteria){
        if (owner_id!=null) {filepoolSearchCriteria.setOwnerId(owner_id);}
        if (path!=null){filepoolSearchCriteria.setPath(path);}
        return new ResponseEntity<>(filepoolService.getFilepools(filepoolPage, filepoolSearchCriteria).getContent(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Filepool> addFilepool(@RequestBody Filepool filepool){
        Filepool resp = filepoolService.addFilepool(filepool);
        if (resp!=null){
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filepool> getFilepool(@PathVariable("id") Long id){
        Optional<Filepool> filepoolOptional= filepoolService.getFilepool(id);
        if (filepoolOptional.isPresent()){
            return new ResponseEntity<>(filepoolOptional.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
