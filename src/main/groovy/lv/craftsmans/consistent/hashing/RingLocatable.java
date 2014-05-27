package lv.craftsmans.consistent.hashing;

import com.google.common.hash.Hasher;

public interface RingLocatable {

    void registerHashAttributes(Hasher hasher);

}
