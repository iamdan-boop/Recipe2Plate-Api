package com.recipe2plate.api.repositories;

import com.recipe2plate.api.entities.VerificationOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// to implement as when verification needed in second batches
@Repository
public interface VerificationOtpRepository extends JpaRepository<VerificationOtp, Long> {

    Optional<VerificationOtp> findByOtpCode(Integer otpCode);
}
