import akka.actor.{ActorRef, ActorSystem}
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, OverflowStrategy}

import scala.concurrent.Future

object SourceWithActor extends App {
  implicit val system = ActorSystem("TestSystem")
  implicit val materializer = ActorMaterializer()

  import system.dispatcher
  val s = Source
    .actorRef[Int](bufferSize = 0, OverflowStrategy.fail)
    .mapMaterializedValue(run)

  def run(actor: ActorRef): Future[Unit] = {
    Future {
      Thread.sleep(300); actor ! 1
    }
    Future {
      Thread.sleep(200); actor ! 2
    }
    Future {
      Thread.sleep(100); actor ! 3
    }
  }
  s runForeach println
}
