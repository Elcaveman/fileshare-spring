package com.fileshare.client.service;

import com.fileshare.client.Vo.Filepool;
import com.fileshare.client.Vo.FilepoolList;
import com.fileshare.client.Vo.ResponseTemplate;
import com.fileshare.client.model.Client;
import com.fileshare.client.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final RestTemplate restTemplate;

    public ClientService(ClientRepository clientRepository, RestTemplate restTemplate) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }

    public Client saveClient(Client client){
        if(client !=null && client.getClient_name() !=null && client.getFile_tree()!=null) {
            log.info("ClientService : Client saved!");
            return clientRepository.save(client);
        }
        else {
            log.warn("ClientService : Bad Request!");
            return null;
        }
    }

    public List<ResponseTemplate> getAllClientsWithFilepools(){
        List<Client> clients = clientRepository.findAll();
        log.info("ClientService : All Clients data fetched");
        List<ResponseTemplate> responseTemplateList = new ArrayList<>();
        for(Client client:clients){
            responseTemplateList.add(getClientWithFilepools(client.getId()));
        }
        return  responseTemplateList;
    }

    public ResponseTemplate getClientWithFilepools(Long id){
        ResponseTemplate responseTemplate = new ResponseTemplate();
        Client client = clientRepository.getById(id);
        log.info("ClientService : Initiating HTTP call to Filepool-service");
        ResponseEntity<List<Filepool>> filepoolsResponse = restTemplate.exchange(
                "http://FILEPOOL-SERVICE/api/filepools/?owner_id=" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Filepool>>() {}
        );

        responseTemplate.setClient(client);
        if (filepoolsResponse != null && filepoolsResponse.hasBody()){
            log.info("ClientService : Results of the call available..");
            List<Filepool> filepools = filepoolsResponse.getBody();
            for (Filepool filepool:filepools){
                responseTemplate.addFilepool(filepool.getId());
            }
            log.info("ClientService : Response Template is made!");
        }
        else{
            log.warn("ClientService : HTTP call failed!");
        }
        return responseTemplate;

    }
}
