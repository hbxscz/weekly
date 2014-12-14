package com.figo.web.shop;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.figo.entity.Item;
import com.figo.entity.Purchase;
import com.figo.entity.PurchaseComparator;
import com.figo.entity.User;
import com.figo.service.account.ShiroDbRealm.ShiroUser;
import com.figo.service.shop.ItemService;
import com.figo.service.shop.PurchaseService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Collections.sort;

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
@RequestMapping(value = "/item")
public class ItemController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("value", "关键词");
		sortTypes.put("sku", "SKU");
	}

	@Autowired
	private ItemService itemService;

    @Autowired
    private PurchaseService purchaseService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Item> items = itemService.getUserItem(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("items", items);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "shop/itemList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("action", "create");
		return "shop/itemForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Item newItem, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
        newItem.setUser(user);

		itemService.saveItem(newItem);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/item/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("item", itemService.getItem(id));
		model.addAttribute("action", "update");
		return "shop/itemForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        itemService.saveItem(item);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/item/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        itemService.deleteItem(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/item/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getItem(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("item", itemService.getItem(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

    @RequestMapping(value = "showPurchase/{itemId}", method = RequestMethod.GET)
    public String showPurchase(@PathVariable("itemId") String itemId, Model model) {
        List<Purchase> purchaseList = purchaseService.getPurchaseByItemId(Long.valueOf(itemId));
        PurchaseComparator comparator = new PurchaseComparator();
        Collections.sort(purchaseList, comparator);
        List<Map> data = new ArrayList<Map>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (Purchase purchase : purchaseList) {
                System.out.println("~~~~~~~~~~~~date " + purchase.getPurchaseDate());
                Map<String, Object> row = new HashMap();
                String purchaseDate = format.format(new SimpleDateFormat(
                            "MMMM-dd-yy HH:mm:ss", Locale.ENGLISH).parse(purchase.getPurchaseDate()));
                row.put("name", purchaseDate);
                row.put("y", purchase.getQty());
                data.add(row);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            model.addAttribute("data", objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "shop/showPurchase";
    }
}
