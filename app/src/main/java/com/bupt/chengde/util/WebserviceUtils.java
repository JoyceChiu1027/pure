package com.bupt.chengde.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.CommonBaseInfo;
import com.bupt.chengde.entity.DiscussCountSignResponse;
import com.bupt.chengde.entity.PayAmountInfo;
import com.bupt.chengde.entity.PayAmountInfoQiandao;
import com.bupt.chengde.entity.ResponseAboutUsMemo;
import com.bupt.chengde.entity.ResponseAppUseGuide;
import com.bupt.chengde.entity.ResponseAppVersion;
import com.bupt.chengde.entity.ResponseBenPeo;
import com.bupt.chengde.entity.ResponseBenPeoById;
import com.bupt.chengde.entity.ResponseBusiType;
import com.bupt.chengde.entity.ResponseBusinessDtl;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.entity.ResponseChannelId;
import com.bupt.chengde.entity.ResponseCommonProblem;
import com.bupt.chengde.entity.ResponseCustLoginInfo;
import com.bupt.chengde.entity.ResponseDiscussList;
import com.bupt.chengde.entity.ResponseForumInfo;
import com.bupt.chengde.entity.ResponseActive;
import com.bupt.chengde.entity.ResponseAdPoster;
import com.bupt.chengde.entity.ResponseAppreciation;
import com.bupt.chengde.entity.ResponseGetMore;
import com.bupt.chengde.entity.ResponseJson;
import com.bupt.chengde.entity.ResponseMacBusiness;
import com.bupt.chengde.entity.ResponseMacBusinessDtl;
import com.bupt.chengde.entity.ResponseMyCollection;
import com.bupt.chengde.entity.ResponseNews;
import com.bupt.chengde.entity.ResponseNotes;
import com.bupt.chengde.entity.ResponsePayAmountInfo;
import com.bupt.chengde.entity.ResponsePhotoDetail;
import com.bupt.chengde.entity.ResponsePhotographer;
import com.bupt.chengde.entity.ResponseProgramInfo;
import com.bupt.chengde.entity.ResponseSerNewsDtl;
import com.bupt.chengde.entity.ResponseStudioInfo;
import com.bupt.chengde.entity.ResponseSuggest;
import com.bupt.chengde.entity.ResponseSuggestScenic;
import com.bupt.chengde.entity.ResponseTogether;
import com.bupt.chengde.entity.ResponseTravel;
import com.bupt.chengde.entity.ResponseTravelDetail;
import com.bupt.chengde.entity.ResponseTvBusiness;
import com.bupt.chengde.entity.ResponseValidate;
import com.bupt.chengde.entity.ResponseYytAddrInfo;
import com.bupt.chengde.ws.online.DiscussSignCountRequest;
import com.bupt.chengde.ws.online.RequestAboutUsMemo;
import com.bupt.chengde.ws.online.RequestActive;
import com.bupt.chengde.ws.online.RequestAdList;
import com.bupt.chengde.ws.online.RequestAddSign;
import com.bupt.chengde.ws.online.RequestAllDetailInfo;
import com.bupt.chengde.ws.online.RequestBenPeoById;
import com.bupt.chengde.ws.online.RequestBreakdown;
import com.bupt.chengde.ws.online.RequestBusiInfo;
import com.bupt.chengde.ws.online.RequestBusiType;
import com.bupt.chengde.ws.online.RequestBusinessDtl;
import com.bupt.chengde.ws.online.RequestBusinessInfo;
import com.bupt.chengde.ws.online.RequestCheckScore;
import com.bupt.chengde.ws.online.RequestCustLoginInfo;
import com.bupt.chengde.ws.online.RequestDiscussList;
import com.bupt.chengde.ws.online.RequestLikeStoreComment;
import com.bupt.chengde.ws.online.RequestMacBusinessDtl;
import com.bupt.chengde.ws.online.RequestModifyName;
import com.bupt.chengde.ws.online.RequestModifyPhone;
import com.bupt.chengde.ws.online.RequestModifyPwd;
import com.bupt.chengde.ws.online.RequestMyChannel;
import com.bupt.chengde.ws.online.RequestMyCollection;
import com.bupt.chengde.ws.online.RequestPerfect;
import com.bupt.chengde.ws.online.RequestProgramInfo;
import com.bupt.chengde.ws.online.RequestReservationProg;
import com.bupt.chengde.ws.online.RequestScenicInfo;
import com.bupt.chengde.ws.online.RequestRegistUser;
import com.bupt.chengde.ws.online.RequestScenicByArea;
import com.bupt.chengde.ws.online.RequestSubmitOrder;
import com.bupt.chengde.ws.online.RequestTvBusiness;
import com.bupt.chengde.ws.online.RequestTwoChannel;

/**
 * @author wyf
 * @类 :网络请求的工具类
 * @version 1.0
 */
public class WebserviceUtils {
	private static final String TAG = "WebServiceUtil";
	//http://192.168.10.10:9090
	public static String URL_PREFIX = "http://192.168.10.10:8080";
	private static String WSDL_URL = URL_PREFIX
			+ "/cdappService/services/External2OssOnlineService";
	public static String PIC_URL = URL_PREFIX + "/cdappService";

	/**
	 * 用户信息维护
	 */

	/**
	 * 获取验证码接口
	 * 
	 * @param phoneNo
	 * @return ResponseValidate
	 * @throws Exception
	 */
	public static ResponseValidate getValidate(String phoneNo,String verType) throws Exception {
		RequestRegistUser request = new RequestRegistUser();
		request.setPhoneNo(phoneNo);
		request.setVerType(verType);
		SoapObject detail = doCommon("getValidate", "getValidateRequest",
				"RequestRegistUser", request, RequestRegistUser.class);
		ResponseValidate response = new ResponseValidate();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;

	}

	/**
	 * 用户注册接口
	 * 
	 * @param userName
	 * @param phoneNo
	 * @return
	 * @throws Exception
	 */
	public static CommonBaseInfo OnlineRegist(String userName, String phoneNo,String tjPhoneNo,String verifyNo)
			throws Exception {
		Date d = new Date();
		SimpleDateFormat sd = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date= sd.format(d);
		RequestRegistUser request = new RequestRegistUser();
		request.setPhoneNo(phoneNo);
		request.setUserName(userName);
		request.setDate(date);
		request.setTjPhoneNo(tjPhoneNo);
		request.setVerifyNo(verifyNo);
		com.bupt.chengde.util.LogUtil.d(TAG, phoneNo+"||"+userName+"||"+date+"||"+tjPhoneNo);
		SoapObject detail = doCommon("OnlineRegist", "OnlineRegistRequest",
				"RequestRegistUser", request, RequestRegistUser.class);
		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;
	}

