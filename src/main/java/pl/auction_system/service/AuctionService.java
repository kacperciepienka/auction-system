package pl.auction_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.auction_system.exception.AuctionNotFoundByReferenceNumber;
import pl.auction_system.exception.CantChangeStartingPriceException;
import pl.auction_system.exception.InvalidNewTitleException;
import pl.auction_system.exception.UserNotFoundByUsernameException;
import pl.auction_system.model.Auction;
import pl.auction_system.model.AuctionCategory;
import pl.auction_system.model.AuctionStatus;
import pl.auction_system.model.User;
import pl.auction_system.repository.AuctionRepository;
import pl.auction_system.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    // POST
    public Auction addAuction(Auction auction, String userUsername) {
        User owner = userRepository.findUserByUsernameEqualsIgnoreCase(userUsername)
                .orElseThrow(() -> new UserNotFoundByUsernameException(userUsername));

        auction.setOwner(owner);
        auction.setCurrentPrice(auction.getStartingPrice());
        auction.setStartTime(LocalDate.now());
        auction.setEndTime(auction.getStartTime().plusDays(14));
        auction.setAuctionStatus(AuctionStatus.ACTIVE);

        Random random = new Random();
        int randomNumber = random.nextInt(1000, 9999);
        String categoryPrefix = auction.getAuctionCategory().name().substring(0, 3).toUpperCase();
        int year = auction.getStartTime().getYear();
        int count = auctionRepository.auctionCount();
        int day = auction.getStartTime().getDayOfMonth();
        auction.setReferenceNumber(categoryPrefix + "-" + year + count + day + randomNumber);

        return auctionRepository.save(auction);
    }

    // DELETE
    public void deleteAuction(String referenceNumber) {
        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        auction.setEndTime(LocalDate.now());
        auctionRepository.delete(auction);
    }

    // PUT
    public Auction changeTitle(String referenceNumber, String newTitle) {
        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        // dodatkowe zabezpieczenie
        if (newTitle.isBlank() || newTitle.length() > 100) {
            throw new InvalidNewTitleException(newTitle);
        }

        auction.setTitle(newTitle);
        return auctionRepository.save(auction);
    }

    public Auction changeDescription(String referenceNumber, String newDescription) {
        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        auction.setDescription(newDescription);
        return auctionRepository.save(auction);
    }

    public Auction changeCategory(String referenceNumber, AuctionCategory newAuctionCategory) {
        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        auction.setAuctionCategory(newAuctionCategory);
        return auctionRepository.save(auction);
    }

    public Auction changeStartingPrice(String referenceNumber, BigDecimal newStartingPrice) {
        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        if (auction.getCurrentPrice().compareTo(auction.getStartingPrice()) != 0) {
            throw new CantChangeStartingPriceException(referenceNumber);
        }

        auction.setStartingPrice(newStartingPrice);
        auction.setCurrentPrice(newStartingPrice);
        return auctionRepository.save(auction);
    }

    public Auction finishAuctionEarlier(String referenceNumber) {
        Auction auction = auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));

        auction.setEndTime(LocalDate.now());
        auction.setAuctionStatus(AuctionStatus.FINISHED);
        return auctionRepository.save(auction);
    }

    // GET
    public Auction findAuctionByReferenceNumberEqualsIgnoreCase(String referenceNumber) {
        return auctionRepository.findAuctionByReferenceNumberEqualsIgnoreCase(referenceNumber)
                .orElseThrow(() -> new AuctionNotFoundByReferenceNumber(referenceNumber));
    }

    public Page<Auction> findAllAuctions(Pageable pageable) {
        return auctionRepository.findAll(pageable);
    }

    public Page<Auction> findAllByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return auctionRepository.findAllByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Auction> findAllByAuctionCategory(AuctionCategory auctionCategory, Pageable pageable) {
        return auctionRepository.findAllByAuctionCategory(auctionCategory, pageable);
    }

    // by current price
    public Page<Auction> findAllByCurrentPriceIsLessThanEqual(BigDecimal currentPriceIsLessThan, Pageable pageable) {
        return auctionRepository.findAllByCurrentPriceIsLessThanEqual(currentPriceIsLessThan, pageable);
    }

    public Page<Auction> findAllByCurrentPriceIsGreaterThanEqual(BigDecimal currentPriceIsGreaterThan, Pageable pageable) {
        return auctionRepository.findAllByCurrentPriceIsGreaterThanEqual(currentPriceIsGreaterThan, pageable);
    }

    public Page<Auction> findAllByCurrentPriceBetween(BigDecimal currentPriceAfter, BigDecimal currentPriceBefore, Pageable pageable) {
        return auctionRepository.findAllByCurrentPriceBetween(currentPriceAfter, currentPriceBefore, pageable);
    }

    // by start date
    public Page<Auction> findAllByStartTime(LocalDate startTime, Pageable pageable) {
        return auctionRepository.findAllByStartTime(startTime, pageable);
    }

    public Page<Auction> findAllByStartTimeIsAfter(LocalDate startTimeAfter, Pageable pageable) {
        return auctionRepository.findAllByStartTimeIsAfter(startTimeAfter, pageable);
    }

    public Page<Auction> findAllByStartTimeIsBefore(LocalDate startTimeBefore, Pageable pageable) {
        return auctionRepository.findAllByStartTimeIsBefore(startTimeBefore, pageable);
    }

    public Page<Auction> findAllByStartTimeIsBetween(LocalDate startTimeAfter, LocalDate startTimeBefore, Pageable pageable) {
        return auctionRepository.findAllByStartTimeIsBetween(startTimeAfter, startTimeBefore, pageable);
    }

    // by end date
    public Page<Auction> findAllByEndTime(LocalDate endTime, Pageable pageable) {
        return auctionRepository.findAllByEndTime(endTime, pageable);
    }

    public Page<Auction> findAllByEndTimeIsAfter(LocalDate endTimeAfter, Pageable pageable) {
        return auctionRepository.findAllByEndTimeIsAfter(endTimeAfter, pageable);
    }

    public Page<Auction> findAllByEndTimeIsBefore(LocalDate endTimeBefore, Pageable pageable) {
        return auctionRepository.findAllByEndTimeIsBefore(endTimeBefore, pageable);
    }

    public Page<Auction> findAllByEndTimeIsBetween(LocalDate endTimeAfter, LocalDate endTimeBefore, Pageable pageable) {
        return auctionRepository.findAllByEndTimeIsBetween(endTimeAfter, endTimeBefore, pageable);
    }

    public Page<Auction> findAllByAuctionStatus(AuctionStatus auctionStatus, Pageable pageable) {
        return auctionRepository.findAllByAuctionStatus(auctionStatus, pageable);
    }

    // by Owner
    public Page<Auction> findAllByOwner_Id(Long ownerId, Pageable pageable) {
        return auctionRepository.findAllByOwner_Id(ownerId, pageable);
    }

    public Page<Auction> findAllByOwner_UserNumberEqualsIgnoreCase(String ownerUserNumber, Pageable pageable){
        return auctionRepository.findAllByOwner_UserNumberEqualsIgnoreCase(ownerUserNumber, pageable);
    }

    public Page<Auction> findAllByOwnerUsernameEqualsIgnoreCase(String ownerUsername, Pageable pageable) {
        return auctionRepository.findAllByOwner_UsernameEqualsIgnoreCase(ownerUsername, pageable);
    }

    public Page<Auction> findAllByOwner_EmailEqualsIgnoreCase(String ownerEmail, Pageable pageable) {
        return auctionRepository.findAllByOwner_EmailEqualsIgnoreCase(ownerEmail, pageable);
    }
}
