object TestFile {
  def printString(str: String): Any = {
    println(str.asInstanceOf[String])
  }
}

TestFile.printString("This is a String")