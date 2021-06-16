package com.fileshare.files.controller;

import com.fileshare.files.model.FileEntity;
import com.fileshare.files.service.FileService;
import com.fileshare.files.vo.FileVO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="api/files")
public class FileController {


    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public ResponseEntity<List<FileEntity>> getAllFiles(){
        return fileService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable("id") Long id){
        return fileService.findById(id);
    }

    @PostMapping(value="/",produces={"application/json"},
            consumes = {"multipart/form-data"})
    public ResponseEntity<FileEntity> saveFile(@RequestParam("File") MultipartFile file,
                                               @RequestParam("display_name") String display_name,
                                               @RequestParam("year") int year,
                                               @RequestParam("filepool") Long filepool
                                               ) throws IOException {
        FileEntity fileEntity = new FileEntity(display_name,year,filepool);
        return fileService.saveFile(file,fileEntity);
    }

    @PutMapping(value="/{id}",produces={"application/json"},
            consumes = {"multipart/form-data"})
    public ResponseEntity<FileEntity> updateFile(@PathVariable("id") Long id,
                                                 @RequestParam("File") MultipartFile file,
                                                 @RequestParam("request") FileEntity fileEntity) throws IOException {
        return fileService.updateFile(id,file,fileEntity);
    }
}
