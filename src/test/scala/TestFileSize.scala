import FileSize.getFileSize

object TestFileSize {
  def main(args: Array[String]): Unit = {
    println(getFileSize("src/test/scala/assets/TestFile.scala"))
  }
}
