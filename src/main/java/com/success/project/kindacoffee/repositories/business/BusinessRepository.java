package com.success.project.kindacoffee.repositories.business;

import com.success.project.kindacoffee.entities.business.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {
}
