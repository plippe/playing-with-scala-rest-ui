package models

import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

case class ArticleForm(
  title: String,
  text: String
)

object ArticleForm {
  val TitleField = "title"
  val TextField = "text"

  val playForm: Form[ArticleForm] = Form(mapping(
    TitleField -> nonEmptyText,
    TextField -> nonEmptyText,
  )(ArticleForm.apply)(ArticleForm.unapply))
}
