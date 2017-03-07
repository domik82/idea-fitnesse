package fitnesse.idea.fixtureclass

import com.intellij.openapi.util.text.StringUtil
import com.intellij.uiDesigner.core.config.FitNessePluginConfig

class FixtureClassResolver {

}

object FixtureClassResolver {
  private var suffix: String = FitNessePluginConfig.getInstance().getFixtureSuffix

  def clearSuffix(): Unit = {
    suffix = null
  }

  def setSuffix(newSuffix: String): Unit = {
    suffix = newSuffix
  }

  def resolve(name: String): String = {
    if (StringUtil.isEmptyOrSpaces(suffix)) {
      name
    } else {
      name + suffix
    }
  }

  def strip(name: String): String = {
    if (StringUtil.isEmptyOrSpaces(suffix)) {
      name
    }else{
      name.stripSuffix(suffix)
    }
  }

  def isMatched(name: String) : Boolean = {
    if (StringUtil.isEmptyOrSpaces(suffix)) {
      true
    }else{
      name.endsWith(suffix)
    }
  }

  def isClassNameValid(className: String) : Boolean = {
    isMatched(className) && !className.startsWith("Abstract")
  }

}
