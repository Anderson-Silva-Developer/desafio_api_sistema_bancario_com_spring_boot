package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.entities.Transaction;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void walletReport(Long idFromWallet, Long idToWallet, BigDecimal amount, Wallet wallet, String typeTransaction) {

        Transaction transaction = new Transaction()
                .setDateTime(new Date())
                .setWallet(wallet)
                .setIdFromWallet(idFromWallet)
                .setIdToWallet(idToWallet)
                .setTypeTransaction(typeTransaction)
                .setAmount(amount);
        this.transactionRepository.save(transaction);

    }


}
