package models

import java.time.LocalDateTime
import java.util.UUID

case class Article(
  id: String,
  title: String,
  text: String,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
)

object Article {
  def fromForm(form: ArticleForm): Article =
    Article(
      UUID.randomUUID.toString,
      form.title,
      form.text,
      LocalDateTime.now(),
      LocalDateTime.now(),
    )

  def updated(self: Article)(form: ArticleForm): Article =
    self.copy(
      title = form.title,
      text = form.text,
      updatedAt = LocalDateTime.now(),
    )
}
