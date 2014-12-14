package com.figo.script;

import com.figo.entity.Item;
import com.figo.repository.ItemDao;
import com.figo.service.shop.ItemService;
import com.figo.web.shop.ItemPipeline;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import org.springframework.beans.factory.BeanFactory;

/**
 * Created by figo on 14/11/2.
 */
public class EbayRepoPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Autowired
    private ItemService itemService;

    @Override
    public void process(Page page) {

        System.out.println(page.getHtml().$("h3.lvtitle a").links().all().size());
        page.addTargetRequests(page.getHtml().$("h3.lvtitle a").links().all());

        Item item = new Item();
        page.putField("title", page.getHtml().xpath("//*[@id=\"itemTitle\"]/text()").toString());
        item.setTitle(page.getHtml().xpath("//*[@id=\"itemTitle\"]/text()").toString());
        item.setItemId(page.getHtml().xpath("//*[@id=\"vi-desc-maincntr\"]/div[4]/div[2]/text()").toString());
        item.setImageUrl(page.getHtml().xpath("//*[@id='icImg']").xpath("//img//@src").toString());
        item.setLocation(page.getHtml().css("#itemLocation > div.u-flL.iti-w75 > div > div.iti-eu-bld-gry").xpath("//div/text(0)").toString());
        page.putField("itemId", page.getHtml().xpath("//*[@id=\"vi-desc-maincntr\"]/div[4]/div[2]/text()").toString());
        page.putField("imageUrl", page.getHtml().xpath("//*[@id='icImg']").xpath("//img//@src").toString());
        page.putField("location", page.getHtml().css("#itemLocation > div.u-flL.iti-w75 > div > div.iti-eu-bld-gry").xpath("//div/text(0)").toString());
        String amount = page.getHtml().xpath("//*[@id='prcIsum']/text()").toString();
        if (StringUtils.isEmpty(amount)) {
            amount = page.getHtml().xpath("//*[@id='mm-saleDscPrc']/text()").toString();
        }
        if (StringUtils.isEmpty(amount)) {
            amount = "0";
        }
        item.setPrice(amount);
        page.putField("price", amount);
        item.setMemberId(page.getHtml().xpath("//*[@class='mbg-nw']/text()").toString());

        page.putField("memberId", page.getHtml().xpath("//*[@class='mbg-nw']/text()").toString());
        page.putField("soldUrl", page.getHtml().$("span.vi-qty-pur-lnk a").links().all());

        page.putField("item", item);

//        if (StringUtils.isNotEmpty(item.getItemId())) {
//
//            System.out.println(item.toString());
//
//            itemDao.save(item);
//        }


    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new EbayRepoPageProcessor())
                //从"http://www.beibaotu.com/items"开始抓
                .addUrl("http://www.ebay.com/sch/i.html?LH_BIN=1&gbr=1&_fcid=1&_from=R40&_clu=2&_sacat=0&_nkw=Women+Chiffon+Blouse+Back+Cutout+Crew+Neck+Casual+Tops&_rdc=1")
                .addPipeline(new ConsolePipeline())
                        .addPipeline(new ItemPipeline())
//                .addPipeline(new JsonFilePipeline("/Users/figo/Documents/"))
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }


}
