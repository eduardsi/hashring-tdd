package lv.craftsmans.consistent.hashing

import spock.lang.Specification
import spock.lang.Stepwise

import static com.google.common.base.Preconditions.checkState

@Stepwise
class TreeMapBucketRingAddRemoveSpec extends Specification {

    def item = Mock(RingLocatable)
    def bucket = new Bucket(item, 0)

    def ringLocationCalculator = Mock(RingLocationCalculator)
    def bucketRing = new TreeMapBucketRing(ringLocationCalculator)

    def "adds bucket to the ring at a location calculated by ring location calculator"() {
        given:
        def calculatedLocation = 13456
        ringLocationCalculator.locationOnARing(bucket) >> calculatedLocation

        when:
        bucketRing.add(bucket)

        then:
        bucketRing.bucketsOnARing.get(calculatedLocation) == bucket
    }

    def "removes bucket from the ring at location calculated by ring location calculator"() {
        given:
        def calculatedLocation = 13456
        hasBucketAt(bucket, calculatedLocation)

        when:
        bucketRing.remove(bucket)

        then:
        bucketRing.bucketsOnARing.isEmpty()
    }

    private hasBucketAt(Bucket bucket, int location) {
        ringLocationCalculator.locationOnARing(bucket) >> location
        bucketRing.add(bucket)
        checkState(bucketRing.bucketsOnARing.containsKey(location))
    }


}
