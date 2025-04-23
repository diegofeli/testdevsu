package com.api.account.service.impl;

import com.api.account.mapper.AccountMapper;
import com.api.account.mapper.ApiAccountInfoMapper;
import com.api.account.model.ApiAccountInfo;
import com.api.account.repository.AccountRepository;
import com.api.account.repository.ClientRepository;
import com.api.account.repository.TransactionRepository;
import com.api.account.repository.TypeAccountRepository;
import com.api.account.service.AccountService;
import com.api.core.starter.security.ApiNotFoundException;
import com.api.persistence.domain.Account;
import com.api.persistence.domain.Client;
import com.api.persistence.domain.Transaction;
import com.api.persistence.domain.TypeAccount;
import com.api.util.Constants;
import com.api.util.ConstantsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TypeAccountRepository typeAccountRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private ApiAccountInfoMapper apiAccountInfoMapper;



	@Override
	public ApiAccountInfo findById(String number) {
		Optional<Account> optionalAccount = accountRepository.findByNumber(number);
		if(optionalAccount.isPresent()){
			return accountMapper.mapAccountToApiAccountInfo(optionalAccount.get());
		}else{
			throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND, ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
		}
	}

	@Override
	public List<ApiAccountInfo> getAccountsByIds(List<String> ids){

		List<Account> accountList = accountRepository.findByNumberIn(ids);

		if(accountList.isEmpty()){
			throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND,ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
		}else {
			return accountMapper.mapListAccountToListApiAccountInfo(accountList);
		}
	}

	@Override
	public void deleteAccount(String id) {

		Optional<Account> account = accountRepository.findByNumber(id);
		if(account.isPresent()){
			List<Transaction> transactionList = transactionRepository.findByAccountId(account.get().getId());

			if (!transactionList.isEmpty()) {
				throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_HAS_TRANSACTION,ConstantsMessage.MESSAGE_ACCOUNT_HAS_TRANSACTION);
			}else {
				accountRepository.deleteById(account.get().getId());
			}
		}else {
			throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND,ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
		}
	}

	@Override
	public void createAccount(ApiAccountInfo accountInfo) {
		Optional<Account> account = accountRepository.findByNumber(accountInfo.getNumber());
		if(account.isPresent()){
			throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_ALREADY_EXIST, ConstantsMessage.MESSAGE_ACCOUNT_ALREADY_EXIST);
		}else{
			Optional<Client> client = clientRepository.findByIdentity(accountInfo.getClientIdentity());
			if(client.isEmpty()){
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
			}else{
				Optional<TypeAccount> typeAccount = typeAccountRepository.findByName(accountInfo.getTypeAccount());
				if(typeAccount.isEmpty()){
					throw new ApiNotFoundException(Constants.ERROR_TYPE_ACCOUNT_NOT_FOUND, ConstantsMessage.MESSAGE_TYPE_ACCOUNT_NOT_FOUND);
				}else {
					Account newAccount = apiAccountInfoMapper.mapApiAccountInfoToAccount(accountInfo);
					newAccount.setClient(client.get());
					newAccount.setTypeAccount(typeAccount.get());
					accountRepository.save(newAccount);
				}
			}
		}
	}

	@Override
	public ApiAccountInfo updateAccount(ApiAccountInfo accountInfo) {
		Optional<Account> optionalAccount = accountRepository.findByNumber(accountInfo.getNumber());
		if(optionalAccount.isPresent()){
			Account account = optionalAccount.get();
			Optional<TypeAccount> typeAccount = typeAccountRepository.findByName(accountInfo.getTypeAccount());
			if (typeAccount.isPresent()) {
				account.setTypeAccount(typeAccount.get());
			}else{
				throw new ApiNotFoundException(Constants.ERROR_TYPE_ACCOUNT_NOT_FOUND, ConstantsMessage.MESSAGE_TYPE_ACCOUNT_NOT_FOUND);
			}

			Optional<Client> client = clientRepository.findByIdentity(accountInfo.getClientIdentity());
			if (client.isPresent()) {
				account.setClient(client.get());
			}else{
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
			}

			account.setDescription(accountInfo.getDescription());
			account.setEnabled(accountInfo.getEnabled());
			account.setOpeningBalance(accountInfo.getOpeningBalance());
			account.setBalance(accountInfo.getBalance());
			accountRepository.save(account);

			return accountInfo;
		}else {
			throw new ApiNotFoundException(Constants.ERROR_ACCOUNT_NOT_FOUND, ConstantsMessage.MESSAGE_ACCOUNT_NOT_FOUND);
		}

	}

	@Override
	public List<ApiAccountInfo> getAll() {
		List<Account> accountList = accountRepository.findAll();
		return accountMapper.mapListAccountToListApiAccountInfo(accountList);
	}
}
