/**
 * Created by KiyonoKara - 05/13/2021
 * File FileSize.scala
 */

// I/O & New I/O
import java.io.IOException
import java.nio.file.{Files, NoSuchFileException, Path, Paths}

// Text
import java.text.DecimalFormat

object FileSize {

  // Value for converting JEDEC to IEC standards such as KB to KiB
  private implicit val conversion: Double = 1.048576

  private val UnitTypes: Map[String, String] = Map(
    "JEDEC" -> "JEDEC",
    "IEC" -> "IEC",
    "FULL_FORM" -> "FULL_FORM"
  )

  private val ByteSymbols: Map[String, Array[String]] = Map(
    "JEDEC" -> Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"),
    "IEC" -> Array("B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB")
  )

  private val FullFormUnits: Map[String, Array[String]] = Map(
    "JEDEC" -> Array("Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes", "Petabytes", "Exabytes", "Zettabytes", "Yottabytes"),
    "IEC" -> Array("Bytes", "Kibibytes", "Mebibytes", "Gibibytes", "Tebibytes", "Pebibytes", "Exbibytes", "Zebibytes", "Yobibytes")
  )

  /**
   * Private method for unit conversions; Shouldn't be used outside of the object
   * @param x First
   * @param y Second
   * @return Output
   */
  private def unitConversion(x: Double, y: Double): Long = {
    (x / y).longValue
  }

  /**
   * Gets the file size, can return the units
   * @param file Path to the file
   * @param isSymbol Symbol or full unit name
   * @param unitType JEDEC, IEC, or full form standards
   * @param integer Determine whether the size is an integer or not
   * @return String with the size and units
   */
  def getFileSize(file: String, isSymbol: Boolean = true, unitType: String = UnitTypes.getOrElse("JEDEC", "JEDEC"), integer: Boolean = false): String = {
    val path: Path = Paths.get(file)

    try {
      val bytes: Long = Files.size(path)
      this.readableFileSize(bytes, isSymbol, unitType, integer)
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

  /**
   * Returns a human-readable file size with the given parameters
   * @param size File size in bytes
   * @param isSymbol Determines if the output is abbreviated / symbolized
   * @param unitType Two standards, JEDEC & IEC
   * @param integer Determine whether the output size is a decimal or not
   * @return {String} File size with labeled units
   */
  implicit private def readableFileSize(size: Long, isSymbol: Boolean = true, unitType: String = "JEDEC", integer: Boolean = false): String = {
    if (size <= 0) return 0.toString
    val unitTypeUC: String = unitType.toUpperCase

    val units: Array[String] = unitTypeUC match {
      case "JEDEC" =>
        (if (isSymbol) ByteSymbols else FullFormUnits).getOrElse(unitType.toUpperCase, "".asInstanceOf[Array[String]])
      case "IEC" =>
        (if (isSymbol) ByteSymbols else FullFormUnits).getOrElse(unitType.toUpperCase, "".asInstanceOf[Array[String]])
    }

    val groups: Int = unitTypeUC match {
      case "JEDEC" =>
        (Math.log10(size) / Math.log10(1024)).asInstanceOf[Int];
      case "IEC" =>
        unitConversion(Math.log10(size) / Math.log10(1024), this.conversion).asInstanceOf[Int];
    }
    val decimalFormat: String = s"""${new DecimalFormat(if (integer) "#,##0.#" else "#,##0.00").format(
      unitTypeUC match {
        case "JEDEC" =>
          size / Math.pow(1024, groups);
        case "IEC" =>
          unitConversion(size / Math.pow(1024, groups), this.conversion);
      }
    )} ${units(groups)}"""
    decimalFormat
  }
}
