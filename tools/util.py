# Copyright (C) 2015 The Minorminor Open Source Project
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

import sys
import os
import hashlib
import urllib
import contextlib
import subprocess
import __builtin__


def make_sure_dir(d):
    if os.path.isdir(d):
        return
    try:
        os.makedirs(d)
    except __builtin__.OSError as e:
        if not os.path.isdir(d):
            raise e


def hash_of(file):
    h = hashlib.sha1()
    with __builtin__.open(file, 'rb') as f:
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
        print('\n received SHA-1: %s \n expected SHA-1: %s' % (h, sha1), file=sys.stderr)
        return False
    return True


def path_of(userpath):
    if userpath.startswith('~/'):
        return os.path.expanduser(userpath)
    return userpath


def sha1_of_file(filepath):
    h = hashlib.sha1()
    with __builtin__.open(filepath, 'rb') as f:
        while True:
            buf = f.read(65536)
            if not buf:
                break;
            h.update(buf)
    return h.hexdigest()


def hash_of_url(url):
    h = hashlib.sha1()
    with contextlib.closing(urllib.urlopen(url)) as f:  # may be binary_file
        while True:
            data = f.read(4096)
            if not data:
                break
            h.update(data)
    return h.hexdigest()


def download(url, to):
    try:
        print("\ndownload %s\n to %s\n" % (url, to), file=sys.stderr)
        subprocess.check_output(['curl',
                                 '--proxy-anyauth',
                                 '--create-dirs',
                                 '-f',
                                 '--silent',
                                 '--insecure',
                                 '-o', to,
                                 '--url', url
                                 ])
    except  subprocess.CalledProcessError as e:
        print('\ncurl is failed to download %s :\n%s,\n%s' % (url, e.cmd, e.output), file=sys.stderr)
        sys.exit(e.returncode)
