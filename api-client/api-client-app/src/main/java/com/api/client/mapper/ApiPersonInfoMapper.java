package com.api.client.mapper;

import com.api.client.model.ApiPersonInfo;
import com.api.persistence.domain.Person;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiPersonInfoMapper {

    private ModelMapper mm;

    public ApiPersonInfoMapper() {

        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(ApiPersonInfo.class, Person.class)
                .addMapping(ApiPersonInfo::getIdentity, Person::setIdentity)
                .addMapping(ApiPersonInfo::getDirection, Person::setDirection)
                .addMapping(ApiPersonInfo::getName, Person::setName)
                .addMapping(ApiPersonInfo::getCountryCode, Person::setCountryCode)
                .addMapping(ApiPersonInfo::getAge, Person::setAge)
                .addMapping(ApiPersonInfo::getPhone, Person::setPhone)
                .addMapping(ApiPersonInfo::getEnabled, Person::setEnabled);
    }


    public List<Person> mapListApiPersonInfoToListPerson(Collection<ApiPersonInfo> apiPersons){
        return apiPersons.stream().map(this::mapApiPersonInfoToPerson).collect(Collectors.toList());
    }

    public Person mapApiPersonInfoToPerson(ApiPersonInfo apiPersonInfo) {
        return mm.map(apiPersonInfo, Person.class);
    }

}
