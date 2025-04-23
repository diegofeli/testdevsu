package com.api.account.web;

import com.api.account.model.ApiAccountInfo;
import com.api.account.service.AccountService;
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

@Tag(name = "AccountController", description = "Operations related to the accounts")
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Operation(summary = "Returns a account", description = "Returns a account identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the account identified by the specified id if exists")
	@GetMapping("/account/id/{number}")
	public ApiAccountInfo findById(@Parameter(description = "Account number") @PathVariable String number) {
		return accountService.findById(number);
	}

	@Operation(summary = "Returns a account list", description = "Returns a account identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the account identified by the specified id if exists")
	@GetMapping("/account/ids/{numbers}")
	public List<ApiAccountInfo> findByIds(@Parameter(description = "ids") @PathVariable List<String> numbers) {
		return accountService.getAccountsByIds(numbers);
	}

	@PostMapping("/account")
	@Operation(summary = "Create a new Account")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "201",
			description = "Account created Successfully")
	@ApiResponse(responseCode = "404", description = "Account by id not found")
		public void createAccount(@Valid @RequestBody ApiAccountInfo apiAccountInfo) {
		this.accountService.createAccount(apiAccountInfo);
	}

	@PutMapping("/account")
	@Operation(summary = "Update a Account")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "200", description = "Account Successfully")
	@ApiResponse(responseCode = "404", description = "Account by id not found")
	public ApiAccountInfo updateAccount(@Valid @RequestBody ApiAccountInfo apiAccountInfo) {
		return this.accountService.updateAccount(apiAccountInfo);
	}

	@DeleteMapping("/account/{accountNumber}")
	@Operation(
			summary = "Delete the Account of a given id"
	)
	@ApiResponse(responseCode = "200", description = "Account deleted successfully")
	@ApiResponse(responseCode = "404", description = "Account by id not found")
	public void deleteAccountById(@Parameter(description = "id of the Account")
									@PathVariable String accountNumber) {
		this.accountService.deleteAccount(accountNumber);
	}

	@Operation(summary = "Returns all Account list", description = "Returns a Account identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the Account identified by the specified id if exists")
	@GetMapping("/account/all")
	public List<ApiAccountInfo> findAll() {
		return accountService.getAll();
	}

}
