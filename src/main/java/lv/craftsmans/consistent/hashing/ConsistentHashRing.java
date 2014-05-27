package lv.craftsmans.consistent.hashing;

import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkState;

public class ConsistentHashRing<T extends RingLocatable> {

    private final int replicaCount;

    private final BucketRing<T> bucketRing;

    public ConsistentHashRing(int replicaCount, BucketRing<T> bucketRing) {
        this.replicaCount = replicaCount;
        this.bucketRing = bucketRing;
    }

    public void add(T item) {
        forEachReplica((replica) -> {
            Bucket<T> bucket = new Bucket<>(item, replica);
            bucketRing.add(bucket);
        });
    }

    public void remove(T item) {
        forEachReplica((replica) -> {
            Bucket<T> bucket = new Bucket<>(item, replica);
            bucketRing.remove(bucket);
        });
    }

    public T findBy(RingLocatable ringLocatable) {
        checkState(!bucketRing.isEmpty(), "Unable to locate item by [%s]. No buckets available", ringLocatable);

        Bucket<T> bucket = bucketRing.findBy(ringLocatable);
        return bucket.getItem();
    }

    private void forEachReplica(Consumer<Integer> consumer) {
        for (int replica = 0; replica < replicaCount; replica++) {
            consumer.accept(replica);
        }
    }

}
