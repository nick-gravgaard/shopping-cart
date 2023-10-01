import scala.util.Try

object ShoppingCart:
  enum Product(val price: BigDecimal):
    case Apple extends Product(BigDecimal(0.60))
    case Orange extends Product(BigDecimal(0.25))

  def main(args: Array[String]): Unit =
    println(generateReceipt(args))

  def productsByName(productNames: List[String]): List[Product] =
    productNames
      .map { name =>
        val titleCase = name.toLowerCase.capitalize
        Try(Product.valueOf(titleCase)).toOption // ignore unknown products
      }
      .flatten

  def sumPrices(products: List[Product]): BigDecimal =
    products
      .map(_.price)
      .sum

  def generateReceipt(productStrings: Array[String]): String =
    val products = productsByName(productStrings.toList)
    val productQuantities = products.groupBy(identity).mapValues(_.size).toList.sortBy(_._1.ordinal)
    val productLines =
      productQuantities
        .map { (product, quantity) =>
          val subtotal = product.price * quantity
          val quantityAndProduct = s"$quantity $product"
          f"$quantityAndProduct%-11s £$subtotal%4.2f"
        }
    val totalCost = sumPrices(products)
    val allLines = productLines :+ f"Total cost  £$totalCost%4.2f"
    allLines.mkString("\n")
