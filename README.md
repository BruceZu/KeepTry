# Sawdust
Algorithms + Data Structures.  DIY.

Environment:

```
$ java -version
java version "1.8.0_101"
Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
$ buck --version
buck version e64a2e2ada022f81e42be750b774024469551398
$ python --version
Python 2.7.11
```

Have libs available and run all Junit tests
```
$ buck targets | xargs buck build && buck test --all
```
Leetcode
```
$ find . -name LC*.java -o -name  Leetcode*[a-zA-Z1].java | grep -v Test.java |wc -l
```
Welcome ideas, comments and commits. No contribution is too small.

WebChat  : brucezu

Facebook : https://www.facebook.com/bruce.zu
