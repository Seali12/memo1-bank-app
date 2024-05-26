package com.aninfo;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.service.AccountService;
import com.aninfo.model.Transaction;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;

import com.aninfo.exceptions.TransactionNotFoundException;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;


	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}

	// TRANSACCIONES
	//creo el tipo de transaccion que quiero
	@PostMapping("/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction createTransaction(@RequestBody Transaction transaction) {

		// Validar que el tipo de transacción no sea nulo ni vacío
		if (transaction.getType() == null || transaction.getType().trim().isEmpty()) {
			throw new IllegalArgumentException("Transaction type cannot be null or empty");
		}
		switch (transaction.getType().toLowerCase()) {
			case "deposit":
				accountService.deposit(transaction.getCbu(), transaction.getAmount());
				return transactionService.deposit(transaction);

			case "withdraw":
				accountService.withdraw(transaction.getCbu(), transaction.getAmount());
				return transactionService.withdraw(transaction);

			default:

					throw new InvalidTransactionTypeException("Invalid transaction type");
		}
	}
	//busco  todas las transacciones de una cuenta
	@GetMapping("/accounts/transactions/cbu/{cbu}")
	public List<Transaction> getTransactionsByCbu(@PathVariable Long cbu) {
		return accountService.findTransactionsbyCbu(cbu);
	}

	// busco una transaccion en especifico
	@GetMapping("/transactions/{idTransaction}")
	public ResponseEntity<Transaction> getTransactionsByTransactionID(@PathVariable Long idTransaction) {
		Optional<Transaction> transaction = Optional.ofNullable(transactionService.findById(idTransaction));
		return ResponseEntity.of(transaction);

	}

	@DeleteMapping("/transactions/{idTransaction}")
	public void deleteTransaction(@PathVariable Long idTransaction) {
		Transaction transaction = transactionService.findById(idTransaction);
		transactionService.deleteTransaction(transaction);
	}
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
