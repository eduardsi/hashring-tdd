package lv.craftsmans.consistent.hashing

import spock.lang.Specification

class ConsistentHashRingSpec extends Specification {

    def replicaCount = 2
    def bucketRing = Mock(BucketRing)
    def consistentHashRing = new ConsistentHashRing(replicaCount, bucketRing)

    def item = Mock(RingLocatable)

    def "adds item to a bucket ring for each replica"() {
        when:
        consistentHashRing.add(item)

        then:
        replicaCount.eachWithIndex { _, int index ->
            1 * bucketRing.add({ it.item == item && it.replica == index } as Bucket)
        }
    }

    def "removes all item replicas from a bucket ring"() {
        when:
        consistentHashRing.remove(item)

        then:
        replicaCount.eachWithIndex { _, int index ->
            1 * bucketRing.remove({ it.item == item && it.replica == index } as Bucket)
        }
    }

    def "finds item in a bucket ring by passed-in ring locatable"() {
        when:
        def ringLocatable = Mock(RingLocatable)
        bucketRing.findBy(ringLocatable) >> new Bucket(item, 0)

        then:
        consistentHashRing.findBy(ringLocatable) == item
    }

    def "fails on item lookup when bucket ring is empty"() {
        given:
        bucketRing.isEmpty() >> true

        when:
        consistentHashRing.findBy(Mock(RingLocatable))

        then:
        thrown(IllegalStateException)
    }


}
