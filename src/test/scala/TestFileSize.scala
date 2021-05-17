import FileSize.getFileSize

object TestFileSize {
  def main(args: Array[String]): Unit = {
    println(getFileSize("src/test/scala/assets/TestFile.scala"))
    println(getFileSize("src/test/scala/assets/TestFile.scala", isSymbol = false, "IEC", integer = true))
    println(getFileSize("src/test/scala/assets/TestFile.scala", isSymbol = true, "JEDEC", integer = true))
  }
}
