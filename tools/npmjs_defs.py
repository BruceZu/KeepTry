#!/usr/bin/env python
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

import os
import unittest
import contextlib
import urllib
import json
import tarfile
import sys
import shutil
import __builtin__
import subprocess
import atexit

import util

NPMJS = 'https://registry.npmjs.org/'

MAVEN = 'http://repo1.maven.org/maven2/'
MINOR_HOME = os.path.expanduser('~/.minorminor')
CACHED_PATH = os.path.join(MINOR_HOME, 'buck-cache', 'download-artifacts-jar')
CACHED_NPMJS_PATH = os.path.join(MINOR_HOME, 'buck-cache', 'download-artifacts-npmjs')

SEMVER_HOME = os.path.join(CACHED_NPMJS_PATH, 'semver')
package_json = 'package/package.json'
node_modules = 'package/node_modules'


def open_to(tgz, to):
    with tarfile.open(tgz, 'r:gz') as tar:
        tar.extractall(to)


def open_to_and_clean(tgz, to):
    open_to(tgz, to)
    os.remove(tgz)


def get_latest_version(name):
    return get_npmjs_time_of(name)[-1]


def get_url(repo, u):
    return '/'.join([repo.rstrip('/'), u.lstrip('/')])


def get_shasum_of(name, version):
    url = '/'.join([NPMJS.rstrip('/'), name.lstrip('/')])
    try:
        with contextlib.closing(urllib.urlopen(url)) as f:
            data = json.loads(f.read())
        return data['versions'][version]['dist']['shasum']
    except __builtin__.ValueError as e:
        print('\n open and parse %s.\n error %s\n' % url, e, file=sys.stderr)
        sys.exit(1)


def get_npmjs_time_of(name):
    url = '/'.join([NPMJS.rstrip('/'), name.lstrip('/')])
    try:
        with contextlib.closing(urllib.urlopen(url)) as f:
            data = json.loads(f.read())
        # print(json.dumps(data, indent=3, sort_keys=True))
        return __builtin__.sorted(data['time'].keys())[:-2]
    except __builtin__.ValueError as e:
        print('\n open and parse %s.\n error %s\n' % url, e, file=sys.stderr)
        sys.exit(1)


def pack_npmjs(home, to):
    pre = os.getcwd()
    os.chdir(os.path.expanduser(home))
    with tarfile.open(to, mode='w|gz') as t:
        t.add('./package')
    os.chdir(pre)


def npmjs_file_name(name, version):
    file_name = '{0}-{1}'.format(name, version)
    return '.'.join([file_name, 'tgz'])


def npmjs_url(name, version):
    file = npmjs_file_name(name, version)
    re = '/'.join([name, '-', file])
    return re


def de_npmjs_url(url):
    file = url.split('/')[-1]
    return file.split('.tgz')[0]


def download_npmjs(name, version, repo, to_path):
    url = get_url(repo, npmjs_url(name, version))
    to = os.path.join(to_path, npmjs_file_name(name, version))
    util.download(url, to, False)
    return to


def get_latest_npmjs_bin(name, repo, to_path):
    latest = get_latest_version(name)
    shasum = get_shasum_of(name, latest)
    open = os.path.join(to_path, '{0}-{1}-{2}'.format(name, latest, shasum))

    re = os.path.join(open, 'package', 'bin', name)
    if os.path.isfile(re):
        return re

    to = download_npmjs(name, latest, repo, to_path)
    if util.is_integrated(to, shasum):
        open_to_and_clean(to, open)
        return re
    sys.exit(1)


def get_semver():
    return get_latest_npmjs_bin('semver', NPMJS, SEMVER_HOME)


def satisfied(range, version):
    semver = get_semver()
    try:
        subprocess.check_output([semver, '-r', range, version])
        return True
    except subprocess.CalledProcessError as e:
        return False


def satisfied_latest(range, versions):
    versions = __builtin__.sorted(versions)
    versions.reverse()
    for v in versions:
        if (satisfied(range, v)):
            return v
    return None


def make_sure_node_module_dir(root):
    node_modles = 'node_modules'
    if os.path.isdir(os.path.join(root, 'package')):
        node_modles = os.path.join(root, 'package', node_modles)
    else:
        node_modles = os.path.join(root, node_modles)
    util.make_sure_dir(node_modles)
    return node_modles


def parse_npmjs_latest_version(name, range):
    versions = get_npmjs_time_of(name)
    return satisfied_latest(range, versions)


def download_dep(name, to_path, repo, version):
    u = get_url(repo, npmjs_url(name, version))
    to = os.path.join(to_path, npmjs_file_name(name, version))
    util.download(u, to, False)
    return to


def parse_deps_down_level(deps, deps_dict, level):
    re = []
    for name, range in deps.iteritems():
        version = parse_npmjs_latest_version(name, range)
        key = "{0}-{1}".format(name, version)
        if key not in deps_dict:
            deps_dict[key] = level
            re.append(key)
    return re


