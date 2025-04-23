package com.api.account.web;

import com.api.account.model.ApiStatementInfo;
import com.api.account.model.ApiStatementResponse;
import com.api.account.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Tag(name = "StatementController", description = "Operations related to the transactions")
@RestController
public class StatementController {

	@Autowired
	private StatementService transactionService;



	@Operation(summary = "Returns all transaction list", description = "Returns a transaction identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the transaction identified by the specified id if exists")
	@ApiResponse(responseCode = "204", description = "Product not found")
	@GetMapping("/statement")
	public ApiStatementResponse getStatement(@RequestParam("clientIdentity") String clientIdentity,
											 @RequestParam("initialDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date initialDate,
											 @RequestParam("finalDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date finalDate) {

		ApiStatementInfo request = ApiStatementInfo.builder()
				.identity(clientIdentity)
				.initialDate(initialDate)
				.finalDate(finalDate)
				.build();
		return transactionService.getStatement(request);
	}
}
