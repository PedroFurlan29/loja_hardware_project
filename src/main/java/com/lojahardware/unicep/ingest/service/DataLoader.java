package com.lojahardware.unicep.ingest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final DummyJsonIngestService ingestService;

    @Autowired
    public DataLoader(DummyJsonIngestService ingestService) {
        this.ingestService = ingestService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Executando DataLoader...");
        ingestService.ingestirDados();
    }
}
