package com.figo.web.shop;

import com.figo.entity.Item;
import com.figo.repository.ItemDao;
import com.figo.service.shop.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * Created by figo on 14/11/13.
 */
//Spring Bean的标识.
@Component("ItemPipeline")
public class ItemPipeline implements Pipeline {

    @Autowired
    private ItemDao itemDao;

    public ItemPipeline() {
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        Item item = (Item) resultItems.get("item");
        if (StringUtils.isNotEmpty(item.getItemId())) {
            itemDao.save(item);
        }
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

}
