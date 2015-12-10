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

from argparse import ArgumentParser
from os import path, remove, link
from sys import stderr, exit
from subprocess import check_output, CalledProcessError
from shutil import copyfile, Error
from util import make_sure_dir, is_integrated, path_of

maven = 'http://repo1.maven.org/maven2/'
MINOR_HOME = '~/.minorandminor'
cached_path = path.join(MINOR_HOME, 'buck-cache', 'download-artifacts')
map_of_sites = {'MAVEN': maven}


def cached_file(args):
    if args.sha1:
        h = args.sha1
    else:
        h = sha1()
        h.update(args.u.encode(encoding='base64'))
        h = h.hexdigest()
    f = '%s-%s' % (path.basename(args.o), h)
    return '/'.join([args.cache_path.rstrip('/'), f])


def map_to_web(alias):
    if alias in map_of_sites.keys():
        return map_of_sites[alias]
    return alias


def download(url, to):
    try:
        check_output(['curl',
                      '--proxy-anyauth',
                      '--create-dirs',
                      '-f',
                      '--silent',
                      '--insecure',
                      '-o', to,
                      '--url', url
                      ])
    except CalledProcessError as e:
        print('curl is failed to download %s :\n%s,\n%s' % (url, e.cmd, e.output), file=stderr)
        exit(e.returncode)


def delete_wrong_file(file):
    try:
        remove(cache_entry)
    except OSError as e:
        if path.isfile(cache_entry):
            print('error remove %s : %s' % (cache_entry, e), file=stderr)
            exit(1)


def check_dir_of(file):
    try:
        d = path.dirname(file)
        if path.isfile(file):
            print("%s exists!\n" % d, file=stderr)
            return
        make_sure_dir(d)
    except OSError as e:
        print('error create %s: %s %s %s' % (d, e.errno, e.strerror, e.filename), file=stderr)
        exit(1)


def hard_link(original, to):
    try:
        link(original, to)
    except OSError as e:
        print('error link %s to %s, try copy file' % (cache_entry, args.o), file=stderr)
        try:
            copyfile(cache_entry, args.o)
        except (IOError, Error) as e:
            print("error copy: %s" % e, file=stderr)
            exit(1)


parser = ArgumentParser(prog='get jar',
                        description='download and cache the jar, then create a hard link specified by \'-o\'.' +
                                    ' Background: with the jar to create genrule used for prebuilt_jar.'
                        )
parser.add_argument('--repo',
                    nargs='?',
                    type=map_to_web,
                    default=maven,
                    help='web site from where to download (default: %s). Can be one of the key of %s' % (
                        maven, str(map_of_sites)),
                    metavar='web site'
                    )
parser.add_argument('--cache-path',
                    nargs='?',
                    type=path_of,
                    default=path_of(cached_path),
                    help='path to cached jar (default: %s) ' % cached_path,
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

cache_entry = cached_file(args)
url = '/'.join([args.repo.rstrip('/'), args.u.lstrip('/')])

if not path.isfile(cache_entry):
    download(url, cache_entry)

if args.sha1 and is_integrated(cache_entry, args.sha1) is False:
    print('error download %s' % url, file=stderr)
    delete_wrong_file(cache_entry)
    exit(1)

check_dir_of(args.o)
hard_link(cache_entry, args.o)
