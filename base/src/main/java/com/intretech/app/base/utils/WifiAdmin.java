package com.intretech.app.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.List;

public class WifiAdmin {
	private static final String TAG = null;
	private WifiManager mWifiManager;
	public WifiInfo mWifiInfo;
	private List<ScanResult> mWifiList;
	private List<WifiConfiguration> mWifiConfiguration;
	WifiLock mWifiLock;
	Context mContext;
	private static WifiAdmin wifiAdmin = null;

	public enum WifiCipherType {
		WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
	}

	public static WifiAdmin getInstance(Context context) {
		if (wifiAdmin == null) {
			wifiAdmin = new WifiAdmin(context);
		}
		return wifiAdmin;

	}

	protected WifiAdmin(Context context) {
		mContext = context;

		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (mWifiManager != null)
			mWifiInfo = mWifiManager.getConnectionInfo();
	}

	public static String getSecurityMode(String scanResult) {
		if (scanResult.contains("WEP")) {
			// WEP
			return "SHARED";
		} else if (scanResult.contains("WPA-PSK")) {
			// WPA-PSK/WPA2-PSK
			return "WPAPSK";
		} else if (scanResult.contains("WPA2-PSK")) {
			// WPA/WPA2
			return "WPA2PSK";
		} else if (scanResult.contains("WPA-EAP")) {
			// WPA/WPA2
			return "WPA-EAP";
		}
		return "OPEN";
	}

	public static String isWPS(String scanResult) {
		if (scanResult.contains("WPS")) {
			return "true";
		}
		return "false";
	}

	public static String getEncryptionType(String scanResult) {
		if (scanResult.contains("TKIP")) {
			return "TKIP";
		}
		return "AES";
	}

	public static int getWifiChannel(int frequency) {
		switch (frequency) {
		case 2412:
			return 1;
		case 2417:
			return 2;
		case 2422:
			return 3;
		case 2427:
			return 4;
		case 2432:
			return 5;
		case 2437:
			return 6;
		case 2442:
			return 7;
		case 2447:
			return 8;
		case 2452:
			return 9;
		case 2457:
			return 10;
		case 2462:
			return 11;
		case 2467:
			return 12;
		case 2472:
			return 13;
		case 2484:
			return 14;
		}
		return 0;
	}

	// 打开WIFI
	public boolean openWifi() {
		boolean bRet = true;
		if (mWifiManager != null) {
			bRet = mWifiManager.isWifiEnabled();
		}
		if (bRet == false) {
			bRet = mWifiManager.setWifiEnabled(true);
			LogUtils.printInfoLog("deejanWifi-----",
					"mWifiManager.setWifiEnabled(true);");
		} else {
			LogUtils.printInfoLog("deejanWifi-----",
					"mWifiManager=null or mWifiManager.isWifiEnabled()");
		}
		return bRet;
	}

