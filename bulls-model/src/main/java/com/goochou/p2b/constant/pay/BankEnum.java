package com.goochou.p2b.constant.pay;



/**
 * Created on 2014-9-2
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [银行枚举]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public enum BankEnum {
    /**工商银行**/
    ICBC("10001","ICBC","工商银行"),
    /**中国银行**/
    BOC("10002","BOC","中国银行"),
    /**建设银行**/
    CCB("10003","CCB","建设银行"),
    /**农业银行**/
    ABC("10004","ABC","农业银行"),
    /**招商银行**/
    CMB("10006","CMB","招商银行"),
    /**浦发银行**/
    SPDB("10011","SPDB","浦发银行"),
    /**广发银行**/
    CGB("10014","CGB","广发银行"),
    /**交通银行**/
    COMM("10005","COMM","交通银行"),
    /**邮政储蓄银行**/
    PSBC("10015","PSBC","邮政储蓄银行"),
    /**中信银行**/
    CITIC("10013","CITIC","中信银行"),
    /**民生银行**/
    CMBC("10012","CMBC","民生银行"),
    /**光大银行**/
    CEB("10009","CEB","光大银行"),
    /**华夏银行**/
    HXB("10010","HXB","华夏银行"),
    /**兴业银行**/
    CIB("10008","CIB","兴业银行"),
    /**上海银行**/
    BOS("10017","BOS","上海银行"),
    /**上海农商银行**/
    SRCB("10016","srcb","上海农商银行"),
    /**平安银行**/
    PAB("10007","PAB","平安银行"),
    /**北京银行**/
    BCCB("10018","BCCB","北京银行"),
    /**北京农商银行**/
    BCCBNS("10019","bccbns","北京农商银行"),
    /**其他银行**/
    QTBANK("10020","qtbank","其他银行"),
    /**杭州银行 20160918 新增银行**/
    HZBANK("10021","hzbank","杭州银行"),
    /**浙商银行 20160918 新增银行**/
    CZBANK("10022","czbank","浙商银行"),
    /**广州银行 20160918 新增银行**/
    GZCB("10023","gzcb","广州银行"),
    /**太仓农村商业银行 20160918 新增银行**/
    TCRCB("10024","tcrcb","太仓农村商业银行"),
    /**东莞农村商业银行 20160918 新增银行**/
    DRCBANK("10025","drcbank","东莞农村商业银行"),
    /**广东省农村信用社联合社 20160918 新增银行**/
    GDRCU("10026","gdrcu","广东省农村信用社联合社"),
    /**广州农村商业银行 20160918 新增银行**/
    GRCBANK("10027","grcbank","广州农村商业银行"),
    /**深圳农村商业银行 20160918 新增银行 英文缩写不存在**/
    SZNSBANK("10028","sznsbank","深圳农村商业银行"),
    /**宁波银行 20160918 新增银行**/
    NBCB("10029","nbcb","宁波银行"),
    /**山东省农村信用社联合社 20160918 新增银行**/
    SDNXS("10030","sdnxs","山东省农村信用社联合社"),
    /**江南农村商业银行 20160918 新增银行**/
    JNBANK("10031","jnbank","江南农村商业银行"),
    /**吉林省农村信用社联合社 20160918 新增银行**/
    JLNLS("10032","jlnls","吉林省农村信用社联合社"),
    /**甘肃省农村信用社联合社 20160918 新增银行**/
    GSRCU("10033","gsrcu","甘肃省农村信用社联合社"),
    /**江苏银行 20160918 新增银行**/
    JSBCHINA("10034","jsbchina","江苏银行"),
    /**靖江市长江城市信用社 20160918 新增银行 无英文缩写**/
    JJCJCSXYS("10035","jjcjcsxys","靖江市长江城市信用社"),
    /**黑龙江省农村信用社联合社 20160918 新增银行**/
    HLJRCC("10036","hljrcc","黑龙江省农村信用社联合社"),
    /**恒丰银行 20160918 新增银行**/
    HFBANK("10037","hfbank","恒丰银行"),
    /**广东南粤银行 20160918 新增银行**/
    GDNYBANK("10038","gdnybank","广东南粤银行"),
    /**东莞银行 20160918 新增银行**/
    DONGGUANBANK("10039","dongguanbank","东莞银行"),
    /**武汉农村商业银行 20160918 新增银行**/
    WHRCBANK("10040","whrcbank","武汉农村商业银行");
    
    /**
     * 类型
     */
    private String featureType;
    /**
     * 名称(数据库featrues存储名)
     */
    private String featureName;
    /**
     * 描叙
     */
    private String description;
    
    /**
     * 初始化
     * @param featureType
     * @param featureName
     * @param description
     */
    BankEnum(String featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static BankEnum getValueByType(int featureType){
        for (BankEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static BankEnum getValueByName(String featureName){
        for (BankEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }
    
    public static BankEnum getValueByDesc(String description){
        for (BankEnum enums : values()) {
            //不区分大小写返回
            if (enums.getDescription().indexOf(description) > -1) {
                return enums;
            }
        }
        return null;
    }

    /**
     * @return the featureType
     */
    public String getFeatureType() {
        return featureType;
    }

    /**
     * @return the featureName
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
