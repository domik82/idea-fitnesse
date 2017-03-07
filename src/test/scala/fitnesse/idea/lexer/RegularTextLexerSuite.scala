package fitnesse.idea.lexer

class RegularTextLexerSuite extends LexerSuite {
  test("Single word") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "Hello")
      )) {
      lex("Hello")
    }
  }

  test("Single word with number") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "Hello1")
      )) {
      lex("Hello1")
    }
  }

  test("Two words") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "Foo"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "bar")
      )) {
      lex("Foo bar")
    }
  }

  test("Number") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "123")
      )) {
      lex("123")
    }
  }

  test("CamelCase word where first letter is lower case") {
    assertResult(List(
      (FitnesseTokenType.TEXT, "thisIsCamelCase")

    )) {
      lex("thisIsCamelCase")
    }
  }

  test("Word that looks like a WikiWord but has 2 capital letters in a row ") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "ThisIsNotAWikiWord")
      )) {
      lex("ThisIsNotAWikiWord")
    }
  }

  test("USAforEver is a regular word") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "USAforEver")
      )) {
      lex("USAforEver")
    }
  }

  test("A sentence") {
    assertResult(
      List(
        (FitnesseTokenType.TEXT, "This"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "is"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "a"),
        (FitnesseTokenType.WHITE_SPACE, " "),
        (FitnesseTokenType.TEXT, "sentence.")
      )) {
      lex("This is a sentence.")
    }
  }
}
