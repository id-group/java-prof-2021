package ru.idgroup.otus.jdbc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.idgroup.otus.jdbc.model.Address;
import ru.idgroup.otus.jdbc.model.Client;
import ru.idgroup.otus.jdbc.services.DBServiceClient;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping(value = "/clients")
@Slf4j
public class ClientController {
    private DBServiceClient serviceClient;

    public ClientController(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("clients", serviceClient.findAll());
        return "clients/clients";
    }

    @GetMapping("/form")
    public String addClient(Model model) {
        model.addAttribute("client", new Client(""));
        return "clients/clientform";
    }

    @GetMapping("/formresult")
    public String clientAddingResult(Model model, @RequestParam(name = "clientid") Long clientId) {
        final Client client = serviceClient.getClient(clientId).orElseThrow(() -> new IllegalArgumentException("Клиент не найден."));
        model.addAttribute("client", client);
        return "clients/formresult";
    }

    @PostMapping("/form")
    public String editClient(@RequestParam HashMap<String, String> formData, Model model) {
        Client client = new Client(formData.get("name"), formData.get("address.street"));
        Client client1 = serviceClient.saveClient(client);
        return "redirect:/clients/formresult?clientid=" + client1.getId();
    }
}
