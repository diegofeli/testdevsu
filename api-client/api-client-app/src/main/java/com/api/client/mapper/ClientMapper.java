package com.api.client.mapper;

import com.api.client.model.ApiClientInfo;
import com.api.persistence.domain.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private ModelMapper mm;

    public ClientMapper() {

        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(Client.class, ApiClientInfo.class)
                .addMapping(Client::getPassword, ApiClientInfo::setPassword)
                .addMapping(Client::getIdentity, ApiClientInfo::setIdentity)
                .addMapping(Client::getDirection, ApiClientInfo::setDirection)
                .addMapping(Client::getName, ApiClientInfo::setName)
                .addMapping(Client::getCountryCode, ApiClientInfo::setCountryCode)
                .addMapping(Client::getAge, ApiClientInfo::setAge)
                .addMapping(Client::getPhone, ApiClientInfo::setPhone)
                .addMapping(Client::getEnabled, ApiClientInfo::setEnabled);
    }


    public List<ApiClientInfo> mapListClientToListApiClientInfo(Collection<Client> clients){
        return clients.stream().map(this::mapClientToApiClientInfo).collect(Collectors.toList());
    }


    public ApiClientInfo mapClientToApiClientInfo(Client iClient) {
        return mm.map(iClient, ApiClientInfo.class);
    }


}
