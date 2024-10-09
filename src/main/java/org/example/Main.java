package org.example;

import org.example.db.Database;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        ClientService clientService = new ClientService();
//        System.out.println(clientService.create("Monika"));
//        System.out.println(clientService.getById(2));
//        clientService.setName(13, "Viktor");
//        clientService.deleteById(14);
        clientService.listAll().stream().forEach(System.out::println);
    }
}