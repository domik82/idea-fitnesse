package fitnesse.idea.fixtureclass

import java.util

import com.intellij.codeInsight.daemon.{RelatedItemLineMarkerInfo, RelatedItemLineMarkerProvider}
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.project.Project
import com.intellij.psi.{PsiClass, PsiElement, PsiIdentifier}
import fitnesse.idea.etc.SearchScope.searchScope
import fitnesse.idea.filetype.FitnesseFileType
import fitnesse.idea.fixtureclass.{FixtureClass, FixtureClassIndex}

import scala.collection.JavaConversions._

class FitNesseLineMarkerProvider extends RelatedItemLineMarkerProvider {
  override def collectNavigationMarkers(element: PsiElement, result: util.Collection[_ >: RelatedItemLineMarkerInfo[_ <: PsiElement]]): Unit = {
    element match {
      case clazz: PsiIdentifier if clazz.getParent.isInstanceOf[PsiClass] =>
        val project = element.getProject
        val fixtureClasses = findFixtureClasses(element.getProject, clazz)
        if (fixtureClasses.nonEmpty) {
          val builder = NavigationGutterIconBuilder
            .create(FitnesseFileType.FILE_ICON)
            .setTargets(fixtureClasses)
            .setTooltipText("Navigate to a FitNesse usages")
          result.add(builder.createLineMarkerInfo(element))
        }
      case _ =>
    }
  }

  def findFixtureClasses(project: Project, key: PsiIdentifier): List[FixtureClass] = {
    FixtureClassIndex.INSTANCE.get(key.getText, project, searchScope(project)).toList
  }
}
