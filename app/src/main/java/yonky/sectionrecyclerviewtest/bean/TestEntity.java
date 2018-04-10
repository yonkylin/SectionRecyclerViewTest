package yonky.sectionrecyclerviewtest.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class TestEntity {
    private BodyBean body;

    public BodyBean getBody(){
        return body;
    }

    public static class BodyBean{
        private List<EListBean> eList;

        public List<EListBean> geteList() {
            return eList;
        }

        public void seteList(List<EListBean> eList) {
            this.eList = eList;
        }

        public static class EListBean{
            private String picture;
            private String time;
            private String userName;
            private String content;
            private String browser;
            private List<String> ePicture;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getBrowser() {
                return browser;
            }

            public void setBrowser(String browser) {
                this.browser = browser;
            }

            public List<String> getePicture() {
                return ePicture;
            }

            public void setePicture(List<String> ePicture) {
                this.ePicture = ePicture;
            }
        }
    }

}
