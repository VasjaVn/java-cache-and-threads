# java-cache-and-threads

Work with multithreading and synchronized in Java.

HOW USE THIS APP:
1.) Configure of config file: System.conf
2.) Run

PARAMETERS OF CONFIG FILE:
1.) Time testing Cache by System in minutes. 
system.timeTestInMinutes

2.) Time update Cache minutes:seconds:milliseconds.
system.cache.timeUpdate.minutes
system.cache.timeUpdate.seconds
system.cache.timeUpdate.milliseconds

3.) Type of element stored in Cache: INT(integer) / STR(string) / CHR(char).
system.cache.typeElement

4.) Count of threads using Cache.
system.thread.useCache.count

5.) Max time of sleep of thread (UseCache) between using Cache.
system.thread.useCache.maxTimeSleepBetweenUseCacheInMilliseconds

6.) Percent use ELEMENT of Cache. Value from 0 to 100. Percent use ALL ELEMENTS
    of Cache equals 100 minus percent use element of Cache.
    IF value equal 0 => thread (UseCache) not using element of Cache ( using all elements of Cache).
    IF value equal 100 => thread (UseCache) using only element of Cache.
system.thread.useCache.percentUseElementCache

7.) Time waiting response on update Cache in seconds ( Thread UpdateCache sleeps).
system.thread.updateCache.timeWaitResponseInSeconds

8.) Count generate elements for Cache.
system.thread.updateCache.countGenerateElements

9.) Use thread (ModifyCache) of modify Cache. Value = TRUE/FALSE.
system.thread.modifyCache.use

10.) Max time of sleep of thread between modifying Cache.
system.thread.modifyCache.maxTimeSleepBetweenModifyCacheInMilliseconds

11.) Percent ADD element to Cache. Value from 0 to 100. Percent REMOVE element
     from Cache equals 100 minus percent add element to Cache.
    IF value equal 0 => thread (ModifyCache) only remove element from Cache.
    IF value equal 100 => thread (ModifyCache) only add element to Cache.
system.thread.modifyCache.percentAddElementToCache
