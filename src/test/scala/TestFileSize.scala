import FileSize.getFileSize

object TestFileSize {
  def main(args: Array[String]): Unit = {
    println(getFileSize("assets/TestFile.sc"))
    println(getFileSize("assets/TestFile.sc", isSymbol = false, "IEC", integer = true))
    println(getFileSize("assets/TestFile.sc", isSymbol = true, "JEDEC", integer = true))

    println(getFileSize("assets/LargeTestFile.sc"))
    println(getFileSize("assets/LargeTestFile.sc", isSymbol = false, "IEC", integer = true))
    println(getFileSize("assets/LargeTestFile.sc", isSymbol = true, "JEDEC", integer = true))
  }
}
