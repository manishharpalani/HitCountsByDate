package Nyansa;

import com.google.common.collect.Sets;

import java.util.*;

class HitBucketImpl implements HitBucket {
    private final String header;
    private final Map<String, Long> urlFreq = new HashMap<String, Long>();   // URL to hitCount
    private final Map<Long, Set<String>> freqToUrls = new HashMap<>(); // hitCount to Set of URLs
    private Long topHitCount = 0l;

    public HitBucketImpl(String header) {
        this.header = header;
    }

    @Override
    public void hit(String entity) {
        Long entityFreq = urlFreq.merge(entity, 1l, Long::sum);

        if (entityFreq > 1) {
            freqToUrls.get(entityFreq - 1).remove(entity);
        }
        freqToUrls.merge(entityFreq, Sets.newHashSet(entity), (a, b) -> {a.addAll(b); return a; } );

        if (entityFreq > topHitCount) {
            topHitCount = entityFreq;
        }
    }

    @Override
    public long getTopHitCount() {
        return topHitCount;
    }

    @Override
    public long getCount(String entity) {
        return urlFreq.getOrDefault(entity, 0l);
    }

    @Override
    public Set<String> getItemsWithHitCount(Long hitCount) {
        return (hitCount > 0 && hitCount <= topHitCount) ? freqToUrls.get(hitCount) : Sets.newHashSet();
    }

    @Override
    public String getHeader() {
        return header;
    }
}