	/**
	 * 用户登录接口
	 * 
	 * @param custlName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static ResponseCustLoginInfo getCustLoginInfo(String custlName,
			String password) throws Exception {
		RequestCustLoginInfo request = new RequestCustLoginInfo();
		request.setCustlName(custlName);
		request.setCustlpwd(password);
		SoapObject detail = doCommon("getCustLoginInfo",
				"getCustLoginInfoRequest", "RequestCustLoginInfo", request,
				RequestCustLoginInfo.class);
		ResponseCustLoginInfo response = new ResponseCustLoginInfo();
		response.setCustId(Long.parseLong(detail.getProperty("custId")
				.toString()));
		response.setCustName(detail.getProperty("custName") + "");
		response.setCustPhone(detail.getProperty("custPhone") + "");
		response.setCustInt(Integer.parseInt(detail.getProperty("custInt")
				.toString()));
		response.setMemo(detail.getProperty("memo") + "");
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		response.setLastModifiedTime(detail.getProperty("lastModifiedTime") + "");
		response.setLastDiscussTime(detail.getProperty("lastDiscussTime") + "");
		response.setUrl(detail.getProperty("url") + "");
		return response;
	}

	/**
	 * 修改手机号接口
	 * 
	 * @param custId
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public static CommonBaseInfo modifyPhoneNo(String custId, String phone)
			throws Exception {
		RequestModifyPhone request = new RequestModifyPhone();
		request.setCustId(custId);
		request.setPhone(phone);
		SoapObject detail = doCommon("modifyPhoneNo", "modifyPhoneNoRequest",
				"RequestModifyPhone", request, RequestModifyPhone.class);
		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;

	}

	/**
	 * 修改用户密码
	 * 
	 * @param custId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 * @throws Exception
	 */
	public static CommonBaseInfo modifyCustPwd(String custId, String oldPwd,
			String newPwd) throws Exception {
		RequestModifyPwd request = new RequestModifyPwd();
		request.setCustId(custId);
		request.setNewPwd(newPwd);
		request.setOldPwd(oldPwd);
		SoapObject detail = doCommon("modifyCustPwd", "modifyCustPwdRequest",
				"RequestModifyPwd", request, RequestModifyPwd.class);
		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;
	}

	/**
	 * 修改用户昵称
	 * 
	 * @param custId
	 * @param oldCustName
	 * @param newCustName
	 * @return
	 * @throws Exception
	 */
	public static CommonBaseInfo modifyCustName(String custId, String oldCustName,
			String newCustName) throws Exception {
		RequestModifyName request = new RequestModifyName();
		request.setCustId(custId);
		request.setNewCustName(newCustName);
		request.setOldCustName(oldCustName);
		SoapObject detail = doCommon("modifyCustName", "modifyCustNameRequest",
				"RequestModifyName", request, RequestModifyName.class);
		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;
	}
	
	/**
	 * 重置密码接口
	 * 
	 * @param phoneNo
	 * @return
	 * @throws Exception
	 */
	public static CommonBaseInfo custFindPwd(String phoneNo,String verifyNo) throws Exception {
		RequestRegistUser request = new RequestRegistUser();
		request.setPhoneNo(phoneNo);
		request.setVerifyNo(verifyNo);
		
		SoapObject detail = doCommon("custFindPwd", "custFindPwdRequest",
				"RequestRegistUser", request, RequestRegistUser.class);
		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;
	}

	/**
	 * 用户签到接口
	 * 
	 * @param custId
	 * @param signDate
	 * @return
	 * @throws Exception
	 */
	public static PayAmountInfoQiandao addSignInfo(int custId, String signDate)
			throws Exception {
		RequestAddSign request = new RequestAddSign();
		request.setCustId(custId);
		request.setSignDate(signDate);
		SoapObject detail = doCommon("addSignInfo", "addSignInfoRequest",
				"RequestAddSign", request, RequestAddSign.class);
		PayAmountInfoQiandao response = new PayAmountInfoQiandao();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		response.setLatestCount(detail.getProperty("latestCount") + "");
		response.setCount(detail.getProperty("count") + "");
		return response;
	}
	
	/**
	 * 用户评论加积分接口
	 * 
	 * @param custId
	 * @param signDate
	 * @return
	 * @throws Exception
	 */
	public static DiscussCountSignResponse addDiscussCount (int custId, String addDiscussCount )
			throws Exception {
		DiscussSignCountRequest request = new DiscussSignCountRequest();
		request.setCustId(custId);
		request.setDiscussTime(addDiscussCount);
		SoapObject detail = doCommon("addDiscussCount", "addDiscussCountRequest",
				"DiscussSignCountRequest", request, DiscussSignCountRequest.class);
		DiscussCountSignResponse response = new DiscussCountSignResponse();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		response.setLatestCount(detail.getProperty("latestCount") + "");
		response.setCount(detail.getProperty("count") + "");
		return response;
	}
	
	/**
	 * 查看我的积分
	 * @param custId 	客户编号
	 * @return
	 * @throws Exception
	 */
	public static String checkScoreFunction(int custId)
			throws Exception {
		RequestCheckScore request = new RequestCheckScore();
		request.setCustId(custId);
		SoapObject detail = doCommon("checkScoreFunction", "checkScoreFunctionRequest",
				"RequestCheckScore", request, RequestCheckScore.class);
		return detail.getProperty("score")+"";
	}
	/**
	 * litf
	 * 获取我的收藏列表 
	 * @param custId 客户ID
	 * @param bigModId bigModId
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseMyCollection> getMyCollection(String custId,String bigModId)
			throws Exception {
		List<ResponseMyCollection> returnList = new ArrayList<ResponseMyCollection>();
		RequestMyCollection  rsq = new RequestMyCollection ();
		rsq.setCustId(custId);
		rsq.setBigModId(bigModId);
		SoapObject detail = doCommon("getMyCollection", "getMyCollectionRequest",
				"RequestMyCollection", rsq, RequestMyCollection.class);
		int count = detail.getPropertyCount();
		System.out.println("count=============" + count);

		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseMyCollection response = new ResponseMyCollection();
			response.setBusId(pii.getProperty("busId") + "");
			response.setBusName(pii.getProperty("busName") + "");
			response.setBusPicUrl(pii.getProperty("busPicUrl") + "");
			response.setModType(pii.getProperty("modType") + "");
			returnList.add(response);
		}
		return returnList;

	}
	/**
	 * 业务信息数据
	 */

	/**
	 * 首页数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ResponseJson getMainPageInfo() throws Exception {
		SoapObject request = new SoapObject(
				CodeConstants.NAMESPACE_IN_GENERATED_REQUEST, "getMainPageInfo");
		SoapObject detail = doCommonBase(request);
		String json = detail.getProperty("json") + "";
		ResponseJson response = new ResponseJson();
		response.setJson(json);
		return response;
	}

	/**
	 * 主页显示更多合并接口
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */

	public static List<ResponseGetMore> getMoreUnique(int type, int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setType(type);
		request.setPage(page);
		/*
		 * RequestBusiType request=new RequestBusiType();
		 * request.setBusiType(busiType)
		 */
		SoapObject detail = doCommon("getMoreUnique", "getMoreUniqueRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseGetMore> returnList = new ArrayList<ResponseGetMore>();
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseGetMore response = new ResponseGetMore();
			response.setMoreId(Integer.parseInt(pii.getProperty("moreId") + ""));
			response.setMoreTitle(pii.getProperty("moreName") + "");
			response.setMorePosterUrl(pii.getProperty("morePosterUrl") + "");
			response.setMoreDate(pii.getProperty("moreDate") + "");
			response.setMoreContent(pii.getProperty("moreContent") + "");
			returnList.add(response);
		}

		return returnList;
	}

