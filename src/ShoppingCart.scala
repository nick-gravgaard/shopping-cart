object ShoppingCart:
  enum Product(val price: BigDecimal):
    case Apple extends Product(BigDecimal(0.60))
    case Orange extends Product(BigDecimal(0.25))

  def main(args: Array[String]): Unit =
    println(generateReceipt(args))

  def productsByName(productNames: List[String]): List[Product] =
    ???

  def sumPrices(products: List[Product]): BigDecimal =
    ???

  def generateReceipt(productStrings: Array[String]): String =
    ???
