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
public class ApiClientInfoMapper {

    private ModelMapper mm;

    public ApiClientInfoMapper() {

        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(ApiClientInfo.class, Client.class)
                .addMapping(ApiClientInfo::getIdentity, Client::setIdentity)
                .addMapping(ApiClientInfo::getPassword, Client::setPassword)
                .addMapping(ApiClientInfo::getName, Client::setName)
                .addMapping(ApiClientInfo::getCountryCode, Client::setCountryCode)
                .addMapping(ApiClientInfo::getPhone, Client::setPhone)
                .addMapping(ApiClientInfo::getAge, Client::setAge)
                .addMapping(ApiClientInfo::getDirection, Client::setDirection)
                .addMapping(ApiClientInfo::getEnabled, Client::setEnabled);
    }


    public List<Client> mapListApiClientToListClient(Collection<ApiClientInfo> apiClients){
        return apiClients.stream().map(this::mapApiClientToClient).collect(Collectors.toList());
    }

    public Client mapApiClientToClient(ApiClientInfo apiClientInfo) {
        return mm.map(apiClientInfo, Client.class);
    }

}
