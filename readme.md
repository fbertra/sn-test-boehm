Test Boehm GC
=============

Boehm GC is a conservative garbage collector. This means it doesn't guarantee 
collect all non reachable memory.

This app is for understanding why this behavior seems to be random. Different runs 
of this program behaves differently: sometimes the memory is released and sometimes 
not.

This random behaviour is a serious problem when implementing test suite.


```
francois@ubuntu:~/proyectos/oss/scala-native-fbd/sn-test-boehm$ ./target/scala-2.11/sn-test-boehm-out 
Test Boehm
testBoehm (): before useMem
heap size: 65536
free bytes: 57344

useMem (): before gc
ptr address hex: 0xef5000, int: 15683584
heap size: 10199040
free bytes: 188416

useMem (): after gc
heap size: 10199040
free bytes: 188416

testBoehm (): after useMem and after gc
heap size: 10199040
free bytes: 10190848


Boehm rocks!!
francois@ubuntu:~/proyectos/oss/scala-native-fbd/sn-test-boehm$ ./target/scala-2.11/sn-test-boehm-out 
Test Boehm
testBoehm (): before useMem
heap size: 65536
free bytes: 57344

useMem (): before gc
ptr address hex: 0x10ef000, int: 17756160
heap size: 10199040
free bytes: 188416

useMem (): after gc
heap size: 10199040
free bytes: 188416

testBoehm (): after useMem and after gc
heap size: 10199040
free bytes: 188416


Come on Boehm, you can do better next time....
```
