package com.api.account.service;

import com.api.account.model.ApiStatementInfo;
import com.api.account.model.ApiStatementResponse;

public interface StatementService {

	ApiStatementResponse getStatement(ApiStatementInfo request);

}
