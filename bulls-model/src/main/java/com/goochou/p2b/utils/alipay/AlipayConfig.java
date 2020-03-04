package com.goochou.p2b.utils.alipay;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2019112169317311";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCbB7OsQc16AY1va5zxFXubbQD4o7wnBI5rrifeoVDjdXne913yrRQfAbKTL1PDbJTybVCcrCJycMVD/bcZgpJyajUVS6KEkqhpYvWUobS+9Y+IwqANzSFjyY5g+hyPJ89CLag/Vp1r1whwced9aUHAaZCaniHnDhh4BfEy2+FJ8aLWpLkKuX8TIc5qWW3hLuVz3OwTWzotG4LcC075yG7PkK2aUnRaZ0TRHW90vrEKfZI/xnn/KrOPOEApvhHd/WcJ8y8mF9HttgHrGCT8IT2rFUJPOLBVNRck1BFCagzZgtLKS9o0HZtJX/pHR56eDKBLIyFjVABZTQdVNr2JCdanAgMBAAECggEAMhjpDM8u0TBTPiAuVRG2IeG1oKN4l7s3MsDSjXTfqME7oruZFmSrzeFEjZ+Ll+kssLJ2jEprz0N126TPWGUDYgnEIZ+RYvnphjS+W0tuFw9PVMhf5FXXZmGpqtjoBbK9nQsYBW+96y1PwtZsEfDDmKQEYJKdLgc2DQMOkHrRcI0uS5P8VW518Q949+QdohBg50F85geMvfc6pazib3RfKlM+7moz6//L8X1mxU91ouo96Ij0/AkY9AJ3enuq/KVvVzu4Ao05FK7SX7Dq/DRqIpOvNgSYSguePg2B0LV9057uSVMXpw+N8DN/DPp9w/f5TMTp2OyFSGVBLIN3aMxb4QKBgQDQED9jxeM2e0mfppsW80RUAIVcLeUJCVIDl40bY94JSRq4fZLXPo3PhtJOkkBndsbyv5ieHpj2oJ3FcHPMVApkWdVcd7w8V7UWWrJ035v9TvcKrTi7afV6EB2waFoBD+zBOgIzaNQr2sHfXHVI7ntIKNE8THiKvlEGLJfLE6gj6QKBgQC+v4CUnf0X+x5K9ilfY4hUSiQvqEp47J4kYQCOrUKI70h3mC13LJUpXKan4AZKUmpWdJzO+roNOz+bnarQHLFTCn90drzr5UD5UGM87FhN1owyV4QNA8OcewEVd+Rm3omkq/Pc+IFwZu+7g04t9YMdCfKodTAMgEmdYxIfZghcDwKBgQCr2i+xhjqkvnJSNrd03lu6km+UcqfxrcwbAWtgiz1TKBm09qt5s8ZH+hDtKumdS8MJr+0r9RPc6U9HTBmpVGT8/CI57QACS2NcYm9yD1v1fuqX2/m2+s7yEiujkJ1+sRuZggNdWSGeU2K8zc5l/ey/7k7cYkFSP94GnoCGEOdZOQKBgEu6te7LBqeVsEIsluJjHsXwJYuyMdbugh6JUHJUwTyiW0/3390XmySvvw6ZN4YcUFGAvJzEGEeWhTtgDmfPbGs+mLmBRqIVA4W3755JawmEK2b1Ld0I6IlgoF6ImeppYZY6GnhZRPJ+O6FjkQ9YELTftTJCTFA6Qn+0zIYguSLrAoGBAKwbV1O2fpQSfa2Z44/Va+ku4PAeh6rggjVIcg4PbCrzljA4JzkKw+XYjKVO/euwxDuPAna2Tl8Y9VRVk+94YS/lT0TStq0J/O7QxmQMcKyis57MHDTTBJ4/8gzl5A8mZyXT1d7l89YnWQ4ke1VrzLTEVc++HUlTd43rVwUZuQDD";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjz9CWoVu9pyEKp8aymdw5WsiPmtIsUoLAf1yQ0dFgIowQduPh7/rYfCCB71VZaGF0SNFX/PToxJ6Z3WxeEw0YlX4FZoZht8EtCWdKqTxi0z+wmDukAD1WTbsye3rDgtOoMT7+3h1v/23X6LiBnZUs8Sb6AtRiTnx8CtuUYt+bOjePTsLj4loo6zDaPvdtC7RatGVFpPHnl3FJq9eufPL3FIoAXM7QtHeLD8S2lZRbwhXxE6wrPGG0XLcApQmX+QJcfoJs6HahlPmO6zTwGoz1fEeYK48vbqXXj5ll9QQ9+/4D3lU4UZGxD8CWt1srgIMGUvL3SzRxGmcpMhDT4jxfQIDAQAB";
    // 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	
	public static String CERT_PATH = "/usr/local/cert/alipay/appCertPublicKey_2019112169317311.crt";
	public static String ALIPAY_PUBLIC_CERT_PATH = "/usr/local/cert/alipay/alipayCertPublicKey_RSA2.crt";
	public static String ROOT_CERT_PATH = "/usr/local/cert/alipay/alipayRootCert.crt";
	
}
