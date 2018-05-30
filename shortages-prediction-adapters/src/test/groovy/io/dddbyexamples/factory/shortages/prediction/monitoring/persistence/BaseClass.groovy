package io.dddbyexamples.factory.shortages.prediction.monitoring.persistence

import groovy.transform.CompileStatic
import io.restassured.RestAssured
import io.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK

/**
 * @author Marcin Grzejszczak
 */
@CompileStatic
// TODO: Migrate to MockMvc - need to know how to set a port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		properties = ["server.port=8080"],
		classes = Config)
@AutoConfigureTestDatabase
abstract class BaseClass extends Specification {

	@Autowired WebApplicationContext context
	@Autowired ShortagesDao shortagesDao

	def setup() {
		RestAssured.baseURI = "http://localhost:8080"
		Config.defaultStubbing(shortagesDao)
	}
}
