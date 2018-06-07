package smoke

import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

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
		given:
			def conditions = new PollingConditions(timeout: this.timeout)
		expect:
			conditions.eventually {
				ResponseEntity<String> entity = this.testRestTemplate
						.getForEntity("http://" + this.applicationUrl + "/shortages", String.class);

				assert entity.getStatusCode().is2xxSuccessful()
			}
	}
}
