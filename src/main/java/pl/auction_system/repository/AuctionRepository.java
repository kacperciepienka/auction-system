package pl.auction_system.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.auction_system.model.Auction;
import pl.auction_system.model.AuctionCategory;
import pl.auction_system.model.AuctionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    // findById jest w standardzie
    // Optional: to jest klucz naturalny
    Optional<Auction> findAuctionByReferenceNumberEqualsIgnoreCase(String referenceNumber);

    Page<Auction> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Auction> findAllByAuctionCategory(AuctionCategory auctionCategory, Pageable pageable);

    // by current price
        Page<Auction> findAllByCurrentPriceIsLessThanEqual(BigDecimal currentPriceIsLessThan, Pageable pageable);
        Page<Auction> findAllByCurrentPriceIsGreaterThanEqual(BigDecimal currentPriceIsGreaterThan, Pageable pageable);
        Page<Auction> findAllByCurrentPriceBetween(BigDecimal currentPriceAfter, BigDecimal currentPriceBefore, Pageable pageable);

    // by start date
        Page<Auction> findAllByStartTime(LocalDate startTime, Pageable pageable);
        Page<Auction> findAllByStartTimeIsAfter(LocalDate startTimeAfter, Pageable pageable);
        Page<Auction> findAllByStartTimeIsBefore(LocalDate startTimeBefore, Pageable pageable);
        Page<Auction> findAllByStartTimeIsBetween(LocalDate startTimeAfter, LocalDate startTimeBefore, Pageable pageable);

    // by end date
        Page<Auction> findAllByEndTime(LocalDate endTime, Pageable pageable); // for exact date
        Page<Auction> findAllByEndTimeIsAfter(LocalDate endTimeAfter, Pageable pageable);
        Page<Auction> findAllByEndTimeIsBefore(LocalDate endTimeBefore, Pageable pageable);
        Page<Auction> findAllByEndTimeIsBetween(LocalDate endTimeAfter, LocalDate endTimeBefore, Pageable pageable);

    Page<Auction> findAllByAuctionStatus(AuctionStatus auctionStatus, Pageable pageable);

    // by Owner
        Page<Auction> findAllByOwner_Id(Long ownerId, Pageable pageable);
        Page<Auction> findAllByOwnerUsernameEqualsIgnoreCase(String ownerUsername, Pageable pageable);
        Page<Auction> findAllByOwner_EmailEqualsIgnoreCase(String ownerEmail, Pageable pageable);
}
