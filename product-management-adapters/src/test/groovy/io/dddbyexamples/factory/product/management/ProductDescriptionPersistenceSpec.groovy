package io.dddbyexamples.factory.product.management

import spock.lang.Ignore

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static java.util.Collections.singletonList

// TODO: Unignore
@Ignore
@SpringBootTest
class ProductDescriptionPersistenceSpec extends Specification {

    @Autowired
    ProductDescriptionDao dao

    void setup() {
        dao.deleteAllInBatch()
    }

    def "verify access to ProductDescription data"() {
        given:
        dao.save(new ProductDescriptionEntity("3009000",
                new ProductDescription("461952398951", singletonList("PROWAD.POJ.NA JARZ.ESSENT"))))

        when:
        def entities = dao.findAll()

        then:
        entities.size() == 1
        entities.get(0).description ==
                new ProductDescription("461952398951", singletonList("PROWAD.POJ.NA JARZ.ESSENT"))
    }
}
