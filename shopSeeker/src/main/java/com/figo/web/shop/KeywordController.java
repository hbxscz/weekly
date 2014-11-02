package com.figo.web.shop;

import com.figo.entity.Keyword;
import com.figo.entity.User;
import com.figo.service.account.ShiroDbRealm.ShiroUser;
import com.figo.service.shop.KeywordService;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /task/
 * Create page : GET /task/create
 * Create action : POST /task/create
 * Update page : GET /task/update/{id}
 * Update action : POST /task/update
 * Delete action : GET /task/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/keyword")
public class KeywordController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("value", "关键词");
		sortTypes.put("sku", "SKU");
	}

	@Autowired
	private KeywordService keywordService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Keyword> keywords = keywordService.getUserkeyword(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("keywords", keywords);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "shop/keywordList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("keyword", new Keyword());
		model.addAttribute("action", "create");
		return "shop/keywordForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Keyword newKeyword, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
        newKeyword.setUser(user);

		keywordService.savekeyword(newKeyword);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/keyword/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("keyword", keywordService.getkeyword(id));
		model.addAttribute("action", "update");
		return "shop/keywordForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("keyword") Keyword keyword, RedirectAttributes redirectAttributes) {
        keywordService.savekeyword(keyword);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/keyword/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        keywordService.deletekeyword(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/keyword/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getKeyword(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("keyword", keywordService.getkeyword(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
