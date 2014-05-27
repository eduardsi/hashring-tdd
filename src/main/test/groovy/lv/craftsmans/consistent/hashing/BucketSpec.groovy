package lv.craftsmans.consistent.hashing

import com.google.common.hash.Hasher
import spock.lang.Specification

import static com.google.common.base.Charsets.UTF_8

class BucketSpec extends Specification {

    private static char COLON = ':'

    def hasher = Mock(Hasher)

    def "registers hash attributes of passed-in value and replica separated by colon"() {
        given:
        def value = "key"
        def replica = 12

        def bucket = new Bucket(new RingLocatableUtf8String(value), replica)

        when:
        bucket.registerHashAttributes(hasher)

        then:
        1 * hasher.putString(value, UTF_8)

        then:
        1 * hasher.putChar(COLON)

        then:
        1 * hasher.putInt(replica)

    }

}
