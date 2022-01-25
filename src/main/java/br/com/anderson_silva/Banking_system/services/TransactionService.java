package br.com.anderson_silva.Banking_system.services;

import br.com.anderson_silva.Banking_system.entities.Transaction;
import br.com.anderson_silva.Banking_system.entities.Wallet;
import br.com.anderson_silva.Banking_system.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TransactionService {
    private  final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void walletReport(Long idFromWallet, Long idToWallet, BigDecimal amount, Wallet wallet, String typeTransaction){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Transaction transaction=new Transaction()
                .setDateTime(new Date())
                .setWallet(wallet)
                .setIdFromWallet(idFromWallet)
                .setIdToWallet(idToWallet)
                .setTypeTransaction(typeTransaction)
                .setAmount(amount);
        this.transactionRepository.save(transaction);

    }



}
