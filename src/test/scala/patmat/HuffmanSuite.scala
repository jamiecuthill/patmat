package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._
import patmat.Huffman.Leaf
import patmat.Huffman.Fork

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }

    assert(weight(Leaf('b', 2)) === 2)
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("empty list is not singleton") {
    assert(!singleton(List()))
  }

  test("list with one item is singleton") {
    assert(singleton(List(Leaf('c', 1))))
  }

  test("list with two items is not a singleton") {
    assert(!singleton(List(Leaf('c', 1), Leaf('a', 2))))
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times") {
    val t: List[(Char, Int)] = times(List('a', 'b', 'a', 'b', 'c', 'c', 'c', 'd', 'e'))

    assert(t.contains('a', 2))
    assert(t.contains('b', 2))
    assert(t.contains('c', 3))
    assert(t.contains('d', 1))
    assert(t.contains('e', 1))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("create code tree") {
    new TestTrees {
      assert(createCodeTree(List('a', 'a', 'b', 'b', 'b')) === t1)
    }
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

//  test("decode using frenchCode") {
//    assert(decode(frenchCode, secret) === decodedSecret)
//  }
//
//  test("encode using frenchCode") {
//    assert(encode(frenchCode)(decodedSecret) === secret)
//  }
//
//  test("decode and encode a very short text should be identity") {
//    new TestTrees {
//      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
//    }
//  }

  test("convert") {
    new TestTrees {
      val codeTable = convert(t1)
      codeTable.map(println)
      assert(codeTable.contains(('a', List(0))))
      assert(codeTable.contains(('b', List(1))))
    }
  }
}
