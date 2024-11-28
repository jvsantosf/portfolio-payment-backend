package jvsantos.tech.payment.repository;

import jvsantos.tech.payment.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payer, Long> {
}
