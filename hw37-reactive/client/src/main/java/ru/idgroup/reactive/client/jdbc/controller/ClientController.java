package ru.idgroup.reactive.client.jdbc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Address;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.idgroup.reactive.client.jdbc.dto.ClientDTO;
import ru.idgroup.reactive.client.jdbc.dto.PhoneDTO;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/clients")
@Slf4j
public class ClientController {
    private final WebClient webClient;

    public ClientController(WebClient.Builder builder) {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }


    @GetMapping
    public String index(Model model) {
        var clients = webClient.get().uri("/clients")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
                    .bodyToMono(List.class);
        model.addAttribute("clients", clients.block());
        return "clients/clients";
    }

    @GetMapping("/form")
    public String addClient(@RequestParam(name = "client_id", required = false) Long clientId, Model model) {
        ClientDTO client = new ClientDTO();
        if ( clientId != null) {
            var clientMono = webClient.get().uri(String.format("/clients/%d", clientId))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ClientDTO.class);
            client = clientMono.block();
        }
        model.addAttribute("client", client);
        return "clients/client-form";
    }

    @GetMapping("/formresult")
    public String clientAddingResult(Model model, @RequestParam(name = "clientid") Long clientId) {
        var clientMono = webClient.get().uri(String.format("/clients/%d", clientId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ClientDTO.class);
        var client = clientMono.block();
        model.addAttribute("client", client);
        return "clients/form-result";
    }

    @PostMapping("/form")
    public String postClientForm(@RequestParam HashMap<String, String> formData, Model model) {

        Long id = null;
        if( StringUtils.hasText(formData.get("id")))
            id = Long.parseLong(formData.get("id"));

        ClientDTO clientDTO = ClientDTO.builder()
                .id(id)
                .address(formData.get("address"))
                .name(formData.get("name"))
                .build();

        Mono<ClientDTO> clientDTOMono;
        if(id == null) {
            clientDTOMono = webClient.post()
                    .uri("/clients")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(clientDTO), ClientDTO.class)
                    .retrieve()
                    .bodyToMono(ClientDTO.class);
        }
        else {
            clientDTOMono = webClient.put()
                    .uri(String.format("/clients/%d", id))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(clientDTO), ClientDTO.class)
                    .retrieve()
                    .bodyToMono(ClientDTO.class);
        }
        ClientDTO savedClient = clientDTOMono.block();
        return "redirect:/clients/formresult?clientid=" + savedClient.getId();
    }

    @GetMapping("/phone-form")
    public String addPhone(@RequestParam("client_id") Long clientId, Model model) {
        model.addAttribute("phone", new PhoneDTO());
        model.addAttribute("clientId", clientId);
        return "clients/phone-form";
    }

    @PostMapping("/phone-form")
    public String editPhone(@RequestParam HashMap<String, String> formData) {

        Long clientId = Long.parseLong(formData.get("clientId"));
        PhoneDTO phone = new PhoneDTO(null, formData.get("number"));
        var phoneDTOMono = webClient.post()
                .uri(String.format("/clients/%d/phones", clientId))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(phone), PhoneDTO.class)
                .retrieve()
                .bodyToMono(PhoneDTO.class);

        PhoneDTO savedPhone = phoneDTOMono.block();
        return String.format("redirect:/clients/phone-form-result?clientid=%d&phoneid=%d", clientId, savedPhone.getId());
    }

    @GetMapping("/phone-form-result")
    public String phoneFormResult(Model model,
                                  @RequestParam(name = "clientid") Long clientId,
                                  @RequestParam(name = "phoneid") Long phoneId
    ) {
        var phoneDTOMono = webClient.get()
                .uri(String.format("/clients/%d/phones/%d", clientId, phoneId))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(PhoneDTO.class);

        model.addAttribute("phone", phoneDTOMono.block());
        return "clients/phone-form-result";
    }
}
