package lv.craftsmans.consistent.hashing

import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class TreeMapBucketRingEmptyCheckSpec extends Specification {

    def ringLocationCalculator = Mock(RingLocationCalculator)
    def bucketRing = new TreeMapBucketRing(ringLocationCalculator)

    def "is empty when internal map is empty"() {
        expect:
        bucketRing.isEmpty()
    }

    def "is not empty when internal map has some elements"() {
        given:
        def item = Mock(RingLocatable)
        def bucket = new Bucket(item, 0)

        when:
        bucketRing.add(bucket)

        then:
        !bucketRing.isEmpty()
    }


}
