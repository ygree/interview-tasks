/*
 * (c) 2014, Gribkov Yury
 * http://gribkov.me/
 */

package me.gribkov.interviewtasks.merge

import org.scalatest._

class MergeUtilsSpec extends FlatSpec with Matchers {

  trait MergeSpec[S[_]] {
    type T = Int
    def toList(s: S[T]): List[T]
    def fromList(l: List[T]): S[T]
    def merge(s1: S[T], s2: S[T]): S[T]

    def mergeLists(l1: List[T], l2: List[T]): List[T] =
      toList(merge(fromList(l1), fromList(l2)))

    it should "merge two empty sequences" in {
      mergeLists(List(), List()) should be (List())
    }

    it should "merge empty sequence with non empty sequence" in {
      mergeLists(List(), List(1,2)) should be (List(1,2))
    }

    it should "merge non empty sequence with empty sequence" in {
      mergeLists(List(1,2), List()) should be (List(1,2))
    }

    it should "merge two non empty sequences" in {
      mergeLists(List(3,5), List(1,2,4)) should be (List(1,2,3,4,5))
    }

    it should "merge two non empty sequences with duplicates" in {
      mergeLists(List(1,2,5), List(1,3,4)) should be (List(1,1,2,3,4,5))
    }
  }

  "MergeUtils.merge" should behave like new MergeSpec[List] {
    def merge(s1: List[T], s2: List[T]) = MergeUtils.merge[T](s1, s2)
    def toList(s: List[T]) = s
    def fromList(l: List[T]) = l
  }

  "MergeUtils.mergeTailRec" should behave like new MergeSpec[List] {
    def merge(s1: List[T], s2: List[T]) = MergeUtils.mergeTailRec[T](s1, s2)
    def toList(s: List[T]) = s
    def fromList(l: List[T]) = l
  }

  "MergeUtils.lazyMerge" should behave like new MergeSpec[Iterator] {
    def merge(s1: Iterator[T], s2: Iterator[T]) = MergeUtils.lazyMerge[T](s1, s2)
    def toList(s: Iterator[T]) = s.toList
    def fromList(l: List[T]) = l.toIterator
  }

  "MergeUtils.imperativeMerge" should behave like new MergeSpec[Array] {
    def merge(s1: Array[T], s2: Array[T]) = MergeUtils.imperativeMerge[T](s1, s2)
    def toList(s: Array[T]) = s.toList
    def fromList(l: List[T]) = l.toArray
  }
}