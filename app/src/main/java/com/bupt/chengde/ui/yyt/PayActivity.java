package com.bupt.chengde.ui.yyt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.PayAmountInfo;
import com.bupt.chengde.entity.ResponseActive;
import com.bupt.chengde.entity.ResponseBusinessInfo;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @类 :支付页面
 * @version 1.0
 */
public class PayActivity extends BaseActivity {

	private Button subButton;
	private TextView priceTextView;
	private EditText nameEditText;
	private EditText addressEditText;
	private EditText phoneEditText;
	private EditText idCardEditText;
	private EditText caCardEditText;

	private String nameString, addressString, phoneString,caCardString, idCardString,
			yewuString, priceString;
	private String orderType, busiId;
	private int type;
	private ResponseBusinessInfo responseBusinessInfo;
	private ResponseActive activeById = null;
	private PayAmountInfo payAmountInfo;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		initView();
		subButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nameString = nameEditText.getText().toString().trim();
				phoneString = phoneEditText.getText().toString().trim();
				addressString = addressEditText.getText().toString().trim();
				idCardString = idCardEditText.getText().toString().trim();
				caCardString=caCardEditText.getText().toString().trim();
				if (TextUtils.isEmpty(nameString)) {
					application.showToast("姓名不能为空");
					return;
				}
				if (TextUtils.isEmpty(addressString)) {
					application.showToast("地址不能为空");
					return;
				}
				if (TextUtils.isEmpty(phoneString)) {
					application.showToast("手机号不能为空");
					return;
				}else if (!Utils.isMobileNO(phoneString)) {
					application.showToast("手机号格式错误");
					return;
				}
//				if (TextUtils.isEmpty(idCardString)) {
//					application.showToast("身份证号码不能为空");
//					return;
//				}
//				if (!TextUtils.isEmpty(idCardString)&&!isValidate18Idcard(idCardString)) {
//					return;
//				}
				if (type!=1) {
					if (TextUtils.isEmpty(idCardString)) {
						application.showToast("身份证号码不能为空");
						return;
					}
					if (!TextUtils.isEmpty(idCardString)&&!isValidate18Idcard(idCardString)) {
						//application.showToast("身份证号码格式有误");
						return;
					}
				}else{
					if (TextUtils.isEmpty(caCardString)) {
						application.showToast("智能卡号不能为空");
						return;
					}	
					if (caCardString.length()!=10) {
						application.showToast("智能卡号格式不正确");
						return;
					}
				}
				showdialog();
			}
		});
	}

	private void showdialog() {
		String msg;
		if (type == 4) {
			msg = yewuString + "\n姓名：" + nameString + "\n电话号码：" + phoneString
					+ "\n身份证号：" + idCardString + "\n地址:" + addressString;
			busiId = activeById.getActiveId();
		} else {
			if (type==1) {
				msg= yewuString + "\n" + priceString + "\n姓名：" + nameString
						+ "\n电话号码：" + phoneString + "\n智能卡号：" + caCardString
						+ "\n地址:" + addressString;
			}else{
				msg = yewuString + "\n" + priceString + "\n姓名：" + nameString
						+ "\n电话号码：" + phoneString + "\n身份证号：" + idCardString
						+ "\n地址:" + addressString;
			}
//			msg = yewuString + "\n" + priceString + "\n姓名：" + nameString
//					+ "\n电话号码：" + phoneString + "\n身份证号：" + idCardString
//					+ "\n地址:" + addressString;
			busiId = responseBusinessInfo.getBusId();
		}
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("确定提交",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								runHandler();
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).create().show();
	}

	@Override
	protected void doBackGround() throws Exception {
		String custId=application.getCustID();
		System.out.println(custId+"  "+nameString + " " + phoneString + " " + addressString
				+ " " + orderType + " " + busiId + " IDCARD=" + idCardString+" CA="+caCardString);
		payAmountInfo = WebserviceUtils
				.custSubmitOrder(custId,nameString, phoneString, addressString,
						orderType, busiId, idCardString,caCardString);
	}

	@Override
	protected void doForeGround() throws Exception {
		if (payAmountInfo.getReturnCode().equals(CodeConstants.RETURN_SUCCESS)) {
			application.showToast("提交订单成功");
		} else {
			application.showToast("订单提交失败，" + payAmountInfo.getReturnMsg());
		}
	}



	public void initView() {
		type = getIntent().getIntExtra(CodeConstants.TYPE, 0);
		subButton = (Button) findViewById(R.id.sub);
		priceTextView = (TextView) findViewById(R.id.pay_price);
		nameEditText = (EditText) findViewById(R.id.pay_name);
		idCardEditText = (EditText) findViewById(R.id.pay_idCard);
		phoneEditText = (EditText) findViewById(R.id.pay_phone);
		addressEditText = (EditText) findViewById(R.id.pay_address);
		caCardEditText=(EditText) findViewById(R.id.pay_caCard);
		if (type == 1) {
			responseBusinessInfo = (ResponseBusinessInfo) getIntent()
					.getSerializableExtra("object");
//			orderType = CodeConstants.BUS_TV;
			orderType="1";
			yewuString = responseBusinessInfo.getBusTitle() + " | 电视业务";
			priceString = "价格：" + responseBusinessInfo.getBusFee() ;
			caCardEditText.setVisibility(View.VISIBLE);
			idCardEditText.setVisibility(View.GONE);
		} else{
			caCardEditText.setVisibility(View.GONE);
			idCardEditText.setVisibility(View.VISIBLE);
			if (type == 2) {
			responseBusinessInfo = (ResponseBusinessInfo) getIntent()
					.getSerializableExtra("object");
//			orderType = CodeConstants.BUS_MAC;
			orderType = "2";			
			yewuString = responseBusinessInfo.getBusTitle() + " | 宽带业务";
			priceString = "价格：" + responseBusinessInfo.getBusFee() ;
		   } else if (type == 3) {
			responseBusinessInfo = (ResponseBusinessInfo) getIntent()
					.getSerializableExtra("object");
//			orderType = CodeConstants.BUS_NEW;
			orderType = "3";
			yewuString = responseBusinessInfo.getBusTitle() + " | 新装业务";
			priceString = "价格：" + responseBusinessInfo.getBusFee() ;
		   } else {
//			orderType = CodeConstants.BUS_SALES;
			orderType = "4";
			activeById = (ResponseActive) getIntent().getSerializableExtra(
					"object");
			
			yewuString = activeById.getActiveTitle() + " | 优惠活动";
			priceString = "价格：" + activeById.getActivePrice();
			//priceTextView.setVisibility(View.GONE);
		  }
		}
		priceTextView.setText(priceString);
		TextView titleTv=((TextView) findViewById(R.id.top_name));
		//titleTv.setTextSize(getResources().getDimension(R.dimen.title_small_sp));
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_small_sp));
		titleTv.setText(yewuString);
		findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	 /** 
     * <p> 
     * 验证身份证的合法性 
     * </p> 
     * * @param idcard 
     * @return 
     */ 
	public boolean isValidate18Idcard(String IDStr) {  
		 String errorInfo = "";// 记录错误信息  
	        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
	                "3", "2" };  
	        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
	                "9", "10", "5", "8", "4", "2" };  
	        String Ai = "";  
	        // ================ 号码的长度 15位或18位 ================  
	        if (IDStr.length() != 15 && IDStr.length() != 18) {  
	            errorInfo = "身份证号码长度应该为15位或18位。";  
//	            return errorInfo;  
	            application.showToast(errorInfo);
	            return false;  
	        }  
	        // =======================(end)========================  
	 
	        // ================ 数字 除最后以为都为数字 ================  
	        if (IDStr.length() == 18) {  
	            Ai = IDStr.substring(0, 17);  
	        } else if (IDStr.length() == 15) {  
	            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
	        }  
	        if (isNumeric(Ai) == false) {  
	            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";  
//	            return errorInfo;  
	            application.showToast(errorInfo);
	            return false;  
	        }  
	        // =======================(end)========================  
	 
	        // ================ 出生年月是否有效 ================  
	        String strYear = Ai.substring(6, 10);// 年份  
	        String strMonth = Ai.substring(10, 12);// 月份  
	        String strDay = Ai.substring(12, 14);// 月份  
	        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {  
	            errorInfo = "身份证生日无效。";  
//	            return errorInfo;  
	            application.showToast(errorInfo);
	            return false;  
	        }  
	        GregorianCalendar gc = new GregorianCalendar();  
	        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
	        try {
				if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 
				        || (gc.getTime().getTime() - s.parse(  
				                strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {  
				    return false;  
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
	            errorInfo = "身份证月份无效";  
//	            return errorInfo;  
	            application.showToast(errorInfo);
	            return false;  
	        }  
	        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {  
	            errorInfo = "身份证日期无效";  
//	            return errorInfo;  
	            application.showToast(errorInfo);
	            return false;  
	        }  
	        // =====================(end)=====================  
	 
	        // ================ 地区码时候有效 ================  
	        Hashtable h = GetAreaCode();  
	        if (h.get(Ai.substring(0, 2)) == null) {  
	            errorInfo = "身份证地区编码错误。";  
//	            return errorInfo;  
	            application.showToast(errorInfo);
	            return false;  
	        }  
	        // ==============================================  
	 
	        // ================ 判断最后一位的值 ================  
	        int TotalmulAiWi = 0;  
	        for (int i = 0; i < 17; i++) {  
	            TotalmulAiWi = TotalmulAiWi  
	                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))  
	                    * Integer.parseInt(Wi[i]);  
	        }  
	        int modValue = TotalmulAiWi % 11;  
	        String strVerifyCode = ValCodeArr[modValue];  
	        Ai = Ai + strVerifyCode; 
	        com.bupt.chengde.util.LogUtil.w("PayActivity", "TotalmulAiWi="+TotalmulAiWi);
	        com.bupt.chengde.util.LogUtil.e("PayActivity", "modValue="+modValue);
	        com.bupt.chengde.util.LogUtil.i("PayActivity", "strVerifyCode="+strVerifyCode);
	        LogUtil.e("PayActivity", "AI="+Ai);
            com.bupt.chengde.util.LogUtil.e("PayActivity","IDStr="+IDStr);
	        if (IDStr.length() == 18) {  
	             if (Ai.equals(IDStr) == false) {  
	                 errorInfo = "身份证无效，不是合法的身份证号码";  
	                
	                 application.showToast(errorInfo);
//	                 return errorInfo;  
	                 return false;  
	             }  
	         } else {  
	             return true;  
	         }  
	         // =====================(end)=====================  
	         return true;  
	     }
	
	/** 
     * 功能：判断字符串是否为数字 
     * @param str 
     * @return 
     */ 
    private static boolean isNumeric(String str) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (isNum.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    
    /**验证日期字符串是否是YYYY-MM-DD格式
     * @param str
     * @return
     */
   public boolean isDataFormat(String str){
     boolean flag=false;
      //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
     String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
     Pattern pattern1=Pattern.compile(regxStr);
     Matcher isNo=pattern1.matcher(str);
     if(isNo.matches()){
       flag=true;
     }
     return flag;
  }
   
   /** 
    * 功能：设置地区编码 
    * @return Hashtable 对象 
    */ 
   private static Hashtable GetAreaCode() {  
       Hashtable hashtable = new Hashtable();  
       hashtable.put("11", "北京");  
       hashtable.put("12", "天津");  
       hashtable.put("13", "河北");  
       hashtable.put("14", "山西");  
       hashtable.put("15", "内蒙古");  
       hashtable.put("21", "辽宁");  
       hashtable.put("22", "吉林");  
       hashtable.put("23", "黑龙江");  
       hashtable.put("31", "上海");  
       hashtable.put("32", "江苏");  
       hashtable.put("33", "浙江");  
       hashtable.put("34", "安徽");  
       hashtable.put("35", "福建");  
       hashtable.put("36", "江西");  
       hashtable.put("37", "山东");  
       hashtable.put("41", "河南");  
       hashtable.put("42", "湖北");  
       hashtable.put("43", "湖南");  
       hashtable.put("44", "广东");  
       hashtable.put("45", "广西");  
       hashtable.put("46", "海南");  
       hashtable.put("50", "重庆");  
       hashtable.put("51", "四川");  
       hashtable.put("52", "贵州");  
       hashtable.put("53", "云南");  
       hashtable.put("54", "西藏");  
       hashtable.put("61", "陕西");  
       hashtable.put("62", "甘肃");  
       hashtable.put("63", "青海");  
       hashtable.put("64", "宁夏");  
       hashtable.put("65", "新疆");  
       hashtable.put("71", "台湾");  
       hashtable.put("81", "香港");  
       hashtable.put("82", "澳门");  
       hashtable.put("91", "国外");  
       return hashtable;  
   }
}  

