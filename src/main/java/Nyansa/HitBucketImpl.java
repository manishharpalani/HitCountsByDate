package Nyansa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class HitBucketImpl implements HitBucket {
    final String header;
    Map<String, Long> freq = new HashMap<String, Long>();

    public HitBucketImpl(String header) {
        this.header = header;
    }

    @Override
    public void hit(String entity) {
        Long count = freq.get(entity);
        if (count == null) {
            freq.put(entity, 1l);
        } else {
            freq.put(entity, count + 1);
        }
    }

    @Override
    public long getCount(String entity) {
        Long count = freq.get(entity);
        if (count == null) {
            return 0;
        } else {
            return count;
        }
    }

    @Override
    public Stream<Map.Entry<String, Long>> getSortedMapByFreq() {
        return freq.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
    }

    @Override
    public String getHeader() {
        return header;
    }
}
