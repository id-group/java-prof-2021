package ru.idgroup.otus.jdbc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import ru.idgroup.otus.jdbc.model.Address;
import ru.idgroup.otus.jdbc.model.Client;
import ru.idgroup.otus.jdbc.model.Phone;
import ru.idgroup.otus.jdbc.services.DBServiceClient;

import java.util.HashMap;
import java.util.List;

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
    public String addClient(@RequestParam(name = "client_id", required = false) Long clientId, Model model) {
        Client client;
        if(clientId == null)
            client = new Client("");
        else
            client = serviceClient.findById(clientId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
        model.addAttribute("client", client);
        return "clients/client-form";
    }

    @GetMapping("/formresult")
    public String clientAddingResult(Model model, @RequestParam(name = "clientid") Long clientId) {
        final Client client = serviceClient.getClient(clientId).orElseThrow(() -> new IllegalArgumentException("Клиент не найден."));
        model.addAttribute("client", client);
        return "clients/form-result";
    }

    @PostMapping("/form")
    public String postClientForm(@RequestParam HashMap<String, String> formData, Model model) {

        Client client;
        if( !StringUtils.isEmpty(formData.get("id"))) {
            Long id = Long.parseLong(formData.get("id"));
            Client exClient = serviceClient.getClient(id).orElseThrow(() -> new RuntimeException("Клиент не найден."));

            Address address = new Address( exClient.getAddress().getId(), formData.get("address.street"), id );
            client = new Client(exClient.getId(), formData.get("name"), address, exClient.getPhones());
        }
        else {
            client = new Client(formData.get("name"), formData.get("address.street"));
        }
        Client savedClient = serviceClient.saveClient(client);
        return "redirect:/clients/formresult?clientid=" + savedClient.getId();
    }

    @GetMapping("/phone-form")
    public String addPhone(@RequestParam("client_id") Long clientId, Model model) {
        model.addAttribute("phone", new Phone(clientId));
        return "clients/phone-form";
    }

    @PostMapping("/phone-form")
    public String editPhone(@RequestParam HashMap<String, String> formData) {
        Phone phone = new Phone(null, formData.get("number"), Long.parseLong(formData.get("clientId")));
        Phone savedPhone = serviceClient.savePhone(phone);
        return "redirect:/clients/phone-form-result?phoneid=" + savedPhone.getId();
    }

    @GetMapping("/phone-form-result")
    public String phoneFormResult(Model model, @RequestParam(name = "phoneid") Long phoneId) {
        final Phone phone = serviceClient.getPhone(phoneId).orElseThrow(() -> new RuntimeException("Телефон не найден."));
        model.addAttribute("phone", phone);
        return "clients/phone-form-result";
    }
}
