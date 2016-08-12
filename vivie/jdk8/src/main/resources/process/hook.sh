#!/usr/bin/env bash
echo "[$@]"
echo "[$*]"
echo "this hook name is $0"
echo "the first parameter is [$1] "
echo "user is ${user}"
echo "do some thing base on parameters and env"
echo "done"
echo "current work directory is \"`pwd`\""