	/**
	 * 推荐、热门、每日惠详情页面
	 * 
	 * @author zhaojie
	 * @param custId
	 * @param detId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static ResponseTravelDetail getMainDetailInfo(String custId,
			String modId, String busiId) throws Exception {
		RequestAllDetailInfo  request = new RequestAllDetailInfo ();
		request.setCustId(custId);
		request.setBusiId(busiId);
		request.setModType(modId);
		SoapObject detail = doCommon("getMainDetailInfo",
				"getMainDetailInfoRequest", "RequestAllDetailInfo", request,
				RequestAllDetailInfo.class);

		ResponseTravelDetail response = new ResponseTravelDetail();
		response.setIsLike(Utils.parseStringToInt(detail.getProperty("isLike")
				+ ""));
		response.setIsStore(Utils.parseStringToInt(detail
				.getProperty("isStore") + ""));
		response.setUrl(detail.getProperty("url") + "");
		response.setReturnCode(Utils.parseStringToInt(detail
				.getProperty("returnCode") + ""));
		response.setReturnMessage(detail.getProperty("returnMessage") + "");		
		return response;
	}

	/**
	 * 广告轮播图
	 * 
	 * @author zhaojie
	 * @param adKind广告类型，1大广告，2小广告
	 * @param adBelong广告所属模块
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseAdPoster> getAdPosterInfo(int adKind,
			String adBelong) throws Exception {
		RequestAdList request = new RequestAdList();
		request.setAdKind(adKind);
		request.setAdBelong(adBelong);
		SoapObject detail = doCommon("getAdPosterInfo",
				"getAdPosterInfoRequest", "RequestAdList", request,
				RequestAdList.class);
		int count = detail.getPropertyCount();
		List<ResponseAdPoster> returnList = new ArrayList<ResponseAdPoster>();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseAdPoster response = new ResponseAdPoster();
			response.setAdId(Utils.parseStringToInt(pi.getProperty("adId")
					+ ""));
			response.setAdPicUrl(pi.getProperty("adPicUrl") + "");
			response.setAdUrl(pi.getProperty("adUrl") + "");
			returnList.add(response);
		}
		System.out.println(returnList.toString());
		return returnList;
	}
	
	/***************
	 * 广告详情接口
	 * @date:2016-4-19 下午2:52:44
	 * @author:litf
	 * @param benPeoId 广告ID
	 * @version:
	 * @return:ResponseAdPoster 对象
	 * @throws:
	 ***************/
	public static ResponseAdPoster getAdDetail(int benPeoId)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setBenPeoId(benPeoId);
		SoapObject detail = doCommon("getAdDetail", "getAdDetailRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		ResponseAdPoster response = new ResponseAdPoster();
		response.setAdUrl(detail.getProperty("adUrl")+"");
		return response;
	}

	/**
	 * 便民模块列表统一接口
	 * 
	 * @author zhaojie
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseBenPeo> getBenInfo(int type, int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setType(type);
		request.setPage(page);
		SoapObject detail = doCommon("getBenInfo", "getBenInfoRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseBenPeo> returnList = new ArrayList<ResponseBenPeo>();
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseBenPeo response = new ResponseBenPeo();
			response.setBenPeoId(Utils.parseStringToInt(pii.getProperty("benPeoId")+""));
			response.setBenPeoName(pii.getProperty("benPeoName") + "");
			response.setBenPeoPhone(pii.getProperty("benPeoPhone") + "");
			response.setBenPeoAddr(pii.getProperty("benPeoAddr") + "");
			response.setBenPeoUrl(pii.getProperty("benPeoUrl") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 便民模块统一接口详细信息显示
	 * 
	 * @author zhaojie
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static ResponseBenPeoById getBenPeoById(String custid,String busiId, String type)
			throws Exception {
		RequestAllDetailInfo  request = new RequestAllDetailInfo ();
		request.setBusiId(busiId);
		request.setModType(type);
		request.setCustId(custid);
		SoapObject detail = doCommon("getBenPeoById", "getBenPeoByIdRequest",
				"RequestAllDetailInfo", request, RequestAllDetailInfo.class);
		ResponseBenPeoById response = new ResponseBenPeoById();
		response.setBenPeoId(Utils.parseStringToInt(detail.getProperty("benPeoId")+""));
		response.setBenPeoAddr(detail.getProperty("benPeoAddr") + "");
		response.setBenPeoPhone(detail.getProperty("benPeoPhone") + "");
		response.setDetailUrl(detail.getProperty("detailUrl") + "");
		response.setIsLike(Utils.parseStringToInt(detail.getProperty("isLike")+""));
		response.setIsStore(Utils.parseStringToInt(detail.getProperty("isStore")+""));
		response.setReturnCode(Utils.parseStringToInt(detail
				.getProperty("returnCode") + ""));
		response.setReturnMessage(detail.getProperty("returnMessage") + "");
		return response;
	}

	/**
	 * 新闻类列表
	 * 
	 * @author zhaojie
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseNews> getNewsInfo(String modType, int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setModType(modType);
		request.setPage(page);
		SoapObject detail = doCommon("getNewsInfo", "getNewsInfoRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseNews> returnList = new ArrayList<ResponseNews>();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseNews response = new ResponseNews();
			response.setNewsDate(pi.getProperty("newsDate") + "");
			response.setNewsName(pi.getProperty("newsName") + "");
			response.setNewsPosterUrl(pi.getProperty("newsPosterUrl") + "");
			response.setNewsId(pi.getProperty("newsId") + "");
			response.setContent(pi.getProperty("content")+"");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 新闻详情页
	 * 
	 * @author litf
	 * @param custId
	 * @param newsId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static ResponseSerNewsDtl getNewsDetail(String busiId,
			String custId, String modType) throws Exception {
		RequestAllDetailInfo  request = new RequestAllDetailInfo ();
		request.setBusiId(busiId);
		request.setCustId(custId);
		request.setModType(modType);
		SoapObject detail = doCommon("getNewsDetail", "getNewsDetailRequest",
				"RequestAllDetailInfo", request, RequestAllDetailInfo.class);
		ResponseSerNewsDtl response = new ResponseSerNewsDtl();
		response.setUrl(detail.getProperty("url") + "");
		response.setIsLike(Utils.parseStringToInt(detail.getProperty("isLike")
				+ ""));
		response.setIsStore(Utils.parseStringToInt(detail
				.getProperty("isStore") + ""));
		response.setReturnCode(Utils.parseStringToInt(detail
				.getProperty("returnCode") + ""));
		response.setReturnMessage(detail.getProperty("returnMessage") + "");
		return response;
	}

	/**
	 * 旅游推荐列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseSuggest> getSuggestInfo(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getSuggestInfo", "getSuggestInfoRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseSuggest> returnList = new ArrayList<ResponseSuggest>();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseSuggest response = new ResponseSuggest();
			response.setSuggestId(pi.getProperty("suggestId") + "");
			response.setPosterurl(pi.getProperty("posterurl") + "");
			response.setSuggestBusid(pi.getProperty("suggestBusid") + "");
			response.setSuggestModid(pi.getProperty("suggestModid") + "");
			response.setSuggestName(pi.getProperty("suggestName") + "");
			response.setSuggestTitle(pi.getProperty("suggestTitle") + "");
			returnList.add(response);
		}

		return returnList;
	}

	/**
	 * 旅游旅行社列表
	 * 
	 * @author zhaojie
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseTravel> getTravelInfo(int page) throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getTravelInfo", "getTravelInfoRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseTravel> returnList = new ArrayList<ResponseTravel>();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseTravel response = new ResponseTravel();
			response.setTravelId(pi.getProperty("travelId") + "");
			response.setTravelName(pi.getProperty("travelName") + "");
			response.setTravelUrl(pi.getProperty("travelUrl") + "");

			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 拼团列表
	 * 
	 * @author zhaojie
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseTogether> getTogetherInfo(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getTogetherInfo",
				"getTogetherInfoRequest", "RequestBenPeoById", request,
				RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseTogether> returnList = new ArrayList<ResponseTogether>();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseTogether response = new ResponseTogether();
			response.setTogetherId(pi.getProperty("togetherId") + "");
			response.setTogetherIntro(pi.getProperty("togetherIntro") + "");
			response.setTogetherDate(pi.getProperty("togetherDate") + "");
			response.setTogetherPersonName(pi.getProperty("togetherPersonName")
					+ "");
			response.setTogetherPersonurl(pi.getProperty("togetherPersonurl")
					+ "");
			response.setTogetherTitle(pi.getProperty("togetherTitle") + "");
			response.setTogetherPersonCount(pi.getProperty("togetherPersonCount")+"");
			returnList.add(response);
		}

		return returnList;
	}

	/**
	 * 旅游景点列表
	 * 
	 * @author zhaojie
	 * @return
	 * @throws Exception
	 */

