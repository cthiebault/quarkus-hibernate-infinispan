# Quarkus Hibernate cache with Infinispan

Sandbox repo to reproduce the [Hibernate cache is using Caffeine instead of Infinispan](https://github.com/quarkusio/quarkus/issues/42541) issue.

Looking at the test logs, it seems that Hibernate is still using the local cache Caffeine instead of Infinispan.

```text
2024-08-14 11:58:06,814 TRACE [org.inf.qua.hib.cac.CaffeineCache] (executor-thread-1) Cache get(key=org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1) returns: null
2024-08-14 11:58:06,816 TRACE [org.inf.qua.hib.cac.PutFromLoadValidator] (executor-thread-1) registerPendingPut(org.acme.Fruit#org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1, 1723629486802) registered using putIfAbsent: { PendingPuts=[R@Session#1618307921], Invalidators=[], LastInvalidationEnd=<none>, Removed=false}
2024-08-14 11:58:06,865 TRACE [org.inf.qua.hib.cac.StrictDataAccess] (executor-thread-1) Registering synchronization on transaction in java.lang.Object@5de2ad46, cache org.acme.Fruit: org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1
2024-08-14 11:58:06,868 TRACE [org.inf.qua.hib.cac.PutFromLoadValidator] (executor-thread-1) beginInvalidatingKey(org.acme.Fruit#org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1, java.lang.Object@5de2ad46) remove invalidator from { PendingPuts=[R@Session#1618307921], Invalidators=[], LastInvalidationEnd=<none>, Removed=false}
2024-08-14 11:58:06,869 TRACE [org.inf.qua.hib.cac.PutFromLoadValidator] (executor-thread-1) beginInvalidatingKey(org.acme.Fruit#org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1, java.lang.Object@5de2ad46) ends with { PendingPuts=[C@Session#1618307921, R@java.lang.Object@5de2ad46], Invalidators=[{Owner=java.lang.Object@5de2ad46, Timestamp=1723629486868}], LastInvalidationEnd=<none>, Removed=false}
2024-08-14 11:58:06,869 TRACE [org.inf.qua.hib.cac.CaffeineCache] (executor-thread-1) Cache invalidate key org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1
2024-08-14 11:58:06,871 DEBUG [org.hib.cac.int.TimestampsCacheEnabledImpl] (executor-thread-1) Pre-invalidating space [Fruit], timestamp: 1723629546871
2024-08-14 11:58:06,871 TRACE [org.inf.qua.hib.cac.CaffeineCache] (executor-thread-1) Cache put key=Fruit value=1723629546871
2024-08-14 11:58:06,873 TRACE [org.inf.qua.hib.cac.Sync] (executor-thread-1) 0 tasks done, 0 tasks not done yet
2024-08-14 11:58:06,874 TRACE [org.inf.qua.hib.cac.Sync] (executor-thread-1) Finished 0 tasks before completion
2024-08-14 11:58:06,876 TRACE [org.inf.qua.hib.cac.StrictDataAccess$LocalInvalidationSynchronization] (executor-thread-1) After completion callback with status 3
2024-08-14 11:58:06,876 TRACE [org.inf.qua.hib.cac.PutFromLoadValidator] (executor-thread-1) Put for external read value, if needed (doPFER=true): key=org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1, valueForPFER=CacheEntry(org.acme.Fruit), owner=java.lang.Object@5de2ad46
2024-08-14 11:58:06,877 TRACE [org.inf.qua.hib.cac.CaffeineCache] (executor-thread-1) Cache put if absent key=org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1 value=CacheEntry(org.acme.Fruit)
2024-08-14 11:58:06,878 TRACE [org.inf.qua.hib.cac.PutFromLoadValidator] (executor-thread-1) endInvalidatingKey(org.acme.Fruit#org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1, java.lang.Object@5de2ad46) ends with { PendingPuts=[C@Session#1618307921], Invalidators=[], LastInvalidationEnd=1723629486876, Removed=false}
2024-08-14 11:58:06,879 DEBUG [org.hib.cac.int.TimestampsCacheEnabledImpl] (executor-thread-1) Invalidating space [Fruit], timestamp: 1723629486879
2024-08-14 11:58:06,880 TRACE [org.inf.qua.hib.cac.CaffeineCache] (executor-thread-1) Cache put key=Fruit value=1723629486879
2024-08-14 11:58:06,881 TRACE [org.inf.qua.hib.cac.Sync] (executor-thread-1) Invoked 0 tasks after completion, 0 are synchronous.
2024-08-14 11:58:06,938 TRACE [org.inf.qua.hib.cac.CaffeineCache] (executor-thread-1) Cache get(key=org.acme.Fruit#e1a3798c-e764-437a-b6b2-3a35a4e3b8a1) returns: CacheEntry(org.acme.Fruit)

```