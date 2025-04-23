package com.api.account.service.impl;

import com.api.account.mapper.ApiAccountResponseMapper;
import com.api.account.model.ApiStatementInfo;
import com.api.account.model.ApiStatementResponse;
import com.api.account.repository.AccountRepository;
import com.api.account.service.StatementService;
import com.api.core.starter.security.ApiNotFoundException;
import com.api.persistence.domain.Account;
import com.api.util.Constants;
import com.api.util.ConstantsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ApiAccountResponseMapper apiAccountResponseMapper;

	@Override
	public ApiStatementResponse getStatement(ApiStatementInfo request) {
		Date finalDate;
		Calendar c = Calendar.getInstance();
		c.setTime(request.getFinalDate());
		c.add(Calendar.DATE, 1);
		finalDate = c.getTime();
		request.setFinalDate(finalDate);
		List<Account> accounts = accountRepository.findAccountsWithFilters(request.getIdentity(), request.getInitialDate(), request.getFinalDate());

		if (accounts.isEmpty()) {
			throw new ApiNotFoundException(Constants.ERROR_TRANSACTION_NOT_FOUND, ConstantsMessage.MESSAGE_TRANSACTION_NOT_FOUND);
		}

		ApiStatementResponse response = new ApiStatementResponse();
		response.setAccountResponses(apiAccountResponseMapper.mapListAccountToListApiAccountResponse(accounts));

		return response;
	}
}
