package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.articles.Article;
import com.example.demo.model.articles.ArticleRepository;

@Controller
public class ArticleController {

	final ArticleRepository articleRepository;

	public ArticleController(ArticleRepository repo) {
		this.articleRepository = repo;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/articles")
	public ModelAndView list() {

		List<Article> articles = articleRepository.findAll();

		ModelAndView modelAndView = new ModelAndView("articles/articles");
		modelAndView.addObject("articles", articles);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/articles/add")
	public ModelAndView add(Model model) {
		model.addAttribute("article", new Article());
		ModelAndView modelAndView = new ModelAndView("articles/add");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/articles/add")
	public ModelAndView save(@ModelAttribute Article article) {
		article.setCreateDate(new Date());
		articleRepository.save(article);

		return list();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/articles/edit", params = "articleId")
	public ModelAndView edit(Model model, HttpServletRequest request) {
		Long articleid = Long.decode(request.getParameter("articleId"));

		Article article = articleRepository.findOne(articleid);
		ModelAndView modelAndView;
		if (article != null) {

			model.addAttribute("article", article);
			modelAndView = new ModelAndView("articles/add");
		}else {
		modelAndView = new ModelAndView("error");
		}
		return modelAndView;
	}
	@RequestMapping(method=RequestMethod.GET, path="/articles/delete",params="articleId")
	public ModelAndView delete( HttpServletRequest request ) {
		Long articleid = Long.decode(request.getParameter("articleId"));
		
		if(articleRepository.exists(articleid))
			articleRepository.delete( articleid );
		return list();
	}

}
