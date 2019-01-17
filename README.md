# KeepTry
Algorithms + Data Structures.  DIY.

Environment:

Ubuntu 32 bit 16.04.1 LTS (Xenial Xerus)
git version 2.7.4
Java

```
$ java  -version
java version "1.8.0_111"
Java(TM) SE Runtime Environment (build 1.8.0_111-b14)
Java HotSpot(TM) Server VM (build 25.111-b14, mixed mode)
```

Buck

```
$ buck --version
buck version bf16295cfcbb55592fa90a474b89beb2997c3aa2
```

Node.js
```
$ node --version
v7.4.0
```

Python

```
$ python --version
Python 2.7.11
```

Get all required Java libs and run all Junit tests

```
$ {buck targets //bow/...; buck targets //arrows/...;} | xargs buck build && buck test --all
```
Import it by Intellij IDEA 2016.3 and start enjoy coding

Leetcode
```
$ find . -name LC*.java -o -name  Leetcode*[a-zA-Z1].java | grep -v Test.java |wc -l
```
Welcome ideas, comments and commits.

WebChat  : brucezu

Facebook : https://www.facebook.com/bruce.zu
