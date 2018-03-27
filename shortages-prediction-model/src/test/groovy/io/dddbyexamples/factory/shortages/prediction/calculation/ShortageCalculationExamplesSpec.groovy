package io.dddbyexamples.factory.shortages.prediction.calculation

import spock.lang.Specification

class ShortageCalculationExamplesSpec extends Specification
        implements ShortagesCalculationTrait {

    void setup() {
        TimeGrammar.apply()
    }

    def "Constant week delivery covered by current stock"() {
        given:
        def forecast = forecast(
                stock(7 * 500L),
                deliveries(
                        (now): 500L,
                        (now + 1.day): 500L,
                        (now + 2.day): 500L,
                        (now + 3.day): 500L,
                        (now + 4.day): 500L,
                        (now + 5.day): 500L,
                        (now + 6.day): 500L
                ),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == noShortages()
    }

    def "Constant week delivery partially covered by current stock"() {
        given:
        def forecast = forecast(
                stock(4 * 500L),
                deliveries(
                        (now): 500L,
                        (now + 1.day): 500L,
                        (now + 2.day): 500L,
                        (now + 3.day): 500L,
                        (now + 4.day): 500L,
                        (now + 5.day): 500L,
                        (now + 6.day): 500L
                ),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage(
                (now + 4.day): 500L,
                (now + 5.day): 500L,
                (now + 6.day): 500L
        )
    }

    def "Constant week delivery covered by stock and plan"() {
        given:
        def forecast = forecast(
                stock(6 * 500L),
                deliveries(
                        (now): 500L,
                        (now + 1.day): 500L,
                        (now + 2.day): 500L,
                        (now + 3.day): 500L,
                        (now + 4.day): 500L,
                        (now + 5.day): 500L,
                        (now + 6.day): 500L
                ),
                plan([production((now + 1.day), 50.min, 10)])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == noShortages()
    }

    def "Constant delivery with regular productions"() {
        given:
        def forecast = forecast(
                stock(500L),
                deliveries(
                        (now): 500L,
                        (now + 1.day): 500L,
                        (now + 2.day): 500L,
                        (now + 3.day): 500L,
                        (now + 4.day): 500L,
                        (now + 5.day): 500L,
                        (now + 6.day): 500L
                ),
                plan([
                        production((now), 50.min, 10),
                        production((now + 1.day), 50.min, 10),
                        production((now + 2.day), 50.min, 10),
                        production((now + 3.day), 50.min, 10)
                ])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage(
                (now + 5.day): 500L,
                (now + 6.day): 500L,
        )
    }
}
