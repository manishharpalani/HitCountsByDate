package Nyansa

import spock.lang.Specification

class HitBucketTest extends Specification {
    HitBucket hitCounts;

    void setup() {
        hitCounts = new HitBucketImpl("01/10/2018 GMT");
        4.times {
            hitCounts.hit("www.nyansa.com")
        }
        2.times {
            hitCounts.hit("www.abc.com")
        }
        2.times {
            hitCounts.hit("www.xyz.com")
        }
    }

    def "test header"() {
        expect:
        assert hitCounts.getHeader() == "01/10/2018 GMT"
    }

    def "test getcount"() {
        expect: "counts for existing and missing items"
        assert hitCounts.getCount(url) == hits

        where:
        url              || hits
        "www.aaa.com"    || 0
        "www.abc.com"    || 2
        "www.xyz.com"    || 2
        "www.nyansa.com" || 4
    }

    def "test getItemsWithHitCount"() {

        expect: "counts for existing and missing items"
        assert hitCounts.getTopHitCount() == 4
        assert hitCounts.getItemsWithHitCount(numHits).containsAll(urlSet)

        where:
        numHits || urlSet
        5       || []
        4       || ["www.nyansa.com"]
        3       || []
        2       || ["www.xyz.com", "www.abc.com"]
        1       || []
        0       || []
    }
}
