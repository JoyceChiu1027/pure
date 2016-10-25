package com.bupt.chengde.control;

public class CodeConstants {
	public static final String NAMESPACE_IN_GENERATED_REQUEST = "http://online.ws.uboss.bupt.com";
	public static final String SOAP_ACTION = "";
	public static final boolean WEBSERVICE_CALL_PRINT = true;
	public static final String RETURN_SUCCESS = "0";
	// 是否是内网开发环境
    public static boolean isIntranet = false;
    //public static final String PACKAGE_NAME = "com.bupt.chengde";
    //http://192.168.10.10:8080/cdappService/services/External2OssOnlineService?wsdl   ssh操作服务器时端口21578
    //承德IP:http://118.192.133.65:8080  域名：http://www.cd96888.com:8080
	public static final String URL_PREFIX=isIntranet ? "http://182.18.31.162:21580" :"http://www.cd96888.com:21580";

    public static final String URL_DOMIN="/cdappService";
	public static final String URL_SUFFIX="/services/External2OssOnlineService";
	public static final String WSDL_URL=URL_PREFIX+URL_DOMIN+URL_SUFFIX;

	public static final String PIC_URL_PREFIX=URL_PREFIX+"/bjcd";
	public static final String HTML_URL_PREFIX=PIC_URL_PREFIX;
	public static final String URL_RES=URL_PREFIX+URL_DOMIN;

	public static final String URL="url";
	public static final String CUST_ID="custId";
	public static final String USER_NAME="userName";
	public static final String NICK_NAEM="nickName";
	public static final String PHONE_NO="phoneNo";
	public static final String CODE="code";
	public static final String TOKEN="token";
	public static final String TYPE="type";
	public static final String TYPE_ID="typeId";
	public static final String HOME_LIST_TYPE="homeListType";
	//public static final String BUSI_TYPE="busiType";
	public static final String TITLE_NAME="titleName";
	public static final String HTML_URL_KEY="html_url";
	public static final String IMG_URL_KEY="img_url";
	public static final String TITLE_KEY="title_text";
	public static final String BRIEF_KEY="brief_text";
	public static final String MODULE_ID="module_id";
	public static final String BUSI_ID="busi_id";
	public static final String MODULE_TYPE="module_type";
	public static final String BUSI_NAME="busi_name";
	//详情页面对应的参数常量
	public static final String MAIN_AD="main_ad";
	public static final String MAIN_MENU="main_menu";
	public static final String MAIN_NEWS="main_news";
	public static final String MAIN_HOT="main_hot";
	public static final String MAIN_SALES="main_sales";
	public static final String AD_ID="ad_id";
	
	public static final String BUS_TV="bus_tv";//电视业务
	public static final String BUS_MAC="bus_mac";//宽带业务
	public static final String BUS_NEW="bus_new";//新装业务
	public static final String BUS_BREAKDOWN="bus_breakdown";
	public static final String BUS_SALES="bus_sales";//优惠活动
	public static final String BUS_ORDER="bus_order";
	public static final String BUS_ADDRESS="bus_address";
	public static final String NEWS="news";
	public static final String NEWS_TOP="news_top";
	public static final String NEWS_CD="news_cd";
	public static final String CHANNEL_PREVUE="channel_prevue";
	public static final String CHANNEL_MINE="channel_mine";
	public static final String BEN="ben";
	public static final String BEN_WEDDING="ben_wedding";
	public static final String BEN_CLEANER="ben_cleaner";
	public static final String BEN_REMOVER="ben_remover";
	public static final String BEN_REPAIR="ben_repair";
	public static final String TRAVEL="travel";
	public static final String TRAVEL_SUGGEST="travel_suggest";
	public static final String TRAVEL_TRAVEL="travel_travel";
	public static final String TRAVEL_TOGETHER="travel_together";
	public static final String TRAVEL_SCENIC="travel_scenic";
	public static final String TRAVEL_NOTES="travel_notes";
	public static final String PHOTO="photo";
	public static final String PHOTO_SUGGEST="photo_suggest";
	public static final String PHOTO_FORUM="photo_forum";
	public static final String PHOTO_APPRECIATION="photo_appreciation";
	public static final String PHOTO_STUDIO="photo_studio";
	public static final String PHOTO_PHOTOGRAPHER="photo_photographer";
	public static final String PHOTO_ALBUM="photo_album";
	public static final String PHOTO_SHARE="photo_share";
	public static final String ME_COLLECTION_BIGMODID="me_collection_bigModId";

	public static final String TAB_INDEX="tab_index";
	public static final String IS_LAZY_LOAD="is_lazy_load";
	
}
