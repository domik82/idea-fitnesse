package fitnesse.idea.fixtureclass

import com.intellij.uiDesigner.core.config.FitNessePluginConfig

class FixtureClassResolver {

}

object FixtureClassResolver {
  val SUFFIX: String = FitNessePluginConfig.getInstance().getFixtureSuffix

  def resolve(name: String): String = name + SUFFIX

  def strip(name: String): String = {
    if(SUFFIX.isEmpty) {
      name
    }else{
      name.stripSuffix(SUFFIX)
    }
  }

  def isMatched(name: String) : Boolean = {
    if(SUFFIX.isEmpty) {
      true
    }else{
      name.endsWith(SUFFIX)
    }
  }

}
