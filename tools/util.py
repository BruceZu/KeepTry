# Copyright (C) 2015 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from __future__ import print_function

from sys import stderr
from os import path, makedirs
from hashlib import sha1


def make_sure_dir(d):
    if path.isdir(d):
        return
    try:
        makedirs(d)
    except  OSError as e:
        if not path.ifdir(d):
            raise e


def hash_of(file):
    h = sha1()
    with open(file, 'rb') as f:
        while True:
            buff = f.read(65536)
            if not buff:
                break
            h.update(buff)
    return h


def sha1_of(file):
    return hash_of(file).hexdigest()


def is_integrated(file, sha1):
    h = sha1_of(file)
    if sha1 != h:
        print('\n received SHA-1: %s' +
              '\n expected SHA-1: %s' % (h, sha1), file=stderr)
        return False
    return True


def path_of(userpath):
    if userpath.startswith('~/'):
        return path.expanduser(userpath)
    return userpath
