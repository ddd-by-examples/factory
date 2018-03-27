package io.dddbyexamples.factory.demand.forecasting

import spock.lang.Specification

import static Adjustment.strong
import static Adjustment.weak
import static Demand.of

class ReviewPolicySpec extends Specification {

    def "'basic review policy' requires review only after strong adjustment \
          when new document doesn't match neither previous document nor adjustment"() {
        given:
        def policy = ReviewPolicy.BASIC

        expect:
        policy.reviewNeeded(
                previousDocument,
                adjustment,
                newDocument
        ) == review

        where:
        previousDocument | adjustment       | newDocument || review
        of(1000)         | strong(of(1000)) | of(1000)    || notNeeded()
        of(1000)         | strong(of(2000)) | of(2000)    || notNeeded()
        of(1000)         | strong(of(2000)) | of(1000)    || notNeeded()
        of(1000)         | strong(of(2000)) | of(1500)    || needed()
        of(1000)         | strong(of(2000)) | of(0)       || needed()
        of(1000)         | weak(of(1000))   | of(1000)    || notNeeded()
        of(1000)         | weak(of(2000))   | of(2000)    || notNeeded()
        of(1000)         | weak(of(2000))   | of(1000)    || notNeeded()
        of(1000)         | weak(of(2000))   | of(1500)    || notNeeded()
        of(1000)         | weak(of(2000))   | of(0)       || notNeeded()
    }

    private static boolean needed() {
        true
    }

    private static boolean notNeeded() {
        false
    }
}
