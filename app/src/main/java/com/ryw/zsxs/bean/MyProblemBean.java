package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Zhao on 2017/6/25.
 */

public class MyProblemBean {

    /**
     * kc_types : 0
     * list : [{"page_i":"0","aid":"196192","kc_id":140478,"kc_name":"鍝嶅簲寮忕綉椤佃璁�","question":"afadlsfjkas","question_time":"2017-6-25 11:23:04","replay":"","replaytime":"","teacher":"璇剧▼鑰佸笀","img":"http://www.chinaplat.com/CourseImg/IMG-20161224/20161224095173197319.png","info":"涓烘彁渚涚敤鎴蜂綋楠屽害锛屾垜浠渶瑕佸埄鐢ㄥ搷搴斿紡璁捐鐨勫姛鑳戒娇寰楁垜浠殑椤甸潰閫傚簲涓嶅悓鐨勮澶囧昂瀵搞\u20ac傚湪鏈绋嬩腑鎴戜滑浼氬涔犲搷搴斿紡璁捐鏈\u20ac甯哥敤鐨勫伐鍏凤紝骞朵笖鍋氫竴浜涘疄渚嬩緵澶у鑱旂郴宸╁浐鎵\u20ac瀛︾殑鐭ヨ瘑锛岀悊璁�+瀹炶返璁╀綘蹇\u20ac熸帉鎻\u2033搷搴斿紡璁捐鏂规硶锛�","money":100}]
     */

    public int kc_types;
    public List<ListBean> list;



    public static class ListBean {
        /**
         * page_i : 0
         * aid : 196192
         * kc_id : 140478
         * kc_name : 鍝嶅簲寮忕綉椤佃璁�
         * question : afadlsfjkas
         * question_time : 2017-6-25 11:23:04
         * replay :
         * replaytime :
         * teacher : 璇剧▼鑰佸笀
         * img : http://www.chinaplat.com/CourseImg/IMG-20161224/20161224095173197319.png
         * info : 涓烘彁渚涚敤鎴蜂綋楠屽害锛屾垜浠渶瑕佸埄鐢ㄥ搷搴斿紡璁捐鐨勫姛鑳戒娇寰楁垜浠殑椤甸潰閫傚簲涓嶅悓鐨勮澶囧昂瀵搞€傚湪鏈绋嬩腑鎴戜滑浼氬涔犲搷搴斿紡璁捐鏈€甯哥敤鐨勫伐鍏凤紝骞朵笖鍋氫竴浜涘疄渚嬩緵澶у鑱旂郴宸╁浐鎵€瀛︾殑鐭ヨ瘑锛岀悊璁�+瀹炶返璁╀綘蹇€熸帉鎻″搷搴斿紡璁捐鏂规硶锛�
         * money : 100
         */

        public String page_i;
        public String aid;
        public int kc_id;
        public String kc_name;
        public String question;
        public String question_time;
        public String replay;
        public String replaytime;
        public String teacher;
        public String img;
        public String info;
        public int money;
    }
}
