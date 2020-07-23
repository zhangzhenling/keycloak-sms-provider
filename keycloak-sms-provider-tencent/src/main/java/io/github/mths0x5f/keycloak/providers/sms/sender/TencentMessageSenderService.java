/**
 * 实现腾讯短信发送接口
 */
package io.github.mths0x5f.keycloak.providers.sms.sender;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;

import java.io.IOException;
import java.util.ArrayList;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import io.github.mths0x5f.keycloak.providers.sms.exception.MessageSendException;
import io.github.mths0x5f.keycloak.providers.sms.spi.MessageSenderService;

public class TencentMessageSenderService implements MessageSenderService {

	private static final Logger logger = Logger.getLogger(TencentMessageSenderService.class);

	private final String appidString;
	private final String appKeyString;
	private final String templateIdString;
	private final String signString;
	private final String templateParams;
//	private final String smsCodeLengthString;
	private final String smsCodeExp;

	TencentMessageSenderService(Scope config) {
		// 参数校验
		appidString = config.get(SmsAuthenticatorContstants.CONF_APP_ID);
		appKeyString = config.get(SmsAuthenticatorContstants.CONF_APP_KEY);
		templateIdString = config.get(SmsAuthenticatorContstants.CONF_TWMPLATE_ID);
		signString = config.get(SmsAuthenticatorContstants.CONF_SIGN);
		templateParams = config.get(SmsAuthenticatorContstants.CONF_TEMPLATE_PARAMS);
//		smsCodeLengthString = config.get(SmsAuthenticatorContstants.CONF_SMS_CODE_LENGTH);
		smsCodeExp = config.get(SmsAuthenticatorContstants.CONF_SMS_CODE_EXP);
	}

	@Override
	public void sendMessage(String phoneNumber, String message) throws MessageSendException {

		String[] params = templateParams.split("#");

		List<String> listParams = Stream.of(params).map(i -> {
			if (i.equals("{code}")) {
				return message;
			}
			if (i.equals("{exp}")) {
				return smsCodeExp;
			}
			return i;
		}).collect(Collectors.toList());

		SmsSingleSender smsSingleSender = new SmsSingleSender(Integer.valueOf(appidString), appKeyString);

		try {
			// 发送短信
			SmsSingleSenderResult result = smsSingleSender.sendWithParam("86", phoneNumber,
					Integer.valueOf(templateIdString), new ArrayList<>(listParams), signString, "", "");
			logger.infof(result.toString());
			if (result.result == 0) {
				logger.infof("发送验证码成功并缓存");
			} else {
				logger.infof(result.errMsg);
			}

		} catch (HTTPException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
	}
}
