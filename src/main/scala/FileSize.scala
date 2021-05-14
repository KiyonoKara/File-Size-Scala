/**
 * Created by KaNguy - 05/13/2021
 * File FileSize.scala
 */

// I/O & New I/O
import java.nio.file.{Files, Path, Paths}
import java.io.IOException

// Text
import java.text.DecimalFormat

object FileSize {
  sealed trait groups

  def getFileSize(file: String, shortened: Boolean = true): String = {
    val path: Path = Paths.get(file)

    try {
      val bytes: Long = Files.size(path)
      this.readableFileSize(bytes.asInstanceOf[Long])
    } catch {
      case io: IOException =>
        io.printStackTrace()
        io.getMessage
    }
  }

  def readableFileSize(size: Long, shortened: Boolean = true): String = {
    if (size <= 0) return 0.toString

    val units: Array[String] = if (shortened) Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB") else Array("Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes", "Peta Bytes", "Exa Bytes", "Zetta Bytes", "Yotta Bytes")
    val groups: Int = (Math.log10(size) / Math.log10(1024)).asInstanceOf[Int]
    val decimalFormat: String = s"""${new DecimalFormat("#,##0.#").format(size / Math.pow(1024, groups))} ${units(groups)}"""
    decimalFormat
  }

  def main(args: Array[String]): Unit = {
    println(getFileSize("src/main/scala/TestFile.scala"))
  }
}
