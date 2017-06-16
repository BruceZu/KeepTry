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

with allow_unsafe_import():
    import sys
    from os.path import abspath, dirname
    sys.path.append(dirname(dirname(abspath(__file__))))
    from tools.npmjs_defs import CACHED_NPMJS_PATH, npmjs_file_name, npmjs_url

# The npmjs is with nested deps as the result created by `npm install --production` (npm@2.9.1)
def cook_genrule_npmjs(
        name,
        version,
        sha1,
        repo='NPMJS'):
    cmd = ['$(exe //tools:download_file)',
           '-o', '$OUT',
           '--cache-path', CACHED_NPMJS_PATH,
           '--repo', repo,
           '-u', npmjs_url(name, version)
           ]
    if sha1:
        cmd.extend(['--sha1', sha1])

    genrule(
        name=name,
        cmd=' '.join(cmd),
        out=npmjs_file_name(name, version),
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
