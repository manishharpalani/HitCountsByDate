package Nyansa;

import java.util.Map;
import java.util.stream.Stream;

public interface HitBucket {
    void hit(String entity);

    long getCount(String entity);

    Stream<Map.Entry<String, Long>> getSortedMapByFreq();

    String getHeader();
}
