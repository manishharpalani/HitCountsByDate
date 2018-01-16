# Steps to Configure/Run with sample input.txt

   1. Create Application Target in Edit Configuration...
   2. Set Main Class: _Nyansa.ReportBuilderImpl_
   3. Set Program Argument: resources/input.txt 

# Program Output
    08/08/2014 GMT
    www.facebook.com 2
    www.google.com 2
    news.ycombinator.com 1
    08/09/2014 GMT
    www.nba.com 3
    sports.yahoo.com 2
    www.cnn.com 1
    08/10/2014 GMT
    www.twitter.com 1

# Details on O(N) Complexity
HitReport creates a HitBucket indexed by getStartOfEpochDay (hour, min, sec of timestamp are discarded
HitBucket keeps the url in 2 maps

    1. url:frequency
    2. frequency;set of urls

Both maps are updated, as needed, for each hit

    N = number of lines

    Space Complexity: O(N)

    Time Complexity:
    loading date file takes O(N)

Generating report requires getting the HitBuckets in order from most recent to the oldest
For each bucket we get the urls in order from most frequent to least frequent (use frequency;set of urls)

Time Complexity: O(N)
