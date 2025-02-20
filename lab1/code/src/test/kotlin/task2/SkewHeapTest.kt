package task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SkewHeapTest {

 @Test
 @DisplayName("Testing merge() with not-null heaps")
 fun `Function merge() should correctly merge 2 not-null SkewHeaps`() {
  val heap1 = SkewHeap<Int>()
  val heap2 = SkewHeap<Int>()

  heap1.insertList(listOf(3, 10, 8))
  heap2.insertList(listOf(7, 9, 12))

  heap1.mergeWith(heap2)

  val expectedStructure = "(3, L=(7, L=(9, L=(10, L=null, R=null), R=null), R=(12, L=null, R=null)), R=(8, L=null, R=null))"
  assertEquals(expectedStructure, heap1.toString())
 }

 @Test
 @DisplayName("Testing merge() with null heap")
 fun `Function merge() with empty heap1 and non-empty heap2 should return heap2`() {
  val heap1 = SkewHeap<Int>()
  val heap2 = SkewHeap<Int>()

  heap1.insertList(listOf(4, 9, 2))
  heap1.mergeWith(heap2)

  val expectedStructure = "(2, L=(4, L=(9, L=null, R=null), R=null), R=null)"
  assertEquals(expectedStructure, heap1.toString())
 }

 @Test
 @DisplayName("Testing insert() with random numbers")
 fun `Function insert() should form correct structure of SkewHeap`() {
  val heap = SkewHeap<Int>()
  heap.insert(10)
  heap.insert(5)
  heap.insert(15)
  heap.insert(3)

  val expectedStructure = "(3, L=(5, L=(15, L=null, R=null), R=(10, L=null, R=null)), R=null)"
  assertEquals(expectedStructure, heap.toString())
 }

 @Test
 @DisplayName("Testing insertList() with list of numbers")
 fun `Function insertList() should form correct structure of SkewHeap`() {
  val heap = SkewHeap<Int>()
  heap.insertList(listOf(8, 3, 10, 1, 6))

  val expectedStructure = "(1, L=(6, L=null, R=null), R=(3, L=(10, L=null, R=null), R=(8, L=null, R=null)))"
  assertEquals(expectedStructure, heap.toString())
 }

 @Test
 @DisplayName("Testing extractMin() to return the minimum element and remove it from the heap")
 fun `Function extractMin() should return smallest value and remove it from SkewHeap`() {
  val heap = SkewHeap<Int>()
  heap.insertList(listOf(5, 12, 10, 3, 8))

  assertEquals(3, heap.extractMin())
  val expectedAfterExtract = "(5, L=(8, L=(12, L=null, R=null), R=null), R=(10, L=null, R=null))"
  assertEquals(expectedAfterExtract, heap.toString())
 }

 @Test
 @DisplayName("Testing extractMin() with empty heap")
 fun `Function extractMin() with empty SkewHeap should return null`() {
  val heap = SkewHeap<Int>()
  assertNull(heap.extractMin())
 }
}