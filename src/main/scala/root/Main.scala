package root

import com.google.inject._
import presentation.Cli

object Main {
  def main(args: Array[String]): Unit = {

    val injector: Injector = Guice.createInjector(new GuiceModule)
    val cli: Cli = injector.getInstance(classOf[Cli])
    cli.run
  }
}
