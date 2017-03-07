package fitnesse.idea.fixtureclass

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Segment
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.{PsiFile, SmartPointerManager, SmartPsiElementPointer}
import fitnesse.idea.psi.PsiSuite
import org.mockito.Mockito._
import org.scalatest.Matchers

class CreateClassQuickFixTest extends PsiSuite {

  val smartPointerManager = mock[SmartPointerManager]

  test("quick fix is available") {
    val table = createTable("| foo bar |")
    val fixtureClass = table.fixtureClass.get
    when(smartPointerManager.createSmartPsiElementPointer(fixtureClass)).thenReturn(getFixtureClassPointer(fixtureClass))

    val quickFix = new CreateClassQuickFix(fixtureClass)

    assertResult(false) {
      // because the mock isInProject implementation returns False
      quickFix.isAvailable(myProject, null, null)
    }
  }

  test("test fixture name without suffix") {
    val table = createTable("| foo bar without suffix |")
    val fixtureClass = table.fixtureClass.get
    when(smartPointerManager.createSmartPsiElementPointer(fixtureClass)).thenReturn(getFixtureClassPointer(fixtureClass))
    val quickFix = new CreateClassQuickFix(fixtureClass)

    assertResult("Create class 'FooBarWithoutSuffix'") {
      quickFix.getText
    }
  }

  test("test fixture name with suffix") {
    FixtureClassResolver.setSuffix("Fixture")
    val table = createTable("| foo bar with suffix |")
    val fixtureClass = table.fixtureClass.get
    when(smartPointerManager.createSmartPsiElementPointer(fixtureClass)).thenReturn(getFixtureClassPointer(fixtureClass))
    val quickFix = new CreateClassQuickFix(fixtureClass)

    assertResult("Create class 'FooBarWithSuffixFixture'") {
      quickFix.getText
    }
    FixtureClassResolver.clearSuffix()
  }

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    myProject.getPicoContainer.registerComponentInstance(classOf[SmartPointerManager].getName, smartPointerManager)
  }

  def getFixtureClassPointer(fixtureClass: FixtureClass): SmartPsiElementPointer[FixtureClass] = {
    new SmartPsiElementPointer[FixtureClass]() {
      override def getContainingFile: PsiFile = ???

      override def getRange: Segment = ???

      override def getElement: FixtureClass = fixtureClass

      override def getProject: Project = ???

      override def getVirtualFile: VirtualFile = ???

      override def getPsiRange: Segment = ???
    }
  }
}
