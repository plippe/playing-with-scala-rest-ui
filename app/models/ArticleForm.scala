package models

import play.api.data.Form
import play.api.data.Forms.{mapping, of}
import play.api.data.format.Formats._
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
    TitleField -> of[String].verifying(minLength(5)),
    TextField -> of[String],
  )(ArticleForm.apply)(ArticleForm.unapply))
}
