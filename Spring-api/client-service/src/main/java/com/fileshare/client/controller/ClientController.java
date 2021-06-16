package com.fileshare.client.controller;

import com.fileshare.client.Vo.ResponseTemplate;
import com.fileshare.client.model.Client;
import com.fileshare.client.repository.ClientRepository;
import com.fileshare.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseTemplate>> getAllClients(){
        List<ResponseTemplate> responseTemplateList = clientService.getAllClientsWithFilepools();
        if (responseTemplateList!=null){
            return new ResponseEntity<>(responseTemplateList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> getClientById(@PathVariable("id") Long clientId){
        ResponseTemplate responseTemplate = clientService.getClientWithFilepools(clientId);
        if (responseTemplate!=null){
            return new ResponseEntity<>(responseTemplate, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        Client newClient = new Client();
        if(client.getClient_name()!=null){
            newClient.setClient_name(client.getClient_name());
            if (client.getFile_tree()==null){
                newClient.setFile_tree("{\"display\":\"root/\",\"filepool\":0,\"subtrees\":[]}");
            }
            else{
                newClient.setFile_tree(client.getFile_tree());
            }

            return new ResponseEntity<>(clientService.saveClient(newClient),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long clientId,
                                               @RequestBody Client client){
        Client newClient = clientService.getClientWithFilepools(clientId).getClient();
        int both_null = 2;
        if(client.getClient_name()!=null){
            newClient.setClient_name(client.getClient_name());
        }
        else{both_null-=1;};
        if (client.getFile_tree()!=null){
            newClient.setFile_tree(client.getFile_tree());
        }
        else{both_null-=1;};
        if (both_null !=0) {
            return new ResponseEntity<>(clientService.saveClient(newClient), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
