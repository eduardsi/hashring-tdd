package lv.craftsmans.consistent.hashing

import spock.lang.Specification

class ConsistentMd5RingLocationCalculatorSpec extends Specification {

    def maxBucketCount = 3
    def calculator = new ConsistentMd5RingLocationCalculator(maxBucketCount)

    def "calculates location on a ring which is consistent hash living in [0..maxBucketCount) range"() {
        when:
        int locationOnARing = calculator.locationOnARing(ringPlaceable)

        then:
        def lowerBound = 0
        def upperBound = maxBucketCount - 1

        (lowerBound..upperBound).contains(locationOnARing)

        where:
        // As we have some probability involved, let's run the test for 10K different values
        ringPlaceable << (1..10000)*.toString().collect { id ->
            new RingLocatableUtf8String(someString: id)
        }
    }

    // Examples are taken from http://tools.ietf.org/html/rfc1321
    def "uses proper MD5 for hashing"() {
        when:
        def actualHash = calculator.md5Hash(new RingLocatableUtf8String(rawValue))

        then:
        actualHash.toString() == expectedMd5Hash

        where:
        rawValue                                                                           || expectedMd5Hash
        ""                                                                                 || "d41d8cd98f00b204e9800998ecf8427e"
        "a"                                                                                || "0cc175b9c0f1b6a831c399e269772661"
        "abc"                                                                              || "900150983cd24fb0d6963f7d28e17f72"
        "message digest"                                                                   || "f96b697d7cb7938d525a2f31aaf161d0"
        "abcdefghijklmnopqrstuvwxyz"                                                       || "c3fcd3d76192e4007dfb496cca67e13b"
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"                   || "d174ab98d277d9f5a5611c2c9f419d9f"
        "12345678901234567890123456789012345678901234567890123456789012345678901234567890" || "57edf4a22be3c955ac49da2e2107b67a"
    }


}
