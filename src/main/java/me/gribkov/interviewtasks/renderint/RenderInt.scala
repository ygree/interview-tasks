/*
 * (c) 2014, Gribkov Yury
 * http://gribkov.me/
 */

package me.gribkov.interviewtasks.renderint

import scala.annotation.tailrec

object RenderInt {

  def renderInt(number: Int): String = {

    def renderDigit(digit: Int) =
      digit match {
        case 0 => "0"
        case 1 => "1"
        case 2 => "2"
        case 3 => "3"
        case 4 => "4"
        case 5 => "5"
        case 6 => "6"
        case 7 => "7"
        case 8 => "8"
        case 9 => "9"
      }

    @tailrec
    def inner(rest: Int, result: String = ""): String =
      if (rest == 0) result
      else {
        val digit = rest % 10
        inner(rest / 10, renderDigit(digit) + result)
      }

    if (number == 0) "0"
    else if (number < 0) "-" + inner(-number)
    else inner(number)
  }
}