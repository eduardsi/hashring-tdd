package lv.craftsmans.consistent.hashing

import spock.lang.Specification

class TreeMapBucketRingFindSpec extends Specification {

    def ringLocationCalculator = Mock(RingLocationCalculator)
    def bucketRing = new TreeMapBucketRing(ringLocationCalculator)

    def "finds a bucket by ring locatable"() {
        given:
        def buckets = [0, 10, 20, 100].collect { newBucketAt(it) }

        and:
        def ringLocatable = ringLocatableAt(ringLocatableLocation)

        expect:
        bucketRing.findBy(ringLocatable) == buckets[expectedBucketIndex]

        where:
        ringLocatableLocation || expectedBucketIndex
        0                     || 0
        10                    || 1
        99                    || 3
        100                   || 3
        101                   || 0
    }

    Bucket newBucketAt(int location) {
        def item = Mock(RingLocatable)
        def bucket = new Bucket(item, 0)
        ringLocationCalculator.locationOnARing(bucket) >> location

        bucketRing.add(bucket)
        bucket
    }

    RingLocatable ringLocatableAt(int location) {
        def ringLocatable = Mock(RingLocatable)
        ringLocationCalculator.locationOnARing(ringLocatable) >> location
        ringLocatable
    }

}
