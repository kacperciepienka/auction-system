package pl.auction_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction_system.dto.BidAdminDto;
import pl.auction_system.mapper.BidAdminMapper;
import pl.auction_system.model.Bid;
import pl.auction_system.service.BidService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/admin/bid")
public class BidAdminController {
    private final BidService bidService;
    private final BidAdminMapper bidAdminMapper;

    // POST
    @PostMapping("/{referenceNumber}")
    public ResponseEntity<BidAdminDto> addBid(@Valid @RequestBody Bid bid,
                                              @RequestParam String username,
                                              @PathVariable String referenceNumber) {
        Bid bitToAdd = bidService.addBid(bid, username, referenceNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(bidAdminMapper.toDto(bitToAdd));
    }

    // GET
    @GetMapping("/id/{bidIdNumber}")
    public ResponseEntity<BidAdminDto> findBidByIdNumber(@PathVariable String bidIdNumber) {
        Bid bid = bidService.findByBidIdNumberEqualsIgnoreCase(bidIdNumber);
        return ResponseEntity.status(HttpStatus.OK).body(bidAdminMapper.toDto(bid));
    }

    @GetMapping("/search/all-bids")
    public ResponseEntity<Page<BidAdminDto>> findAllBids(Pageable pageable) {
        Page<Bid> bids = bidService.findAllBids(pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder-username/{bidderUsername}")
    public ResponseEntity<Page<BidAdminDto>> findAllBidsByBidderUsername(@PathVariable String bidderUsername,
                                                                         Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameEqualsIgnoreCase(bidderUsername, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder-number/{userNumber}")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UserNumber(@PathVariable String userNumber,
                                                                        Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UserNumberEqualsIgnoreCase(userNumber, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/amount-less")
    public ResponseEntity<Page<BidAdminDto>> findAllByAmountIsLessThanEqual(@RequestParam BigDecimal amountIsLessThan,
                                                                            Pageable pageable) {
        Page<Bid> bids = bidService.findAllByAmountIsLessThanEqual(amountIsLessThan, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/amount-greater")
    public ResponseEntity<Page<BidAdminDto>> findAllByAmountIsGreaterThanEqual(@RequestParam BigDecimal amountIsGreaterThan,
                                                                               Pageable pageable) {
        Page<Bid> bids = bidService.findAllByAmountIsGreaterThanEqual(amountIsGreaterThan, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/amount-between")
    public ResponseEntity<Page<BidAdminDto>> findAllByAmountIsBetween(@RequestParam BigDecimal amountAfter,
                                                                      @RequestParam BigDecimal amountBefore,
                                                                      Pageable pageable) {
        Page<Bid> bids = bidService.findAllByAmountIsBetween(amountAfter, amountBefore, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/amount-less")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndAmountIsLessThanEqual(@PathVariable String bidderUsername,
                                                                                              @RequestParam BigDecimal amountIsLessThan,
                                                                                              Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndAmountIsLessThanEqual(bidderUsername, amountIsLessThan, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/amount-greater")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndAmountIsGreaterThanEqual(@PathVariable String bidderUsername,
                                                                                                 @RequestParam BigDecimal amountIsGreaterThan,
                                                                                                 Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndAmountIsGreaterThanEqual(bidderUsername, amountIsGreaterThan, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/amount-between")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndAmountBetween(@PathVariable String bidderUsername,
                                                                                      @RequestParam BigDecimal amountAfter,
                                                                                      @RequestParam BigDecimal amountBefore,
                                                                                      Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndAmountIsBetween(bidderUsername, amountAfter, amountBefore, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/time")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidTime(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTime,
                                                              Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidTime(bidTime, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/time-after")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidTimeIsAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeAfter,
                                                                     Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidTimeIsAfter(bidTimeAfter, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/time-before")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidTimeIsBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeBefore,
                                                                      Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidTimeIsBefore(bidTimeBefore, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/time-between")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidTimeIsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeAfter,
                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeBefore,
                                                                       Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidTimeIsBetween(bidTimeAfter, bidTimeBefore, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndBidTime(@PathVariable String bidderUsername,
                                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTime,
                                                                                Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTime(bidderUsername, bidTime, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time-after")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndBidTimeIsAfter(@PathVariable String bidderUsername,
                                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeAfter,
                                                                                       Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTimeIsAfter(bidderUsername, bidTimeAfter, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time-before")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndBidTimeIsBefore(@PathVariable String bidderUsername,
                                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeBefore,
                                                                                        Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTimeIsBefore(bidderUsername, bidTimeBefore, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/bidder/{bidderUsername}/time-between")
    public ResponseEntity<Page<BidAdminDto>> findAllByBidder_UsernameAndBidTimeIsBetween(@PathVariable String bidderUsername,
                                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeAfter,
                                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidTimeBefore,
                                                                                         Pageable pageable) {
        Page<Bid> bids = bidService.findAllByBidder_UsernameAndBidTimeIsBetween(bidderUsername, bidTimeAfter, bidTimeBefore, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/auction/{referenceNumber}")
    public ResponseEntity<Page<BidAdminDto>> findAllByAuction_ReferenceNumber(@PathVariable String referenceNumber,
                                                                              Pageable pageable) {
        Page<Bid> bids = bidService.findAllByAuction_ReferenceNumberEqualsIgnoreCase(referenceNumber, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<BidAdminDto>> findAllByAuction_Title(@RequestParam String title,
                                                                    Pageable pageable) {
        Page<Bid> bids = bidService.findAllByAuction_TitleContainingIgnoreCase(title, pageable);
        Page<BidAdminDto> dtoPage = bids.map(bidAdminMapper::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }
}