	public void closeWifi() {
		if (mWifiManager != null && !mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	public WifiManager getWifiManager() {
		return mWifiManager;
	}

	public String getCurrentConnectInfo() {
		try {
			if (mWifiManager != null)
				mWifiInfo = mWifiManager.getConnectionInfo();
			return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
		} catch (Exception e) {
			return "NULL";
		}
	}

	public String getCurrentConnectSSID() {
		try {
			if (mWifiManager != null)
				mWifiInfo = mWifiManager.getConnectionInfo();
			return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID().replace("\"", "");
		} catch (Exception e) {
			return "NULL";
		}
	}

	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	public void releaseWifiLock() {
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	public void creatWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("Test");
	}

	public List<WifiConfiguration> getConfiguration() {
		if (mWifiManager != null)
			mWifiConfiguration = mWifiManager.getConfiguredNetworks();
		return mWifiConfiguration;
	}

	public void connectConfiguration(int index) {
		if (index > mWifiConfiguration.size()) {
			return;
		}
		mWifiInfo = mWifiManager.getConnectionInfo();
		DisconnectWifi(mWifiInfo.getNetworkId());
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
				false);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	public void startScan() {
		mWifiManager.startScan();
		mWifiList = mWifiManager.getScanResults();
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
	}

	public List<ScanResult> getWifiList() {
		return mWifiList;
	}

	public Boolean lookUpWifiBySSID(String ssid) {
		// mWifiManager= (WifiManager)
		// context.getSystemService(Context.WIFI_SERVICE);
		mWifiList = mWifiManager.getScanResults();
		for (int i = 0; i < mWifiList.size(); i++) {// LogUtils.printInfoLog("Deejan mWifiList=",mWifiList.get(i).SSID);
			if (mWifiList.get(i).SSID.compareTo(ssid) == 0)
				return true;
		}
		return false;
	}

	@SuppressLint("UseValueOf")
	public StringBuilder lookUpScan() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder
					.append("Index_" + new Integer(i + 1).toString() + ":");
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}

	public String getMacAddress() {
		return (mWifiInfo == null) ? null : mWifiInfo.getMacAddress();
	}

	public String getSSID() {
		try {
			if (mWifiManager != null)
				mWifiInfo = mWifiManager.getConnectionInfo();

			if (mWifiInfo == null) {
				return null;
			} else {
				return mWifiInfo.getSSID();
			}

		} catch (Exception e) {
			return null;
		}

	}

	// BSSID
	public String getBSSID() {
		return (mWifiInfo == null) ? null : mWifiInfo.getBSSID();
	}

	public int getIPAddress() {
		try {
			if (mWifiManager != null)
				mWifiInfo = mWifiManager.getConnectionInfo();
			return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getIpAddress(){
		String ip = null;
		int i = getIPAddress();
		ip = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
		return ip;
	}

	public int getNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	public String getWifiInfo() {
		return (mWifiInfo == null) ? null : mWifiInfo.toString();
	}

	public void addNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		mWifiManager.enableNetwork(wcgID, true);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	public static boolean pingIPAddress(byte[] ipAddr) {
		int timeOut = 3000;
		try {
			boolean status = InetAddress.getByAddress(ipAddr).isReachable(
					timeOut);
			LogUtils.printInfoLog("Ping status=", String.valueOf(status));
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		LogUtils.printInfoLog("Deejan", "isNetworkAvailable");
		NetworkInfo networkInfo;
		// String android.content.Context.CONNECTIVITY_SERVICE = "connectivity"
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			LogUtils.printInfoLog("Deejan", "network available");
			return true;
		} else {
			LogUtils.printInfoLog("Deejan", "network not available");
			return false;
		}
	}

	public int addWifiCongfig(String SSID, String passwd) {
		WifiConfiguration wc = new WifiConfiguration();
		wc.SSID = "\"" + SSID + "\"";
		wc.preSharedKey = "\"" + passwd + "\"";
		;
		wc.hiddenSSID = true;
		wc.status = WifiConfiguration.Status.ENABLED;
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		wc.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
		wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		int res = mWifiManager.addNetwork(wc);
		// boolean b = mWifiManager.enableNetwork(res, true);
		mWifiManager.saveConfiguration();
		// mWifiConfiguration.clear();
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
		return res;
	}

	public int connectWifiWithNoPassword(String SSID) {
		// ClearWifiConfigBySSID(SSID);
		WifiConfiguration wc = new WifiConfiguration();
		wc.SSID = "\"" + SSID + "\"";
		wc.allowedKeyManagement.set(KeyMgmt.NONE);
		int networkId = mWifiManager.addNetwork(wc);
		if (networkId != -1) {
			boolean b = mWifiManager.enableNetwork(networkId, true);
			LogUtils.printInfoLog("Deejan mWifiManager.enableNetwork=",
					String.valueOf(b));
		}
		LogUtils.printInfoLog("Deejan mWifiManager.networkId=",
				String.valueOf(networkId));
		return networkId;
	}

	public boolean isExistInConfig(String ssid) {
		for (int i = 0; i < mWifiConfiguration.size(); i++) {
			if (ssid.compareTo(mWifiConfiguration.get(i).SSID) == 0)
				return true;
		}
		return false;
	}

	public boolean isExistInWifiList(String ssid) {
		LogUtils.printInfoLog("Deejan isExistInWifiList=", ssid);
		for (int i = 0; i < mWifiList.size(); i++) {
			if (ssid.compareTo(mWifiList.get(i).SSID) == 0) {
				LogUtils.printInfoLog("Deejan isExistInWifiList=", "true");
				return true;
			}
		}
		return false;
	}

	public void ClearWifiConfigBySSID(String ssid) {
		for (int i = 0; i < mWifiConfiguration.size(); i++) {
			if (ssid.compareTo(mWifiConfiguration.get(i).SSID) == 0)
				mWifiManager.removeNetwork(mWifiConfiguration.get(i).networkId);
			LogUtils.printInfoLog("Deejan removeNetwork=",
					mWifiConfiguration.get(i).SSID);
		}

	}

	public void DisconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

	public String getCurrentWifiCipherType() {
		String ssid = getSSID();
		String tempSSID;
		mWifiList = mWifiManager.getScanResults();
		for (int i = 0; i < mWifiList.size(); i++) {// LogUtils.printInfoLog("Deejan mWifiList=",mWifiList.get(i).SSID);
			tempSSID = "\"" + mWifiList.get(i).SSID + "\"";
			if (tempSSID.compareTo(ssid) == 0) {
				return mWifiList.get(i).capabilities;
			}
		}
		return null;

	}

	public boolean Connect(String SSID, String Password, WifiCipherType Type) {
		if (!this.openWifi()) {
			return false;
		}
		while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				Thread.currentThread();
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}
		}

		WifiConfiguration wifiConfig = this
				.CreateWifiInfo(SSID, Password, Type);
		//
		if (wifiConfig == null) {
			return false;
		}

		WifiConfiguration tempConfig = this.isExsits(SSID);

		if (tempConfig != null) {
			mWifiManager.removeNetwork(tempConfig.networkId);
			mWifiManager.saveConfiguration();
		}

		int netID = mWifiManager.addNetwork(wifiConfig);
		boolean bRet = mWifiManager.enableNetwork(netID, true);
		return bRet;
	}

	public boolean Connect2RouterBySSID(String SSID) {
		WifiConfiguration tempConfig = this.isExsits(SSID);
		if (tempConfig != null) {
			return mWifiManager.enableNetwork(tempConfig.networkId, true);
		}
		return false;
	}

	public boolean CloseCurrentConnect() {
		String SSID = getCurrentConnectSSID().replace("\"", "");
		WifiConfiguration tempConfig = this.isExsits(SSID);
		if (tempConfig != null) {
			return mWifiManager.disableNetwork(tempConfig.networkId);
		}
		return false;
	}


	public void removeWifiByWorkId() {
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		if (existingConfigs != null) {
			for (int index = 0; index < existingConfigs.size(); index++) {
				if (existingConfigs.get(index).SSID.toLowerCase().contains("UMS-AUTO".toLowerCase())
						) {
					// 移除对应wifi的networkId
					mWifiManager.removeNetwork(existingConfigs.get(index).networkId);

					LogUtils.printInfoLog("wifiManager",
							existingConfigs.get(index).SSID + "已移除！");
				}
			}
			mWifiManager.saveConfiguration();
		}

	}
	public void removeWifiBySSID(String ssid) {
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		if (existingConfigs != null) {
			for (int index = 0; index < existingConfigs.size(); index++) {
				if (existingConfigs.get(index).SSID.toLowerCase().contains(ssid.toLowerCase())
						||existingConfigs.get(index).SSID.toLowerCase().contains("UMS-AUTO".toLowerCase())
						) {
					// 移除对应wifi的networkId
					mWifiManager.removeNetwork(existingConfigs.get(index).networkId);

					LogUtils.printInfoLog("wifiManager",
							existingConfigs.get(index).SSID + "已移除！");
				}
			}
			mWifiManager.saveConfiguration();
		}

	}

	private WifiConfiguration isExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}

