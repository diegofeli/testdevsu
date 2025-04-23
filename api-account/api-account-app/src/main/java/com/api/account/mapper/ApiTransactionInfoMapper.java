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
public class ApiTransactionInfoMapper {

    private ModelMapper mm;

    public ApiTransactionInfoMapper() {

            mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(ApiTransactionInfo.class, Transaction.class)
                .addMapping(ApiTransactionInfo::getDescription, Transaction::setDescription)
                .addMapping(ApiTransactionInfo::getTotalAmount, Transaction::setTotalAmount)
                .addMapping(ApiTransactionInfo::getEnabled, Transaction::setEnabled);
    }


    public List<Transaction> mapListApiTransactionInfoToListTransaction(Collection<ApiTransactionInfo> apiTransactions){
        return apiTransactions.stream().map(this::mapApiTransactionInfoToTransaction).collect(Collectors.toList());
    }

    public Transaction mapApiTransactionInfoToTransaction(ApiTransactionInfo apiTransactionInfo) {
        return mm.map(apiTransactionInfo, Transaction.class);
    }

}
