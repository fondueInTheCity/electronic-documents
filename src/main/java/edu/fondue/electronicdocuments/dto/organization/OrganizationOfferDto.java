package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.Offer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationOfferDto {

    private Long id;

    private String ownerUsername;

    private String type;

    static public OrganizationOfferDto fromOffer(final Offer offer, final Boolean toUser) {
        return OrganizationOfferDto.builder()
                .id(offer.getId())
                .type(offer.getType().name())
                .ownerUsername(toUser
                        ? offer.getOrganization().getName()
                        : offer.getUser().getUsername()).build();
    }
}
