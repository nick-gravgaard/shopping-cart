import scala.util.Try

object ShoppingCart:

  enum MultiplesOffer(val multipleSize: Int, val forPriceOf: Int):
    case BuyOneGetOneFree extends MultiplesOffer(2, 1) // same as 2 for the price of 1
    case ThreeForThePriceOfTwo extends MultiplesOffer(3, 2)

  import MultiplesOffer._

  enum Product(val price: BigDecimal, val maybeOffer: Option[MultiplesOffer]):
    case Apple extends Product(BigDecimal(0.60), Some(BuyOneGetOneFree))
    case Orange extends Product(BigDecimal(0.25), Some(ThreeForThePriceOfTwo))

  def main(args: Array[String]): Unit =
    println(generateReceipt(args))

  def productByName(productName: String): Option[Product] =
    val titleCase = productName.toLowerCase.capitalize
    Try(Product.valueOf(titleCase)).toOption // ignore unknown products

  def subtotalForQuantity(price: BigDecimal, quantity: Int, maybeOffer: Option[MultiplesOffer]): BigDecimal =
    maybeOffer.map { offer =>
      val nofMultiples = quantity / offer.multipleSize
      val remainder = quantity % offer.multipleSize
      ((nofMultiples * offer.forPriceOf) + remainder) * price
    }
    .getOrElse(price * quantity)

  def generateReceipt(productStrings: Array[String]): String =
    val (subtotals, descriptions) =
      productStrings
        .map(productByName(_))
        .flatten
        .groupBy(identity)
        .mapValues(_.size)
        .toList
        .sortBy(_._1.ordinal)
        .map { (product, quantity) =>
          val subtotal = subtotalForQuantity(product.price, quantity, product.maybeOffer)
          val description = f"${s"$quantity $product"}%-11s £$subtotal%4.2f"
          (subtotal, description)
        }
        .unzip
    val allLines = descriptions :+ f"Total cost  £${subtotals.sum}%4.2f"
    allLines.mkString("\n")
