package com.api.client.service.impl;

import com.api.client.mapper.ApiClientInfoMapper;
import com.api.client.mapper.ClientMapper;
import com.api.client.model.ApiClientInfo;
import com.api.client.repository.AccountRepository;
import com.api.client.repository.ClientRepository;
import com.api.client.repository.PersonRepository;
import com.api.client.service.ClientService;
import com.api.core.starter.security.ApiNotFoundException;
import com.api.persistence.domain.Client;
import com.api.persistence.domain.Person;
import com.api.util.Constants;
import com.api.util.ConstantsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	private ApiClientInfoMapper apiClientInfoMapper;


	@Override
	public ApiClientInfo findByIdentity(String identity) {
		Optional<Person> optionalPerson = personRepository.findByIdentity(identity);
		if(optionalPerson.isPresent()){
			Optional<Client> client = clientRepository.findById(optionalPerson.get().getId());

			if(client.isPresent()){
				return clientMapper.mapClientToApiClientInfo(client.get());
			}else{
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND, ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
			}
		}else{
			throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
		}
	}

	@Override
	public List<ApiClientInfo> getClientsByIds(List<String> ids){

		List<Person> personList = personRepository.findByIdentityIn(ids);

		if(personList.isEmpty()){
			throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
		}else {
			List<Integer> personIds = personList.stream()
					.map(Person::getId)
					.toList();
			List<Client> list = clientRepository.findByIdIn(personIds);

			if(list.isEmpty()){
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
			}else {
				return clientMapper.mapListClientToListApiClientInfo(list);
			}
		}
	}

	@Override
	public void deleteClient(String id) {
		Optional<Person> optionalPerson = personRepository.findByIdentity(id);
		if (optionalPerson.isPresent()) {
			Optional<Client> client = clientRepository.findById(optionalPerson.get().getId());
			if(client.isPresent()){
				if(!accountRepository.findByClientId(client.get().getId()).isEmpty()){
					throw new ApiNotFoundException(Constants.ERROR_CLIENT_HAS_ACCOUNT,ConstantsMessage.MESSAGE_CLIENT_HAS_ACCOUNT);
				}
				clientRepository.deleteById(optionalPerson.get().getId());
			}else{
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
			}
		}else{
			throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
		}

	}

	@Override
	public void createClient(ApiClientInfo clientInfo) {
		Optional<Person> optionalPerson = personRepository.findByIdentity(clientInfo.getIdentity());
		if(optionalPerson.isPresent()){
			if(clientRepository.findById(optionalPerson.get().getId()).isPresent()){
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_ALREADY_EXIST, ConstantsMessage.MESSAGE_CLIENT_ALREADY_EXIST);
			}else{
				Client newClient= apiClientInfoMapper.mapApiClientToClient(clientInfo);
				clientRepository.save(newClient);
			}
		}else {
			Client newClient= apiClientInfoMapper.mapApiClientToClient(clientInfo);
			clientRepository.save(newClient);
		}

	}

	@Override
	public ApiClientInfo updateClient(ApiClientInfo clientInfo) {
		Optional<Person> optionalPerson = personRepository.findByIdentity(clientInfo.getIdentity());
		if(optionalPerson.isPresent()){
			Optional<Client> optionalClient = clientRepository.findById(optionalPerson.get().getId());
			if(optionalClient.isEmpty()){
				throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
			}else{
				Client client = optionalClient.get();
				client.setDirection(clientInfo.getDirection());
				client.setPhone(clientInfo.getPhone());
				client.setName(clientInfo.getName());
				client.setEnabled(clientInfo.getEnabled());
				client.setAge(clientInfo.getAge());
				client.setPassword(clientInfo.getPassword());
				client.setCountryCode(clientInfo.getCountryCode());
				clientRepository.save(client);

				return clientInfo;
			}
		}else {
			throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
		}

	}

	@Override
	public List<ApiClientInfo> getAll() {
		List<Client> clientList = clientRepository.findAll();

		if(clientList.isEmpty()){
			throw new ApiNotFoundException(Constants.ERROR_CLIENT_NOT_FOUND,ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND);
		}else {
			return clientMapper.mapListClientToListApiClientInfo(clientList);
		}

	}
}
