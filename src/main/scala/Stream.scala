import akka.actor._
import akka.stream._
import akka.stream.scaladsl._

object Stream extends App {

  implicit val system = ActorSystem("TestSystem")
  implicit val materializer = ActorMaterializer()

  //Source
  val source = Source(1 to 5)

  //Sink
  val sink = Sink.foreach[Int](element => println(s"Sink receive -> $element."))

  //Flow
  val doubler = Flow[Int].map(element => element * 2)
  val invert = Flow[Int].map(element => element * -1)
  val runnableFlow = source via doubler via invert to sink

  runnableFlow.run()

}
