object ShoppingCart:
  enum Product(val price: BigDecimal):
    case Apple extends Product(BigDecimal(0.60))
    case Orange extends Product(BigDecimal(0.25))

  def main(args: Array[String]): Unit =
    println(generateReceipt(args))

  def generateReceipt(args: Array[String]): String =
    ???
