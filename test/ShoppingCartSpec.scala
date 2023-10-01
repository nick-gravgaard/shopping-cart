import munit.*
import ShoppingCart.*
import ShoppingCart.MultiplesOffer.*
import ShoppingCart.Product.*

class ShoppingCartSpec extends FunSuite:
  test("productByName"):
    assertEquals(productByName("apple"), Some(Apple))
    assertEquals(productByName("Apple"), Some(Apple))
    assertEquals(productByName("APPLE"), Some(Apple))
    assertEquals(productByName("orange"), Some(Orange))
    assertEquals(productByName("banana"), None)

  test("subtotalForQuantity"):
    test("no offer"):
      val obtained = subtotalForQuantity(BigDecimal(11), 7, None)
      val expected = BigDecimal(77)
      assertEquals(obtained, expected)

    test("BuyOneGetOneFree"):
      val obtained = subtotalForQuantity(BigDecimal(11), 7, Some(BuyOneGetOneFree))
      val expected = BigDecimal(44)
      assertEquals(obtained, expected)

    test("ThreeForThePriceOfTwo"):
      val obtained = subtotalForQuantity(BigDecimal(11), 7, Some(ThreeForThePriceOfTwo))
      val expected = BigDecimal(55)
      assertEquals(obtained, expected)

  test("generateReceipt"):
    val obtained = generateReceipt(Array("apple", "apple", "orange", "apple", "orange", "orange", "orange"))
    val expected =
    """3 Apple     £1.20
      |4 Orange    £0.75
      |Total cost  £1.95""".stripMargin
    assertEquals(obtained, expected)
