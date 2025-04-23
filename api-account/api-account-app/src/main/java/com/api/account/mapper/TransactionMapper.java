package com.api.account.mapper;

import com.api.account.model.ApiTransactionInfo;
import com.api.persistence.domain.Account;
import com.api.persistence.domain.Transaction;
import com.api.persistence.domain.TransactionType;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    private ModelMapper mm;

    public TransactionMapper() {

        Converter<Account,String> accountMapper = ctx -> ctx.getSource().getNumber();

        Converter<TransactionType, String> typeTransactionMapper = ctx -> ctx.getSource().getName();
        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(Transaction.class, ApiTransactionInfo.class)
                .addMappings(mapper -> mapper.using(typeTransactionMapper).map(Transaction::getTransactionType, ApiTransactionInfo::setTransactionType))
                .addMapping(Transaction::getDescription, ApiTransactionInfo::setDescription)
                .addMapping(Transaction::getTotalAmount, ApiTransactionInfo::setTotalAmount)
                .addMapping(Transaction::getEnabled, ApiTransactionInfo::setEnabled)
                .addMappings(mapper -> mapper.using(accountMapper).map(Transaction::getAccount, ApiTransactionInfo::setAccountNumber));
    }


    public List<ApiTransactionInfo> mapListTransactionToListApiTransactionInfo(Collection<Transaction> transactions){
        return transactions.stream().map(this::mapTransactionToApiTransactionInfo).collect(Collectors.toList());
    }


    public ApiTransactionInfo mapTransactionToApiTransactionInfo(Transaction iTransaction) {
        return mm.map(iTransaction, ApiTransactionInfo.class);
    }


}
