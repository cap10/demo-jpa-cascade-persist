package com.samsofx.demojpacascadepersist;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Log4j2
public class DemoJpaCascadePersistApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJpaCascadePersistApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner runner(final TransactionRepository transactionRepository) {
        return args -> {
            final Transaction t1 = Transaction
                    .builder()
                    .amount(new BigDecimal("10.38"))
                    .description("Purchase Debit transaction")
                    .build();

            final Transaction t2 = Transaction
                    .builder()
                    .amount(new BigDecimal("0.00"))
                    .description("Purchase Credit transaction")
                    .counterTransaction(t1)
                    .build();

            log.info("Debit transaction before persist  {} ", t1);
            log.info("Credit transaction before persist  {} ", t2);

            final List<Transaction> persistedTxns = transactionRepository.saveAll(Arrays.asList(t1, t2));
            log.info("Persisted transactions [{}]", persistedTxns);
        };
    }

}
