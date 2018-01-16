package Nyansa

import spock.lang.Specification


class HitReportTest extends Specification {
    HitReport report

    void setup() {
        report = new HitReportImpl()
        report.getNumReportDays() == 0
        report.process("./resources/input.txt")
    }

    def "check collection returned by getHitCounts"() {
        given:
        Collection<HitBucketImpl> hitCounts = report.getHitCounts()

        expect:
        assert hitCounts.size() == 3
        HitBucketImpl dayHitCounts = hitCounts[day]
        assert dayHitCounts.getHeader() == header
        assert dayHitCounts.getItemsWithHitCount(numHits).containsAll(urls)

        where:
        day || header           | numHits || urls
        0   || "08/08/2014 GMT" | 2       || ["www.facebook.com", "www.google.com"]
        0   || "08/08/2014 GMT" | 1       || ["news.ycombinator.com"]
        1   || "08/09/2014 GMT" | 3       || ["www.nba.com"]
        1   || "08/09/2014 GMT" | 2       || ["sports.yahoo.com"]
        1   || "08/09/2014 GMT" | 1       || ["www.cnn.com"]
        2   || "08/10/2014 GMT" | 1       || ["www.twitter.com"]
    }

    def "Add more lines and ensure report data is updated appropriately"() {
        setup:
        report.processLine("1515649698|www.manish.com") // date: 01/11/2018 (should become last entry)
        report.processLine("1292046498|www.sierra.com") // date: 12/11/2010 (should become first entry)
        Collection<HitBucketImpl> hitCounts = report.getHitCounts()

        expect:
        assert hitCounts.size() == 5
        HitBucketImpl dayHitCounts = hitCounts[day]
        assert dayHitCounts.getHeader() == header
        assert dayHitCounts.getItemsWithHitCount(numHits).containsAll(urls)

        where: "dates should be inserted in date order rather than alphabetic order"
        day || header           | numHits || urls
        0   || "12/11/2010 GMT" | 1       || ["www.sierra.com"]
        1   || "08/08/2014 GMT" | 2       || ["www.facebook.com", "www.google.com"]
        1   || "08/08/2014 GMT" | 1       || ["news.ycombinator.com"]
        2   || "08/09/2014 GMT" | 3       || ["www.nba.com"]
        2   || "08/09/2014 GMT" | 2       || ["sports.yahoo.com"]
        2   || "08/09/2014 GMT" | 1       || ["www.cnn.com"]
        3   || "08/10/2014 GMT" | 1       || ["www.twitter.com"]
        4   || "01/11/2018 GMT" | 1       || ["www.manish.com"]
    }
}
