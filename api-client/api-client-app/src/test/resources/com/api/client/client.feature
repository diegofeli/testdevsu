Feature: Test GET request for client information

  Background:
    # Definir las variables globales
    * url 'http://localhost:8091'
    * def token = 'YOUR_TOKEN_HERE'
    * def sessionId = 'E819D04888815867CD519322ABD287EA'

  Scenario: Get client information by ID
    Given path 'client/id/1130640371'
    And header X-Authorization = token
    And header Cookie = 'JSESSIONID=' + sessionId
    When method get
    Then status 200
    And match response == { "identity": "1130640371", "phone": "3152169276", "direction": "cocli",  "age": 37, "name": "Diego", "enabled": true, "countryCode": 57, "password": "123456" }

  Scenario: Try to get information of a non-existent client (status 404)
    Given path 'client/id/9999999999'
    And header X-Authorization = token
    And header Cookie = 'JSESSIONID=' + sessionId
    When method get
    Then status 404