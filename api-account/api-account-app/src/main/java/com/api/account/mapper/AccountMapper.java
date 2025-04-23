package com.api.account.mapper;

import com.api.account.model.ApiAccountInfo;
import com.api.persistence.domain.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    private ModelMapper mm;

    public AccountMapper() {

        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(Account.class, ApiAccountInfo.class)
                .addMapping(account -> account.getClient().getIdentity(), ApiAccountInfo::setClientIdentity)
                .addMapping(Account::getDescription, ApiAccountInfo::setDescription)
                .addMapping(Account::getNumber, ApiAccountInfo::setNumber)
                .addMapping(account -> account.getTypeAccount().getName(), ApiAccountInfo::setTypeAccount)
                .addMapping(Account::getOpeningBalance, ApiAccountInfo::setOpeningBalance)
                .addMapping(Account::getBalance, ApiAccountInfo::setBalance)
                .addMapping(Account::getEnabled, ApiAccountInfo::setEnabled);
    }


    public List<ApiAccountInfo> mapListAccountToListApiAccountInfo(Collection<Account> accounts){
        return accounts.stream().map(this::mapAccountToApiAccountInfo).collect(Collectors.toList());
    }


    public ApiAccountInfo mapAccountToApiAccountInfo(Account iAccount) {
        return mm.map(iAccount, ApiAccountInfo.class);
    }


}
