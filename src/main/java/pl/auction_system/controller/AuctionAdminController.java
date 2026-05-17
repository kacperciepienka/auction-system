package pl.auction_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction_system.dto.AuctionAdminDto;
import pl.auction_system.mapper.AuctionAdminMapper;
import pl.auction_system.model.Auction;
import pl.auction_system.model.AuctionCategory;
import pl.auction_system.model.AuctionStatus;
import pl.auction_system.service.AuctionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/admin/auction")
public class AuctionAdminController {
    private final AuctionService auctionService;
    private final AuctionAdminMapper auctionAdminMapper;

    // POST
    @PostMapping("/{userUsername}/add")
    public ResponseEntity<AuctionAdminDto> addAuction(@Valid @RequestBody Auction auction,
                                                      @PathVariable String userUsername) {
        Auction auctionToAdd = auctionService.addAuction(auction, userUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(auctionAdminMapper.toDto(auctionToAdd));
    }

    // DELETE
    @DeleteMapping("/{referenceNumber}")
    public ResponseEntity<Void> deleteAuction(@PathVariable String referenceNumber) {
        auctionService.deleteAuction(referenceNumber);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // PUT
    @PutMapping("/change/{referenceNumber}/title")
    public ResponseEntity<AuctionAdminDto> changeAuctionTitle(@PathVariable String referenceNumber,
                                                              @RequestParam String newTitle) {
        Auction auction = auctionService.changeTitle(referenceNumber, newTitle);
        return ResponseEntity.status(HttpStatus.OK).body(auctionAdminMapper.toDto(auction));
    }

    @PutMapping("change/{referenceNumber}/description")
    public ResponseEntity<AuctionAdminDto> changeAuctionDescription(@PathVariable String referenceNumber,
                                                                    @RequestParam String newDescription) {
        Auction auction = auctionService.changeDescription(referenceNumber, newDescription);
        return ResponseEntity.status(HttpStatus.OK).body(auctionAdminMapper.toDto(auction));
    }

    @PutMapping("change/{referenceNumber}/category")
    public ResponseEntity<AuctionAdminDto> changeAuctionCategory(@PathVariable String referenceNumber,
                                                                 @RequestParam AuctionCategory category) {
        Auction auction = auctionService.changeCategory(referenceNumber, category);
        return ResponseEntity.status(HttpStatus.OK).body(auctionAdminMapper.toDto(auction));
    }

    @PutMapping("change/{referenceNumber}/starting-price")
    public ResponseEntity<AuctionAdminDto> changeAuctionStartingPrice(@PathVariable String referenceNumber,
                                                                      @RequestParam BigDecimal newStartingPrice) {
        Auction auction = auctionService.changeStartingPrice(referenceNumber, newStartingPrice);
        return ResponseEntity.status(HttpStatus.OK).body(auctionAdminMapper.toDto(auction));
    }

    @PutMapping("/finish/{referenceNumber}/")
    public ResponseEntity<AuctionAdminDto> finishAuctionEarlier(@PathVariable String referenceNumber) {
        Auction auction = auctionService.finishAuctionEarlier(referenceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(auctionAdminMapper.toDto(auction));
    }

    // GET
    @GetMapping("/search/{referenceNumber}")
    public ResponseEntity<AuctionAdminDto> findAuctionByReferenceNumber(@PathVariable String referenceNumber) {
        Auction auction = auctionService.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(auctionAdminMapper.toDto(auction));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AuctionAdminDto>> allAuctions(Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllAuctions(pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<AuctionAdminDto>> findByTitle(@RequestParam String title,
                                                            Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByTitleContainingIgnoreCase(title, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/category")
    public ResponseEntity<Page<AuctionAdminDto>> findByAuctionCategory(@RequestParam AuctionCategory auctionCategory,
                                                                      Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByAuctionCategory(auctionCategory, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/current-price-less")
    public ResponseEntity<Page<AuctionAdminDto>> findByCurrentPriceIsLessThanEqual(@RequestParam BigDecimal currentPrice,
                                                                                  Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByCurrentPriceIsLessThanEqual(currentPrice, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/current-price-greater")
    public ResponseEntity<Page<AuctionAdminDto>> findByCurrentPriceIsGreaterThanEqual(@RequestParam BigDecimal currentPrice,
                                                                                     Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByCurrentPriceIsGreaterThanEqual(currentPrice, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/current-price-between")
    public ResponseEntity<Page<AuctionAdminDto>> findByCurrentPriceIsBetween(@RequestParam BigDecimal currentPriceAfter,
                                                                            @RequestParam BigDecimal currentPriceBefore,
                                                                            Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByCurrentPriceIsBetween(currentPriceAfter, currentPriceBefore, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time")
    public ResponseEntity<Page<AuctionAdminDto>> findByStartingTime(@RequestParam LocalDateTime startTime,
                                                                   Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTime(startTime, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time-after")
    public ResponseEntity<Page<AuctionAdminDto>> findByStartingTimeAfter(@RequestParam LocalDateTime startTime,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTimeIsAfter(startTime, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time-before")
    public ResponseEntity<Page<AuctionAdminDto>> findByStartingTimeBefore(@RequestParam LocalDateTime startTime,
                                                                         Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTimeIsBefore(startTime, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/start-time-between")
    public ResponseEntity<Page<AuctionAdminDto>> findByStartingTimeBetween(@RequestParam LocalDateTime startTimeAfter,
                                                                          @RequestParam LocalDateTime startTimeBefore,
                                                                          Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByStartTimeIsBetween(startTimeAfter, startTimeBefore, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time")
    public ResponseEntity<Page<AuctionAdminDto>> findByEndingTime(@RequestParam LocalDateTime endTime,
                                                                 Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTime(endTime, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time-after")
    public ResponseEntity<Page<AuctionAdminDto>> findByEndingTimeAfter(@RequestParam LocalDateTime endTime,
                                                                      Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTimeIsAfter(endTime, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time-before")
    public ResponseEntity<Page<AuctionAdminDto>> findByEndingTimeBefore(@RequestParam LocalDateTime endTime,
                                                                       Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTimeIsBefore(endTime, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/end-time-between")
    public ResponseEntity<Page<AuctionAdminDto>> findByEndingTimeBetween(@RequestParam LocalDateTime endTimeAfter,
                                                                        @RequestParam LocalDateTime endTimeBefore,
                                                                        Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByEndTimeIsBetween(endTimeAfter, endTimeBefore, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/owner/{ownerUsername}")
    public ResponseEntity<Page<AuctionAdminDto>> findAllByOwnerUsername(@PathVariable String ownerUsername, Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByOwnerUsernameEqualsIgnoreCase(ownerUsername, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/owner/{userNumber}")
    public ResponseEntity<Page<AuctionAdminDto>> findAllByOwnerUserNumber(@PathVariable String userNumber,
                                                                          Pageable pageable) {
        Page<Auction> auctions = auctionService.findAllByOwner_UserNumberEqualsIgnoreCase(userNumber, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/status")
    public ResponseEntity<Page<AuctionAdminDto>> findAllAuctionByAuctionStatus(@RequestParam AuctionStatus auctionStatus,
                                                                               Pageable pageable){
        Page<Auction> auctions = auctionService.findAllByAuctionStatus(auctionStatus, pageable);
        Page<AuctionAdminDto> dtoPage = auctions.map(auctionAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }
}
