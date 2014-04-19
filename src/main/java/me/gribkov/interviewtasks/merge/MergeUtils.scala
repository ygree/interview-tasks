/*
 * (c) 2014, Gribkov Yury
 * http://gribkov.me/
 */

package me.gribkov.interviewtasks.merge

import scala.annotation.tailrec
import scala.reflect.ClassTag

object MergeUtils {

  def merge[T: Ordering](in1: List[T], in2: List[T]): List[T] = {
    val ordering = implicitly[Ordering[T]]
    import ordering._

    (in1, in2) match {
      case (h1 :: t1, h2 :: t2) =>
        if (h1 < h2) h1 :: merge(t1, in2)
        else h2 :: merge(in1, t2)

      case (Nil, nel) => nel
      case (nel, Nil) => nel
    }
  }

  def mergeTailRec[T: Ordering](in1: List[T], in2: List[T]): List[T] = {
    val ordering = implicitly[Ordering[T]]
    import ordering._

    @tailrec
    def mergeImpl(in1: List[T], in2: List[T], result: List[T]): List[T] =
      (in1, in2) match {
        case (h1 :: t1, h2 :: t2) =>
          if (h1 < h2) mergeImpl(t1, in2, h1 :: result)
          else mergeImpl(in1, t2, h2 :: result)

        case (Nil, nel) => nel.reverse ::: result
        case (nel, Nil) => nel.reverse ::: result
      }

    mergeImpl(in1, in2, Nil).reverse
  }

  def lazyMerge[T: Ordering](in1: Iterator[T], in2: Iterator[T]): Iterator[T] = {
    val ordering = implicitly[Ordering[T]]
    import ordering._

    (in1.hasNext, in2.hasNext) match {
      case (false, false) => Iterator.empty
      case (true, false)  => in1
      case (false, true)  => in2
      case (true, true)   =>
        val n1 = in1.next()
        val n2 = in2.next()
        if (n1 < n2) Iterator(n1) ++ lazyMerge(in1, Iterator(n2) ++ in2)
        else Iterator(n2) ++ lazyMerge(Iterator(n1) ++ in1, in2)
    }
  }

  def imperativeMerge[T: Ordering: ClassTag](in1: Array[T], in2: Array[T]): Array[T] = {
    val ordering = implicitly[Ordering[T]]
    import ordering._

    val result = Array.ofDim[T](in1.length + in2.length)
    var i1, i2, j = 0
    while (j < result.length) {
      if (i1 < in1.length && (i2 >= in2.length || in1(i1) < in2(i2))) {
        result(j) = in1(i1)
        i1 += 1
      }
      else {
        result(j) = in2(i2)
        i2 += 1
      }
      j += 1
    }
    result
  }
}