import munit.*
import ShoppingCart.*

class ShoppingCartSpec extends FunSuite:
  test("generateReceipt"):
    val obtained = generateReceipt(Array("apple", "apple", "orange", "apple"))
    val expected =
    """3 Apple     £1.80
      |1 Orange    £0.25
      |Total cost  £2.05""".stripMargin
    assertEquals(obtained, expected)
