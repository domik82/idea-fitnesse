package fitnesse.idea.lexer

class TableLexerSuite extends LexerSuite {

  test("iterators") {
    val iter = Iterator("A", "B")

    assertResult(true) { iter.hasNext }
    assertResult("A") { iter.next }

    val iter2 = Iterator("C") ++ iter

    assertResult(true) { iter2.hasNext }
    assertResult("C") { iter2.next }
    assertResult(true) { iter2.hasNext }
    assertResult("B") { iter2.next }
    assertResult(false) { iter2.hasNext }
  }

  test("Simple table") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|A|B|\n|C|D|\n"),
        (FitnesseTokenType.ROW_START, "A|B|\n|"),
        (FitnesseTokenType.CELL_START, "A|"),
        (FitnesseTokenType.TEXT, "A"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "B|\n|"),
        (FitnesseTokenType.TEXT, "B"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "C|D|\n"),
        (FitnesseTokenType.CELL_START, "C|"),
        (FitnesseTokenType.TEXT, "C"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "D|\n"),
        (FitnesseTokenType.TEXT, "D"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("|A|B|\n|C|D|\n\n")
    }
  }

  test("Simple table with CRLF") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|A|B|\r\n|C|D|\r\n"),
        (FitnesseTokenType.ROW_START, "A|B|\r\n|"),
        (FitnesseTokenType.CELL_START, "A|"),
        (FitnesseTokenType.TEXT, "A"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "B|\r\n|"),
        (FitnesseTokenType.TEXT, "B"),
        (FitnesseTokenType.CELL_END, "|\r\n|"),
        (FitnesseTokenType.ROW_END, "|\r\n|"),
        (FitnesseTokenType.ROW_START, "C|D|\r\n"),
        (FitnesseTokenType.CELL_START, "C|"),
        (FitnesseTokenType.TEXT, "C"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "D|\r\n"),
        (FitnesseTokenType.TEXT, "D"),
        (FitnesseTokenType.CELL_END, "|\r\n"),
        (FitnesseTokenType.ROW_END, "|\r\n"),
        (FitnesseTokenType.TABLE_END, "|\r\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\r\n")
      )) {
      lex("|A|B|\r\n|C|D|\r\n\r\n")
    }
  }

  test("Simple table with nothing at end") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|A|B|\n|C|D|"),
        (FitnesseTokenType.ROW_START, "A|B|\n|"),
        (FitnesseTokenType.CELL_START, "A|"),
        (FitnesseTokenType.TEXT, "A"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "B|\n|"),
        (FitnesseTokenType.TEXT, "B"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "C|D|"),
        (FitnesseTokenType.CELL_START, "C|"),
        (FitnesseTokenType.TEXT, "C"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "D|"),
        (FitnesseTokenType.TEXT, "D"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.ROW_END, "|"),
        (FitnesseTokenType.TABLE_END, "|")
      )) {
      lex("|A|B|\n|C|D|")
    }
  }

  test("Two simple tables") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|A|B|\n|C|D|\n"),
        (FitnesseTokenType.ROW_START, "A|B|\n|"),
        (FitnesseTokenType.CELL_START, "A|"),
        (FitnesseTokenType.TEXT, "A"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "B|\n|"),
        (FitnesseTokenType.TEXT, "B"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "C|D|\n"),
        (FitnesseTokenType.CELL_START, "C|"),
        (FitnesseTokenType.TEXT, "C"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "D|\n"),
        (FitnesseTokenType.TEXT, "D"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.TABLE_START, "|E|F|\n|G|H|"),
        (FitnesseTokenType.ROW_START, "E|F|\n|"),
        (FitnesseTokenType.CELL_START, "E|"),
        (FitnesseTokenType.TEXT, "E"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "F|\n|"),
        (FitnesseTokenType.TEXT, "F"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "G|H|"),
        (FitnesseTokenType.CELL_START, "G|"),
        (FitnesseTokenType.TEXT, "G"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "H|"),
        (FitnesseTokenType.TEXT, "H"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.ROW_END, "|"),
        (FitnesseTokenType.TABLE_END, "|")
      )) {
      lex("|A|B|\n|C|D|\n\n|E|F|\n|G|H|")
    }
  }


  test("Decision table") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|Class|Arg1|Arg2|\n|numerator|denominator|quotient?|\n|10|5|2|\n|20|4|5|\n"),
        (FitnesseTokenType.ROW_START, "Class|Arg1|Arg2|\n|"),
        (FitnesseTokenType.CELL_START, "Class|"),
        (FitnesseTokenType.TEXT, "Class"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "Arg1|"),
        (FitnesseTokenType.TEXT, "Arg1"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "Arg2|\n|"),
        (FitnesseTokenType.TEXT, "Arg2"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "numerator|denominator|quotient?|\n|"),
        (FitnesseTokenType.CELL_START, "numerator|"),
        (FitnesseTokenType.TEXT, "numerator"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "denominator|"),
        (FitnesseTokenType.TEXT, "denominator"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "quotient?|\n|"),
        (FitnesseTokenType.TEXT, "quotient?"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "10|5|2|\n|"),
        (FitnesseTokenType.CELL_START, "10|"),
        (FitnesseTokenType.TEXT, "10"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "5|"),
        (FitnesseTokenType.TEXT, "5"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "2|\n|"),
        (FitnesseTokenType.TEXT, "2"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "20|4|5|\n"),
        (FitnesseTokenType.CELL_START, "20|"),
        (FitnesseTokenType.TEXT, "20"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "4|"),
        (FitnesseTokenType.TEXT, "4"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "5|\n"),
        (FitnesseTokenType.TEXT, "5"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("|Class|Arg1|Arg2|\n|numerator|denominator|quotient?|\n|10|5|2|\n|20|4|5|\n\n")
    }
  }

  test("Query table") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|Query:some stuff|with param1|\n|Col1|Col2|\n|Result1 Col1|Result1 Col2|\n"),
        (FitnesseTokenType.ROW_START, "Query:some stuff|with param1|\n|"),
        (FitnesseTokenType.CELL_START, "Query:some stuff|"),
        (FitnesseTokenType.TEXT, "Query"),
        (FitnesseTokenType.COLON, ":"),
        (FitnesseTokenType.TEXT, "some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "stuff"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "with param1|\n|"),
        (FitnesseTokenType.TEXT, "with"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "param1"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "Col1|Col2|\n|"),
        (FitnesseTokenType.CELL_START, "Col1|"),
        (FitnesseTokenType.TEXT, "Col1"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "Col2|\n|"),
        (FitnesseTokenType.TEXT, "Col2"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "Result1 Col1|Result1 Col2|\n"),
        (FitnesseTokenType.CELL_START, "Result1 Col1|"),
        (FitnesseTokenType.TEXT, "Result1"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "Col1"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "Result1 Col2|\n"),
        (FitnesseTokenType.TEXT, "Result1"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "Col2"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("|Query:some stuff|with param1|\n|Col1|Col2|\n|Result1 Col1|Result1 Col2|\n\n")
    }
  }

  test("Escaped table") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "!|Query:some stuff|\n"),
        (FitnesseTokenType.ROW_START, "Query:some stuff|\n"),
        (FitnesseTokenType.CELL_START, "Query:some stuff|\n"),
        (FitnesseTokenType.TEXT, "Query"),
        (FitnesseTokenType.COLON, ":"),
        (FitnesseTokenType.TEXT, "some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "stuff"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("!|Query:some stuff|\n\n")
    }
  }

  test("Table with empty cell") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|Some table||\n"),
        (FitnesseTokenType.ROW_START, "Some table||\n"),
        (FitnesseTokenType.CELL_START, "Some table|"),
        (FitnesseTokenType.TEXT, "Some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "table"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "|\n"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("|Some table||\n\n")
    }
  }

  test("Table with empty first cell") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "||Some table|\n"),
        (FitnesseTokenType.ROW_START, "|Some table|\n"),
        (FitnesseTokenType.CELL_START, "|"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "Some table|\n"),
        (FitnesseTokenType.TEXT, "Some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "table"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END,"|\n"),
        (FitnesseTokenType.TABLE_END,"|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("||Some table|\n\n")
    }
  }

  test("Table with spaces in cells") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "| Some table | Some value |"),
        (FitnesseTokenType.ROW_START, " Some table | Some value |"),
        (FitnesseTokenType.CELL_START, " Some table |"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "Some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "table"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, " Some value |"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "Some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "value"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.ROW_END,"|"),
        (FitnesseTokenType.TABLE_END,"|")
      )) {
      lex("| Some table | Some value |")
    }
  }

  test("'Table' with newline after the first |") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "|"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("|\n")
    }
  }

  test("'Table' with newline in middle of first cell") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "|Hello"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n")
      )) {
      lex("|Hello\n")
    }
  }

  test("Table with row being currently written at EOF") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|Some table|\n|"),
        (FitnesseTokenType.ROW_START, "Some table|\n|"),
        (FitnesseTokenType.CELL_START, "Some table|\n|"),
        (FitnesseTokenType.TEXT, "Some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "table"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, ""),
        (FitnesseTokenType.ROW_END, ""),
        (FitnesseTokenType.TABLE_END, "")
      )) {
      lex("|Some table|\n|")
    }
  }

  test("Table with row being currently written in middle of file") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "|Some"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "table|"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.TEXT, "|"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.TEXT, "Hello")
      )) {
      lex("|Some table|\n|\n\nHello")
    }
  }

  test("Misformed table with !define statement") {
    assertResult(
      List(
        (FitnesseTokenType.TABLE_START, "|!-ArrangementToOrderV1-!|\n|!define ArrangementToOrderAccountNumber {125673051}|\n"),
        (FitnesseTokenType.ROW_START,"!-ArrangementToOrderV1-!|\n|"),
        (FitnesseTokenType.CELL_START,"!-ArrangementToOrderV1-!|\n|"),
        (FitnesseTokenType.TEXT, "!-ArrangementToOrderV1-!"),
        (FitnesseTokenType.CELL_END,"|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START,"!define ArrangementToOrderAccountNumber {125673051}|\n"),
        (FitnesseTokenType.CELL_START,"!define ArrangementToOrderAccountNumber {125673051}|\n"),
        (FitnesseTokenType.DEFINE, "!define ArrangementToOrderAccountNumber {125673051}"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END,"|\n"),
        (FitnesseTokenType.TABLE_END,"|\n")
      )) {
      lex("|!-ArrangementToOrderV1-!|\n|!define ArrangementToOrderAccountNumber {125673051}|\n")
    }
  }

  test("Sample table from config") {
    assertResult(
      List(
        (FitnesseTokenType.INCLUDE, "!include DataSetup\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.HEADER, "!3 Some wiki header\n"),
        (FitnesseTokenType.WIKI_WORD, "WikiWord"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.TABLE_START, "|sample fixture|\n|fieldOne|field two|\n|ensure|action|\n"),
        (FitnesseTokenType.ROW_START, "sample fixture|\n|"),
        (FitnesseTokenType.CELL_START, "sample fixture|\n|"),
        (FitnesseTokenType.TEXT, "sample"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "fixture"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "fieldOne|field two|\n|"),
        (FitnesseTokenType.CELL_START, "fieldOne|"),
        (FitnesseTokenType.TEXT, "fieldOne"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "field two|\n|"),
        (FitnesseTokenType.TEXT, "field"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "two"),
        (FitnesseTokenType.CELL_END, "|\n|"),
        (FitnesseTokenType.ROW_END, "|\n|"),
        (FitnesseTokenType.ROW_START, "ensure|action|\n"),
        (FitnesseTokenType.CELL_START, "ensure|"),
        (FitnesseTokenType.TEXT, "ensure"),
        (FitnesseTokenType.CELL_END, "|"),
        (FitnesseTokenType.CELL_START, "action|\n"),
        (FitnesseTokenType.TEXT, "action"),
        (FitnesseTokenType.CELL_END, "|\n"),
        (FitnesseTokenType.ROW_END, "|\n"),
        (FitnesseTokenType.TABLE_END, "|\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.COMMENT,"#Some comment\n"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.COLLAPSIBLE_START, "!*> Block\n!define Variable_Name {Variable Value}\n*!"),
        (FitnesseTokenType.TEXT, "Block"),
        (FitnesseTokenType.DEFINE,"!define Variable_Name {Variable Value}"),
        (FitnesseTokenType.LINE_TERMINATOR, "\n"),
        (FitnesseTokenType.COLLAPSIBLE_END, "*!")
      )) {
      lex("!include DataSetup\n" +
        "\n" +
        "!3 Some wiki header\n" +
        "WikiWord\n\n" +
        "|sample fixture|\n" +
        "|fieldOne|field two|\n" +
        "|ensure|action|\n" +
        "\n" +
        "#Some comment\n" +
        "\n" +
        "!*> Block\n" +
        "!define Variable_Name {Variable Value}\n" +
        "*!")
    }
  }
}
