package com.ntt_data.backend_challenge.client_person.web.controller;

import com.ntt_data.backend_challenge.client_person.application.dto.ClientDTO;
import com.ntt_data.backend_challenge.client_person.application.usecases.ClientUseCase;
import com.ntt_data.backend_challenge.common.dtos.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    private final ClientUseCase clientUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getAllClients() {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Clients retrieved successfully", clientUseCase.getAllClients()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Client retrieved successfully", clientUseCase.getClientById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientDTO>> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.created(URI.create("/v1/clients")).body(ApiResponse.success(HttpStatus.CREATED, "Client created successfully", clientUseCase.createClient(clientDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Client updated successfully", clientUseCase.updateClient(id, clientDTO)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Long id) {
        clientUseCase.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(HttpStatus.NO_CONTENT, "Client deleted successfully", null));
    }
}