	public static List<ResponseSuggestScenic> getSuggestScenicInfo(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getSuggestScenicInfo",
				"getSuggestScenicInfoRequest", "RequestBenPeoById", request,
				RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseSuggestScenic> returnList = new ArrayList<ResponseSuggestScenic>();

		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseSuggestScenic response = new ResponseSuggestScenic();
			response.setScenicId(pi.getProperty("scenicId") + "");
			response.setScenicAdurl(pi.getProperty("scenicAdurl") + "");
			response.setScenicName(pi.getProperty("scenicName") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 单个地区下对应的景点信息列表接口
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseSuggestScenic> getScenicByArea(int areaID,
			int page) throws Exception {
		RequestScenicByArea rsq = new RequestScenicByArea();
		rsq.setAreaID(areaID);
		rsq.setPage(page);
		SoapObject detail = doCommon("getScenicByArea",
				"getScenicByAreaRequest", "RequestScenicByArea", rsq,
				RequestScenicByArea.class);
		int count = detail.getPropertyCount();
		List<ResponseSuggestScenic> returnList = new ArrayList<ResponseSuggestScenic>();

		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseSuggestScenic response = new ResponseSuggestScenic();
			response.setScenicId(pi.getProperty("scenicId") + "");
			response.setScenicAdurl(pi.getProperty("scenicAdurl") + "");
			response.setScenicName(pi.getProperty("scenicName") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 单个景点的概况信息接口
	 * 
	 * @author zhaojie
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ResponseSuggestScenic getScenicInfo(int scenicId, int custId)
			throws Exception {
		RequestScenicInfo rsq = new RequestScenicInfo();
		rsq.setScenicId(scenicId);
		rsq.setCustId(custId);
		SoapObject detail = doCommon("getScenicInfo", "getScenicInfoRequest",
				"RequestScenicInfo", rsq, RequestScenicInfo.class);
		ResponseSuggestScenic response = new ResponseSuggestScenic();
		response.setDetailUrl(detail.getProperty("detailUrl") + "");
		response.setIsLike(detail.getProperty("isLike") + "");
		response.setIsStore(detail.getProperty("isStore") + "");
		return response;
	}

	/**
	 * 旅游游记列表
	 * 
	 * @author litf
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseNotes> getNotesInfo(int page) throws Exception {
		RequestBenPeoById rsq = new RequestBenPeoById();
		rsq.setPage(page);
		SoapObject detail = doCommon("getNotesInfo", "getNotesInfoRequest",
				"RequestBenPeoById", rsq, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		List<ResponseNotes> returnList = new ArrayList<ResponseNotes>();

		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseNotes response = new ResponseNotes();
			response.setNotePersonurl(pi.getProperty("notePersonurl") + "");
			response.setNoteId(pi.getProperty("noteId") + "");
			response.setNoteTitle(pi.getProperty("noteTitle") + "");
			response.setNoteIntro(pi.getProperty("noteIntro") + "");
			response.setNoteDate(pi.getProperty("noteDate") + "");
			response.setNotePersonname(pi.getProperty("notePersonname") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 旅游模块详情页接口（现暂时是所有旅游模块都是一个详情接口）
	 * 
	 * @author litf
	 * @param custId
	 * @param busiId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static ResponseTravelDetail getTravelDetail(String busiId, String custId,
			String modType) throws Exception {
		RequestAllDetailInfo  request = new RequestAllDetailInfo ();
		request.setBusiId(busiId);
		request.setCustId(custId);
		request.setModType(modType);
		SoapObject detail = doCommon("getTravelDetail",
				"getTravelDetailRequest", "RequestAllDetailInfo", request,
				RequestAllDetailInfo.class);
		ResponseTravelDetail response = new ResponseTravelDetail();
		response.setUrl(detail.getProperty("url") + "");
		//response.setContent(detail.getProperty("content")+"");
		response.setIsLike(Integer.parseInt(detail.getProperty("isLike") + ""));
		response.setIsStore(Integer.parseInt(detail.getProperty("isStore") + ""));
		response.setReturnCode(Utils.parseStringToInt(detail
				.getProperty("returnCode") + ""));
		response.setReturnMessage(detail.getProperty("returnMessage") + "");
		return response;
	}

	/**
	 * 摄影模块推荐列表
	 * 
	 * @author litf
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseSuggest> getSuggestPictureInfo(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getSuggestPictureInfo",
				"getSuggestPictureInfoRequest", "RequestBenPeoById", request,
				RequestBenPeoById.class);
		List<ResponseSuggest> returnList = new ArrayList<ResponseSuggest>();
		int count = detail.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseSuggest response = new ResponseSuggest();
			response.setSuggestId(pi.getProperty("suggestId") + "");
			response.setSuggestModid(pi.getProperty("suggestModid") + "");
			response.setSuggestBusid(pi.getProperty("suggestBusid") + "");
			response.setPosterurl(pi.getProperty("posterurl") + "");
			response.setSuggestTitle(pi.getProperty("suggestTitle") + "");
			response.setSuggestName(pi.getProperty("suggestName") + "");
			returnList.add(response);
		}

		return returnList;
	}

	/**
	 * 摄影模块论坛列表
	 * 
	 * @author litf
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseForumInfo> getForumInfo(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getForumInfo", "getForumInfoRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		List<ResponseForumInfo> returnList = new ArrayList<ResponseForumInfo>();
		int count = detail.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseForumInfo response = new ResponseForumInfo();
			response.setCardId(pi.getProperty("cardId") + "");
			response.setCardTitle(pi.getProperty("cardTitle") + "");
			//response.setCardContent(pi.getProperty("cardContent") + "");
			response.setCardDate(pi.getProperty("cardDate") + "");
			response.setCardPersonurl(pi.getProperty("cardPersonurl") + "");
			response.setCardPersonname(pi.getProperty("cardPersonname") + "");
			returnList.add(response);
		}

		return returnList;
	}

	/***************
	 * 摄影模块--赏析列表
	 * 
	 * @date:2016-4-12 下午2:13:17
	 * @author:litf
	 * @version:
	 * @return:
	 * @throws:
	 ***************/
	public static List<ResponseAppreciation> getAppreciationList(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getAppreciationList",
				"getAppreciationListRequest", "RequestBenPeoById", request,
				RequestBenPeoById.class);
		List<ResponseAppreciation> returnList = new ArrayList<ResponseAppreciation>();
		int count = detail.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseAppreciation response = new ResponseAppreciation();
			response.setAprePicUrl(pi.getProperty("aprePicUrl") + "");
			response.setApreName(pi.getProperty("apreName") + "");
			//response.setApreInto(pi.getProperty("apreInto") + "");
			response.setApreId(pi.getProperty("apreId") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 摄影模块影楼列表
	 * 
	 * @date:2016-4-7 下午6:03:28
	 * @author:litf
	 * @version:
	 * @return:
	 * @throws:Exception
	 * */
	public static List<ResponseStudioInfo> getStudioInfo(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		SoapObject detail = doCommon("getStudioInfo", "getStudioInfoRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		List<ResponseStudioInfo> returnList = new ArrayList<ResponseStudioInfo>();
		int count = detail.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseStudioInfo response = new ResponseStudioInfo();
			response.setStudioId(Integer.parseInt(pi.getProperty("studioId")
					+ ""));
			response.setStudioName(pi.getProperty("studioName") + "");
			response.setStudioUrl(pi.getProperty("studioUrl") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 摄影模块下摄友列表
	 * 
	 * @author zhaojie
	 * @return
	 * @throws Exception
	 */
	public static ResponsePhotographer getPhotographerInfo()
			throws Exception {
		/*RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(1);
		SoapObject detail = doCommon("getPhotographerInfo",
				"getPhotographerInfoRequest", "RequestBenPeoById", request,
				RequestBenPeoById.class);*/
		SoapObject request=new SoapObject(CodeConstants.NAMESPACE_IN_GENERATED_REQUEST, "getPhotographerInfo");
		SoapObject detail=doCommonBase(request);
		ResponsePhotographer response=new ResponsePhotographer();
		response.setJson(detail.getProperty("json")+"");
		return response;
	}

	/**
	 * 摄影详情界面接口
	 * 
	 * @author litf
	 * @param custId
	 * @param type
	 *            Type : 1 推荐 2 论坛 3 赏析 4 影楼 5 摄友
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public static ResponsePhotoDetail getPhotoDetail(String busiId, String custId,
			String modType) throws Exception {
		RequestAllDetailInfo  request = new RequestAllDetailInfo ();
		request.setBusiId(busiId);
		request.setCustId(custId);
		request.setModType(modType);
		SoapObject detail = doCommon("getPhotoDetail", "getPhotoDetailRequest",
				"RequestAllDetailInfo", request, RequestAllDetailInfo.class);
		ResponsePhotoDetail response = new ResponsePhotoDetail();
		response.setUrl(detail.getProperty("url") + "");
		response.setIsLike(Integer.parseInt(detail.getProperty("isLike") + ""));
		response.setIsStore(Integer.parseInt(detail.getProperty("isStore") + ""));
		response.setReturnCode(Utils.parseStringToInt(detail
				.getProperty("returnCode") + ""));
		response.setReturnMessage(detail.getProperty("returnMessage") + "");
		
		return response;
	}

	/**
	 * 得到相应的评论列表
	 * 
	 * @author zhaojie
	 * @param modId
	 * @param busiId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseDiscussList> getDiscussList(String modId,
			String busiId, int page) throws Exception {
		RequestDiscussList request = new RequestDiscussList();
		request.setModId(modId);
		request.setDelId(busiId);
		request.setPage(page);
		SoapObject detail = doCommon("getDiscussList", "getDiscussListRequest","RequestDiscussList", request, RequestDiscussList.class);
		int count =detail.getPropertyCount();
		List<ResponseDiscussList> returnList = new ArrayList<ResponseDiscussList>();
		for (int i = 0; i < count; i++) {
			SoapObject pi = (SoapObject) detail.getProperty(i);
			ResponseDiscussList response = new ResponseDiscussList();
			response.setDiscussId(pi.getProperty("discussId") + "");
			response.setDiscussCustId(pi.getProperty("discussCustId") + "");
			response.setContent(pi.getProperty("content") + "");
			response.setDiscussDate(pi.getProperty("discussDate")+"");
			response.setNickName(pi.getProperty("custName")+"");
			response.setPhoneNo(pi.getProperty("custPhone")+"");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 点赞，收藏，评论接口
	 * 
	 * @author zhaojie
	 * @param custName
	 * @param custId
	 * @param modId
	 * @param busiId
	 * @param type
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static CommonBaseInfo likeStoreFunction(String custName,
			String custId, String modId, String busiId, int type, String content)
			throws Exception {
		RequestLikeStoreComment request = new RequestLikeStoreComment();
		request.setCustId(custId);
		request.setCustName(custName);
		request.setType(type);
		request.setModId(modId);
		request.setBusiId(busiId);
		request.setContent(content);
		SoapObject detail = doCommon("likeStoreFunction",
				"likeStoreFunctionRequest", "RequestLikeStoreComment", request,
				RequestLikeStoreComment.class);

		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;
	}

	public static CommonBaseInfo cancleLikeStroe(String custId, String modId,
			String busiId, int type) throws Exception {
    	RequestLikeStoreComment request = new RequestLikeStoreComment();
		request.setCustId(custId);
		request.setType(type);
		request.setModId(modId);
		request.setBusiId(busiId);

		SoapObject detail = doCommon("cancleLikeStroe",
				"cancleLikeStroeRequest", "RequestLikeStoreComment", request,
				RequestLikeStoreComment.class);
		CommonBaseInfo response = new CommonBaseInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		return response;
    }
	/**
	 * 得到业务列表信息
	 * 
	 * @param busiType
	 *            类型 1 电视 2 宽带 3 新装
	 * @param page
	 *            页数
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseBusinessInfo> getBusinessInfo(String typeId,int busiType,
			int page) throws Exception {
		List<ResponseBusinessInfo> returnList = new ArrayList<ResponseBusinessInfo>();
		RequestBusinessInfo rsq = new RequestBusinessInfo();
		rsq.setType(busiType);
		rsq.setPage(page);
		rsq.setTypeId(typeId);
		SoapObject detail = doCommon("getBusinessInfo",
				"getBusinessInfoRequest", "RequestBusinessInfo", rsq,
				RequestBusinessInfo.class);
		int count = detail.getPropertyCount();
		System.out.println("===========" + count);
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseBusinessInfo pi = new ResponseBusinessInfo();
			pi.setBusId(pii.getProperty("busId") + "");
			pi.setBusPicUrl(pii.getProperty("busPicUrl") + "");
			pi.setBusTitle(pii.getProperty("busTitle") + "");
			pi.setBusContent(pii.getProperty("busContent") + "");
			pi.setBusFee(pii.getProperty("busFee") + "");
			pi.setBusLimitTime(pii.getProperty("busLimitTime") + "");
			returnList.add(pi);
		}
		return returnList;
	}

	/**
	 * 优惠活动列表显示
	 * 
	 * @param page
	 *            页数
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseBusinessInfo> getSalesActive(int page)
			throws Exception {
		List<ResponseBusinessInfo> returnList = new ArrayList<ResponseBusinessInfo>();
		RequestBenPeoById rsq = new RequestBenPeoById();
		rsq.setPage(page);
		SoapObject detail = doCommon("getSalesActive", "getSalesActiveRequest",
				"RequestBenPeoById ", rsq, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		System.out.println("===========" + count);
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseBusinessInfo pi = new ResponseBusinessInfo();
			pi.setBusId(pii.getProperty("activeId") + "");
			pi.setBusTitle(pii.getProperty("activeTitle") + "");
			pi.setBusPicUrl(pii.getProperty("activePosterUrl") + "");
			pi.setBusContent(pii.getProperty("activeDate") + "");
			returnList.add(pi);
		}
		return returnList;
	}
	/**
	 * 优惠活动详细信息
	 * 
	 * @param activeId
	 * @return
	 * @throws Exception
	 */
	public static ResponseActive getActiveById(int activeId) throws Exception {
		RequestActive rsq = new RequestActive();
		rsq.setActiveId(activeId);
		SoapObject detail = doCommon("getActiveById", "getActiveByIdRequest",
				"RequestActive ", rsq, RequestActive.class);
		ResponseActive responseActive = new ResponseActive();
		responseActive.setActiveContent(detail.getProperty("activeContent")
				+ "");
		responseActive.setActiveDate(detail.getProperty("activeDate") + "");
		responseActive.setActiveId(detail.getProperty("activeId") + "");
		responseActive.setActivePrice(detail.getProperty("activeLimittime")
				+ "");
		responseActive.setActivePosterUrl(detail.getProperty("activePosterUrl")
				+ "");
		responseActive.setActiveStatus(detail.getProperty("activeStatus") + "");
		responseActive.setActiveTitle(detail.getProperty("activeTitle") + "");
		return responseActive;
	}

	/**
	 * 获取电视,宽带业务种类
	 * 
	 * @param busiType
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseBusiType> getBusiType(String busiType)
			throws Exception {
		List<ResponseBusiType> returnList = new ArrayList<ResponseBusiType>();
		RequestBusiType rsq = new RequestBusiType();

		rsq.setBusiType(busiType);

		SoapObject detail = doCommon("getBusiType", "getBusiTypeRequest",
				"RequestBusiType", rsq, RequestBusiType.class);
		int count = detail.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseBusiType pi = new ResponseBusiType();
			pi.setTypeName(pii.getProperty("typeName") + "");
			pi.setTypeId(pii.getProperty("typeId")+"");
			returnList.add(pi);
		}

		return returnList;

	}

	/**
	 * 得到电视业务列表
	 * 
	 * @param favType
	 * @param feeType
	 * @param tvType
	 * @param proType
	 * @param showType
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseTvBusiness> getTVBusiness(String favType,
			String feeType, String tvType, String proType, String showType)
			throws Exception {

		List<ResponseTvBusiness> returnList = new ArrayList<ResponseTvBusiness>();
		RequestTvBusiness rsq = new RequestTvBusiness();

		rsq.setFavType(favType);
		rsq.setFeeType(feeType);
		rsq.setTvType(tvType);
		rsq.setProType(proType);
		rsq.setShowType(showType);

		SoapObject detail = doCommon("getTVBusiness", "getTVBusinessRequest",
				"RequestTvBusiness", rsq, RequestTvBusiness.class);

		int count = detail.getPropertyCount();

		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseTvBusiness pi = new ResponseTvBusiness();
			pi.setTbTitle(pii.getProperty("tbTitle") + "");
			pi.setTbPicUrl(pii.getProperty("tbPicUrl") + "");
			pi.setTvbId(pii.getProperty("tvbId") + "");
			pi.setSubContent(pii.getProperty("subContent") + "");
			returnList.add(pi);
		}

		return returnList;

	}

	/**
	 * 电视业务详情
	 * 
	 * @param tvbId
	 * @return
	 * @throws Exception
	 */
	public static ResponseBusinessDtl getTVBusinessDtl(String tvbId)
			throws Exception {
		ResponseBusinessDtl pai = new ResponseBusinessDtl();
		RequestBusinessDtl rsq = new RequestBusinessDtl();

		rsq.setTvbId(tvbId);

		SoapObject detail = doCommon("getTVBusinessDtl",
				"getTVBusinessDtlRequest", "RequestBusinessDtl", rsq,
				RequestBusinessDtl.class);
		pai.setTbTitle(detail.getProperty("tbTitle") + "");
		pai.setTbContent(detail.getProperty("tbContent") + "");
		pai.setTbPicUrl(detail.getProperty("tbPicUrl") + "");
		pai.setTbMvUrl(detail.getProperty("tbMvUrl") + "");
		pai.setTbLevel(detail.getProperty("tbLevel") + "");
		pai.setFee(detail.getProperty("fee") + "");
		pai.setReturnCode(detail.getProperty("returnCode") + "");
		pai.setReturnMsg(detail.getProperty("returnMsg") + "");
		return pai;

	}

	/**
	 * 得到宽带业务列表
	 * 
	 * @param proType
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseMacBusiness> getMacBusiness(String proType)
			throws Exception {
		List<ResponseMacBusiness> returnList = new ArrayList<ResponseMacBusiness>();
		RequestBusiInfo rsq = new RequestBusiInfo();
		rsq.setBusiType(proType);
		SoapObject detail = doCommon("getMacBusiness", "getMacBusinessRequest",
				"RequestBusiInfo", rsq, RequestBusiInfo.class);

		int count = detail.getPropertyCount();
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseMacBusiness pi = new ResponseMacBusiness();
			if (!(pii.getProperty("returnMsg") == null)
					|| !((pii.getProperty("returnCode") + "")
							.equals(CodeConstants.RETURN_SUCCESS))) {
				break;
			}
			pi.setMbTitle(pii.getProperty("mbTitle") + "");
			pi.setMbId(pii.getProperty("mbId") + "");
			pi.setMbPicUrl(pii.getProperty("mbPicUrl") + "");
			pi.setFee(pii.getProperty("fee") + "");
			pi.setLimitTime(pii.getProperty("limitTime") + "");
			pi.setBandWidth(pii.getProperty("bandWidth") + "");
			pi.setLevel(pii.getProperty("level") + "");
			pi.setSaleFee(pii.getProperty("saleFee") + "");
			pi.setSubContent(pii.getProperty("subContent") + "");
			pi.setProType(pii.getProperty("proType") + "");
			pi.setReturnCode(pii.getProperty("returnCode") + "");
			pi.setReturnMsg(pii.getProperty("returnMsg") + "");
			returnList.add(pi);
		}
		return returnList;

	}

	/**
	 * 宽带业务详情
	 * 
	 * @param mbId
	 * @return
	 * @throws Exception
	 */
	public static ResponseMacBusinessDtl getMacBusinessDtl(String mbId)
			throws Exception {
		ResponseMacBusinessDtl pai = new ResponseMacBusinessDtl();
		RequestMacBusinessDtl rsq = new RequestMacBusinessDtl();

		rsq.setMbId(mbId);

		SoapObject detail = doCommon("getMacBusinessDtl",
				"getMacBusinessDtlRequest", "RequestMacBusinessDtl", rsq,
				RequestMacBusinessDtl.class);

		pai.setMbTitle(detail.getProperty("mbTitle") + "");
		pai.setMbPicUrll(detail.getProperty("mbPicUrl") + "");
		pai.setFee(detail.getProperty("fee") + "");
		pai.setMbId(detail.getProperty("mbId") + "");
		pai.setMbContent(detail.getProperty("mbContent") + "");
		pai.setReturnCode(detail.getProperty("returnCode") + "");
		pai.setReturnMsg(detail.getProperty("returnMsg") + "");

		return pai;

	}

	/**
	 * 业务预定接口
	 * 
	 * @param custName
	 *            客户名
	 * @param phoneNo
	 *            手机号码
	 * @param address
	 *            客户地址
	 * @param orderType
	 *            产品类型
	 * @param orderBusId
	 *            产品具体类型
	 * @param idCardNo
	 *            客户身份证号
	 * @return
	 * @throws Exception
	 */
	public static PayAmountInfo custSubmitOrder(String custId,String custName,
			String phoneNo, String address, String orderType,
			String orderBusId, String idCardNo,String caCardNo) throws Exception {
		
		RequestSubmitOrder rsq = new RequestSubmitOrder();
		rsq.setCustId(custId);
		rsq.setCustName(custName);
		rsq.setPhoneNo(phoneNo);
		rsq.setAddress(address);
		rsq.setOrderType(orderType);
		rsq.setOrderBusId(orderBusId);
		rsq.setIdCardNo(idCardNo);
		rsq.setCaCardNo(caCardNo);
		SoapObject detail = doCommon("custSubmitOrder",
				"custSubmitOrderRequest", "RequestSubmitOrder", rsq,
				RequestSubmitOrder.class);
		PayAmountInfo payAmountInfo = new PayAmountInfo();
		payAmountInfo.setReturnCode(detail.getProperty("returnCode") + "");
		payAmountInfo.setReturnMsg(detail.getProperty("returnMsg") + "");
		return payAmountInfo;

	}

	/**
	 * 得到故障类型接口
	 */
//	public static List<ResponseQuestionReasonMemo> getQuestionReason()
//			throws Exception {
//		RequestBenPeoById rsqBenPeoById = new RequestBenPeoById();
//		rsqBenPeoById.setPage(1);
//
//		SoapObject detail = doCommon("getQuestionReason",
//				"getQuestionReasonRequest", "RequestBenPeoById", rsqBenPeoById,
//				RequestBenPeoById.class);
//
//		int count = detail.getPropertyCount();
//		System.out.println("count=============" + count);
//		List<ResponseQuestionReasonMemo> reasonMemos = new ArrayList<ResponseQuestionReasonMemo>();
//
//		for (int i = 0; i < count; i++) {
//			ResponseQuestionReasonMemo reasonMemo = new ResponseQuestionReasonMemo();
//			SoapObject property = (SoapObject) detail.getProperty(i);
//			reasonMemo.setTypeIntro(property.getProperty("typeIntro")
//					.toString());
//			reasonMemo.setTypeId(Integer.parseInt(property
//					.getProperty("typeId").toString()));
//			reasonMemo.setBreakType(Integer.parseInt(property.getProperty(
//					"breakType").toString()));
//			reasonMemos.add(reasonMemo);
//		}
//		return reasonMemos;
//	}

	/**
	 * 故障报修
	 * 
	 * @param custname
	 *            客户姓名
	 * @param subphone
	 *            提交电话
	 * @param addr
	 *            地址信息
	 * @param cano
	 *            智能卡号
	 * @param idcard
	 *            身份证号
	 * @param typeid
	 *            故障类型编号
	 * @param gzIntro
	 *            故障描述
	 * @return
	 * @throws Exception
	 */
	public static ResponsePayAmountInfo addBreakdown(String custname, String subphone,
			String addr, String cano, String idcard, int typeid, 
			String gzIntro
			)
			throws Exception {
		RequestBreakdown rsq = new RequestBreakdown();
		rsq.setAddr(addr);
		rsq.setCano(cano);
		rsq.setCustname(custname);
		rsq.setIdcard(idcard);
		rsq.setSubphone(subphone);
		rsq.setTypeid(typeid);
		rsq.setGzIntro(gzIntro);
		SoapObject detail = doCommon("addBreakdown", "addBreakdownRequest",
				"RequestBreakdown", rsq, RequestBreakdown.class);
		ResponsePayAmountInfo rpai = new ResponsePayAmountInfo();
		rpai.setReturnCode(detail.getProperty("returnCode") + "");
		rpai.setReturnMsg(detail.getProperty("returnMsg") + "");
		return rpai;

	}

	/**
	 * 实体营业厅地址信息
	 * 
	 * @return
	 * @throws Exception
	 */

	public static List<ResponseYytAddrInfo> getAddress(int page)
			throws Exception {
		List<ResponseYytAddrInfo> returnAddrInfos = new ArrayList<ResponseYytAddrInfo>();
		RequestBenPeoById rsq = new RequestBenPeoById();
		rsq.setPage(page);
		SoapObject detail = doCommon("getAddress", "getAddressRequest",
				"RequestBenPeoById", rsq, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		System.out.println("count=============" + count);

		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseYytAddrInfo responseYytAddrInfo = new ResponseYytAddrInfo();
			responseYytAddrInfo.setAddr(pii.getProperty("addressAddr") + "");
			responseYytAddrInfo.setYytName(pii.getProperty("addressName") + "");
			responseYytAddrInfo
					.setPhoneNo(pii.getProperty("addressPhone") + "");
			responseYytAddrInfo.setAddressId(pii.getProperty("addressId") + "");
			returnAddrInfos.add(responseYytAddrInfo);
		}

		return returnAddrInfos;

	}

	/**
	 * 得到一级频道列表
	 * 
	 * @return
	 */
	public static List<ResponseChannelId> getChannel() throws Exception {
		List<ResponseChannelId> returnlChannelIds = new ArrayList<ResponseChannelId>();
		SoapObject req1 = new SoapObject(
				CodeConstants.NAMESPACE_IN_GENERATED_REQUEST, "getchannel");

		SoapObject detail = doCommonBase(req1);

		int count = detail.getPropertyCount();
		System.out.println("count=============" + count);

		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseChannelId channelId = new ResponseChannelId();
			channelId.setChannelId(pii.getProperty("chanelId") + "");
			channelId.setChannelName(pii.getProperty("channelName") + "");
			returnlChannelIds.add(channelId);
		}
		return returnlChannelIds;
	}

	/**
	 * 得到二级频道列表
	 * 
	 * @param fatherChannelId
	 *            父类频道id
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseChannelId> getAllTwoChannel(
			String fatherChannelId) throws Exception {
		List<ResponseChannelId> returnlChannelIds = new ArrayList<ResponseChannelId>();
		RequestTwoChannel rsqChannel = new RequestTwoChannel();
		rsqChannel.setFatherChannelId(fatherChannelId);
		SoapObject detail = doCommon("getAllTwoChannel",
				"getAllTwoChannelRequest", "RequestTwoChannel", rsqChannel,
				RequestTwoChannel.class);

		int count = detail.getPropertyCount();
		System.out.println("count=============" + count);

		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseChannelId channelId = new ResponseChannelId();
			channelId.setChannelId(pii.getProperty("chanelId") + "");
			channelId.setChannelName(pii.getProperty("channelName") + "");
			returnlChannelIds.add(channelId);
		}
		System.out.println(returnlChannelIds.toString());
		return returnlChannelIds;
	}

	/**
	 * 节目预告信息
	 * 
	 * @param channelId
	 * @param broadCastDate
	 * @throws Exception
	 */
	public static List<ResponseProgramInfo> getProgramInfo(String channelId,
			String broadCastDate,String phoneNo) throws Exception {
		List<ResponseProgramInfo> list = new ArrayList<ResponseProgramInfo>();
		RequestProgramInfo rsq = new RequestProgramInfo();
		rsq.setChannelId(channelId);
		rsq.setBroadCastDate(broadCastDate);
		rsq.setPhoneNo(phoneNo);
		SoapObject detail = doCommon("getProgramInfo", "getProgramInfoRequest",
				"RequestProgramInfo", rsq, RequestProgramInfo.class);
		System.out.println("count = " + detail.getPropertyCount());
		for (int i = 0; i < detail.getPropertyCount(); i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseProgramInfo programInfo = new ResponseProgramInfo();
			programInfo.setBroadCastTime(pii.getProperty("broadCastTime") + "");
			programInfo.setChannel(pii.getProperty("channel") + "");
			programInfo.setProdName(pii.getProperty("prodName") + "");
			programInfo.setPrevueId(pii.getProperty("prevueId")+"");
			programInfo.setStatus(Utils.parseStringToInt(pii.getProperty("status")+""));
			list.add(programInfo);
		}
		return list;
	}
	/**
	 * 节目预约
	 * @param prevueId
	 * @param status
	 * @param phoneNo
	 * @return
	 * @throws Exception
	 */
    public static CommonBaseInfo reservationProg(String phoneNo,String prevueId,String status) throws Exception{
		RequestReservationProg request=new RequestReservationProg();
		request.setPhoneNo(phoneNo);
		request.setPrevueId(prevueId);
		request.setStatus(status);
		SoapObject detail=doCommon("reservationProg", "reservationProgRequest", "RequestReservationProg", request, RequestReservationProg.class);
    	CommonBaseInfo response=new CommonBaseInfo();
    	response.setReturnCode(detail.getProperty("returnCode")+"");
    	response.setReturnMsg(detail.getProperty("returnMsg")+"");
    	return response;
    	
    }
	/**
	 * 我的频道列表
	 * 
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public static List<ResponseChannelId> getMyChannel(String custId)
			throws Exception {
		RequestMyChannel request = new RequestMyChannel();
		request.setCustId(custId);
		SoapObject detail = doCommon("getMyChannel", "getMyChannelRequest",
				"RequestMyChannel", request, RequestMyChannel.class);
		int count = detail.getPropertyCount();
		List<ResponseChannelId> returnList = new ArrayList<ResponseChannelId>();
		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseChannelId response = new ResponseChannelId();
			response.setChannelId(pii.getProperty("channelId") + "");
			response.setChannelName(pii.getProperty("channelName") + "");
			response.setFatherChannelId(pii.getProperty("fatherChannelId") + "");
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 获取版本
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ResponseAppVersion checkAPPVersion() throws Exception {
		ResponseAppVersion response = new ResponseAppVersion();
		// RequestPhoneType rsq = new RequestPhoneType();
		// rsq.setPhoneType("0");
		SoapObject req1 = new SoapObject(
				CodeConstants.NAMESPACE_IN_GENERATED_REQUEST, "checkAPPVersion");
		SoapObject detail = doCommonBase(req1);
		// SoapObject detail = doCommon("checkAPPVersion",
		// "checkAPPVersionRequest",
		// "RequestPhoneType", rsq, RequestPhoneType.class);
		//response.setVersionCode(detail.getProperty("versionId") + "");
		response.setVersionName(detail.getProperty("versionName") + "");
		response.setVersionCode(Utils.parseStringToInt(detail.getProperty("versionCode")+""));
		response.setApkUrl(detail.getProperty("apkUrl") + "");
		response.setReturnCode(detail.getProperty("returnCode") + "");
		return response;

	}

	/**
	 * 常见问题列表
	 * 
	 * @return
	 */
	public static List<ResponseCommonProblem> getQuestions(int page)
			throws Exception {
		RequestBenPeoById request = new RequestBenPeoById();
		request.setPage(page);
		List<ResponseCommonProblem> commonProblems = new ArrayList<ResponseCommonProblem>();
		SoapObject detail = doCommon("getQuestions", "getQuestionsRequest",
				"RequestBenPeoById", request, RequestBenPeoById.class);
		int count = detail.getPropertyCount();
		System.out.println("count=============" + count);

		for (int i = 0; i < count; i++) {
			SoapObject pii = (SoapObject) detail.getProperty(i);
			ResponseCommonProblem commonProblem = new ResponseCommonProblem();
			System.out.println(i + "=====" + pii.getProperty("qtitle") + "");
			System.out.println(i + "=====" + pii.getProperty("qcontent") + "");
			commonProblem.setCommonProblem(pii.getProperty("qtitle") + "");
			commonProblem.setCommonSolution(pii.getProperty("qcontent") + "");
			commonProblems.add(commonProblem);
		}
		return commonProblems;
	}

	/**
	 * 使用指南
	 * 
	 * @return
	 */
	public static List<ResponseAppUseGuide> getUserGuide(int page)
			throws Exception {
		
	    SoapObject request=new SoapObject(CodeConstants.NAMESPACE_IN_GENERATED_REQUEST, "getUserGuide");
	    SoapObject detail=doCommonBase(request);
	    int count=detail.getPropertyCount();
		List<ResponseAppUseGuide> returnList = new ArrayList<ResponseAppUseGuide>();
		for (int i = 0; i < count; i++) {
			ResponseAppUseGuide response=new ResponseAppUseGuide();
			SoapObject pi=(SoapObject) detail.getProperty(i);
			response.setGuideId(pi.getProperty("guideId")+"");
			response.setGuideTitle(pi.getProperty("guideTitle")+"");
			response.setGuideContent(pi.getProperty("guideContent")+"");
			response.setGuideUrl(pi.getProperty("guideUrl")+"");
			response.setDate(pi.getProperty("date")+"");
			com.bupt.chengde.util.LogUtil.e(TAG, "guidUrl="+response.getGuideUrl());
			returnList.add(response);
		}
		return returnList;
	}

	/**
	 * 关于我们
	 * 
	 * @throws Exception
	 */
	public static ResponseAboutUsMemo getAboutMemo(int id) throws Exception {
		RequestAboutUsMemo rsq = new RequestAboutUsMemo();
		rsq.setId(id + "");
		SoapObject pii = doCommon("getAboutMemo", "getAboutMemoRequest",
				"RequestAboutUsMemo", rsq, RequestAboutUsMemo.class);

		ResponseAboutUsMemo aboutUs = new ResponseAboutUsMemo();
		aboutUs.setMemo(pii.getProperty("memo") + "");
		aboutUs.setPicurl(pii.getProperty("pic") + "");
		// aboutUs.setUserFeedbackQq(pii.getProperty("userFeedbackQq") + "");
		// aboutUs.setPublicWeixin(pii.getProperty("publicWeixin") + "");
		// aboutUs.setCompanyCopyright(pii.getProperty("companyCopyright") +
		// "");

		return aboutUs;
	}
	
	/**
	 * 用户上传 头像
	 * @param custId
	 * @param fileName
	 * @param file
	 * @return
	 * @throws Exception
	 */
	
	public static ResponsePayAmountInfo updatePicture (String custId, String fileName,String file)
			throws Exception {
		RequestPerfect request = new RequestPerfect();
		request.setCustId(custId);
		request.setFileName(fileName);
		request.setFile(file);
		SoapObject detail = doCommon("updatePicture", "updatePictureRequest",
				"RequestPerfect", request, RequestPerfect.class);
		ResponsePayAmountInfo response = new ResponsePayAmountInfo();
		response.setReturnCode(detail.getProperty("returnCode") + "");
		response.setReturnMsg(detail.getProperty("returnMsg") + "");
		response.setUrl(detail.getProperty("url") + "");
		return response;

	}

	@SuppressWarnings("rawtypes")
	private static SoapObject doCommon(String methodName, String requestName1,
			String requestName2, SoapObject request, Class requestClass)
			throws Exception {

		SoapObject rpc = new SoapObject(
				CodeConstants.NAMESPACE_IN_GENERATED_REQUEST, methodName);
		PropertyInfo argument = new PropertyInfo();
		argument.setName(requestName1);
		argument.setValue(request);
		argument.setNamespace(CodeConstants.NAMESPACE_IN_GENERATED_REQUEST);
		argument.setType(requestClass);
		rpc.addProperty(argument);

		ExtendedSoapSerializationEnvelope envelope = newEnvelope(rpc);
		envelope.addMapping(CodeConstants.NAMESPACE_IN_GENERATED_REQUEST,
				requestName2, requestClass);

		return call(envelope);
	}

	private static SoapObject doCommonBase(SoapObject request) throws Exception {
		ExtendedSoapSerializationEnvelope envelope = newEnvelope(request);
		return call(envelope);
	}

	private static ExtendedSoapSerializationEnvelope newEnvelope(
			SoapObject request) {
		ExtendedSoapSerializationEnvelope envelope = new ExtendedSoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		return envelope;
	}

	private static SoapObject call(ExtendedSoapSerializationEnvelope envelope)
			throws Exception {
		URL_PREFIX = CodeConstants.URL_PREFIX;//上传头像时用到的
	    WSDL_URL=CodeConstants.WSDL_URL;
		HttpTransportSE ht = new HttpTransportSE(WSDL_URL);
        com.bupt.chengde.util.LogUtil.d(TAG, "CALL.WSDL_URL=" +WSDL_URL);
		ht.debug = true;
		SoapObject detail = null;
		try {
			ht.call(CodeConstants.SOAP_ACTION, envelope);
			detail = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网络连接超时");
		}
		if (CodeConstants.WEBSERVICE_CALL_PRINT) {
			System.out.println(ht.requestDump);
			System.out
					.println
					("======================================");
			System.out.println(ht.responseDump);
		}
		return detail;
	}
	

}
