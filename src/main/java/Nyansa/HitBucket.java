package Nyansa;

import java.util.Set;

public interface HitBucket {
    void hit(String entity);

    long getTopHitCount();

    long getCount(String entity);

    Set<String> getItemsWithHitCount(Long hitCount);

    String getHeader();
}
