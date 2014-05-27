package lv.craftsmans.consistent.hashing;

public interface RingLocationCalculator {

    <T extends RingLocatable> int locationOnARing(T ringPlaceable);

}
