//  Copyright 2016 The Sawdust Open Source Project
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

package array;

/**
 * Describe an efficient algorithm for finding the ten largest elements in an array of size n. What
 * is the running time of your algorithm?
 *
 * <p>排序法 最差时间分析 平均时间复杂度 稳定度 空间复杂度 Toplogical Sort O(n) 堆排序 O(n*log2n) O(n*log2n) 不稳定 O(1) 快速排序
 * O(n2) O(n*log2n) 不稳定 O(log2n)~O(n) 二叉树排序 O(n2) O(n*log2n) 不一顶 O(n) 希尔排序 O O 不稳定 O(1)
 *
 * <p>选择排序 O(n2) O(n2) 稳定 O(1) RadixSort is not comparison based. its time complexity in the
 * comparison model is 0. The statement that RadixSort is O(n) implicitly references a model in
 * which the keys can be lexicographically picked apart in constant time.
 *
 * <p>MergeSort makes O(nlogn)
 *
 * <p>randomized QuickSortOnePivot2way is O(nlogn) QuickSortOnePivot2way is O(n2) they are probably
 * talking about its worst case complexity.
 */
//  C436
public class TheTenLargestElements {
  // To-do
}
