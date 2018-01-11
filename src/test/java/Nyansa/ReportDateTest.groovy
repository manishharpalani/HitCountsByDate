package Nyansa

import spock.lang.Specification

import java.util.concurrent.TimeUnit

class ReportDateTest extends Specification {
    def static epochDaySec = TimeUnit.DAYS.toSeconds(1)
    def static day0String = "01/01/1970 GMT"
    def static day1String = "01/02/1970 GMT"

    def "test asGmtStr/getStartOfEpochDay near midnight"() {
        expect:
        ReportDate.asGmtStr(epochSec) == dateString
        ReportDate.getStartOfEpochDay(epochSec) == startEpochDay

        where:
        epochSec        | dateString | startEpochDay
        0               | day0String | 0
        1               | day0String | 0
        epochDaySec - 1 | day0String | 0
        epochDaySec     | day1String | epochDaySec
    }

    def "test asGmtStr/getStartOfEpochDay for sample data"() {
        expect:
        ReportDate.asGmtStr(epochSec) == dateString
        ReportDate.getStartOfEpochDay(epochSec) == startEpochDay

        where:
        epochSec    | dateString        | startEpochDay
        1407564301  | "08/09/2014 GMT"  | 1407542400
        1407478021  | "08/08/2014 GMT"  | 1407456000
        1407478022  | "08/08/2014 GMT"  | 1407456000
        1407481200  | "08/08/2014 GMT"  | 1407456000
        1407478028  | "08/08/2014 GMT"  | 1407456000
        1407564301  | "08/09/2014 GMT"  | 1407542400
        1407564300  | "08/09/2014 GMT"  | 1407542400
        1407564300  | "08/09/2014 GMT"  | 1407542400
        1407564300  | "08/09/2014 GMT"  | 1407542400
        1407564301  | "08/09/2014 GMT"  | 1407542400
        1407478022  | "08/08/2014 GMT"  | 1407456000
        1407648022  | "08/10/2014 GMT"  | 1407628800
    }
}
