package fitnesse.idea.highlighting

import com.intellij.openapi.options.colors.{AttributesDescriptor, ColorDescriptor, ColorSettingsPage}
import fitnesse.idea.filetype.FitnesseFileType

class FitnesseColorSettingsPage extends ColorSettingsPage {
  def getAttributeDescriptors = FitnesseColorSettingsPage.DESCRIPTORS

  def getColorDescriptors = ColorDescriptor.EMPTY_ARRAY

  def getDisplayName = "FitNesse"

  def getIcon = FitnesseFileType.FILE_ICON

  def getHighlighter = new FitnesseHighlighter

  def getDemoText = "!include DataSetup\n" +
    "\n" +
    "!3 Some wiki header\n" +
    "WikiWord\n" +
    "\n" +
    "|sample fixture    |\n" +
    "|fieldOne|field two|\n" +
    "|ensure  |action   |\n" +
    "\n" +
    "#Some comment\n" +
    "\n" +
    "!*> Block\n" +
    "!define Variable_Name {Variable Value}\n" +
    "*!"

  def getAdditionalHighlightingTagToDescriptorMap = null
}

object FitnesseColorSettingsPage {
  final val DESCRIPTORS = Array(
    new AttributesDescriptor("Include", FitnesseHighlighter.INCLUDE),
    new AttributesDescriptor("Header Line", FitnesseHighlighter.HEADER),
    new AttributesDescriptor("WikiWord", FitnesseHighlighter.WIKI_WORD),
    new AttributesDescriptor("Text", FitnesseHighlighter.TEXT),
    new AttributesDescriptor("Comment", FitnesseHighlighter.COMMENT),
    new AttributesDescriptor("Define", FitnesseHighlighter.DEFINE),
    new AttributesDescriptor("Collapsible", FitnesseHighlighter.COLLAPSIBLE)
  )
}
