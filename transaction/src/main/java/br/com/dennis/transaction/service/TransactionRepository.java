package br.com.dennis.transaction.service;

import br.com.dennis.transaction.model.TransactionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEvent, String> {
}
