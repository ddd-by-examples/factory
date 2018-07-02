package io.dddbyexamples.factory.shortages.prediction.monitoring.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * @author Marcin Grzejszczak
 */
@SpringBootTest(webEnvironment = MOCK, classes = Config)
@AutoConfigureMockMvc
// would love to get rid of this
@AutoConfigureTestDatabase
@AutoConfigureRestDocs
@ActiveProfiles("test")
class ShortagesDaoSpec extends Specification {

    @Autowired
    ShortagesDao shortagesDao
    @Autowired
    MockMvc mockMvc

    def "should find ref by no"() {
        given:
        Config.defaultStubbing(shortagesDao)
        expect:
        mockMvc.perform(get("/shortages?refNo=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("find_ref_by_no",
                SpringCloudContractRestDocs.dslContract()))
    }
}
