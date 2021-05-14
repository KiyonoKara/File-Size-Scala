/**
 * Created by KaNguy - 05/13/2021
 * File FileSize.scala
 */

import java.nio.file.{Files, Path, Paths}
import java.io.IOException

object FileSize {
  def getFileSize(file: String): String = {
    val path: Path = Paths.get(file)

    try {
      val bytes: Long = Files.size(path)
      bytes.toString
    } catch {
      case io: IOException =>
        io.printStackTrace()
        io.getMessage
    }
  }

  def main(args: Array[String]): Unit = {
    println(getFileSize("src/main/scala/TestFile.scala"))
  }
}
