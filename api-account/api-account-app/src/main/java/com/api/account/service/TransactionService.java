package com.api.account.service;

import com.api.account.model.ApiTransactionInfo;

import java.util.List;

public interface TransactionService {

	ApiTransactionInfo findById(Integer number);

	List<ApiTransactionInfo> getTransactionsByAccountNumbers(List<String> ids);

	List<ApiTransactionInfo> getAll();

	void deleteTransaction(Integer id);

	void createTransaction(ApiTransactionInfo transactionInfo);

	ApiTransactionInfo updateTransaction(ApiTransactionInfo transactionInfo);
}
