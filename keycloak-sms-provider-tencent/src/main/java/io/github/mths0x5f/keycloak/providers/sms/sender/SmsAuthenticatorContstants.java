package io.github.mths0x5f.keycloak.providers.sms.sender;

public class SmsAuthenticatorContstants {

	/**
	 * ---- conf start -----------
	 **/
	public static final String CONF_APP_ID = "appid";
	public static final String CONF_APP_KEY = "appKey";

	// 签名
	public static final String CONF_SIGN = "sign";
	// 模板id
	public static final String CONF_TWMPLATE_ID = "templateId";
	// 模板参数

	public static final String CONF_TEMPLATE_PARAMS = "templateParams";
	// 验证码长度
	public static final String CONF_SMS_CODE_LENGTH = "smsCodeLength";

	// 验证码过期时间 分钟为单位
	public static final String CONF_SMS_CODE_EXP = "smsCodeExp";

	/**
	 * ---- conf start -----------
	 **/

}
