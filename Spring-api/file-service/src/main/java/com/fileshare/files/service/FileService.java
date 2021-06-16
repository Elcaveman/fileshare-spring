package com.fileshare.files.service;

import com.fileshare.files.model.FileEntity;
import com.fileshare.files.repository.FileRepository;
import com.fileshare.files.vo.Filepool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    String MEDIA_DIRECTORY;
    @Value("${webserver.media-url}")
    String MEDIA_URL;
    @Autowired
    private final FileRepository fileRepository;
    @Autowired
    private final RestTemplate restTemplate;

    public FileService(FileRepository fileRepository, RestTemplate restTemplate) {
        this.fileRepository = fileRepository;
        this.restTemplate = restTemplate;
    }
    public String randomText(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
    public ResponseEntity<FileEntity> saveFile(MultipartFile file, FileEntity fileDetails) throws IOException {
        ResponseEntity<Filepool> filepoolsResponse = restTemplate.exchange(
                "http://localhost:8080/api/filepools/" + fileDetails.getFilepool(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Filepool>() {}
        );
        if (fileDetails.notNull()){
            Filepool filepool = filepoolsResponse.getBody();
            System.out.println("DIR:"+MEDIA_DIRECTORY);
            File myFile = new File(MEDIA_DIRECTORY+file.getOriginalFilename());
            while (myFile.exists()){
                myFile = new File(MEDIA_DIRECTORY+(randomText()+file.getOriginalFilename()));
            }
            myFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(file.getBytes());
            fos.close();
            fileDetails.setFile_path(MEDIA_URL+"spring/"+
                    filepool.getOwner_id()+"/"
                    +file.getOriginalFilename());
            return new ResponseEntity<>(fileRepository.save(fileDetails), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<FileEntity> updateFile(Long id,MultipartFile file, FileEntity fileDetails)
            throws IOException{
        Optional<FileEntity> updatedFile = fileRepository.findById(id);
        if (updatedFile.isPresent()){
            if (fileDetails.getDisplay_name()!=null){
                updatedFile.get().setDisplay_name(fileDetails.getDisplay_name());
            }
            if (fileDetails.getFilepool()!=null){
                updatedFile.get().setFilepool(fileDetails.getFilepool());
            }
            if (fileDetails.getYear()>0){
                updatedFile.get().setYear(fileDetails.getYear());
            }
            ResponseEntity<Filepool> filepoolsResponse = restTemplate.exchange(
                    "http://FILEPOOL-SERVICE/api/filepools/" + updatedFile.get().getFilepool(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Filepool>() {}
            );
            Filepool filepool = filepoolsResponse.getBody();

            if (file!=null){
                File myFile = new File(MEDIA_DIRECTORY+"\\uploads\\"
                        +file.getOriginalFilename());
                myFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(myFile);
                fos.write(file.getBytes());
                fos.close();
                updatedFile.get().setFile_path(MEDIA_URL+"upload/"+
                        filepool.getOwner_id()+"/"
                        +file.getOriginalFilename()
                );
            }
            return new ResponseEntity<>(fileRepository.save(updatedFile.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<List<FileEntity>> findAll() {
        List<FileEntity> fileList = fileRepository.findAll();
        return new ResponseEntity<>(fileList,HttpStatus.OK);
    }

    public ResponseEntity<FileEntity> findById(Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        if (fileEntity.isPresent()){
            return new ResponseEntity<>(fileEntity.get(),HttpStatus.OK);
        }
        else{return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }
}
