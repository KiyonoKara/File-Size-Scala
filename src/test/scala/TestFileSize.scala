import FileSize.getFileSize

object TestFileSize {
  def main(args: Array[String]): Unit = {
    println(getFileSize("src/main/scala/assets.TestFile.scala"))
  }
}
