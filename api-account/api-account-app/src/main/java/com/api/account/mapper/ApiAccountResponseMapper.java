package com.api.account.mapper;

import com.api.account.model.ApiAccountResponse;
import com.api.account.model.ApiClientResponse;
import com.api.account.model.ApiTransactionResponse;
import com.api.persistence.domain.Account;
import com.api.persistence.domain.Transaction;
import com.api.persistence.domain.TypeAccount;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiAccountResponseMapper {

    private ModelMapper mm;

    public ApiAccountResponseMapper() {
        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<List<Transaction>, List<ApiTransactionResponse>> transactionMapper = ctx -> {
            List<ApiTransactionResponse> apiTransactionResponses = new ArrayList<>();

            ctx.getSource().forEach(transaction -> {
                ApiTransactionResponse transactionResponse = new ApiTransactionResponse();
                transactionResponse.setId(transaction.getId());
                transactionResponse.setTransactionType(transaction.getTransactionType().getName());
                transactionResponse.setDescription(transaction.getDescription());
                transactionResponse.setEnabled(transaction.getEnabled());
                transactionResponse.setTotalAmount(transaction.getTotalAmount());
                apiTransactionResponses.add(transactionResponse);
            });
            return apiTransactionResponses;
        };

        Converter<TypeAccount, String> typeAccountMapper = ctx -> ctx.getSource().getName();

        mm.createTypeMap(Account.class, ApiAccountResponse.class)
                .addMappings(mapper -> mapper.using(transactionMapper).map(Account::getTransactions, ApiAccountResponse::setTransactionList))
                .addMapping(Account::getDescription, ApiAccountResponse::setDescription)
                .addMapping(Account::getEnabled, ApiAccountResponse::setEnabled)
                .addMapping(Account::getBalance, ApiAccountResponse::setBalance)
                .addMapping(Account::getNumber, ApiAccountResponse::setNumber)
                .addMapping(Account::getOpeningBalance, ApiAccountResponse::setOpeningBalance)
                .addMappings(mapper -> mapper.using(typeAccountMapper).map(Account::getTypeAccount, ApiAccountResponse::setTypeAccount))
        ;
    }


    public List<ApiAccountResponse> mapListAccountToListApiAccountResponse(Collection<Account> accounts){
        return accounts.stream().map(this::mapAccountToApiAccountResponse).collect(Collectors.toList());
    }


    public ApiAccountResponse mapAccountToApiAccountResponse(Account account) {
        ApiAccountResponse apiAccountResponse = mm.map(account, ApiAccountResponse.class);

        ApiClientResponse client = new ApiClientResponse();
        client.setIdentity(account.getClient().getIdentity());
        client.setEnabled(account.getClient().getEnabled());
        client.setAge(account.getClient().getAge());
        client.setName(account.getClient().getName());
        client.setDirection(account.getClient().getDirection());
        client.setPhone(account.getClient().getPhone());
        client.setCountryCode(account.getClient().getCountryCode());
        apiAccountResponse.setClient(client);
        return apiAccountResponse;
    }


}