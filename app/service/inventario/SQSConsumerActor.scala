package service.inventario

import akka.actor.{Actor, Props}
import akka.stream.{ActorMaterializer, ThrottleMode}
import akka.stream.alpakka.sqs.{MessageAttributeName, SqsSourceSettings}
import akka.stream.alpakka.sqs.scaladsl.SqsSource
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.sqs.{AmazonSQSAsync, AmazonSQSAsyncClientBuilder}
import com.amazonaws.services.sqs.model.{DeleteMessageRequest, Message}
import com.typesafe.config.Config
import dao.InventarioDAO
import service.inventario.SQSConsumerActor.DeleteMsg
import scala.concurrent.duration._
import scala.collection.JavaConverters._

object SQSConsumerActor {

  case class DeleteMsg(message: Message)

  def props(inventarioDAO: InventarioDAO) = Props(new SQSConsumerActor(inventarioDAO))

}

class SQSConsumerActor(inventarioDAO: InventarioDAO) extends Actor {

  private val queueUrl = "marketplace_grupo4"
  private val sqsUrl = "https://sqs.us-east-1.amazonaws.com/803611297537/marketplace_grupo4"
  private val sqsRegion = "us-east-1"
  private val maxMsg = 10000
  private val timeDelay = 1

  implicit private val sqsClient: AmazonSQSAsync = AmazonSQSAsyncClientBuilder
    .standard()
    .withEndpointConfiguration(new EndpointConfiguration(sqsUrl, sqsRegion))
    .build()

  implicit private val mat = ActorMaterializer()

  SqsSource(sqsUrl, SqsSourceSettings.Defaults)
    .throttle(maxMsg, timeDelay.milli, maxMsg, ThrottleMode.shaping)
    .runForeach((message) => {

      println("Se recibio este mensaje: " + message.getBody)
      val productosId = message.getBody.split("-").toList.map(_.toLong)

      productosId.map( idProd => inventarioDAO.updateProducto(1L, idProd))

      sqsClient.deleteMessage(
        new DeleteMessageRequest(sqsUrl, message.getReceiptHandle)
      )
    })

  def receive: Receive = {

    case _ => println("Mensaje eliminado")
  }

  override def preStart(): Unit =
    println("-------------------------------------------> Empezo")
}