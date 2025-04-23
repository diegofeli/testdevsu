package com.api.account.mapper;

import com.api.account.model.ApiAccountInfo;
import com.api.persistence.domain.Account;
import com.api.persistence.domain.Client;
import com.api.persistence.domain.TypeAccount;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiAccountInfoMapper {

    private ModelMapper mm;

    public ApiAccountInfoMapper() {

        Converter<String, Client> clientMapper = ctx -> {
            Client client = new Client();
            client.setIdentity(ctx.getSource());
            return client;
        };

        Converter<Integer, TypeAccount> typeAccountMapper = ctx -> {
            TypeAccount typeAccount = new TypeAccount();
            typeAccount.setId(ctx.getSource());
            return typeAccount;
        };

            mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(ApiAccountInfo.class, Account.class)
                //.addMappings(mapper -> mapper.using(typeAccountMapper).map(ApiAccountInfo::getTypeAccount, Account::setTypeAccount))
                .addMapping(ApiAccountInfo::getDescription, Account::setDescription)
                .addMapping(ApiAccountInfo::getNumber, Account::setNumber)
                //.addMappings(mapper -> mapper.using(clientMapper).map(ApiAccountInfo::getClientIdentity, Account::setClient))
                .addMapping(ApiAccountInfo::getOpeningBalance, Account::setOpeningBalance)
                .addMapping(ApiAccountInfo::getBalance, Account::setBalance)
                .addMapping(ApiAccountInfo::getEnabled, Account::setEnabled);
    }


    public List<Account> mapListApiAccountInfoToListAccount(Collection<ApiAccountInfo> apiAccounts){
        return apiAccounts.stream().map(this::mapApiAccountInfoToAccount).collect(Collectors.toList());
    }

    public Account mapApiAccountInfoToAccount(ApiAccountInfo apiAccountInfo) {
        return mm.map(apiAccountInfo, Account.class);
    }

}
