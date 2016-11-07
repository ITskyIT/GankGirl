package com.tian.gankgirl.bean;

import java.util.List;

/**
 * Created by jiujiu on 2016/11/2.
 */
public class MainBean {
    private String error;
    private List<MainData> results;
    public static class MainData{
             /* "_id": "58193781421aa90e6f21b49f",
                "createdAt": "2016-11-02T08:46:57.726Z",
                "desc": "11-2",
                "images": [ "http://img.gank.io/b9456ed7-4b83-40a5-b5bd-f62d5483c338"],
                "publishedAt": "2016-11-02T11:49:08.835Z",
                "source": "chrome",
                "type": "\u798f\u5229",
                "url": "http://ww4.sinaimg.cn/large/610dc034jw1f9dh2ohx2vj20u011hn0r.jpg",
                "used": true,
                "who": "daimajia"*/
        private String id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private String used;
        private String who;
        private List<String> images;

        public List<String> getImages() {
            return images;
        }

        public String getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getSource() {
            return source;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public String getUsed() {
            return used;
        }

        public String getWho() {
            return who;
        }
    }

    public String getError() {
        return error;
    }

    public List<MainData> getResults() {
        return results;
    }
}
