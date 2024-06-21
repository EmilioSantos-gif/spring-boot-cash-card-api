package com.example.cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import java.net.URI;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cashcard")
public class CashCardController {

    CashCardRepository cashCardRepository;

    CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("{requestedId}")
    ResponseEntity<CashCard> getCashCardById(@PathVariable Long requestedId) {
        Optional<CashCard> optionalCashCard = cashCardRepository.findById(requestedId);

        return optionalCashCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Void> createCashCard(@RequestBody CashCard newCasCardRequest, UriComponentsBuilder ucb){
        CashCard savedCashCard = cashCardRepository.save(newCasCardRequest);

        URI locationOfNewCashCard = ucb
                .path("cashcard/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();

        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
