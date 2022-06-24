package ru.idgroup.reactive.source.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;
import ru.idgroup.reactive.source.dto.ClientDTO;
import ru.idgroup.reactive.source.dto.PhoneDTO;
import ru.idgroup.reactive.source.model.Address;
import ru.idgroup.reactive.source.model.Client;
import ru.idgroup.reactive.source.model.Phone;
import ru.idgroup.reactive.source.services.ClientMapper;
import ru.idgroup.reactive.source.services.DBServiceClient;
import ru.idgroup.reactive.source.services.PhoneMapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clients")
@Slf4j
public class SourceController {
    private DBServiceClient serviceClient;
    public static final int DATA_PROCESS_THREAD_POOL_SIZE = 5;
    private final ExecutorService executor = Executors.newFixedThreadPool(DATA_PROCESS_THREAD_POOL_SIZE);

    public SourceController(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @GetMapping()
    public Mono<List<ClientDTO>> gelClients() {
        var future = CompletableFuture
                .supplyAsync(() ->
                    serviceClient.findAll().stream()
                    .map(client -> ClientMapper.toDTO(client))
                    .collect(Collectors.toList())
                ,
                executor);

        return Mono.fromFuture(future);
    }

    @GetMapping("/{clientId}")
    public Mono<ClientDTO> gelClient(@PathVariable(value = "clientId", required = false) long clientId) {
        var future = CompletableFuture
                .supplyAsync(() ->
                    serviceClient.findById(clientId)
                    .map(ClientMapper::toDTO)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND))
                ,
                executor);

        return Mono.fromFuture(future);
    }

    @PutMapping("/{clientId}")
    public Mono<ClientDTO> putClient(@PathVariable(value = "clientId") Long clientId,
                                     @RequestBody final ClientDTO client) {
        var future = CompletableFuture
                .supplyAsync(() -> {
                    Client savingClient = serviceClient.getClient(clientId)
                            .map(exClient -> ClientMapper.updateClient(exClient, client))
                            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

                    Client savedClient = serviceClient.saveClient(savingClient);
                    return ClientMapper.toDTO(savedClient);
                },
                executor);

        return Mono.fromFuture(future);
    }

    @PostMapping()
    public Mono<ClientDTO> postClient(@RequestBody final ClientDTO client) {
        var future = CompletableFuture
                .supplyAsync(() -> {
                    Client savingClient = new Client(client.getName(), client.getAddress());
                    Client savedClient = serviceClient.saveClient(savingClient);
                    return ClientMapper.toDTO(savedClient);
                },
                executor);

        return Mono.fromFuture(future);
    }

    @GetMapping("/{clientId}/phones/{phoneId}")
    public Mono<PhoneDTO> getPhone(@PathVariable("clientId") Long clientId, @PathVariable("phoneId") Long phoneId ) {
        var future = CompletableFuture
                .supplyAsync(() -> {
                    return serviceClient.getPhone(phoneId)
                        .map(phone -> PhoneMapper.toDTO(phone))
                        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
                },
                executor);

        return Mono.fromFuture(future);
    }

    @PostMapping("/{clientId}/phones")
    public Mono<PhoneDTO> postPhone(@PathVariable("clientId") Long clientId, @RequestBody PhoneDTO phoneDTO) {
        var future = CompletableFuture
                .supplyAsync(() -> {
                    Phone phone = new Phone(phoneDTO.getId(), phoneDTO.getNumber(), clientId);
                    return PhoneMapper.toDTO(serviceClient.savePhone(phone));
                },
                executor);

        return Mono.fromFuture(future);
    }


}
