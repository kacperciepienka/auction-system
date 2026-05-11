package pl.auction_system.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BidAdminDto {
    private Long bidderId;
    private String bidderUsername;
    private String bidderEmail;
    private BigDecimal amount;
    private LocalDateTime bidTime;
    private Long auctionId;
    private String auctionReferenceNumber;
    private String auctionTitle;
}

