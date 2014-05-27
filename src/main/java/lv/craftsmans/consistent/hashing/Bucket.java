package lv.craftsmans.consistent.hashing;

import com.google.common.hash.Hasher;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Bucket<T extends RingLocatable> implements RingLocatable {

    private final T item;

    private final int replica;

    public Bucket(T item, int replica) {
        this.item = checkNotNull(item, "Bucket item cannot be null");
        this.replica = replica;
    }

    public T getItem() {
        return item;
    }

    @Override
    public void registerHashAttributes(Hasher hasher) {
        addValueHashAttributes(hasher);
        addReplicaHashAttribute(hasher);
    }

    private void addValueHashAttributes(Hasher hasher) {
        item.registerHashAttributes(hasher);
    }

    private void addReplicaHashAttribute(Hasher hasher) {
        hasher.putChar(':');
        hasher.putInt(replica);
    }


}
