package frc.robot.Utilities;

import java.util.TreeMap;
import java.util.Map;

/**
 * Interpolating Tree Map based on Team 254's ITM.
 * @see <a href="https://github.com/Team254/FRC-2016-Public/blob/master/src/com/team254/lib/util/InterpolatingTreeMap.java">Team 254 Interpolating Tree Map</a>
 * Used for estimating flywheel speeds from a tree of already determined values.
 * @param <Key>
 * @param <Value>
 */

public class InterpolatingTreeMap<Key extends InverseInterpolable<Key> & Comparable<Key>, Value extends Interpolable<Value>> extends TreeMap<Key, Value> {
    int maxTreeSize;

    /**
     * Constructor if trimming tree size
     * @param maxSize The maximum size of the tree to trim to
     */
    public InterpolatingTreeMap(int maxSize){
        maxTreeSize = maxSize;
    }
    
    /**
     * Constructor for creating the tree without trimming
     */
    public InterpolatingTreeMap() {
        this(0);
    }

    @Override
    public Value put(Key key, Value value) {
        if(maxTreeSize > 0 && maxTreeSize <= size()){
            Key first = firstKey();
            remove(first);
        }

        super.put(key, value);

        return value;
    }

    public Value getInterpolated(Key key){
        Value gotValue = get(key);
        if (getValue == null){
            Key topBound = ceilingKey(key);
            Key botBound = floorKey(key);

            if (topBound == null && bottomBound == null) {
                return null;
            } 
            else if (topBound == null) {
                return get(bottomBound);
            } 
            else if (bottomBound == null) {
                return get(topBound);
            }

            Value topElement = get(topBound);
            Value bottomElement = get(bottomBound);
            return bottomElement.interpolate(topElement, bottomBound.inverseInterpolate(topBound, key));
        } else {
            return gotValue;
        }
    }

}