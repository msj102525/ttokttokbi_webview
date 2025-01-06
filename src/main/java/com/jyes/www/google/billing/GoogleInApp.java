package com.jyes.www.google.billing;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.ResourceUtils;

import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.services.androidpublisher.model.InappproductsListResponse;
import com.google.api.services.androidpublisher.model.InAppProduct;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ListSubscriptionsResponse;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.api.services.androidpublisher.model.Subscription;
import com.google.api.services.androidpublisher.model.SubscriptionDeferralInfo;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.google.api.services.androidpublisher.model.SubscriptionPurchasesDeferRequest;
import com.google.api.services.androidpublisher.model.SubscriptionPurchasesDeferResponse;
import com.google.api.services.androidpublisher.model.VoidedPurchasesListResponse;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jyes.www.util.LogUtils;
import com.jyes.www.util.StringUtil;

public class GoogleInApp {
	public static String GOOGLE_KEY_FILE_PATH = "api-5264199636846743917-375244-149e035ce1d0.json";
	public static String YOUR_APPLICATION_NAME = "kr.co.jyes.sam";
	public static void main(String[] args) {
		try {
//			String subscriptionId = "wireless_one_year_ticket";
			String subscriptionId = "wireless_fixed_subscribe_ticket_01";
			String token = "bmcgmmhmgolgidclnhepondk.AO-J1OzJx7AvwFACwEsJK8vBdN03_3a6BppzJh4CSEfuK0hMv8whFt08pr4pDj5McrLStEAw20bg4EAnhvRCUCTOX48akAa-PQ";
//			System.out.println(GoogleInApp.checkSubscription(subscriptionId));
			System.out.println(GoogleInApp.checkSubscriptionPurchase(subscriptionId, token));
//			System.out.println(GoogleInApp.checkProductPurchase("wireless_one_year_ticket", "ajeaphddpgnkaebjnajaahnd.AO-J1Ox-jLZIfwkKBqCuKcV5scLnXljJPfHKcaAfm7PWTuQUg1HEK44Q0denEqOliG_XNi6BIvtjm7hql_QOZPc_MJ1YqEdseUv5auWPlaacOHowRobOXNBhtA8gRv6oBcWvH19Rbeo7"));
//			System.out.println(GoogleInApp.checkSubscriptionPurchase("wireless_fixed_subscribe_ticket", "ciaemgggjgbmigfdbbpngede.AO-J1OxNaDKWRDdInnhK_H1i7tWlBh_MQ67gorqAHXN01wyS6-MLTJVDk5SOCYJQ1jQZw4BC3e8zJiyIHinM89zFW18UpQ9ZkJmWorplrrquOSJ3_GodIxrayouJltj50__gXkigNjiM"));
//			System.out.println(GoogleInApp.deferSubscriptionsPurchase(1581909961781L, 1581823561781L,"wireless_fixed_subscribe_ticket", "ciaemgggjgbmigfdbbpngede.AO-J1OxNaDKWRDdInnhK_H1i7tWlBh_MQ67gorqAHXN01wyS6-MLTJVDk5SOCYJQ1jQZw4BC3e8zJiyIHinM89zFW18UpQ9ZkJmWorplrrquOSJ3_GodIxrayouJltj50__gXkigNjiM"));
//			GoogleInApp.revokeSubscriptionPurchase("wireless_fixed_subscribe_ticket", "amdkhdibnimfickpklmdbmgo.AO-J1OyZPCd8j47Va7we3g1WiqzXB6wSo0fm5FzsYkViCdY0U8zEJht76Zd61NHWZnxgdJyClu6z4kTPq-qxQqy2V7Cv4hiE5sbeLpGDHW9v8tnKn1kvWQ4RCRA9YsGC9IzwwJ4j86di");
		} catch (Exception e) {
			e.printStackTrace();
		}
		long timeInMillis = 1665453303681L;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date timeInDate = new Date(timeInMillis);
		// 날짜 더하기
        Calendar cal = Calendar.getInstance();
        cal.setTime(timeInDate);
		System.out.println("기본:"+transFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
		System.out.println(cal.getTimeInMillis());
		System.out.println("변경:"+transFormat.format(cal.getTime()));
	}
//	public static GoogleCredential getGoogleCredential() throws IOException {
//	    List<String> scopes = new ArrayList<String>();
//	    scopes.add(AndroidPublisherScopes.ANDROIDPUBLISHER);
//	    GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:com/jyes/www/google/billing/"+GOOGLE_KEY_FILE_PATH))).createScoped(scopes);
//	    return credential;
//	}
	public static GoogleCredentials getNewGoogleCredential() throws IOException {
		List<String> scopes = new ArrayList<String>();
	    scopes.add(AndroidPublisherScopes.ANDROIDPUBLISHER);
	    GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:com/jyes/www/google/billing/"+GOOGLE_KEY_FILE_PATH))).createScoped(scopes);
	    return credentials;
	}
	public static AndroidPublisher setAndroidPublisher() throws Exception {
//		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//	    JsonFactory jsonFactory = new JacksonFactory();
		return new AndroidPublisher(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), new HttpCredentialsAdapter(GoogleInApp.getNewGoogleCredential()));
	}
	public static void refund(String orderId) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    System.out.println(publisher.orders().refund(YOUR_APPLICATION_NAME, orderId).execute());
	}
	public static void cancelSubscriptionPurchase(String subscriptionId, String token) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    publisher.purchases().subscriptions().cancel(YOUR_APPLICATION_NAME, subscriptionId, token).execute();
	}
	public static void revokeSubscriptionPurchase(String subscriptionId, String token) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    publisher.purchases().subscriptions().revoke(YOUR_APPLICATION_NAME, subscriptionId, token).execute();
	}
	public static VoidedPurchasesListResponse voidedPurchases() throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    VoidedPurchasesListResponse voidedPurchasesListResponse = publisher.purchases().voidedpurchases().list(YOUR_APPLICATION_NAME).execute();
	    return voidedPurchasesListResponse;
	}
	public static void voidedPurchasesRefund(StringBuffer logData) {
		try {
			VoidedPurchasesListResponse voidedPurchasesListResponse = GoogleInApp.voidedPurchases();
			logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "voidedPurchasesListResponse : " + voidedPurchasesListResponse + "\n");
			if(voidedPurchasesListResponse!=null) {
				JsonParser jsonParser = new JsonParser();
				Object object = jsonParser.parse(voidedPurchasesListResponse.toString());
				if(object!=null) {
					JsonObject jsonObject = (JsonObject)object;
					if(jsonObject!=null&&jsonObject.get("voidedPurchases")!=null) {
						if(jsonObject.get("voidedPurchases") instanceof JsonArray) {
							JsonArray jsonArray = (JsonArray)jsonObject.get("voidedPurchases");
							for (int i = 0; i < jsonArray.size(); i++) {
								if(jsonArray.get(i)!=null) {
									if(jsonArray.get(i) instanceof JsonObject) {
										JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
										if(jsonObject2!=null&&jsonObject2.get("purchaseToken")!=null) {
											String orderId = StringUtil.nvl(jsonObject2.get("orderId").getAsString());
											String purchaseTimeMillis = StringUtil.nvl(jsonObject2.get("purchaseTimeMillis").getAsString());
											String voidedTimeMillis = StringUtil.nvl(jsonObject2.get("voidedTimeMillis").getAsString());
											logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "orderId : " + orderId + "\n");
											logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "purchaseTimeMillis : " + purchaseTimeMillis + "\n");
											logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "voidedTimeMillis : " + voidedTimeMillis + "\n");
											SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
											if(!"".equals(purchaseTimeMillis)) {
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "transFormat.format(new Date(purchaseTimeMillis)) : " + transFormat.format(new Date(Long.parseLong(purchaseTimeMillis))) + "\n");
											}
											if(!"".equals(voidedTimeMillis)) {
												logData.append("[" + LogUtils.getCurrentTime() + "]" + " " + "transFormat.format(new Date(voidedTimeMillis)) : " + transFormat.format(new Date(Long.parseLong(voidedTimeMillis))) + "\n");
											}
											if(!"".equals(orderId)) {
//												GoogleInApp.refund(orderId);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static InappproductsListResponse getInappproducts() throws Exception {
//	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
//	    InappproductsListResponse inappproductsListResponse = publisher.inappproducts().list(YOUR_APPLICATION_NAME).execute();
//	    return inappproductsListResponse;
//	}
	public static InAppProduct getInappproducts(String sku) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    InAppProduct inAppProduct = publisher.inappproducts().get(YOUR_APPLICATION_NAME, sku).execute();
	    return inAppProduct;
	}
	public static ListSubscriptionsResponse getSubscriptions() throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    ListSubscriptionsResponse listSubscriptionsResponse = publisher.monetization().subscriptions().list(YOUR_APPLICATION_NAME).execute();
	    return listSubscriptionsResponse;
	}
	public static Subscription getSubscriptions(String sku) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    Subscription subscription = publisher.monetization().subscriptions().get(YOUR_APPLICATION_NAME, sku).execute();
	    return subscription;
	}
	public static int checkInAppProducts(String productId) {
		int returnValue = -1;
		try {
			InAppProduct inAppProduct = GoogleInApp.getInappproducts(productId);
			if(inAppProduct!=null) {
				JsonParser jsonParser = new JsonParser();
				Object object = jsonParser.parse(inAppProduct.toString());
				if(object!=null) {
					JsonObject jsonObject = (JsonObject)object;
					if(jsonObject!=null&&jsonObject.get("sku")!=null) {
						String sku = jsonObject.get("sku").getAsString();
						if(productId.equals(sku)) {
							if(jsonObject.get("purchaseType")!=null) {
								String purchaseType = jsonObject.get("purchaseType").getAsString();
								if("subscription".equals(purchaseType)) {
									returnValue = 1;
								} else {
									returnValue = 0;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	public static int checkSubscription(String productId) {
		int returnValue = -1;
		try {
			Subscription subscription = GoogleInApp.getSubscriptions(productId);
			if(subscription!=null) {
				System.out.println(subscription.toString());
				JsonParser jsonParser = new JsonParser();
				Object object = jsonParser.parse(subscription.toString());
				if(object!=null) {
					JsonObject jsonObject = (JsonObject)object;
					System.out.println(jsonObject);
					if(jsonObject!=null&&jsonObject.get("productId")!=null) {
						String sku = jsonObject.get("productId").getAsString();
						if(productId.equals(sku)) {
							returnValue = 1;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	/**
	 * 결제 타입 확인
	 * @param productId
	 * @return 0:일반결제, 1:정기결제, -1:오류
	 */
	public static int checkListSubscription(String productId) {
		int returnValue = -1;
		try {
			ListSubscriptionsResponse listSubscriptionsResponse = GoogleInApp.getSubscriptions();
			if(listSubscriptionsResponse!=null) {
				System.out.println(listSubscriptionsResponse.toString());
				JsonParser jsonParser = new JsonParser();
				Object object = jsonParser.parse(listSubscriptionsResponse.toString());
				if(object!=null) {
					JsonObject jsonObject = (JsonObject)object;
					if(jsonObject!=null&&jsonObject.get("subscriptions")!=null) {
						if(jsonObject.get("subscriptions") instanceof JsonArray) {
							JsonArray jsonArray = (JsonArray)jsonObject.get("subscriptions");
							for (int i = 0; i < jsonArray.size(); i++) {
								if(jsonArray.get(i)!=null) {
									if(jsonArray.get(i) instanceof JsonObject) {
										JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
										System.out.println(jsonObject2);
										if(jsonObject2!=null&&jsonObject2.get("productId")!=null) {
											String sku = jsonObject2.get("productId").getAsString();
											if(productId.equals(sku)) {
												returnValue = 1;
//												if(jsonObject2.get("purchaseType")!=null) {
//													String purchaseType = jsonObject2.get("purchaseType").getAsString();
//													if("subscription".equals(purchaseType)) {
//														returnValue = 1;
//													} else {
//														returnValue = 0;
//													}
//													break;
//												}
											} else {
												returnValue = 0;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	public static ProductPurchase checkProductPurchase(String productId, String token) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    ProductPurchase productPurchase = publisher.purchases().products().get(YOUR_APPLICATION_NAME, productId, token).execute();
		return productPurchase;
	}
	public static SubscriptionPurchase checkSubscriptionPurchase(String subscriptionId, String token) throws Exception {
		SubscriptionPurchase subscriptionPurchase = null;
		try {
		    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
		    subscriptionPurchase = publisher.purchases().subscriptions().get(YOUR_APPLICATION_NAME, subscriptionId, token).execute();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return subscriptionPurchase;
	}
	public static SubscriptionPurchasesDeferResponse deferSubscriptionsPurchase(long expectedExpiryTimeMillis, long desiredExpiryTimeMillis, String subscriptionId, String token) throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    SubscriptionDeferralInfo subscriptionDeferralInfo = new SubscriptionDeferralInfo();
	    subscriptionDeferralInfo.setExpectedExpiryTimeMillis(expectedExpiryTimeMillis);
	    subscriptionDeferralInfo.setDesiredExpiryTimeMillis(desiredExpiryTimeMillis);
	    SubscriptionPurchasesDeferRequest content = new SubscriptionPurchasesDeferRequest();
	    content.setDeferralInfo(subscriptionDeferralInfo);
	    SubscriptionPurchasesDeferResponse subscriptionPurchasesDeferResponse = publisher.purchases().subscriptions().defer(YOUR_APPLICATION_NAME, subscriptionId, token, content).execute();
		return subscriptionPurchasesDeferResponse;
	}
	public static void voidList() throws Exception {
	    AndroidPublisher publisher = GoogleInApp.setAndroidPublisher();
	    VoidedPurchasesListResponse voidedPurchasesListResponse = publisher.purchases().voidedpurchases().list(YOUR_APPLICATION_NAME).execute();
	    System.out.println(voidedPurchasesListResponse);
	}
}