	public void disconnectWifi() {
		int networkId = getNetworkId();
		// 在Nexus5上，如果networkId�?-1，会导致手机重启
		if (networkId != -1) {
			mWifiManager.disableNetwork(networkId);
		}
		mWifiManager.disconnect();
	}

	private WifiConfiguration checkWifiConfigurationExist(String ssid) {
		List<WifiConfiguration> wifiConfigurations = mWifiManager
				.getConfiguredNetworks();
		for (WifiConfiguration wifiConfiguration : wifiConfigurations) {
			if (ssid.equals(wifiConfiguration.SSID)) {
				return wifiConfiguration;
			}
		}
		return null;
	}

	private WifiConfiguration CreateWifiInfo(String SSID, String Password,
                                             WifiCipherType Type) {

		disconnectWifi();
		WifiConfiguration config = checkWifiConfigurationExist(SSID);
		if (config == null) {
			config = new WifiConfiguration();
		} else {
			// 移除未加密的热点，否则低版本手机会出现相同名字�?�不能移除已加密的热点，否则Nexus5上不能连接到指定热点
			if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
				mWifiManager.removeNetwork(config.networkId);
			}
		}
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
			// config.wepKeys[0] = "";
			config.allowedKeyManagement.set(KeyMgmt.NONE);
			// config.wepTxKeyIndex = 0;
		}
		if (Type == WifiCipherType.WIFICIPHER_WEP) {
			/*
			 * config.preSharedKey = "\"" + Password + "\""; config.hiddenSSID =
			 * true; config.allowedAuthAlgorithms
			 * .set(WifiConfiguration.AuthAlgorithm.SHARED);
			 * config.allowedGroupCiphers
			 * .set(WifiConfiguration.GroupCipher.CCMP);
			 * config.allowedGroupCiphers
			 * .set(WifiConfiguration.GroupCipher.TKIP);
			 * config.allowedGroupCiphers
			 * .set(WifiConfiguration.GroupCipher.WEP40);
			 * config.allowedGroupCiphers
			 * .set(WifiConfiguration.GroupCipher.WEP104);
			 * config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			 * config.wepTxKeyIndex = 0;
			 */
			// config.SSID = "\"SSID_NAME\""; //IMP! This should be in Quotes!!
			config.hiddenSSID = true;
			config.status = WifiConfiguration.Status.DISABLED;
			config.priority = 40;
			config.allowedKeyManagement.set(KeyMgmt.NONE);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);

			config.wepKeys[0] = "\"" + Password + "\""; // This is the WEP
														// Password
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiCipherType.WIFICIPHER_WPA) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			/*
			 * config.allowedAuthAlgorithms
			 * .set(WifiConfiguration.AuthAlgorithm.OPEN);
			 * config.allowedGroupCiphers
			 * .set(WifiConfiguration.GroupCipher.TKIP);
			 * config.allowedKeyManagement
			 * .set(WifiConfiguration.KeyMgmt.WPA_PSK);
			 * config.allowedPairwiseCiphers
			 * .set(WifiConfiguration.PairwiseCipher.TKIP); //
			 * config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			 * //config.status = WifiConfiguration.Status.ENABLED;
			 * config.allowedGroupCiphers
			 * .set(WifiConfiguration.GroupCipher.CCMP);
			 * config.allowedPairwiseCiphers
			 * .set(WifiConfiguration.PairwiseCipher.CCMP); config.status =
			 * WifiConfiguration.Status.ENABLED;
			 */

			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA); // For
																			// WPA
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN); // For
																			// WPA2
			config.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
			config.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		} else {
			return null;
		}
		return config;
	}

	public boolean setWifiApEnabled(boolean enabled) {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		if (enabled) { // disable WiFi in any case
			wifiManager.setWifiEnabled(false);
		}
		try {
			WifiConfiguration apConfig = new WifiConfiguration();
			apConfig.SSID = "SmartCoreServer_1";// +CommonAPI.getInstance().getBlutoothMac();

			apConfig.preSharedKey = "12345678";
			apConfig.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			apConfig.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.TKIP);
			apConfig.allowedKeyManagement
					.set(KeyMgmt.WPA_PSK);
			apConfig.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			apConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			apConfig.status = WifiConfiguration.Status.ENABLED;
			LogUtils.printInfoLog(TAG, "setWifiApEnabled=" + enabled);

			// 通过反射调用设置热点
			Method method = wifiManager.getClass().getMethod(
					"setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
			// 返回热点打开状�??
			return (Boolean) method.invoke(wifiManager, apConfig, enabled);
		} catch (Exception e) {
			return false;
		}
	}


		public void destroy(){
			try{
				if(wifiAdmin!=null){
					wifiAdmin = null;
				}
			}catch (Exception e){

			}
		}

}
