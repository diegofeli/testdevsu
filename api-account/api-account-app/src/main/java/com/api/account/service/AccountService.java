package com.api.account.service;

import com.api.account.model.ApiAccountInfo;

import java.util.List;

public interface AccountService {

	ApiAccountInfo findById(String number);

	List<ApiAccountInfo> getAccountsByIds(List<String> ids);

	List<ApiAccountInfo> getAll();

	void deleteAccount(String number);

	void createAccount(ApiAccountInfo accountInfo);

	ApiAccountInfo updateAccount(ApiAccountInfo accountInfo);
}
