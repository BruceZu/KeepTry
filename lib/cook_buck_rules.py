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

include_defs('//tools/defs.py')


def cook_genrule_npmjs(
        name,
        version,
        sha1,
        repo='NPMJS'):
    file_name = '{0}-{1}'.format(name, version)
    file = '.'.join([file_name, 'tgz'])
    u = '/'.join([name, '-', file])
    from os import path
    cached_npmjs_path = path.join(MINOR_HOME, 'buck-cache', 'download-artifacts-npmjs')

    cmd = ['$(exe //tools:download_file)',
           '-o', '$OUT',
           '--cache-path', cached_npmjs_path,
           '--repo', repo,
           '-u', u
           ]
    if sha1:
        cmd.extend(['--sha1', sha1])

    genrule(
        name=name,
        cmd=' '.join(cmd),
        out=file,
    )


def cook_prebuilt_jar(
        name,
        group_id,
        artifact_id,
        version,
        bin_sha1,
        src_sha1,
        deps=[],
        visibility=['PUBLIC'],
        repo='MAVEN'):
    u = '/'.join([
        group_id.replace('.', '/'),
        artifact_id,
        version,
        artifact_id + '-' + version
    ])

    bin_url = u + '.jar'
    src_url = u + '-sources.jar'

    out = '-'.join([
        name,
        artifact_id,
        version
    ])

    bin_jar = out + '.jar'
    src_jar = out + '-src.jar'

    cmd = ['$(exe //tools:download_file)', '-o', '$OUT']

    if repo != 'MAVEN':
        cmd.extend(['--repo', repo])

    bin_cmd = cmd[:]
    bin_cmd.extend(['-u', bin_url])
    if bin_sha1:
        bin_cmd.extend(['--sha1', bin_sha1])

    src_cmd = cmd[:]
    src_cmd.extend(['-u', src_url])
    if src_sha1:
        src_cmd.extend(['--sha1', src_sha1])

    genrule(
        name='%s__download_bin' % name,
        cmd=' '.join(bin_cmd),
        out=bin_jar,
    )

    genrule(
        name='%s__download_src' % name,
        cmd=' '.join(src_cmd),
        out=src_jar
    )
    prebuilt_jar(
        name=name,
        binary_jar=':%s__download_bin' % name,
        source_jar=':%s__download_src' % name,
        deps=deps,
        visibility=visibility
    )
