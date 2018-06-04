package smoke

import java.util.concurrent.TimeUnit

import org.awaitility.Awaitility
import org.junit.runner.RunWith
import spock.lang.Specification

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.BDDAssertions.then

/**
 * @author Marcin Grzejszczak
 */
@SpringBootTest(classes = SmokeSpec.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ImportAutoConfiguration(PropertyPlaceholderAutoConfiguration)
class SmokeSpec extends Specification {

	//@Value('${stubrunner.url}') String stubRunnerUrl
	@Value('${application.url}') String applicationUrl
	@Value('${test.timeout:60}') Long timeout

	TestRestTemplate testRestTemplate = new TestRestTemplate();

	def 'should work'() {
		expect:
			Awaitility.await().atMost(this.timeout, TimeUnit.SECONDS).untilAsserted({
				ResponseEntity<String> entity = this.testRestTemplate
						.getForEntity("http://" + this.applicationUrl + "/shortages", String.class);

				then(entity.getStatusCode().is2xxSuccessful()).isTrue();
			})
	}
}
