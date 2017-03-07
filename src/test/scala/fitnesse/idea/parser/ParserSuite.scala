package fitnesse.idea.parser

import com.intellij.lang._
import com.intellij.lang.impl.PsiBuilderFactoryImpl
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.mock._
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.{Document, EditorFactory}
import com.intellij.openapi.extensions.Extensions
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.impl.FileDocumentManagerImpl
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.impl.ProgressManagerImpl
import com.intellij.psi.impl.source.SourceTreeToPsiMap
import com.intellij.psi.impl.source.tree.{FileElement, CompositeElement}
import com.intellij.psi.impl.{PsiFileFactoryImpl, PsiManagerEx}
import com.intellij.psi.tree.IElementType
import com.intellij.psi.{JavaPsiFacade, PsiFileFactory, PsiManager, SingleRootFileViewProvider}
import com.intellij.testFramework.LightVirtualFile
import com.intellij.uiDesigner.core.config.FitNessePluginConfig
import com.intellij.util.Function
import fitnesse.idea.filetype.FitnesseLanguage
import fitnesse.idea.psi.FitnesseFile
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar.mock
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

trait ParserSuite extends FunSuite with Matchers with BeforeAndAfterAll {
  val parserDefinition = new FitnesseParserDefinition
  val myTestRootDisposable = new Disposable {
    def dispose(): Unit = {}
  }

  var app: MockApplicationEx = null
  var myProject: MockProjectEx = null
  var myConfig: FitNessePluginConfig = null

  var myPsiManager: PsiManagerEx = null
  var myPsiFileFactory: PsiFileFactory = null

  val editorFactory: MockEditorFactory = new MockEditorFactory

  val fileDocumentManager = new MockFileDocumentManagerImpl(new Function[CharSequence, Document] {
    def fun(charSequence: CharSequence): Document = {
      editorFactory.createDocument(charSequence)
    }
  }, FileDocumentManagerImpl.HARD_REF_TO_DOCUMENT_KEY)


  override protected def beforeAll(): Unit = {
    super.beforeAll()

    Extensions.registerAreaClass("IDEA_PROJECT", null)
    Extensions.registerAreaClass("IDEA_PROJECT", null)
    Extensions.registerAreaClass(MultiHostInjector.MULTIHOST_INJECTOR_EP_NAME.getName, null)

    app = new MockApplicationEx(myTestRootDisposable)
    myProject = new MockProjectEx(myTestRootDisposable)
    myPsiManager = new MockPsiManager(myProject)
    myPsiFileFactory = new PsiFileFactoryImpl(myPsiManager)
    myConfig = mock[FitNessePluginConfig]
    when(myConfig.getFixtureSuffix).thenReturn("Fixture")

    myProject.getPicoContainer.registerComponentInstance(classOf[PsiFileFactory].getName, myPsiFileFactory)
    myProject.getPicoContainer.registerComponentInstance(classOf[PsiManager].getName,  myPsiManager)

    ApplicationManager.setApplication(app, myTestRootDisposable)

    app.getPicoContainer.registerComponentInstance(classOf[FitNessePluginConfig].getName, myConfig)
    app.getPicoContainer.registerComponentInstance(classOf[FileTypeManager].getName, new MockFileTypeManager(new MockLanguageFileType(parserDefinition.getFileNodeType.getLanguage, "txt")))
    app.getPicoContainer.registerComponentInstance(classOf[EditorFactory].getName, editorFactory)
    app.getPicoContainer.registerComponentInstance(classOf[FileDocumentManager].getName, fileDocumentManager)
    app.getPicoContainer.registerComponentInstance(classOf[DefaultASTFactory].getName, new DefaultASTFactoryImpl)
    app.getPicoContainer.registerComponentInstance(classOf[PsiBuilderFactory].getName, new PsiBuilderFactoryImpl)
    app.getPicoContainer.registerComponentInstance(classOf[ProgressManager].getName,  new ProgressManagerImpl)

    LanguageParserDefinitions.INSTANCE.addExplicitExtension(parserDefinition.getFileNodeType.getLanguage, parserDefinition)
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    app.getPicoContainer.unregisterComponent(classOf[FileTypeManager].getName)
    app.getPicoContainer.unregisterComponent(classOf[EditorFactory].getName)
    app.getPicoContainer.unregisterComponent(classOf[FileDocumentManager].getName)
    app.getPicoContainer.unregisterComponent(classOf[DefaultASTFactory].getName)
    app.getPicoContainer.unregisterComponent(classOf[PsiBuilderFactory].getName)
    app.getPicoContainer.unregisterComponent(classOf[ProgressManager].getName)

    myProject.getPicoContainer.unregisterComponent(classOf[JavaPsiFacade].getName)
    myProject.getPicoContainer.unregisterComponent(classOf[PsiManager].getName)
    myProject.getPicoContainer.unregisterComponent(classOf[PsiFileFactory].getName)
    myProject.getPicoContainer.unregisterComponent(classOf[FitNessePluginConfig].getName)
  }

  abstract class Tree
  case class Node(elementType: IElementType, children: List[Tree]) extends Tree
  case class Leaf(elementType: IElementType, text: String) extends Tree

  def parse(text: String) = {
    convertToTree(parseFile(text))
  }

  def parseFile(text: String): FileElement = {
    val file: FitnesseFile = parsePsiFile(text)
    SourceTreeToPsiMap.psiElementToTree(file).asInstanceOf[FileElement]
  }

  def parsePsiFile(text: String): FitnesseFile = {
    val virtualFile: LightVirtualFile = new LightVirtualFile("content.txt", FitnesseLanguage.INSTANCE, text)
    val viewProvider = new SingleRootFileViewProvider(myPsiManager, virtualFile, true)
    val file = parserDefinition.createFile(viewProvider)
    file
  }

  private def convertToTree(node: ASTNode): Tree = {
    if (node.isInstanceOf[CompositeElement]) {
      val children = node.getChildren(null)
      val leaves = children.map(child => convertToTree(child)).toList
      Node(node.getElementType, leaves)
    } else {
      Leaf(node.getElementType, node.getText)
    }
  }
}
