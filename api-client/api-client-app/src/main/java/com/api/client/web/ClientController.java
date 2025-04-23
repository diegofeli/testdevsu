package com.api.client.web;

import com.api.client.model.ApiClientInfo;
import com.api.client.service.ClientService;
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

@Tag(name = "ClientController", description = "Operations related to the clients")
@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Operation(summary = "Returns a client", description = "Returns a client identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the client identified by the specified id if exists")
	@GetMapping("/client/id/{id}")
	public ApiClientInfo findById(@Parameter(description = "Client id") @PathVariable String id) {
		return clientService.findByIdentity(id);
	}

	@Operation(summary = "Returns a client list", description = "Returns a client identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the client identified by the specified id if exists")
	@GetMapping("/client/ids/{ids}")
	public List<ApiClientInfo> findByIds(@Parameter(description = "ids") @PathVariable List<String> ids) {
		return clientService.getClientsByIds(ids);
	}

	@PostMapping("/client")
	@Operation(summary = "Create a new Client")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "201",
			description = "Client created Successfully")
		public void createClient(@Valid @RequestBody ApiClientInfo apiClientInfo) {
		this.clientService.createClient(apiClientInfo);
	}

	@PutMapping("/client")
	@Operation(summary = "Update a Client")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "200", description = "Client Successfully")
	@ApiResponse(responseCode = "404", description = "Client by id not found")
	public ApiClientInfo updateClient(@Valid @RequestBody ApiClientInfo apiClientInfo) {
		return this.clientService.updateClient(apiClientInfo);
	}

	@DeleteMapping("/client/{clientId}")
	@Operation(
			summary = "Delete the Client of a given id"
	)
	@ApiResponse(responseCode = "200", description = "Client deleted successfully")
	@ApiResponse(responseCode = "404", description = "Client by id not found")
	public void deleteClientById(@Parameter(description = "id of the Client")
									@PathVariable String clientId) {
		this.clientService.deleteClient(clientId);
	}

	@Operation(summary = "Returns all Client list", description = "Returns a Client identified by its id.")
	@ApiResponse(responseCode = "200", description = "Returns the Client identified by the specified id if exists")
	@GetMapping("/client/all")
	public List<ApiClientInfo> findAll() {
		return clientService.getAll();
	}

}
