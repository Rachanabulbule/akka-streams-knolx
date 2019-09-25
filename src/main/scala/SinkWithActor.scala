import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

object SinkWithActor extends App {
  implicit val system = ActorSystem("TestSystem")
  implicit val materializer = ActorMaterializer()

  val actor = system.actorOf(Props(new Actor {
    override def receive = {
      case msg => println(s"actor received: $msg")
    }
  }))
  val sink = Sink.actorRef[Int](actor, onCompleteMessage = "stream completed")
  val runnable = Source(1 to 3) to sink
  runnable.run()
}
