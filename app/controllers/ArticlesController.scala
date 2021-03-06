package controllers

import java.util.UUID
import javax.inject._
import play.api.mvc._

@Singleton
class ArticlesController @Inject()(
  val controllerComponents: MessagesControllerComponents
) extends MessagesBaseController {

  val store = collection.mutable.Map.empty[UUID, models.Article]

  def newArticle = Action { implicit request =>
    val playform = models.ArticleForm.playForm
    Ok(views.html.articles.newArticle(playform))
  }

  def editArticle(id: UUID) = Action { implicit request =>
    store.get(id)
      .fold(Redirect(routes.ArticlesController.listArticles)) { article =>
        val form = models.ArticleForm.fromModel(article)
        val playform = models.ArticleForm.playForm.fill(form)
        Ok(views.html.articles.editArticle(article, playform))
      }
  }

  def createArticle = Action { implicit request =>
    models.ArticleForm.playForm
      .bindFromRequest
      .fold({ formWithErrors =>
        BadRequest(views.html.articles.newArticle(formWithErrors))
      }, { form =>
        val model = models.Article.fromForm(form)
        store.update(model.id, model)
        Redirect(routes.ArticlesController.showArticle(model.id))
      })
  }

  def listArticles = Action {
    Ok(views.html.articles.listArticles(store.values))
  }

  def showArticle(id: UUID) = Action {
    store.get(id)
      .fold(Redirect(routes.ArticlesController.listArticles)) { article =>
        Ok(views.html.articles.showArticle(article))
      }
  }

  def updateArticle(id: UUID) = Action { implicit request =>
    store.get(id)
      .fold(Redirect(routes.ArticlesController.listArticles)) { article =>
        models.ArticleForm.playForm
          .bindFromRequest
          .fold({ formWithErrors =>
            BadRequest(views.html.articles.editArticle(article, formWithErrors))
          }, { form =>
            val model = models.Article.updated(article)(form)
            store.update(model.id, model)
            Redirect(routes.ArticlesController.showArticle(model.id))
          })
      }
  }

  def deleteArticle(id: UUID) = Action {
    store.remove(id)
    Redirect(routes.ArticlesController.listArticles)
  }

}
