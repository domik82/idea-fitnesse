package fitnesse.idea.action

import java.awt.Desktop
import java.net.{MalformedURLException, URI, URL}
import java.util.Objects

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent, CommonDataKeys}
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.{FileDocumentManager, FileEditorManager}
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.uiDesigner.core.config.FitNessePluginConfig
import fitnesse.idea.filetype.FitnesseLanguage

class OpenInBrowserAction extends AnAction {
  override def actionPerformed(anActionEvent: AnActionEvent): Unit = {
    val psiFile: PsiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE)
    if ((psiFile != null) && Objects.equals(psiFile.getLanguage.getID, FitnesseLanguage.INSTANCE.getID)) {
      val url: String = generateUrl(psiFile)
      try
        openWebPage(new URL(url))
      catch {
        case e1: MalformedURLException => e1.printStackTrace()
      }
    }
  }

  override def update(e: AnActionEvent): Unit = {
    val project: Project = e.getProject
    if (project == null) return
    val editor: Editor = FileEditorManager.getInstance(project).getSelectedTextEditor
    if (editor == null) return
    val virtualFile: VirtualFile = FileDocumentManager.getInstance.getFile(editor.getDocument)
    if (virtualFile == null) return
    val isFitnesse: Boolean = isFitnesseTest(virtualFile.getName)
    e.getPresentation.setVisible(isFitnesse)
  }

  private def isFitnesseTest(fileName: String): Boolean = fileName.equalsIgnoreCase("content.txt") || fileName.endsWith(".wiki")

  private def generateUrl(psiFile: PsiFile): String = {
    val rootFolderUrl: String = FitNessePluginConfig.getInstance().getFitnesseRootFolderUrl
    var psiFilePath: String = psiFile.getParent.toString
    val rootFolderName: String = rootFolderUrl.split("/")(rootFolderUrl.split("/").length - 1)
    val i: Int = psiFilePath.indexOf(rootFolderName)
    if (i == -1) {
      Messages.showMessageDialog("Wrong root folder URL: " + rootFolderUrl, "Error", Messages.getErrorIcon)
      return ""
    }
    psiFilePath = psiFilePath.substring(i).replace('\\', '.')
    rootFolderUrl.replaceAll(rootFolderName, "") + psiFilePath
  }

  @throws[java.io.IOException]
  private def openWebPage(uri: URI) {
    val desktop: Desktop = if (Desktop.isDesktopSupported) Desktop.getDesktop
    else null
    if ((desktop != null) && desktop.isSupported(java.awt.Desktop.Action.BROWSE)) desktop.browse(uri)
  }

  private def openWebPage(url: URL) {
    try
      openWebPage(url.toURI)
    catch {
      case e: Throwable => e.printStackTrace()
    }
  }
}