def bundled_entry(deps, pwd, repo, deps_dict, level):
    re = parse_deps_down_level(deps, deps_dict, level)
    re = __builtin__.sorted(re)
    for n_v in re:
        ind = n_v.rindex('-')
        name, version = n_v[:ind], n_v[ind + 1:]
        nm_path = make_sure_node_module_dir(pwd)
        tgz = download_dep(name, nm_path, repo, version)
        print('%s%s %s@%s' % ('|', '___' * level, name, version), file=sys.stderr)
        open_to_and_clean(tgz, nm_path)

        src = os.path.join(nm_path, 'package')
        dest = os.path.join(nm_path, name)
        shutil.move(src, dest)

        deps = parse_deps_file(os.path.join(dest, 'package.json'))
        if not os.path.exists(os.path.join(dest, 'node_modules')) and deps:
            bundled_entry(deps, dest, repo, deps_dict, level + 1)
    for n_v in re:
        deps_dict.pop(n_v)


def parse_deps_file(f):
    with __builtin__.open(f, 'r') as f:
        data = json.loads(f.read())
        if 'dependencies' in data.keys():
            re = data['dependencies']
            if __builtin__.len(re) != 0:
                return re

    return None


def parse_deps(tgz):
    try:
        with tarfile.open(tgz, mode='r:gz') as tar:
            f = tar.extractfile(package_json)
            data = json.loads(f.read())
            return data['dependencies']
    except (tarfile.ReadError, tarfile.CompressionError, __builtin__.ValueError, tarfile.ExtractError,
            tarfile.StreamError) as e:
        print('error %s : %s' % (tgz, e), file=sys.stderr)
        sys.exit(1)


def find_node_modules(tgz):
    try:
        if tarfile.is_tarfile(tgz):
            with tarfile.open(tgz, mode='r:gz') as tar:
                ns = tar.getnames()
                for n in ns:
                    if n.startswith(node_modules):
                        return True
                return False;
        else:
            print('error. %s is not tarfile' % tgz, file=sys.stderr)
            sys.exit(1)
    except (tarfile.ReadError, tarfile.CompressionError, __builtin__.ValueError, __builtin__.IOError) as e:
        print('error %s : %s' % (tgz, e), file=sys.stderr)
        sys.exit(1)


def deps_set(depsdic, depsset):
    return depsset.union(depsdic.keys())


def init_deps_dict(url):
    f = de_npmjs_url(url)
    return {f: 0}


def get_name_from_npmjs_url(url):
    f = de_npmjs_url(url)
    ind = f.rindex('-')
    return f[:ind]


def make_sure_deps(url, cache_entry, repo):
    if repo == NPMJS and not find_node_modules(cache_entry):
        deps = parse_deps(cache_entry)
        opened = "%s-%s" % (cache_entry, "temp")
        if deps:
            open_to(cache_entry, opened)
            atexit.register(lambda: shutil.rmtree(opened))
            bundled_entry(deps, opened, repo, init_deps_dict(url), 1)

        with_deps = "%s-%s" % (cache_entry, "with_deps")
        add_bundledDependency(os.path.join(opened, package_json))
        pack_npmjs(opened, with_deps)
        return with_deps
    return cache_entry


def filter_npmjs_node_modules_path_of(till, dirpath):
    re = ''
    while dirpath != till and __builtin__.len(dirpath) > 0:
        if os.path.basename(dirpath) != 'node_modules':
            re = os.path.join(os.path.basename(dirpath), re)
        dirpath = os.path.dirname(dirpath)
    return re


def get_npmjs_version(package):
    f = os.path.join(package, 'package.json')
    if os.path.isfile(f):
        with __builtin__.open(f) as f:
            data = json.load(f)
            return data['version']


def show_npmjs_deps_of(package):
    '''with it to test the nested deps with that created by `npm install --prodution` (npm@2.9.1) '''
    till = os.path.join(package, 'node_modules')
    for dirpath, dirnames, filenames in os.walk(package):
        if os.path.basename(dirpath) == 'node_modules':
            for dir in dirnames:
                if (dir != '.bin'):
                    print('{0}{1}@{2}'.format(filter_npmjs_node_modules_path_of(till, dirpath),
                                              dir,
                                              get_npmjs_version(os.path.join(dirpath, dir))))


def add_bundledDependency(file):
    with __builtin__.open(file, 'r') as f:
        data = json.loads(f.read())
        data['bundledDependencies'] = data['dependencies'].keys()
        with __builtin__.open(file, 'w') as f:
            json.dump(data, f, indent=3)


class Test_NPMJS_functions(unittest.TestCase):
    def npmjs_url_test(self):
        self.assertEqual(npmjs_url('semver', '5.0.1'), 'semver/-/semver-5.0.1.tgz')

    def semver_test(self):
        range = '1.2.7 || >=1.2.9 <2.0.0'
        versions = ['1.2.9', '1.9.1-alpha.3', '1.9.1', '2.0.1']
        satisfied = '1.9.1'  # not support prelease tag
        self.assertEqual(satisfied_latest(range, versions), satisfied)
        self.assertEqual(satisfied_latest('~0.2.2', get_npmjs_time_of('stream-connect')), '0.2.4')


if __name__ == '__main__':
    unittest.main()
# suite = unittest.TestLoader().loadTestsFromTestCase(TestConcreteNPMJSFileVersion)
# unittest.TextTestRunner(verbosity=2).run(suite)
