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
public class PersonMapper {

    private ModelMapper mm;

    public PersonMapper() {

        mm = new ModelMapper();
        mm.getConfiguration().setSkipNullEnabled(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.createTypeMap(Person.class, ApiPersonInfo.class)
                .addMapping(Person::getIdentity, ApiPersonInfo::setIdentity)
                .addMapping(Person::getName, ApiPersonInfo::setName)
                .addMapping(Person::getDirection, ApiPersonInfo::setDirection)
                .addMapping(Person::getPhone, ApiPersonInfo::setPhone)
                .addMapping(Person::getCountryCode, ApiPersonInfo::setCountryCode)
                .addMapping(Person::getAge, ApiPersonInfo::setAge)
                .addMapping(Person::getEnabled, ApiPersonInfo::setEnabled);
    }


    public List<ApiPersonInfo> mapListPersonToListApiPersonInfo(Collection<Person> people){
        return people.stream().map(this::mapPersonToApiPersonInfo).collect(Collectors.toList());
    }

    public ApiPersonInfo mapPersonToApiPersonInfo(Person person) {
        return mm.map(person, ApiPersonInfo.class);
    }

}
