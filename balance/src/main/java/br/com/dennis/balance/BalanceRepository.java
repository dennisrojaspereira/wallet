package br.com.dennis.balance;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BalanceRepository implements PanacheRepository<Balance> {
}
