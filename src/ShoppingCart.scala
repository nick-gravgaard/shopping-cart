import scala.util.Try

object ShoppingCart:

  enum MultiplesOffer(val multipleSize: Int, val forPriceOf: Int):
    case BuyOneGetOneFree extends MultiplesOffer(2, 1) // same as 2 for the price of 1
    case ThreeForThePriceOfTwo extends MultiplesOffer(3, 2)

  enum Product(val price: BigDecimal, val maybeOffer: Option[MultiplesOffer]):
    case Apple extends Product(BigDecimal(0.60), None)
    case Orange extends Product(BigDecimal(0.25), None)

  case class ReceiptItem(subtotal: BigDecimal, description: String)

  def main(args: Array[String]): Unit =
    println(generateReceipt(args))

  def productsByName(productNames: List[String]): List[Product] =
    productNames
      .map { name =>
        val titleCase = name.toLowerCase.capitalize
        Try(Product.valueOf(titleCase)).toOption // ignore unknown products
      }
      .flatten

  def subtotalForQuantity(price: BigDecimal, quantity: Int, maybeOffer: Option[MultiplesOffer]): BigDecimal =
    maybeOffer.map { offer =>
      ???
    }.getOrElse(price * quantity)

  def generateReceipt(productStrings: Array[String]): String =
    val products = productsByName(productStrings.toList)
    val productQuantities = products.groupBy(identity).mapValues(_.size).toList.sortBy(_._1.ordinal)
    val productLines =
      productQuantities
        .map { (product, quantity) =>
          val subtotal = subtotalForQuantity(product.price, quantity, product.maybeOffer)
          val quantityAndProduct = s"$quantity $product"
          ReceiptItem(subtotal, f"$quantityAndProduct%-11s £$subtotal%4.2f")
        }
    val totalCost = productLines.map(_.subtotal).sum
    val allLines = productLines.map(_.description) :+ f"Total cost  £$totalCost%4.2f"
    allLines.mkString("\n")
