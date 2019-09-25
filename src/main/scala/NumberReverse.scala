import akka.NotUsed
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._

object NumberReverse extends App {

  implicit val system = ActorSystem("TestSystem")
  implicit val materializer = ActorMaterializer()

  //Sink
  val sink = Sink.foreach(println)

  //Flow
  val numberReverseFlow: Flow[Int, String, NotUsed] =
    Flow[Int].map(reverseDigit)

  //Source connecting to Sink via flow
  Source(100 to 110).via(numberReverseFlow).to(sink).run()

  def reverseDigit(myInt: Int): String = myInt.toString.reverse
}
