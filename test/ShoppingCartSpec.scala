import munit.*
import ShoppingCart.*
import ShoppingCart.Product.*

class ShoppingCartSpec extends FunSuite:
  test("productsByName"):
    val obtained = productsByName(List("apple", "banana", "Apple", "orange", "APPLE"))
    val expected = List(Apple, Apple, Orange, Apple)
    assertEquals(obtained, expected)

  test("subtotalForQuantity"):
    test("no offer"):
      val obtained = subtotalForQuantity(BigDecimal(11), 7, None)
      val expected = BigDecimal(77)
      assertEquals(obtained, expected)

  test("generateReceipt"):
    val obtained = generateReceipt(Array("apple", "apple", "orange", "apple"))
    val expected =
    """3 Apple     £1.80
      |1 Orange    £0.25
      |Total cost  £2.05""".stripMargin
    assertEquals(obtained, expected)
