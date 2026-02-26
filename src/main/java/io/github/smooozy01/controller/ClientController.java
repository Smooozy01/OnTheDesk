package io.github.smooozy01.controller;

import io.github.smooozy01.dto.ClientBalanceDTO;
import io.github.smooozy01.dto.ClientDTO;
import io.github.smooozy01.facade.ClientCarFacade;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("clients")
public class ClientController {
    
    private final ClientCarFacade facade;
    public ClientController(ClientCarFacade facade) { this.facade = facade; }
    
    
    @GetMapping
    public Page<ClientBalanceDTO> getClients(
            @RequestParam(required = false) String clientName, 
            @RequestParam(required = false) String carName,
            @PageableDefault(page = 0, size = 10) Pageable pageable)
        {
        
        return facade.getClients(clientName, carName, pageable);
    }
    
    
    @GetMapping("{id}")
    public ClientBalanceDTO getClientById(@PathVariable int id) {
        return facade.getClientByID(id);
    }
    
    
    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody @Valid ClientDTO clientDTO) {
        
        return facade.createClient(clientDTO);
    }
    
    
    @PutMapping("{id}")
    public ResponseEntity<String> updateClient(@PathVariable int id, 
                                               @RequestBody ClientDTO clientDTO) {
        
        return facade.updateClient(id, clientDTO);
    }
    
    
    @DeleteMapping("{id}")    
    public ResponseEntity<String> deleteClient(@PathVariable int id) {
        return facade.deleteClient(id);
    }
                     
}
