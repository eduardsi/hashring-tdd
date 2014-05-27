package lv.craftsmans.consistent.hashing

import com.google.common.base.Charsets
import com.google.common.hash.Hasher
import groovy.transform.Immutable
import groovy.transform.PackageScope

@Immutable
@PackageScope
class RingLocatableUtf8String implements RingLocatable {

    String someString

    @Override
    void registerHashAttributes(Hasher hasher) {
        hasher.putString(someString, Charsets.UTF_8)
    }
}