package kuke.board.article.service;

import kuke.board.article.entity.Article;
import kuke.board.article.repository.ArticleRepository;
import kuke.board.article.service.request.ArticleCreateRequest;
import kuke.board.article.service.request.ArticleUpdateRequest;
import kuke.board.article.service.response.ArticleResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        //create Entity
        Article article = Article.create(
                snowflake.nextId(),
                request.getTitle(),
                request.getContent(),
                request.getBoardId(),
                request.getWriterId()
        );

        //save
        articleRepository.save(article);

        //return response
        return ArticleResponse.from(article);
    }


    @Transactional(readOnly = true)
    public ArticleResponse read(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        return ArticleResponse.from(article);
    }


    @Transactional
    public ArticleResponse update(Long articleId,
                                  ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());

        return ArticleResponse.from(article);
    }


    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

}
