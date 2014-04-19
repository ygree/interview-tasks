/*
 * (c) 2014, Gribkov Yury
 * http://gribkov.me/
 */

package me.gribkov.interviewtasks.merge

import org.scalatest._

class MergeUtilsSpec extends FlatSpec with Matchers {

  import MergeUtils._

  "MergeUtils.merge" should "merge Lists using recurtion" in {
    merge(List(1,5), List(2,3,4)) should be (List(1,2,3,4,5))
  }

  "MergeUtils.mergeTailRec" should "merge Lists using tail recurtion" in {
    mergeTailRec(List(1,5), List(2,3,4)) should be (List(1,2,3,4,5))
  }

  "MergeUtils.lazyMerge" should "merge Iterators" in {
    lazyMerge(List(1,5).toIterator, List(2,3,4).toIterator).toList should be (List(1,2,3,4,5))
  }

  "MergeUtils.imperativeMerge" should "merge Arrays" in {
    imperativeMerge(Array(1,5), Array(2,3,4)) should be (Array(1,2,3,4,5))
  }
}