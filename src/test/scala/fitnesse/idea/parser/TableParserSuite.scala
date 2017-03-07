package fitnesse.idea.parser

import fitnesse.idea.lexer.FitnesseTokenType

class TableParserSuite extends ParserSuite {

  test("One row decision table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "A")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END,"|")
        ))
      ))
    ) {
      parse("|A|")
    }
  }

  test("Simple decision table with no prefix") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "A")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.DECISION_INPUT, List(
              Leaf(FitnesseTokenType.TEXT, "B")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "C")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END,"|\n")
        )),
        Leaf(FitnesseTokenType.WHITE_SPACE,"\n")))
    ) {
      parse("|A|\n|B|\n|C|\n\n")
    }
  }

  test("Simple decision table with no prefix with more cell text") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "Should"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "I"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "buy"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "it")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.DECISION_INPUT, List(
              Leaf(FitnesseTokenType.TEXT, "have"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "money")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.DECISION_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "buy"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "it?")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "yes")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "yes")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|\n")
        )),
        Leaf(FitnesseTokenType.WHITE_SPACE, "\n")
      ))
    ) {
      parse("|Should I buy it|\n|have money|buy it?|\n|yes|yes|\n\n")
    }
  }

  test("Empty cells 1/2") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "A")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  ")
          )),
          Leaf(FitnesseTokenType.TABLE_END,"|")
        ))
      ))
    ) {
      parse("|A||  |")
    }
  }

  test("Empty cells 2/2") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "A")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Leaf(FitnesseTokenType.CELL_END, "|")
          )),
          Leaf(FitnesseTokenType.TABLE_END,"|")
        ))
      ))
    ) {
      parse("|A|  ||")
    }
  }

  test("Simple decision table with 'dt' prefix") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "dt")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "Should"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "I"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "buy"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "it")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.DECISION_INPUT, List(
              Leaf(FitnesseTokenType.TEXT, "have"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "money")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.DECISION_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "buy"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "it?")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "yes")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "yes")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|\n")
        )),
        Leaf(FitnesseTokenType.WHITE_SPACE, "\n")
      ))
    ) {
      parse("|dt:Should I buy it|\n|have money|buy it?|\n|yes|yes|\n\n")
    }
  }

  test("Simple decision table with 'decision' prefix") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.DECISION_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "decision")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "Should"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "I"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "buy"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "it")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.DECISION_INPUT, List(
              Leaf(FitnesseTokenType.TEXT, "have"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "money")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.DECISION_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "buy"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "it?")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "yes")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "yes")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|\n")
        )),
        Leaf(FitnesseTokenType.WHITE_SPACE, "\n")
      ))
    ) {
      parse("|decision:Should I buy it|\n|have money|buy it?|\n|yes|yes|\n\n")
    }
  }

  test("Query table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.QUERY_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "query")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "param1")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "1")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "2")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|query:stuff|param1|\n|foo field|bar field|\n|1|2|")
    }
  }

  test("Subset query table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.QUERY_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "subset"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "query")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "param1")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "1")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "2")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|subset query:stuff|param1|\n|foo field|bar field|\n|1|2|")
    }
  }


  test("Ordered query table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.QUERY_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "ordered"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "query")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "param1")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "1")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.QUERY_OUTPUT, List(
              Leaf(FitnesseTokenType.TEXT, "2")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|ordered query:stuff|param1|\n|foo field|bar field|\n|1|2|")
    }
  }

  test("Escaped script table with colon separator") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCRIPT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "!|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "script")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "param1")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("!|script:stuff|param1|\n|foo field|bar field|")
    }
  }

  test("Script tablewith colon separator") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCRIPT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "script")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "param1")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|script:stuff|param1|\n|foo field|bar field|")
    }
  }

  test("Script table with cell separator") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCRIPT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "script")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "param1")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|script|stuff|param1|\n|foo field|bar field|")
    }
  }

  test("Script table without fixture class") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCRIPT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "script")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|script|\n|foo field|bar field|")
    }
  }

  test("Script table with comment lines") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCRIPT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "script")
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "note")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "comment")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "#")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "comment")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "*")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "comment")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "comment")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|script|\n| note | comment |\n| # | comment |\n| * | comment |\n| | comment |")
    }
  }

  test("Script table with extra white space") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCRIPT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "script")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "stuff")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, "  ")
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|  script  |  stuff  |\n|  foo field  |  bar field  |")
    }
  }

  test("Scenario table with colon separator") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCENARIO_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "scenario")
            )),
            Leaf(FitnesseTokenType.COLON, ":"),
            Node(FitnesseElementType.SCENARIO_NAME, List(
              Node(FitnesseElementType.CELL, List(
                Leaf(FitnesseTokenType.TEXT, "stuff")
              )),
              Leaf(FitnesseTokenType.CELL_END, "|"),
              Node(FitnesseElementType.CELL, List(
                Leaf(FitnesseTokenType.TEXT, "param1")
              ))
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|scenario:stuff|param1|\n|foo field|bar field|")
    }
  }

  test("Scenario table with cell separator") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCENARIO_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "scenario")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.SCENARIO_NAME, List(
              Node(FitnesseElementType.CELL, List(
                Leaf(FitnesseTokenType.TEXT, "stuff")
              )),
              Leaf(FitnesseTokenType.CELL_END, "|"),
              Node(FitnesseElementType.CELL, List(
                Leaf(FitnesseTokenType.TEXT, "param1")
              ))
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|scenario|stuff|param1|\n|foo field|bar field|")
    }
  }

  test("Scenario table with cell separator and extra spaces") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCENARIO_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "scenario")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.SCENARIO_NAME, List(
              Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
              Node(FitnesseElementType.CELL, List(
                Leaf(FitnesseTokenType.TEXT, "stuff")
              )),
              Leaf(FitnesseTokenType.WHITE_SPACE, "  "),
              Leaf(FitnesseTokenType.CELL_END, "|"),
              Node(FitnesseElementType.CELL, List(
                Leaf(FitnesseTokenType.TEXT, "param1")
              ))
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|scenario|  stuff  |param1|\n|foo field|bar field|")
    }
  }

  test("Scenario table without name") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.SCENARIO_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "scenario")
            )),
            Node(FitnesseElementType.SCENARIO_NAME, List(
            ))
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.SCRIPT_ROW, List(
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "foo"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            )),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "bar"),
              Leaf(FitnesseTokenType.WHITE_SPACE, " "),
              Leaf(FitnesseTokenType.TEXT, "field")
            ))
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("|scenario|\n|foo field|bar field|")
    }
  }

  test("import table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.IMPORT_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "import")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.IMPORT, List(
              Leaf(FitnesseTokenType.TEXT, "fixtures")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("| import |\n| fixtures |")
    }
  }

  test("library table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.LIBRARY_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "library")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "fixtures")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("| library |\n| fixtures |")
    }
  }

  test("library table with constructor argument") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.LIBRARY_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.TABLE_TYPE, List(
              Leaf(FitnesseTokenType.TEXT, "library")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.ROW_END, "|\n|"),
          Node(FitnesseElementType.ROW, List(
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.FIXTURE_CLASS, List(
              Leaf(FitnesseTokenType.TEXT, "fixtures")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Leaf(FitnesseTokenType.CELL_END, "|"),
            Leaf(FitnesseTokenType.WHITE_SPACE, " "),
            Node(FitnesseElementType.CELL, List(
              Leaf(FitnesseTokenType.TEXT, "arg")
            )),
            Leaf(FitnesseTokenType.WHITE_SPACE, " ")
          )),
          Leaf(FitnesseTokenType.TABLE_END, "|")
        ))
      ))
    ) {
      parse("| library |\n| fixtures | arg |")
    }
  }

  test("partial table") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
        Node(TableElementType.UNKNOWN_TABLE, List(
          Leaf(FitnesseTokenType.TABLE_START, "|"),
          Node(FitnesseElementType.ROW, List(
          ))
        ))
      ))
    ) {
      parse("|")
    }
  }

  test("partial table, editing second cell") {
    assertResult(
      Node(FitnesseElementType.FILE, List(
//        Node(TableElementType.UNKNOWN_TABLE, List(
//          Leaf(FitnesseTokenType.TABLE_START, "|"),
//          Node(FitnesseElementType.ROW, List(
//            Node(FitnesseElementType.TABLE_TYPE, List(
//              Leaf(FitnesseTokenType.TEXT, "script")
//            )),
//            Leaf(FitnesseTokenType.CELL_END, "|")
//          ))
//        ))
        Leaf(FitnesseTokenType.TEXT,"|script|"),
        Leaf(FitnesseTokenType.WHITE_SPACE, " "),
        Leaf(FitnesseTokenType.TEXT, "foo")
      ))
    ) {
      parse("|script| foo")
    }
  }
}
