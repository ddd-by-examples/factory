package io.dddbyexamples.factory.shortages.prediction.calculation

import spock.lang.Specification

class ShortageCalculationAlgorithmSpec extends Specification
        implements ShortagesCalculationTrait {

    void setup() {
        TimeGrammar.apply()
    }

    def "Takes stock into account"() {
        given:
        def forecast = forecast(
                stock(1000L),
                deliveries((now + 20.min): 500L),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == noShortages()
    }

    def "Locked stock is not included"() {
        given:
        def forecast = forecast(
                stock(0L, 1000L),
                deliveries((now + 20.min): 500L),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage((now + 20.min): 500L, 1000L)
    }

    def "Shortages are not cumulative"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries((now + 20.min): 500L, (now + 1.day): 500L),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage(
                (now + 20.min): 500L,
                (now + 1.day): 500L
        )
    }

    def "Includes momentary delivery"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries((now): 500L),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage((now): 500L)
    }

    def "No demands means no shortages"() {
        given:
        def forecast = forecast(
                stock(0),
                noDeliveries(),
                noProductions()
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == noShortages()
    }

    def "Takes production plan into account"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries((now + 5.h): 500L),
                plan([production(now, 50.min, 10)])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == noShortages()
    }

    def "Takes current production partially into account"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries((now + 20.min): 500L),
                plan([production(now, 50.min, 10)])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage((now + 20.min): 300L)
    }

    def "Future productions cannot be counted into delivery"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries((now): 500L),
                plan([production(now + 1.h, 50.min, 10)])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage((now): 500L)
    }

    def "Production outputs are cumulative"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries((now + 30.min): 600L),
                plan([production(now, 50.min, 10), production(now, 50.min, 10)])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == noShortages()
    }

    def "Production outputs are not cumulative between deliveries"() {
        given:
        def forecast = forecast(
                stock(0),
                deliveries(
                        (now + 60.min): 500L, // first delivery
                        (now + 1.day + 60.min): 500L + 100L), // second delivery
                plan([
                        production(now, 50.min, 10), // consumed by first delivery
                        production(now + 1.day, 50.min, 10) // provides 500 for second delivery
                ])
        )

        when:
        def shortages = forecast.findShortages()

        then:
        shortages == shortage(
                (now + 1.day + 60.min): 100L
        )
    }
}
