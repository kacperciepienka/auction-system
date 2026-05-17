package pl.auction_system.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction_system.dto.BidUserDto;
import pl.auction_system.mapper.BidUserMapper;
import pl.auction_system.model.Bid;
import pl.auction_system.service.BidService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/user/bid")
public class BidUserController {
    private final BidService bidService;
    private final BidUserMapper bidUserMapper;

    // POST - user musi móc dać jakąś ofertę
    @PostMapping("/{referenceNumber}")
    public ResponseEntity<BidUserDto> addBid(@Valid @RequestBody Bid bid,
                                             @RequestParam String username,
                                             @PathVariable String referenceNumber){
        Bid bitToAdd = bidService.addBid(bid, username, referenceNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(bidUserMapper.toDto(bitToAdd));
    }

    // GET
    @GetMapping("/id/{bidIdNumber}")
    public ResponseEntity<BidUserDto> findBidByIdNumber(@PathVariable String bidIdNumber){
        Bid bid = bidService.findByBidIdNumberEqualsIgnoreCase(bidIdNumber);
        return ResponseEntity.status(HttpStatus.OK).body(bidUserMapper.toDto(bid));
    }

    @GetMapping("/search/bidder/{bidderUsername}")
    public ResponseEntity<Page<BidUserDto>> findAllBidsByBidderUsername(@PathVariable String bidderUsername,
                                                                        Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameEqualsIgnoreCase(bidderUsername, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/amount-less")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndAmountIsLessThanEqual(@PathVariable String bidderUsername,
                                                                                             @RequestParam BigDecimal amountIsLessThan,
                                                                                             Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndAmountIsLessThanEqual(bidderUsername, amountIsLessThan, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/amount-greater")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndAmountIsGreaterThanEqual(@PathVariable String bidderUsername,
                                                                                                @RequestParam BigDecimal amountIsGreaterThan,
                                                                                                Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndAmountIsGreaterThanEqual(bidderUsername, amountIsGreaterThan, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/amount-between")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndAmountBetween(@PathVariable String bidderUsername,
                                                                                     @RequestParam BigDecimal amountAfter,
                                                                                     @RequestParam BigDecimal amountBefore,
                                                                                     Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndAmountIsBetween(bidderUsername, amountAfter, amountBefore, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndBidTime(@PathVariable String bidderUsername,
                                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTime,
                                                                               Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTime(bidderUsername, bidTime, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time-after")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndBidTimeIsAfter(@PathVariable String bidderUsername,
                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeAfter,
                                                                                      Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTimeIsAfter(bidderUsername, bidTimeAfter, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time-before")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndBidTimeIsBefore(@PathVariable String bidderUsername,
                                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeBefore,
                                                                                       Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTimeIsBefore(bidderUsername, bidTimeBefore, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time-between")
    public ResponseEntity<Page<BidUserDto>> findAllByBidder_UsernameAndBidTimeIsBetween(@PathVariable String bidderUsername,
                                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeAfter,
                                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeBefore,
                                                                                        Pageable pageable){
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTimeIsBetween(bidderUsername, bidTimeAfter, bidTimeBefore, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/auction/{referenceNumber}")
    public ResponseEntity<Page<BidUserDto>> findAllByAuction_ReferenceNumberEqualsIgnoreCase(@PathVariable String referenceNumber,
                                                                                             Pageable pageable){
        Page<Bid> bids = bidService.findAllByAuction_ReferenceNumberEqualsIgnoreCase(referenceNumber, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<BidUserDto>> findAllByAuction_TitleContainingIgnoreCase(@RequestParam String title,
                                                                                       Pageable pageable){
        Page<Bid> bids = bidService.findAllByAuction_TitleContainingIgnoreCase(title, pageable);
        Page<BidUserDto> dtoPage = bids.map(bidUserMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

}
