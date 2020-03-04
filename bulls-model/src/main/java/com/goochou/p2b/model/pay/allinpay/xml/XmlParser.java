package com.goochou.p2b.model.pay.allinpay.xml;


import com.goochou.p2b.model.pay.allinpay.xstruct.accttrans.AcctTransferReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.accttransfer.BSum;
import com.goochou.p2b.model.pay.allinpay.xstruct.accttransfer.BacctTransferReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.accttransfer.Dtl;
import com.goochou.p2b.model.pay.allinpay.xstruct.acquery.AcNode;
import com.goochou.p2b.model.pay.allinpay.xstruct.acquery.AcQueryRep;
import com.goochou.p2b.model.pay.allinpay.xstruct.acquery.AcQueryReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.ahquery.AHQueryRep;
import com.goochou.p2b.model.pay.allinpay.xstruct.ahquery.AHQueryReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.ahquery.BalNode;
import com.goochou.p2b.model.pay.allinpay.xstruct.cardbin.QCardBinReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.cardbin.QCardBinRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.AipgReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.AipgRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.InfoReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.etdtlquery.EtDtl;
import com.goochou.p2b.model.pay.allinpay.xstruct.etdtlquery.EtQReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.etdtlquery.EtQRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.etdtlquery.EtSum;
import com.goochou.p2b.model.pay.allinpay.xstruct.etquery.EtNode;
import com.goochou.p2b.model.pay.allinpay.xstruct.etquery.EtQueryRep;
import com.goochou.p2b.model.pay.allinpay.xstruct.etquery.EtQueryReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.netbank.NetBankReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.netbank.NetBankRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FAGRA;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FAGRARET;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FAGRCEXT;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FAGRCNL;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FAGRCNLRET;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FAGRCRET;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FASTTRX;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FASTTRXRET;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FASTTRXRETC;
import com.goochou.p2b.model.pay.allinpay.xstruct.quickpay.FasttrxDetail;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.AGRCFM;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.AGRCFMRSP;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.AGRCNL;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.AGRCNLRSP;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.AGRINFO;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.AGRRSP;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.QAGRDETAIL;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.QAGRINFO;
import com.goochou.p2b.model.pay.allinpay.xstruct.stdagr.QAGRRSP;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.CashRep;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.CashReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.ChargeReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.ELE_BILL;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.JointDtlDto;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.JointDto;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.Jointrans;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.LedgerDtl;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.Ledgers;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.Refund;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.TransExt;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.TransRet;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.breq.Trans_Detail;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.brsp.Body;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.brsp.Ret_Detail;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.qry.QTDetail;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.qry.QTransRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.qry.TransQueryReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.transfer.Transfer;
import com.goochou.p2b.model.pay.allinpay.xstruct.tunotify.TUNotifyRep;
import com.goochou.p2b.model.pay.allinpay.xstruct.tunotify.TUNotifyReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNP;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNPA;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNPARET;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNPC;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNPCRET;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNPR;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.RNPRRET;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.ValbSum;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.ValidBD;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.ValidBReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.ValidR;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.ValidRet;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.ValidTR;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.VbDetail;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.idv.IdVer;
import com.goochou.p2b.model.pay.allinpay.xstruct.ver.idv.VALIDRETDTL;
import com.thoughtworks.xstream.XStream;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class XmlParser {
	public static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	private static final XStream xsreq = initXStream(new XStreamEx(), true);
	private static final XStream xsrsp = initXStream(new XStreamEx(), false);

	public static AipgReq parseReq(String xml) {
		return (AipgReq) xsreq.fromXML(xml);
	}

	public static AipgRsp parseRsp(String xml) {
		return (AipgRsp) xsrsp.fromXML(xml);
	}

	public static String toXml(Object o) {
		return (o instanceof AipgReq) ?  XmlParser.toXml(xsreq, o) : XmlParser.toXml(xsrsp, o);

	}

	public static String toXml(XStream xs, Object o) {
		String xml;
		xml = xs.toXML(o);
		xml = xml.replaceAll("__", "_");
		xml = HEAD + xml;
		return xml;
	}

	public static AipgReq xmlReq(String xmlMsg) {
		AipgReq req = (AipgReq) xsreq.fromXML(xmlMsg);
		return req;
	}

	public static AipgRsp xmlRsp(String xmlMsg) {
		AipgRsp rsp = (AipgRsp) xsrsp.fromXML(xmlMsg);
		return rsp;
	}

	public static String reqXml(AipgReq req) {
		String xml = HEAD + xsreq.toXML(req);
		xml = xml.replace("__", "_");
		return xml;
	}

	public static String rspXml(AipgRsp rsp) {
		String xml = HEAD + xsrsp.toXML(rsp);
		xml = xml.replace("__", "_");
		return xml;
	}

	public static XStream initXStream(XStream xs, boolean isreq) {
		if (isreq) {
			xs.alias("AIPG", AipgReq.class);
			xs.alias("BODY", com.goochou.p2b.model.pay.allinpay.xstruct.trans.breq.Body.class);
			xs.alias("TRANS_DETAIL", Trans_Detail.class);
			xs.aliasField("TRANS_DETAILS", com.goochou.p2b.model.pay.allinpay.xstruct.trans.breq.Body.class,
					"details");
		} else {
			xs.alias("AIPG", AipgRsp.class);
			xs.alias("BODY", Body.class);
		}
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QTRANSRSP", QTransRsp.class);
		xs.alias("QTDETAIL", QTDetail.class);
		xs.alias("TRANS", TransExt.class);
		xs.alias("LEDGERS", Ledgers.class);
		xs.addImplicitCollection(Ledgers.class, "list");
		xs.alias("LEDGERDTL", LedgerDtl.class);
		xs.alias("TRANSRET", TransRet.class);
		xs.alias("TRANSRET", TransRet.class);
		xs.alias("VALIDR", ValidR.class);
		xs.alias("VALIDTR", ValidTR.class);
		xs.alias("QAGRINFO", QAGRINFO.class);
		xs.alias("TRANSFER", Transfer.class);
		
		xs.alias("IDVER", IdVer.class);
	
		
		xs.alias("FASTTRXRET", FASTTRXRET.class);
		xs.alias("FASTTRX_DETAIL", FasttrxDetail.class);
		xs.alias("VALIDRET", ValidRet.class);
		xs.alias("VALIDBREQ", ValidBReq.class);
		xs.alias("VALBSUM", ValbSum.class);
		xs.alias("VALIDBD", ValidBD.class);
		xs.alias("VBDETAIL", VbDetail.class);
		xs.addImplicitCollection(ValidBD.class, "details");
		xs.addImplicitCollection(QTransRsp.class, "details");
		xs.addImplicitCollection(QAGRRSP.class, "details");
		xs.addImplicitCollection(AcQueryRep.class, "details");
		xs.addImplicitCollection(AHQueryRep.class, "details");
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
		xs.alias("QAGRRSP", QAGRRSP.class);
		xs.alias("ACQUERYREP", AcQueryRep.class);
		xs.alias("QAGRDETAIL", QAGRDETAIL.class);
		
		xs.aliasField("RET_DETAILS", Body.class, "details");
		xs.alias("RET_DETAIL", Ret_Detail.class);
		xs.alias("REFUND", Refund.class);
		
		xs.alias("CHARGEREQ", ChargeReq.class);
		xs.alias("VALIDRETDTL", VALIDRETDTL.class);
		
		xs.alias("QCARDBINREQ", QCardBinReq.class);
		xs.alias("QCARDBINRSP", QCardBinRsp.class);
		xs.alias("ACQUERYREQ", AcQueryReq.class);
		xs.alias("ACNODE", AcNode.class);
		xs.alias("FASTTRXRETC", FASTTRXRETC.class);
		xs.alias("RNP", RNP.class);
		xs.alias("RNPA", RNPA.class);
		xs.alias("RNPARET", RNPARET.class);
		xs.alias("RNPR", RNPR.class);
		xs.alias("RNPRRET", RNPRRET.class);
		xs.alias("RNPC", RNPC.class);
		xs.alias("RNPCRET", RNPCRET.class);

		xs.alias("FAGRA", FAGRA.class);
		xs.alias("FAGRARET", FAGRARET.class);
		xs.alias("FAGRC", FAGRCEXT.class);
		xs.alias("FAGRCRET", FAGRCRET.class);
		xs.alias("FAGRCNL", FAGRCNL.class);
		xs.alias("FAGRCNLRET", FAGRCNLRET.class);
		xs.alias("FASTTRX", FASTTRX.class);
		xs.alias("FASTTRXRET", FASTTRXRET.class);
		xs.alias("ELE_BILL", ELE_BILL.class);
		
		xs.alias("AHQUERYREQ", AHQueryReq.class);
		xs.alias("AHQUERYREP", AHQueryRep.class);
		xs.alias("BALNODE", BalNode.class);
		xs.alias("TUQNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		xs.alias("ETQREQ", EtQReq.class);
		xs.alias("ETQRSP", EtQRsp.class);
		xs.addImplicitCollection(EtQRsp.class, "details");
		xs.alias("ETSUM", EtSum.class);
		xs.alias("ETDTL", EtDtl.class);
		xs.aliasField("ETDTLS", com.goochou.p2b.model.pay.allinpay.xstruct.trans.breq.Body.class,
				"ETDTLS");
		xs.alias("ACCTTRANSFERREQ", AcctTransferReq.class);
		
		xs.alias("BACCTTRANSFERREQ", BacctTransferReq.class);
		xs.aliasField("DTLS", Body.class, "details");
		xs.alias("BSUM", BSum.class);
		
		xs.alias("DTL", Dtl.class);
		xs.alias("NETBANKREQ", NetBankReq.class);
		xs.alias("NETBANKRSP", NetBankRsp.class);
		//联合放贷产品
		xs.alias("JOINTTRANS",Jointrans.class);
		xs.alias("JOINTDTL",JointDtlDto.class);
		xs.alias("JOINT",JointDto.class);
		xs.addImplicitCollection(JointDto.class, "list");
		//委托签约
		xs.alias("AGRRSP",AGRRSP.class);
		xs.alias("AGRINFO",AGRINFO.class);
		xs.alias("AGRCFM",AGRCFM.class);
		xs.alias("AGRCNL",AGRCNL.class);
		xs.alias("AGRCFMRSP",AGRCFMRSP.class);
		xs.alias("AGRCNLRSP",AGRCNLRSP.class);
		return xs;
	}


}
