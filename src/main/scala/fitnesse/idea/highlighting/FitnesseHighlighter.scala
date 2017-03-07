package fitnesse.idea.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.{SyntaxHighlighter, SyntaxHighlighterBase, SyntaxHighlighterFactory}
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import fitnesse.idea.lexer.{FitnesseLexer, FitnesseTokenType}

class FitnesseHighlighter extends SyntaxHighlighterBase {

  def getHighlightingLexer = new FitnesseLexer

  override def getTokenHighlights(elementType: IElementType) = {
    elementType match {
      case FitnesseTokenType.TABLE_START | FitnesseTokenType.ROW_START | FitnesseTokenType.CELL_START
           | FitnesseTokenType.TABLE_END |FitnesseTokenType.ROW_END | FitnesseTokenType.CELL_END
           | FitnesseTokenType.TEXT => Array(FitnesseHighlighter.TEXT)
      case FitnesseTokenType.WIKI_WORD => Array(FitnesseHighlighter.WIKI_WORD)
      case FitnesseTokenType.INCLUDE => Array(FitnesseHighlighter.INCLUDE)
      case FitnesseTokenType.COMMENT => Array(FitnesseHighlighter.COMMENT)
      case FitnesseTokenType.DEFINE => Array(FitnesseHighlighter.DEFINE)
      case FitnesseTokenType.BOLD => Array(FitnesseHighlighter.BOLD)
      case FitnesseTokenType.ITALIC => Array(FitnesseHighlighter.ITALIC)
      case FitnesseTokenType.HEADER => Array(FitnesseHighlighter.HEADER)
      case FitnesseTokenType.COLLAPSIBLE_END | FitnesseTokenType.COLLAPSIBLE_START => Array(FitnesseHighlighter.COLLAPSIBLE)
      case _ => Array.empty
    }
  }
}

object FitnesseHighlighter {
  final val BOLD = TextAttributesKey.createTextAttributesKey("FITNESSE.BOLD", DefaultLanguageHighlighterColors.MARKUP_ENTITY)
  final val ITALIC = TextAttributesKey.createTextAttributesKey("FITNESSE.ITALIC", DefaultLanguageHighlighterColors.MARKUP_TAG)

  final val WIKI_WORD = TextAttributesKey.createTextAttributesKey("FITNESSE.WIKI_WORD", DefaultLanguageHighlighterColors.KEYWORD)
  final val TEXT = TextAttributesKey.createTextAttributesKey("FITNESSE.TEXT", DefaultLanguageHighlighterColors.STRING)
  final val COMMENT = TextAttributesKey.createTextAttributesKey("FITNESSE.COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
  final val DEFINE = TextAttributesKey.createTextAttributesKey("FITNESSE.DEFINE", DefaultLanguageHighlighterColors.CONSTANT)
  final val INCLUDE = TextAttributesKey.createTextAttributesKey("FITNESSE.INCLUDE", DefaultLanguageHighlighterColors.NUMBER)
  final val HEADER = TextAttributesKey.createTextAttributesKey("FITNESSE.HEADER", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE)
  final val COLLAPSIBLE = TextAttributesKey.createTextAttributesKey("FITNESSE.COLLAPSIBLE", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
}

class FitnesseSyntaxHighlighterFactory extends SyntaxHighlighterFactory {

  override def getSyntaxHighlighter(project: Project, virtualFile: VirtualFile): SyntaxHighlighter = new FitnesseHighlighter()
}
