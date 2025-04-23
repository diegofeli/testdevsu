package com.api.account.web;

import com.api.account.model.ApiTransactionInfo;
import com.api.account.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "TransactionController", description = "Operations related to the transactions")
@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Operation(summary = "Returns a transaction", description = "Returns a transaction identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the transaction identified by the specified id if exists")
	@GetMapping("/transaction/id/{id}")
	public ApiTransactionInfo findById(@Parameter(description = "Transaction id") @PathVariable Integer id) {
		return transactionService.findById(id);
	}

	@Operation(summary = "Returns a transaction list", description = "Returns a transaction identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the transaction identified by the specified id if exists")
	@GetMapping("/transaction/accounts/{numbers}")
	public List<ApiTransactionInfo> findByIds(@Parameter(description = "ids") @PathVariable List<String> numbers) {
		return transactionService.getTransactionsByAccountNumbers(numbers);
	}

	@PostMapping("/transaction")
	@Operation(summary = "Create a new Transaction")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "201",
			description = "Transaction created Successfully")
	@ApiResponse(responseCode = "404", description = "Transaction by id not found")
		public void createTransaction(@Valid @RequestBody ApiTransactionInfo apiTransactionInfo) {
		this.transactionService.createTransaction(apiTransactionInfo);
	}

	@PutMapping("/transaction")
	@Operation(summary = "Update a Transaction")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "200", description = "Transaction Successfully")
	@ApiResponse(responseCode = "404", description = "Transaction by id not found")
	public ApiTransactionInfo updateTransaction(@Valid @RequestBody ApiTransactionInfo apiTransactionInfo) {
		return this.transactionService.updateTransaction(apiTransactionInfo);
	}

	@DeleteMapping("/transaction/{id}")
	@Operation(
			summary = "Delete the Transaction of a given id"
	)
	@ApiResponse(responseCode = "200", description = "Transaction deleted successfully")
	@ApiResponse(responseCode = "404", description = "Transaction by id not found")
	public void deleteTransactionById(@Parameter(description = "id of the Transaction")
									@PathVariable Integer id) {
		this.transactionService.deleteTransaction(id);
	}

	@Operation(summary = "Returns all Transaction list", description = "Returns a Transaction identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the Transaction identified by the specified id if exists")
	@GetMapping("/transaction/all")
	public List<ApiTransactionInfo> findAll() {
		return transactionService.getAll();
	}

}
