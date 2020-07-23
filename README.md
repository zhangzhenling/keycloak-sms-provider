# Keycloak SMS Provider

With this provider you can **enforce authentication policies based on a verification token sent to users' mobile phones**.
Currently, there are implementations of Twilio and TotalVoice SMS sender services. That said, is nice to note that more
services can be used with ease thankfully for the adopted modularity and in fact, nothing stop you from implementing a 
sender of TTS calls or WhatsApp messages. 

This is what you can do for now:
  + Check ownership of a phone number (Forms and HTTP API)
  + Use SMS as second factor in 2FA method (Browser flow)
  
Two user attributes are going to be used by this provider: _phoneNumberVerified_ (bool) and _phoneNumber_ (str). Many
users can have the same _phoneNumber_, but only one of them is getting _phoneNumberVerified_ = true at the end of a 
verification process. This accommodates the use case of pre-paid numbers that get recycled if inactive for too much time.

## Compatibility

This was initially developed using 10.0.2 version of Keycloak as baseline, and I did not test another user storage beyond
the default like Kerberos or LDAP. I may try to help you but I cannot guarantee.

## Usage

**Build:** To build the project simply run `mvn package` after cloning the repository. At the end of the goal, the `build`
directory will contain all jars correctly placed in a WildFly-like folder structure. 

**Installing:**
 
  1. Merge that content with the root folder of Keycloak. You can of course delete the modules of services you won't use,
  like TotalVoice if you're going to use Twilio.
  2. Open your `standalone.xml` (or equivalent) and (i) include the base module and at least one SMS service provider in
  the declaration of modules for keycloak-server subsystem. (ii) Add properties for overriding the defaults of selected
  service provider and expiration time of tokens. (iii) Execute the additional step specified on selected service provider
  module README.md.
  3. Start Keycloak.
  4. Now in Authentication page, copy the browser flow and add a subflow to the forms, then adding `OTP Over SMS` as a
  new execution. Don't forget to bind this flow copy as the de facto browser flow.
  5. Finally, register the required actions `Update Phone Number` and `Configure OTP over SMS` in the Required Actions tab.

i. add modules defs
```xml
<subsystem xmlns="urn:jboss:domain:keycloak-server:1.1">
    <web-context>auth</web-context>
    <providers>
        <provider>classpath:${jboss.home.dir}/providers/*</provider>
        <provider>module:keycloak-sms-provider</provider>
        <provider>module:keycloak-sms-provider-dummy</provider>
    </providers>
...
```
ii. set provider and token expiration time
```xml
<spi name="phoneMessageService">
    <provider name="default" enabled="true">
        <properties>
            <property name="service" value="TotalVoice"/>
            <property name="tokenExpiresIn" value="60"/>
        </properties>
    </provider>
</spi>
```

**About the API endpoints:** You'll get 2 extra endpoints that are useful to do the verification from a custom application.

  + GET /auth/realms/{realmName}/sms/verification-code?phoneNumber=+5534990001234 (To request a number verification. No auth required.)
  + POST /auth/realms/{realmName}/sms/verification-code?phoneNumber=+5534990001234&code=123456 (To verify the process. User must be authenticated.)

## Thanks
Some code written is based on existing ones in these two projects: [keycloak-phone-authenticator](https://github.com/FX-HAO/keycloak-phone-authenticator)
and [keycloak-sms-authenticator](https://github.com/gwallet/keycloak-sms-authenticator). Certainly I would have many problems
coding all those providers blindly. Thank you!

---
# zhangzhl
## 修正代码bug ， 增加腾讯发短信的实现（2020-07-23）
