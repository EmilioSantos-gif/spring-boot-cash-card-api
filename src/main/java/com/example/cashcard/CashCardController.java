package com.example.cashcard;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import java.net.URI;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    CashCardRepository cashCardRepository;

    CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{requestedId}")
    ResponseEntity<CashCard> getCashCardById(@PathVariable Long requestedId, Principal principal) {
        Optional<CashCard> optionalCashCard = Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));

        return optionalCashCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {

        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                        ));

        return ResponseEntity.ok(page.getContent());

    }

    @PostMapping
    ResponseEntity<Void> createCashCard(@RequestBody CashCard newCasCardRequest, UriComponentsBuilder ucb, Principal principal){
        CashCard cashCardWithOwner = new CashCard(null, newCasCardRequest.amount(), principal.getName());

        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);

        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();

        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}