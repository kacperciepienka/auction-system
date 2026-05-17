package pl.auction_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction_system.dto.AuctionUserDto;
import pl.auction_system.mapper.AuctionUserMapper;
import pl.auction_system.model.Auction;
import pl.auction_system.model.AuctionCategory;
import pl.auction_system.service.AuctionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/user/auction")
public class AuctionUserController {
    private final AuctionService auctionService;
    private final AuctionUserMapper auctionUserMapper;

    // POST
    @PostMapping("/{userUsername}/add")
    public ResponseEntity<AuctionUserDto> addAuction(@Valid @RequestBody Auction auction,
                                                     @PathVariable String userUsername) {
        Auction auctionToAdd = auctionService.addAuction(auction, userUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(auctionUserMapper.toDto(auctionToAdd));
    }

    // DELETE
    @DeleteMapping("/{referenceNumber}")
    public ResponseEntity<Void> deleteAuction(@PathVariable String referenceNumber) {
        auctionService.deleteAuction(referenceNumber);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // PUT
    @PutMapping("/change/{referenceNumber}/title")
    public ResponseEntity<AuctionUserDto> changeAuctionTitle(@PathVariable String referenceNumber,
                                                             @RequestParam String newTitle) {
        Auction auction = auctionService.changeTitle(referenceNumber, newTitle);
        return ResponseEntity.status(HttpStatus.OK).body(auctionUserMapper.toDto(auction));
    }

    @PutMapping("/change/{referenceNumber}/description")
    public ResponseEntity<AuctionUserDto> changeAuctionDescription(@PathVariable String referenceNumber,
                                                                   @RequestParam String newDescription) {
        Auction auction = auctionService.changeDescription(referenceNumber, newDescription);
        return ResponseEntity.status(HttpStatus.OK).body(auctionUserMapper.toDto(auction));
    }

    @PutMapping("/change/{referenceNumber}/category")
    public ResponseEntity<AuctionUserDto> changeAuctionCategory(@PathVariable String referenceNumber,
                                                                @RequestParam AuctionCategory category) {
        Auction auction = auctionService.changeCategory(referenceNumber, category);
        return ResponseEntity.status(HttpStatus.OK).body(auctionUserMapper.toDto(auction));
    }

    @PutMapping("/change/{referenceNumber}/starting-price")
    public ResponseEntity<AuctionUserDto> changeAuctionStartingPrice(@PathVariable String referenceNumber,
                                                                     @RequestParam BigDecimal newStartingPrice) {
        Auction auction = auctionService.changeStartingPrice(referenceNumber, newStartingPrice);
        return ResponseEntity.status(HttpStatus.OK).body(auctionUserMapper.toDto(auction));
    }

    @PutMapping("/finish/{referenceNumber}")
    public ResponseEntity<AuctionUserDto> finishAuctionEarlier(@PathVariable String referenceNumber) {
        Auction auction = auctionService.finishAuctionEarlier(referenceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(auctionUserMapper.toDto(auction));
    }


    // GET
    @GetMapping("/search/{referenceNumber}")
    public ResponseEntity<AuctionUserDto> findAuctionByReferenceNumber(@PathVariable String referenceNumber) {
        Auction auction = auctionService.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(auctionUserMapper.toDto(auction));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AuctionUserDto>> allAuctions(Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllAuctions(pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<AuctionUserDto>> findByTitle(@RequestParam String title,
                                                            Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByTitleContainingIgnoreCase(title, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/category")
    public ResponseEntity<Page<AuctionUserDto>> findByAuctionCategory(@RequestParam AuctionCategory auctionCategory,
                                                                      Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByAuctionCategory(auctionCategory, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/current-price-less")
    public ResponseEntity<Page<AuctionUserDto>> findByCurrentPriceIsLessThanEqual(@RequestParam BigDecimal currentPrice,
                                                                                  Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByCurrentPriceIsLessThanEqual(currentPrice, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/current-price-greater")
    public ResponseEntity<Page<AuctionUserDto>> findByCurrentPriceIsGreaterThanEqual(@RequestParam BigDecimal currentPrice,
                                                                                     Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByCurrentPriceIsGreaterThanEqual(currentPrice, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/current-price-between")
    public ResponseEntity<Page<AuctionUserDto>> findByCurrentPriceIsBetween(@RequestParam BigDecimal currentPriceAfter,
                                                                            @RequestParam BigDecimal currentPriceBefore,
                                                                            Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByCurrentPriceIsBetween(currentPriceAfter, currentPriceBefore, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time")
    public ResponseEntity<Page<AuctionUserDto>> findByStartingTime(@RequestParam LocalDateTime startTime,
                                                                   Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTime(startTime, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time-after")
    public ResponseEntity<Page<AuctionUserDto>> findByStartingTimeAfter(@RequestParam LocalDateTime startTime,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTimeIsAfter(startTime, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time-before")
    public ResponseEntity<Page<AuctionUserDto>> findByStartingTimeBefore(@RequestParam LocalDateTime startTime,
                                                                         Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTimeIsBefore(startTime, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time-between")
    public ResponseEntity<Page<AuctionUserDto>> findByStartingTimeBetween(@RequestParam LocalDateTime startTimeAfter,
                                                                          @RequestParam LocalDateTime startTimeBefore,
                                                                          Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTimeIsBetween(startTimeAfter, startTimeBefore, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time")
    public ResponseEntity<Page<AuctionUserDto>> findByEndingTime(@RequestParam LocalDateTime endTime,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTime(endTime, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time-after")
    public ResponseEntity<Page<AuctionUserDto>> findByEndingTimeAfter(@RequestParam LocalDateTime endTime,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTimeIsAfter(endTime, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time-before")
    public ResponseEntity<Page<AuctionUserDto>> findByEndingTimeBefore(@RequestParam LocalDateTime endTime,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTimeIsBefore(endTime, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time-between")
    public ResponseEntity<Page<AuctionUserDto>> findByEndingTimeBetween(@RequestParam LocalDateTime endTimeAfter,
                                                                        @RequestParam LocalDateTime endTimeBefore,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTimeIsBetween(endTimeAfter, endTimeBefore, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/owner/{ownerUsername}")
    public ResponseEntity<Page<AuctionUserDto>> findAllByOwnerUsername(@PathVariable String ownerUsername, Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByOwnerUsernameEqualsIgnoreCase(ownerUsername, pageable);
        Page<AuctionUserDto> dtoPage = auctions.map(auctionUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }
}
