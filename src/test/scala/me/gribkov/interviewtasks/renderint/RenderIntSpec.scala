/*
 *  (c) 2014, Gribkov Yury
 *  http://gribkov.me/
 */

package me.gribkov.interviewtasks.renderint

import org.scalatest._

class RenderIntSpec extends FlatSpec with Matchers {

  import RenderInt._

  "RenderInt" should "render positive integer numbers" in {
    renderInt(512) should be ("512")
  }

  it should "render negative integer numbers" in {
    renderInt(-2000048) should be ("-2000048")
  }

  it should "render zero as well" in {
    renderInt(0) should be ("0")
  }
}