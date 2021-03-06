package edu.fondue.electronicdocuments.repositories;

import edu.fondue.electronicdocuments.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    boolean existsByUserIdAndOrganizationId(Long userId, Long organizationId);
}
