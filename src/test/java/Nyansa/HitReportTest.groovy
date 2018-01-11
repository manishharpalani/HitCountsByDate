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
        assert dayHitCounts.getSortedMapByFreq().collect().size() == numEntries

        where:
        day | header           | numEntries
        0   | "08/08/2014 GMT" | 3
        1   | "08/09/2014 GMT" | 3
        2   | "08/10/2014 GMT" | 1
    }

    def "Add more lines and ensure report data is updated appropriately"() {
        setup:
        report.processLine("1292046498|www.sierra.com") // date: 12/11/2018 (should become first entry)
        report.processLine("1515649698|www.manish.com") // date: 01/11/2018 (should become last entry)
        Collection<HitBucketImpl> hitCounts = report.getHitCounts()

        expect:
        assert hitCounts.size() == 5
        HitBucketImpl dayHitCounts = hitCounts[day]
        assert dayHitCounts.getHeader() == header
        assert dayHitCounts.getSortedMapByFreq().collect().size() == numEntries

        where: "dates should be inserted in date order rather than alphabetic order"
        day | header           | numEntries
        0   | "12/11/2010 GMT" | 1
        1   | "08/08/2014 GMT" | 3
        2   | "08/09/2014 GMT" | 3
        3   | "08/10/2014 GMT" | 1
        4   | "01/11/2018 GMT" | 1
    }
}
