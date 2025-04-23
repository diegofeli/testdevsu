package com.api.account.service.impl;

import com.api.account.mapper.ApiTransactionInfoMapper;
import com.api.account.mapper.TransactionMapper;
import com.api.account.model.ApiTransactionInfo;
import com.api.account.repository.AccountRepository;
import com.api.account.repository.ClientRepository;
import com.api.account.repository.TransactionRepository;
import com.api.account.repository.TransactionTypeRepository;
import com.api.account.service.TransactionService;
import com.api.core.starter.security.ApiNotFoundException;
import com.api.persistence.domain.Account;
import com.api.persistence.domain.Transaction;
import com.api.persistence.domain.TransactionType;
import com.api.util.Constants;
import com.api.util.ConstantsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionTypeRepository typeTransactionRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private ApiTransactionInfoMapper apiTransactionInfoMapper;



	@Override
	public ApiTransactionInfo findById(Integer id) {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
		if (optionalTransaction.isPresent()) {
			return transactionMapper.mapTransactionToApiTransactionInfo(optionalTransaction.get());
		} else {
			throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND, ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
		}
	}

	@Override
	public List<ApiTransactionInfo> getTransactionsByAccountNumbers(List<String> numbers){
		List<Account> accountList = accountRepository.findByNumberIn(numbers);
		if(accountList.isEmpty()){
			throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND,ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
		}else {
			List<Integer> accountIds = accountList.stream().map(Account::getId).toList();

			List<Transaction> transactionList = transactionRepository.findByAccountIdIn(accountIds);

			if(transactionList.isEmpty()){
				throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND,ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
			}else {
				return transactionMapper.mapListTransactionToListApiTransactionInfo(transactionList);
			}
		}
	}

	@Override
	public void deleteTransaction(Integer id) {

		Optional<Transaction> transaction = transactionRepository.findById(id);
		if(transaction.isPresent()){
			Account account = transaction.get().getAccount();
			if(transaction.get().getTransactionType().getName().equals("CREDIT")){
				account.setBalance(account.getBalance().add(transaction.get().getTotalAmount()));
			}else {
				account.setBalance(account.getBalance().subtract(transaction.get().getTotalAmount()));
			}
			transactionRepository.deleteById(transaction.get().getId());
			accountRepository.save(account);
		}else {
			throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND,ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
		}
	}

	@Override
	public void createTransaction(ApiTransactionInfo transactionInfo) {

			Optional<Account> account = accountRepository.findByNumber(transactionInfo.getAccountNumber());
			if(account.isEmpty()){
				throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND,ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
			}else{
				Account account2 = account.get();
				Optional<TransactionType> typeTransaction = typeTransactionRepository.findByName(transactionInfo.getTransactionType());
				if(typeTransaction.isEmpty()){
					throw new ApiNotFoundException(Constants.ERROR_TYPE_TRANSACTION_NOT_FOUND,ConstantsMessage.MESSAGE_TYPE_TRANSACTION_NOT_FOUND);
				}else {
					Transaction newTransaction = apiTransactionInfoMapper.mapApiTransactionInfoToTransaction(transactionInfo);
					newTransaction.setTransactionType(typeTransaction.get());
					if(typeTransaction.get().getName().equals("CREDIT")){
						if(account2.getBalance().subtract(transactionInfo.getTotalAmount()).compareTo(BigDecimal.ZERO)<0){
							throw new ApiNotFoundException(Constants.ERROR_NOT_HAVE_FUNDS,ConstantsMessage.MESSAGE_NOT_HAVE_FUNDS);
						}else{
							account2.setBalance(account2.getBalance().subtract(transactionInfo.getTotalAmount()));
						}
					}else {
						account2.setBalance(account2.getBalance().add(transactionInfo.getTotalAmount()));
					}
					newTransaction.setAccount(account2);
					transactionRepository.save(newTransaction);
					accountRepository.save(account2);
				}
			}
	}

	@Override
	public ApiTransactionInfo updateTransaction(ApiTransactionInfo transactionInfo) {
		if(transactionInfo.getId() == null){
			throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND,ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
		}else {
			Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionInfo.getId());
			if(optionalTransaction.isPresent()){
				Transaction transaction = optionalTransaction.get();
				String initTypeTransaction = transaction.getTransactionType().getName();
				String newTypeTransaction = "";
				Optional<TransactionType> typeTransaction = typeTransactionRepository.findByName(transactionInfo.getTransactionType());
				if (typeTransaction.isPresent()) {
					newTypeTransaction = typeTransaction.get().getName();
					transaction.setTransactionType(typeTransaction.get());
				}else{
					throw new ApiNotFoundException(Constants.ERROR_TYPE_ACCOUNT_NOT_FOUND,ConstantsMessage.MESSAGE_TYPE_ACCOUNT_NOT_FOUND);
				}

				Optional<Account> accountOptional = accountRepository.findByNumber(transactionInfo.getAccountNumber());
				if (accountOptional.isPresent()) {
					Account account = accountOptional.get();
					if(!Objects.equals(accountOptional.get().getNumber(), optionalTransaction.get().getAccount().getNumber())){
						transaction.setAccount(accountOptional.get());
					}else {
						if(initTypeTransaction.equals(newTypeTransaction)){
							if(initTypeTransaction.equals("CREDIT")){
								account.setBalance(account.getBalance().add(optionalTransaction.get().getTotalAmount()));
								account.setBalance(account.getBalance().subtract(transactionInfo.getTotalAmount()));
								if(account.getBalance().compareTo(BigDecimal.ZERO)<0){
									throw new ApiNotFoundException(Constants.ERROR_NOT_HAVE_FUNDS,ConstantsMessage.MESSAGE_NOT_HAVE_FUNDS);
								}
							}else{
								account.setBalance(account.getBalance().subtract(optionalTransaction.get().getTotalAmount()));
								account.setBalance(account.getBalance().add(transactionInfo.getTotalAmount()));
							}
						}else {
							if(initTypeTransaction.equals("CREDIT") && newTypeTransaction.equals("DEBIT")){
								account.setBalance(account.getBalance().add(optionalTransaction.get().getTotalAmount()));
								account.setBalance(account.getBalance().add(transactionInfo.getTotalAmount()));
							}else{
								account.setBalance(account.getBalance().subtract(optionalTransaction.get().getTotalAmount()));
								account.setBalance(account.getBalance().subtract(transactionInfo.getTotalAmount()));
								if(account.getBalance().compareTo(BigDecimal.ZERO)<0){
									throw new ApiNotFoundException(Constants.ERROR_NOT_HAVE_FUNDS,ConstantsMessage.MESSAGE_NOT_HAVE_FUNDS);
								}
							}
						}
					}
					transaction.setDescription(transactionInfo.getDescription());
					transaction.setEnabled(transactionInfo.getEnabled());
					transaction.setTotalAmount(transactionInfo.getTotalAmount());
					transactionRepository.save(transaction);
					accountRepository.save(account);
				}else{
					throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND,ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
				}


				return transactionInfo;
			}else {
				throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND,ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
			}
		}
	}

	@Override
	public List<ApiTransactionInfo> getAll() {
		List<Transaction> transactionList = transactionRepository.findAll();
		if(transactionList.isEmpty()){
			throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND,ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
		}else {
			return transactionMapper.mapListTransactionToListApiTransactionInfo(transactionList);
		}
	}
}
