package Nyansa

import spock.lang.Specification

class HitBucketTest extends Specification {
    HitBucket hitCounts;

    void setup() {
        hitCounts = new HitBucketImpl("01/10/2018 GMT");
        3.times {
            hitCounts.hit("www.nyansa.com")
        }
        2.times {
            hitCounts.hit("www.abc.com")
        }
        hitCounts.hit("www.xyz.com")
    }

    def "test header"() {
        expect:
        assert hitCounts.getHeader() == "01/10/2018 GMT"
    }

    def "test getcount"() {
        expect: "counts for existing and missing items"
        assert hitCounts.getCount(url) == hits

        where:
        url             |   hits
        "www.aaa.com"   |   0
        "www.abc.com"   |   2
        "www.xyz.com"   |   1
        "www.nyansa.com"|   3
    }

    def "test getSortedMapByFreq"() {
        setup:
        ArrayList<Map.Entry> sortedEntries = hitCounts.getSortedMapByFreq().collect();

        expect: "counts for existing and missing items"
        assert sortedEntries.size() == 3
        assert sortedEntries.get(index).getKey() == url
        assert sortedEntries.get(index).getValue() == hits

        where:
        index   |url                |hits
        0       |"www.nyansa.com"   |3
        1       |"www.abc.com"      |2
        2       |"www.xyz.com"      |1
    }
}
