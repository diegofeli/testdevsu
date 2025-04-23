package com.api.client.service;

import com.api.client.model.ApiClientInfo;

import java.util.List;

public interface ClientService {

	ApiClientInfo findByIdentity(String identity);

	List<ApiClientInfo> getClientsByIds(List<String> ids);

	List<ApiClientInfo> getAll();

	void deleteClient(String id);

	void createClient(ApiClientInfo clientInfo);

	ApiClientInfo updateClient(ApiClientInfo clientInfo);
}
