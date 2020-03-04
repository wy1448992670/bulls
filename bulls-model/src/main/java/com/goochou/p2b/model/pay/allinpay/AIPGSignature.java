package com.goochou.p2b.model.pay.allinpay;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;


/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class AIPGSignature {
	private Provider prvd = null;
	private String encoding = "GBK";
	
	public AIPGSignature(){
		
	}
	
	public AIPGSignature(String encoding){
		this.encoding = encoding;
	}
	
	public AIPGSignature(Provider provider){
		this.prvd = provider;
	}
	
	public AIPGSignature(String encoding, Provider provider){
		this.encoding = encoding;
		this.prvd = provider;
	}
	
	/**
	 * @param msg  签名报文
	 * @param keyfile
	 * @param pass
	 * @return  签名字符串
	 * @throws Exception
	 */
	public String signMsg(String msg, String keyfile, String pass) throws AIPGException{
		String signedStr = null;
		try {
			KeyStore ks = prvd == null ? KeyStore.getInstance("PKCS12") : KeyStore.getInstance("PKCS12",prvd);
			FileInputStream fiKeyFile = new FileInputStream(keyfile);
			try {
				ks.load(fiKeyFile, pass.toCharArray());
			} catch (Exception ex) {
				if (fiKeyFile != null)
					fiKeyFile.close();
				throw new AIPGException("加载证书时出错" + ex.getMessage(), ex);
			}
			Enumeration<String> myEnum = ks.aliases();
			String keyAlias = null;
			RSAPrivateCrtKey prikey = null;
			while (myEnum.hasMoreElements()) {
				keyAlias = (String) myEnum.nextElement();
				if (ks.isKeyEntry(keyAlias)) {
					prikey = (RSAPrivateCrtKey) ks.getKey(keyAlias, pass.toCharArray());
					break;
				}
			}
			if (prikey == null) {
				throw new AIPGException("没有找到匹配私钥");
			}
			Signature sign = prvd == null ? Signature.getInstance("SHA1withRSA") : Signature.getInstance("SHA1withRSA", prvd);
			sign.initSign(prikey);
			sign.update(msg.getBytes(encoding));
			byte signed[] = sign.sign();
			byte sign_asc[] = new byte[signed.length * 2];
			Hex2Ascii(signed.length, signed, sign_asc);
			signedStr = new String(sign_asc);
		}catch (Exception e) {
			throw new AIPGException("加签失败 keyfile=" + keyfile + " pass=" + pass + " CAUSE: " + e.getMessage()
					, e);
		}
		return signedStr;
	}
	
	/**
	 * 
	 * @param signedStr  签名字符串
	 * @param msg   报文
	 * @param pkfile
	 * @return
	 * @throws AIPGException
	 */
	public boolean verifyMsg(String signedStr, String msg, String pkfile) throws AIPGException{
		try {
			FileInputStream certfile = new FileInputStream(pkfile);
			CertificateFactory cf = prvd == null ? CertificateFactory.getInstance("X.509") 
					: CertificateFactory.getInstance("X.509", prvd);
			X509Certificate x509cert = null;
			try {
				x509cert = (X509Certificate) cf.generateCertificate(certfile);
			} catch (Exception ex) {
				if (certfile != null)
					certfile.close();
				throw new AIPGException("加载证书时出错" + ex.getMessage(), ex);
			}
			RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
			Signature verify = prvd==null?Signature.getInstance("SHA1withRSA"):Signature.getInstance("SHA1withRSA", prvd);
			verify.initVerify(pubkey);
			byte signeddata[] = new byte[signedStr.length() / 2];
			Ascii2Hex(signedStr.length(), signedStr.getBytes(encoding), signeddata);
			verify.update(msg.getBytes(encoding));
			return verify.verify(signeddata);
		} catch (Exception e) {
			throw new AIPGException("验签失败 signstr=[" + signedStr + "] pkfile=" + pkfile+ " CAUSE: " + e.getMessage()
					, e);
		}
	}
	
	/**
	 * 将十六进制数据转换成ASCII字符串
	 * @param len  十六进制数据长度
	 * @param data_in 待转换的十六进制数据
	 * @param data_out 已转换的ASCII字符串
	 */
	private static void Hex2Ascii(int len, byte data_in[], byte data_out[]) {
		byte temp1[] = new byte[1];
		byte temp2[] = new byte[1];
		for (int i = 0, j = 0; i < len; i++) {
			temp1[0] = data_in[i];
			temp1[0] = (byte) (temp1[0] >>> 4);
			temp1[0] = (byte) (temp1[0] & 0x0f);
			temp2[0] = data_in[i];
			temp2[0] = (byte) (temp2[0] & 0x0f);
			if (temp1[0] >= 0x00 && temp1[0] <= 0x09) {
				(data_out[j]) = (byte) (temp1[0] + '0');
			} else if (temp1[0] >= 0x0a && temp1[0] <= 0x0f) {
				(data_out[j]) = (byte) (temp1[0] + 0x57);
			}
			if (temp2[0] >= 0x00 && temp2[0] <= 0x09) {
				(data_out[j + 1]) = (byte) (temp2[0] + '0');
			} else if (temp2[0] >= 0x0a && temp2[0] <= 0x0f) {
				(data_out[j + 1]) = (byte) (temp2[0] + 0x57);
			}
			j += 2;
		}
	}

	/**
	 * 将ASCII字符串转换成十六进制数据
	 * @param len ASCII字符串长度
	 * @param data_in 待转换的ASCII字符串
	 * @param data_out 已转换的十六进制数据
	 */
	private static void Ascii2Hex(int len, byte data_in[], byte data_out[]) {
		byte temp1[] = new byte[1];
		byte temp2[] = new byte[1];
		for (int i = 0, j = 0; i < len; j++) {
			temp1[0] = data_in[i];
			temp2[0] = data_in[i + 1];
			if (temp1[0] >= '0' && temp1[0] <= '9') {
				temp1[0] -= '0';
				temp1[0] = (byte) (temp1[0] << 4);
				temp1[0] = (byte) (temp1[0] & 0xf0);
			} else if (temp1[0] >= 'a' && temp1[0] <= 'f') {
				temp1[0] -= 0x57;
				temp1[0] = (byte) (temp1[0] << 4);
				temp1[0] = (byte) (temp1[0] & 0xf0);
			}
			if (temp2[0] >= '0' && temp2[0] <= '9') {
				temp2[0] -= '0';
				temp2[0] = (byte) (temp2[0] & 0x0f);
			} else if (temp2[0] >= 'a' && temp2[0] <= 'f') {
				temp2[0] -= 0x57;
				temp2[0] = (byte) (temp2[0] & 0x0f);
			}
			data_out[j] = (byte) (temp1[0] | temp2[0]);
			i += 2;
		}
	}
	
}
