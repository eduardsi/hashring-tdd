package lv.craftsmans.consistent.hashing;

import com.google.common.annotations.VisibleForTesting;

import java.util.SortedMap;
import java.util.TreeMap;

class TreeMapBucketRing<T extends RingLocatable> implements BucketRing<T> {

    @VisibleForTesting
    final SortedMap<Integer, Bucket<T>> bucketsOnARing = new TreeMap<>();

    private final RingLocationCalculator ringLocationCalculator;

    public TreeMapBucketRing(RingLocationCalculator ringLocationCalculator) {
        this.ringLocationCalculator = ringLocationCalculator;
    }

    @Override
    public boolean isEmpty() {
        return bucketsOnARing.isEmpty();
    }

    @Override
    public void add(Bucket<T> bucket) {
        int locationOnARing = locationOnARing(bucket);
        bucketsOnARing.put(locationOnARing, bucket);
    }

    @Override
    public void remove(Bucket<T> bucket) {
        int locationOnARing = locationOnARing(bucket);
        bucketsOnARing.remove(locationOnARing);
    }

    @Override
    public Bucket<T> findBy(RingLocatable ringLocatable) {

        int locationOnARing = locationOnARing(ringLocatable);

        if (bucketStartsAt(locationOnARing)) {
            return getBucketAt(locationOnARing);
        }

        int nextBucketLocationOnARing = findNextBucketLocationByMovingClockwise(locationOnARing);
        return getBucketAt(nextBucketLocationOnARing);
    }

    private boolean bucketStartsAt(int locationOnARing) {
        return bucketsOnARing.containsKey(locationOnARing);
    }

    private Bucket<T> getBucketAt(int locationOnARing) {
        return bucketsOnARing.get(locationOnARing);
    }

    private int findNextBucketLocationByMovingClockwise(int startingLocation) {
        SortedMap<Integer, ?> bucketsAhead = bucketsOnARing.tailMap(startingLocation);
        return bucketsAhead.isEmpty() ? bucketsOnARing.firstKey() : bucketsAhead.firstKey();
    }

    private int locationOnARing(RingLocatable ringLocatable) {
        return ringLocationCalculator.locationOnARing(ringLocatable);
    }

}
