/**
 * Created by KaNguy - 05/13/2021
 * File FileSize.scala
 */

// I/O & New I/O
import java.nio.file.{Files, NoSuchFileException, Path, Paths}
import java.io.IOException

// Text
import java.text.DecimalFormat

object FileSize {
  sealed trait groups
  private val ByteSymbols: Map[String, Array[String]] = Map(
    "JEDEC" -> Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"),
    "IEC" -> Array("B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB")
  )

  private val FullFormUnits: Map[String, Array[String]] = Map(
    "JEDEC" -> Array("Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes", "Petabytes", "Exabytes", "Zettabytes", "Yottabytes"),
  )

  def getFileSize(file: String, shortened: Boolean = true, integer: Boolean = false): String = {
    val path: Path = Paths.get(file)

    try {
      val bytes: Long = Files.size(path)
      this.readableFileSize(bytes.asInstanceOf[Long], shortened, integer)
    } catch {
      case io: IOException =>
        io.printStackTrace()
        io.getMessage;
      case so: StackOverflowError =>
        so.printStackTrace()
        so.getMessage;
      case noSuchFileException: NoSuchFileException =>
        noSuchFileException.printStackTrace()
        noSuchFileException.getMessage;
    }
  }

  def readableFileSize(size: Long, shortened: Boolean = true, integer: Boolean = false): String = {
    if (size <= 0) return 0.toString

    val units: Array[String] = if (shortened) Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB") else Array("Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes", "Peta Bytes", "Exa Bytes", "Zetta Bytes", "Yotta Bytes")
    val groups: Int = (Math.log10(size) / Math.log10(1024)).asInstanceOf[Int]
    val decimalFormat: String = s"""${new DecimalFormat(if (integer) "#,##0.#" else "#,##0.00").format(size / Math.pow(1024, groups))} ${units(groups)}"""
    decimalFormat
  }
}
