package models

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.data.validation.Constraints._

case class ArticleForm(
  title: String,
  text: String
)

object ArticleForm {
  def fromModel(model: Article): ArticleForm =
    ArticleForm(
      title = model.title,
      text = model.text,
    )

  val TitleField = "title"
  val TextField = "text"

  val playForm: Form[ArticleForm] = Form(mapping(
    TitleField -> text.verifying(minLength(5)),
    TextField -> text,
  )(ArticleForm.apply)(ArticleForm.unapply))
}
