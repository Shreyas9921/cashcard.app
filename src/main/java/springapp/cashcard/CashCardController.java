package springapp.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardController {
    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }
    
    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                ));
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard != null) {
            CashCard updatedCashCard = new CashCard(requestedId, cashCardUpdate.amount(), principal.getName());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CashCard findCashCard(Long requestedId, Principal principal) {
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal/*Add Principal to parameter list*/) {
        if(!cashCardRepository.existsByIdAndOwner(id, principal.getName()))
        	return ResponseEntity.notFound().build();
        cashCardRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        
        /* 
         * if (cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
         * cashCardRepository.deleteById(id);
         * return ResponseEntity.noContent().build();
         * }
         * return ResponseEntity.notFound().build();
         */
    }

//    @GetMapping("/{requestedId}")
//    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
//        Optional<CashCard> cashCardOptional = Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));
//        if (cashCardOptional.isPresent()) {
//            return ResponseEntity.ok(cashCardOptional.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

/*    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                ));
        return ResponseEntity.ok(page.getContent());
    }
*/
//    @PutMapping("/{requestedId}")
//    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
//        // just return 204 NO CONTENT for now.
//        CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
//        CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
//
//        cashCardRepository.save(updatedCashCard);
//        return ResponseEntity.noContent().build();
//    }
    
/*    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
        CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
        
        if (null != cashCard) {
            CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
            
            cashCardRepository.save(updatedCashCard);
            
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
*/

    // @PutMapping("/{requestedId}")
    // private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
    //     CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    //     if (null != cashCard) {
    //         CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
    //         cashCardRepository.save(updatedCashCard);
    //         return ResponseEntity.noContent().build();
    // }
    // return ResponseEntity.notFound().build();
    //}

}
/*
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardController {

	private final CashCardRepository cashCardRepository;

	private CashCardController(CashCardRepository cashCardRepository) {
		this.cashCardRepository = cashCardRepository;
	}

	@GetMapping("/{requestedId}")
	    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
	        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);

	        if(cashCardOptional.isPresent()) {
	            return ResponseEntity.ok(cashCardOptional.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }

//	@GetMapping("/{requestedId}")
//	private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
//		if (requestedId.equals(99L)) {
//			CashCard cashCard = new CashCard(99L, 123.45);
//			return ResponseEntity.ok(cashCard);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
	}
	
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
            .path("cashcards/{id}")
            .buildAndExpand(savedCashCard.id())
            .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
    
    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable) {
       Page<CashCard> page = cashCardRepository.findAll(
               PageRequest.of(
                       pageable.getPageNumber(),
                       pageable.getPageSize(),
                       pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));
       return ResponseEntity.ok(page.getContent());
    }
}
*/