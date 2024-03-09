package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.Client;
import ru.otus.service.ClientService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/")
    public String clientsListView(Model model) {
        var clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/client/save")
    public String clientCreateView(@ModelAttribute("client") Client client) {
        return "add_client";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(@ModelAttribute("client") Client client) {
        var savedClient = clientService.saveClient(client);
        return new RedirectView("/api/", true);
    }

    @GetMapping ("/client/{id}")
    public String searchByName(Model model, @PathVariable long id) {
        var client = clientService.findById(id);
        model.addAttribute("client", client);
        return "client";
    }
}
