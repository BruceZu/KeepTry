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

from argparse import ArgumentParser
import os
import sys
import shutil
import __builtin__
import hashlib

import util
import npmjs_defs

map_of_sites = {'MAVEN': npmjs_defs.MAVEN, 'NPMJS': npmjs_defs.NPMJS}


def cached_file(sha1, o, u, cache_path):
    if sha1:
        h = sha1
    else:
        hash = hashlib.sha1()
        hash.update(u.encode(encoding='base64'))
        h = hash.hexdigest()
    f = '%s-%s' % (os.path.basename(o), h)
    return os.path.join(cache_path.rstrip('/'), f)


def map_to_web(alias):
    if alias in map_of_sites.keys():
        return map_of_sites[alias]
    return alias


def delete_wrong_file(file):
    try:
        os.remove(file)
    except __builtin__.OSError as e:
        if os.path.isfile(file):
            print('error remove %s : %s' % (file, e), file=sys.stderr)
            sys.exit(1)


def check_dir_of(file):
    try:
        d = os.path.dirname(file)
        if os.path.isfile(file):
            print("%s exists!\n" % d, file=sys.stderr)
            return
        util.make_sure_dir(d)
    except __builtin__.OSError as e:
        print('error create %s: %s %s %s' % (d, e.errno, e.strerror, e.filename), file=sys.stderr)
        sys.exit(1)


def hard_link(original, to):
    try:
        print("link %s to %s" % (original, to), file=sys.stderr)
        os.link(original, to)
    except __builtin__.OSError as e:
        print('error link %s to %s, try copy file' % (original, to), file=sys.stderr)
        try:
            shutil.copyfile(original, to)
        except (__builtin__.IOError, shutil.Error) as e:
            print("error copy: %s" % e, file=sys.stderr)
            sys.exit(1)


def run():
    parser = ArgumentParser(prog='download_file',
                            description='download and cache the jar, then create a hard link specified by \'-o\'.' +
                                        ' Background: with the jar to create genrule used for prebuilt_jar.'
                            )
    parser.add_argument('--repo',
                        nargs='?',
                        type=lambda alias: map_of_sites[alias] if alias in map_of_sites.keys() else alias,
                        default=npmjs_defs.MAVEN,
                        help='web site from where to download (default: %s). Can be one of the key of %s' % (
                            npmjs_defs.MAVEN, __builtin__.str(map_of_sites)),
                        metavar='web site'
                        )
    parser.add_argument('--cache-path',
                        nargs='?',
                        type=util.path_of,
                        default=util.path_of(npmjs_defs.CACHED_PATH),
                        help='path to cached jar (default: %s) ' % npmjs_defs.CACHED_PATH,
                        metavar='cached path'
                        )
    required = parser.add_argument_group('required input')
    required.add_argument('-u',
                          required=True,
                          help='part of URL work with, not including the content of \'-w\'',
                          metavar='tail of URL'
                          )
    required.add_argument('-o',
                          required=True,
                          help='the local hard link name',
                          metavar='file name'
                          )
    parser.add_argument('--sha1',
                        help='jar\'s SHA-1, with it to verify content integration',
                        metavar='SHA-1'
                        )
    args = parser.parse_args()

    cache_entry = cached_file(args.sha1, args.o, args.u, args.cache_path)

    url = npmjs_defs.get_url(args.repo, args.u)
    if not os.path.isfile(cache_entry):
        util.download(url, cache_entry, True)

    if args.sha1 and util.is_integrated(cache_entry, args.sha1) is False:
        print('error download %s' % url, file=sys.stderr)
        delete_wrong_file(cache_entry)
        sys.exit(1)
    cache_entry = npmjs_defs.make_sure_deps(args.u, cache_entry, args.repo)
    check_dir_of(args.o)
    hard_link(cache_entry, args.o)
    return 0


if __name__ == '__main__':
    sys.exit(run())
