package pl.auction_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.auction_system.exception.*;
import pl.auction_system.model.Auction;
import pl.auction_system.model.AuctionStatus;
import pl.auction_system.model.Bid;
import pl.auction_system.model.User;
import pl.auction_system.repository.AuctionRepository;
import pl.auction_system.repository.BidRepository;
import pl.auction_system.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    //DELETE ->bid nie da się usunąć

    //POST
    public Bid addBid(Bid bid, String bidderUsername, String referenceNumber){
        // find bidder
        User bidder = userRepository.findUserByUsernameEqualsIgnoreCase(bidderUsername)
                .orElseThrow(() -> new UserNotFoundByUsernameException(bidderUsername));

        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        // właściciel nie może licytować
        if (auction.getOwner().getUsername().equals(bidder.getUsername())){
            throw new OwnerCantBidException(bidderUsername);
        }

        if (auction.getCurrentPrice().compareTo(bid.getAmount()) >= 0){
            // cena bid nie może być niższa lub równa obecnemu bid
            throw new BadBidException(bid.getAmount());
        }

        // podwójne zabezpieczenie
        if (bid.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidBidException(bid.getAmount());
        }

        if (auction.getAuctionStatus().equals(AuctionStatus.FINISHED)){
            throw new AuctionIsAlreadyFinishedException(auction.getTitle(), auction.getReferenceNumber());
        }

        bid.setBidder(bidder);
        bid.setAuction(auction);
        bid.setBidTime(LocalDateTime.now());

        Random random = new Random();
        int randomNumber = random.nextInt(1000, 9999);
        String usernamePrefix = bidder.getUsername().substring(0,3).toUpperCase();
        int year = LocalDateTime.now().getYear();
        int count = bidRepository.bidCount();
        int day = LocalDateTime.now().getDayOfMonth();
        bid.setBidIdNumber(usernamePrefix + "-" + year + count + day + randomNumber);

        // zmiana ceny aukcji
        auction.setCurrentPrice(bid.getAmount());

        return bidRepository.save(bid);
    }


    //PUT
    // zaakceptowanego bid nie można edytować
    //GET
    public Bid findByBidIdNumberEqualsIgnoreCase(String bidIdNumber){
        return bidRepository.findByBidIdNumberEqualsIgnoreCase(bidIdNumber)
                .orElseThrow(() -> new BidNotFoundByIdNumberException(bidIdNumber));
    }

    public Page<Bid> findAllBids(Pageable pageable){
        return bidRepository.findAll(pageable);
    }


    public Page<Bid> findAllByBidder_UsernameEqualsIgnoreCase(String bidderUsername, Pageable pageable){
        return bidRepository.findAllByBidder_UsernameEqualsIgnoreCase(bidderUsername, pageable);
    }

    public Page<Bid> findAllByBidder_UserNumberEqualsIgnoreCase(String userNumber, Pageable pageable){
        return bidRepository.findAllByBidder_UserNumberEqualsIgnoreCase(userNumber, pageable);
    }

    public Page<Bid> findAllByBidder_EmailEqualsIgnoreCase(String bidderEmail, Pageable pageable){
        return bidRepository.findAllByBidder_EmailEqualsIgnoreCase(bidderEmail, pageable);
    }

    public Page<Bid> findAllByBidder_Id(Long bidderId, Pageable pageable){
        return bidRepository.findAllByBidder_Id(bidderId, pageable);
    }

    // by amount of bid
    public Page<Bid> findAllByAmountIsLessThanEqual(BigDecimal amountIsLessThan, Pageable pageable){
        return bidRepository.findAllByAmountIsLessThanEqual(amountIsLessThan, pageable);
    }

    public Page<Bid> findAllByAmountIsGreaterThan(BigDecimal amountIsGreaterThan, Pageable pageable){
        return bidRepository.findAllByAmountIsGreaterThan(amountIsGreaterThan, pageable);
    }

    public Page<Bid> findAllByAmountBetween(BigDecimal amountAfter, BigDecimal amountBefore, Pageable pageable){
        return bidRepository.findAllByAmountBetween(amountAfter, amountBefore, pageable);
    }

    // by date of bid
    public Page<Bid> findAllByBidTime(LocalDateTime bidTime, Pageable pageable){
        return bidRepository.findAllByBidTime(bidTime, pageable);
    }

    public Page<Bid> findAllByBidTimeIsAfter(LocalDateTime bidTimeAfter, Pageable pageable){
        return bidRepository.findAllByBidTimeIsAfter(bidTimeAfter, pageable);
    }

    public Page<Bid> findAllByBidTimeIsBefore(LocalDateTime bidTimeBefore, Pageable pageable){
        return bidRepository.findAllByBidTimeIsBefore(bidTimeBefore, pageable);
    }

    public Page<Bid> findAllByBidTimeIsBetween(LocalDateTime bidTimeAfter, LocalDateTime bidTimeBefore, Pageable pageable){
        return bidRepository.findAllByBidTimeIsBetween(bidTimeAfter, bidTimeBefore, pageable);
    }

    // by auction
    public Page<Bid> findAllByAuction_Id(Long auctionId, Pageable pageable){
        return bidRepository.findAllByAuction_Id(auctionId, pageable);
    }

    public Page<Bid> findAllByAuction_ReferenceNumberEqualsIgnoreCase(String auctionReferenceNumber, Pageable pageable){
        return bidRepository.findAllByAuction_ReferenceNumberEqualsIgnoreCase(auctionReferenceNumber, pageable);
    }
}
