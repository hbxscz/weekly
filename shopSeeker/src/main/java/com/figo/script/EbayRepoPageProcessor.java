package com.figo.script;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by figo on 14/11/2.
 */
public class EbayRepoPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
//        page.putField("area", page.getHtml().$("div.plan h5", "text").all());
        //#main > div.plan > ul:nth-child(3) > li:nth-child(1) > a
//        page.putField("area", page.getHtml().$("div.plan ul li a", "text").all());
        ////*[@id="js-plan-list"]/dl[1]/dt/a[1]/em

        page.addTargetRequests(page.getHtml().links().regex("/items/\\w+").all());
//        System.out.println(page.getHtml().links().regex("/items/\\w+").all().size());
        page.putField("plan-name", page.getHtml().$("a.plan-name", "text").all());
        page.putField("plan-days", page.getHtml().$("span.plan-days", "text").all());
        page.putField("plan_budget", page.getHtml().$("span.plan_budget", "text").all());

        ResultItems resultItems = page.getResultItems();

System.out.println(resultItems.toString());

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new EbayRepoPageProcessor())
                //从"http://www.beibaotu.com/items"开始抓
                .addUrl("http://www.beibaotu.com/items")
                .addPipeline(new ConsolePipeline())
//                .addPipeline(new JsonFilePipeline("/Users/figo/Documents/"))
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }
}
