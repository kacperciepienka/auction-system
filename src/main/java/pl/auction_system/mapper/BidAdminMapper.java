package pl.auction_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.auction_system.dto.BidAdminDto;
import pl.auction_system.model.Bid;

@Mapper(componentModel = "spring")
public interface BidAdminMapper {

    @Mapping(target = "bidderId", source = "bidder.id")
    @Mapping(target = "bidderUsername", source = "bidder.username")
    @Mapping(target = "bidderEmail", source = "bidder.email")
    @Mapping(target = "auctionId", source = "auction.id")
    @Mapping(target = "auctionReferenceNumber", source = "auction.referenceNumber")
    @Mapping(target = "auctionTitle", source = "auction.title")

    BidAdminDto toDto(Bid bid);
}
