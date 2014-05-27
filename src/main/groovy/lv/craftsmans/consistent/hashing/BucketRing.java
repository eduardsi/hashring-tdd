package lv.craftsmans.consistent.hashing;

public interface BucketRing<T extends RingLocatable> {

    boolean isEmpty();

    void add(Bucket<T> bucket);

    void remove(Bucket<T> bucket);

    Bucket<T> findBy(RingLocatable ringLocatable);
}
