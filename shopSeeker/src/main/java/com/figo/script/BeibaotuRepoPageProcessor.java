package com.figo.script;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by figo on 14/11/2.
 */
public class BeibaotuRepoPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return null;
    }

    public static void main(String[] args) {

        Spider.create(new BeibaotuRepoPageProcessor())
                //从"http://www.beibaotu.com/items"开始抓
                .addUrl("http://www.beibaotu.com/items")
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }
}
