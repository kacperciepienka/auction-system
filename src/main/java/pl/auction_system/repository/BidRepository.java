package pl.auction_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.auction_system.model.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    // Optional: to jest klucz naturalny
    Optional<Bid> findByBidIdNumberEqualsIgnoreCase(String bidIdNumber);

    // by bidder
        Page<Bid> findAllByBidder_UsernameEqualsIgnoreCase(String bidderUsername, Pageable pageable);
        Page<Bid> findAllByBidder_UserNumberEqualsIgnoreCase(String bidderUserNumber, Pageable pageable);
        Page<Bid> findAllByBidder_EmailEqualsIgnoreCase(String bidderEmail, Pageable pageable);
        Page<Bid> findAllByBidder_Id(Long bidderId, Pageable pageable);

    // by amount of bid
        Page<Bid> findAllByAmountIsLessThanEqual(BigDecimal amountIsLessThan, Pageable pageable);
        Page<Bid> findAllByAmountIsGreaterThan(BigDecimal amountIsGreaterThan, Pageable pageable);
        Page<Bid> findAllByAmountBetween(BigDecimal amountAfter, BigDecimal amountBefore, Pageable pageable);

    // by date of bid
        Page<Bid> findAllByBidTime(LocalDateTime bidTime, Pageable pageable); // for exact date
        Page<Bid> findAllByBidTimeIsAfter(LocalDateTime bidTimeAfter, Pageable pageable);
        Page<Bid> findAllByBidTimeIsBefore(LocalDateTime bidTimeBefore, Pageable pageable);
        Page<Bid> findAllByBidTimeIsBetween(LocalDateTime bidTimeAfter, LocalDateTime bidTimeBefore, Pageable pageable);

    // by auction
        Page<Bid> findAllByAuction_Id(Long auctionId, Pageable pageable);
        Page<Bid> findAllByAuction_ReferenceNumberEqualsIgnoreCase(String auctionReferenceNumber, Pageable pageable);

        @Query("SELECT COUNT(b) FROM Bid b")
        Integer bidCount();
}
