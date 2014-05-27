package lv.craftsmans.consistent.hashing;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import lv.craftsmans.consistent.hashing.RingLocatable;
import lv.craftsmans.consistent.hashing.RingLocationCalculator;

import static com.google.common.hash.Hashing.consistentHash;
import static com.google.common.hash.Hashing.md5;

public class ConsistentMd5RingLocationCalculator implements RingLocationCalculator {

    private final int maxBucketCount;

    public ConsistentMd5RingLocationCalculator(int maxBucketCount) {
        this.maxBucketCount = maxBucketCount;
    }

    @Override
    public <T extends RingLocatable> int locationOnARing(T ringLocatable) {
        HashCode hashCode = md5Hash(ringLocatable);
        return consistentHash(hashCode, maxBucketCount);
    }

    @VisibleForTesting
    <T extends RingLocatable> HashCode md5Hash(T ringLocatable) {
        Hasher hasher = md5().newHasher();
        ringLocatable.registerHashAttributes(hasher);
        return hasher.hash();
    }
}
