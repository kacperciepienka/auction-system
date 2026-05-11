package pl.auction_system.dto;

import lombok.Data;
import pl.auction_system.model.AuctionCategory;
import pl.auction_system.model.AuctionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AuctionAdminDto {
    private Long id;
    private String referenceNumber;
    private String title;
    private String description;
    private AuctionCategory auctionCategory;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private LocalDate startTime;
    private LocalDate endTime;
    private AuctionStatus auctionStatus;
    private Long ownerId;
    private String ownerUsername;
    private String ownerEmail;
}
