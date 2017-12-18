//  Copyright 2017 The keepTry Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package Bloom_filter;

/**
 * http://blog.csdn.net/hguisu/article/details/7866173
 * https://www.youtube.com/watch?v=bEmBh1HtYrw
 */
public class BloomFilter {
    // Bloom Filter判断元素不再集合，那肯定不在
    // hash collision. -> more hash
    // hash函数个数k=(ln2)*(m/n)时错误率最小. why?
    // 在错误率不大于E的情况 下，m至少要等于n*lg(1/E) 才能表示任意n个元素的集合。
    // 但m还应该更大些，因为还要保证bit数组里至少一半为0，则m应 该>=
    // nlg(1/E)*lge ，大概就是nlg(1/E)1.44倍(lg表示以2为底的对数)。
    // 举个例子我们假设错误率为0.01，则此时m应大概是n的13倍。这样k大概是8个。
}
