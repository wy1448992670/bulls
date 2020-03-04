<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="keywords" content="鑫聚财,鑫聚财,P2P,P2B,安全透明,网络投资,网络投资平台,网上投资,抵押借贷,免费投资,放心投资,安全投资,安全理财,P2P网络投资,P2P网上投资,本金保障,高收益,高回报,投资,借贷,理财,个人投资,民间投资,P2P投资,企业融资,企业借贷" />
    <meta name="description" content="鑫聚财-活期理财专家" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" href="${basePath}css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${basePath}css/icommon.css"/>
    <title>债权转让协议</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
        }

        #body li {
            list-style: none;
        }

        #body {
            padding: 0;
            margin: 0 auto;
            font-size: 14px;
            line-height: 1.5;
            color: #8c8c8c;
            background: url("http://qmlc.oss-cn-hangzhou.aliyuncs.com/images/yuanlai/shuiyinBack.Jpeg") repeat;
            /*background-size: 100% 100%;*/
            background-color: #fff;
            color: #666;
            word-wrap: break-word;
            word-break: break-all;
        }

        #body.container {
            padding: 15px 15px 50px;
        }

        #body.container .title {
            font-size: 20px;
            text-align: center;
            line-height: 30px;
            margin-bottom: 10px;
        }

        #body p {
            margin: 0 0 10px;
            line-height: 20px;
        }

        #body .fontWeigen {
            font-weight: 700;
        }

        #body b {
            text-decoration: underline;
        }

        #body b img {
            vertical-align: middle;
        }

        #body .margin_bt {
            margin-bottom: 20px;
        }

        #body table tbody td {
            /*text-align: center;*/
        }

        #body table {
            border-top: 1px solid #ddd;
            border-left: 1px solid #ddd;
            margin-bottom: 15px;
            /*width: 100%;*/
            font-size: 12px;
            white-space: nowrap;
        }

        #body table td {
            line-height: 24px;
            border-bottom: 1px solid #ddd;
            border-right: 1px solid #ddd;
            padding-left: 6px;
        }

        #body table.whiteSpace {
            white-space: normal;
        }

        #body h4 {
            text-align: center;
            font-size: 16px;
            padding-bottom: 8px;
        }

        #body .fontSizeP {
            font-size: 14px;
        }

        #body .fontIdentTiele {
            text-indent: 20px;
        }

        #body .fontIdent {
            text-indent: 35px;
        }

        #body .contract {
            position: relative;
            width: 100%;
            height: 260px;
            margin-top: -15px;
            padding-top: 130px;
            padding-right: 50px;
        }

        #body .contract img {
            position: absolute;
            width: 120px;
            height: 120px;
            top: 78px;
        }

        #body img.xinjucai {
            left: 55px;
        }

        #body img.yuanlai {
            right: 0;
        }

        #body .overflowTable {
            position: relative;
            width: 100%;
            margin-bottom: 15px;
            overflow-x: hidden;
            overflow-y: hidden;
        }

        #body .overflowTable .tableBox {
            background: #fff;
            width: 100%;
            z-index: 999999;
            overflow-y: hidden;
            overflow-x: scroll;
        }
    </style>
</head>

<body>
<div id="body" class="container">
    <h2 class="title">债权转让协议</h2>
    <p>甲方（转让人/原债权人）：<b>${trueName}</b></p>
    <p>证件类型：<b>身份证</b></p>
    <p>证件号码：<b>
    <shiro:lacksPermission name="user:adminPhone">
        ${identityCard.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1****$2")}
    </shiro:lacksPermission>
    <shiro:hasPermission name="user:adminPhone">
        ${identityCard }
    </shiro:hasPermission>
    </b></p>

    <p>乙方（受让人/新债权人）：<b class="trueName">${user.trueName}</b>&nbsp;&nbsp;&nbsp;平台用户名：<b id="username">${user.username}</b></p>
    <p>证件类型：<b>身份证</b></p>
    <p>证件号码：<b id="identityCard">
    <shiro:lacksPermission name="user:adminPhone">
        ${user.identityCard.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1****$2")}
    </shiro:lacksPermission>
    <shiro:hasPermission name="user:adminPhone">
        ${user.identityCard }
    </shiro:hasPermission>
    </b></p>

    <p>丙方（居间人）：<b>鑫聚财（上海）金融信息服务有限公司</b></p>
    <p>证件类型：<b>营业执照</b></p>
    <p>证件号码：<b>310115002605418</b></p>
    <p class="margin_bt">住所地：<b>上海市浦东新区福山路33号建工大厦24楼E座</b></p>

    <h3 style="margin-bottom:10px;">鉴于</h3>
    <p>1.甲方为依法成立并有效存续的法人或具有完全民事权利能力和民事行为能力的自然人，存在将其通过丙方运营管理的【鑫聚财】平台（以下称“丙方平台”）获得的债权进行转让的需求，希望通过丙方平台进行融资。</p>
    <p>2.本协议所指借款人系指基于融资需要，经丙方居间撮合而从甲方处借得资金，并在借款协议约定时间内还本付息的主体。</p>
    <p>3.本协议所指债权系指甲方通过丙方平台向借款人出借自有资金后形成的债权。</p>
    <p>4.乙方为在丙方平台上完成全部注册程序并依法认证通过的注册用户，拟通过丙方的居间撮合，将受让甲方债权并以自有合法资金支付债权转让对价款。乙方受让甲方对借款人的债权后，成为新债权人。</p>
    <p>5.丙方为掌握丰富的融资项目资源信息并拥有专业的信用评价和管理服务的平台，丙方撮合甲方与乙方达成投融资交易，并由丙方提供与投融资交易的履行有关的投融资咨询服务、撮合等服务。</p>
    <p>6.甲方与丙方已签订了债权人居间服务协议，就丙方为甲方提供居间服务而产生的双方权利义务做了明确约定；就丙方为乙方提供居间服务而产生的双方权利义务在本协议中约定，乙丙双方不另行签订居间服务协议。</p>
    <p>
        <strong>现,</strong>三方经过友好协商，就甲乙双方通过丙方的居间撮合服务达成融资交易事项达成一致意见并签署本《债权转让协议》（以下简称“本协议”），以资共同遵守。</p>

    <h4>一、丙方的服务</h4>
    <p class="fontSizeP">
        <strong>1.1 </strong>投资咨询服务</p>
    <p class="fontIdentTiele">1.1.1&nbsp;丙方同意向乙方提供投资咨询服务，乙方同意接受丙方提供的该项服务。双方同意，丙方向乙方提供投资咨询服务应按如下范围和规则进行：</p>
    <p class="fontIdent">(1)在适用法律和技术手段允许和合理的范围内，丙方应对在丙方平台上展示的债权转让信息进行必要的披露，以供乙方在决定是否受让甲方债权时参考。</p>
    <p class="fontIdent">(2)丙方可以选择是否通过丙方平台向乙方提供其自行收集或从其他第三方合作机构获得的除债权转让信息之外的其他甲方、借款人信息，如工作情况、收入情况、家庭情况、信用报告、历史偿债情况等，以供乙方在决定是否受让甲方债权时参考。乙方应自行判断该等信息的真实性、准确性和完整性。</p>

    <p class="fontSizeP">
        <strong>1.2 </strong>资金管理服务</p>
    <p class="fontIdentTiele">1.2.1&nbsp;丙方同意向乙方提供资金管理服务，乙方同意接受丙方提供的该项服务。双方同意，丙方向乙方提供资金管理服务应按如下范围和规则进行：</p>
    <p class="fontIdent">(1)资金保管：乙方应通过丙方平台在丙方合作的资金托管/存管机构以乙方名义开立资金托管/存管账户，账户中托管以下两种情形下的资金：(a) 乙方为了出借资金或投资的目的，按照丙方平台的相关交易规则和本协议的规定向托管/存管账户转入资金；(b) 甲方向乙方按期偿还本息及根据债权转让协议约定偿付的逾期罚息、违约金等。上述资金均在托管/存管账户中乙方的个人账户，独立于丙方的自有资金，丙方将严格根据乙方的指示及授权进行资金的操作。除丙方平台另有规定，上述资金在托管/存管账户托管期间不计利息。</p>
    <p class="fontIdent">(2)资金冻结：乙方同意由丙方按照丙方平台的相关交易规则和本协议的规定冻结乙方托管/存管账户中相应数额的资金，包括但不限于：(a) 在乙方与甲方签署债权转让协议后，乙方同意并授权丙方在乙方托管/存管账户中冻结等值于拟出借数额的资金。该项冻结资金在债权转让协议生效时由托管/存管机构划转至甲方账户，冻结解除；或在甲方的债权转让金额未全部得到满足而导致融资项目取消时，该冻结资金解冻； (b)各类交易中如发生乙方应支付相应费用的，乙方同意并授权丙方在乙方托管账记中冻结等值于拟支付的费用的资金，该项冻结在交易生效支付费用时解除。</p>
    <p class="fontIdent">(3)资金代付：乙方同意并授权丙方按照丙方平台的相关交易规则和本协议的规定，委托其合作的资金托管/存管机构，从乙方托管/存管账户中代为划扣乙方应支付的相应数额的资金，包括但不限于：(a) 根据乙方与甲方之间的债权转让协议应向甲方划转的债权转让本金；(b) 乙方应支付给相关方的相应费用等。</p>
    <p class="fontIdent">(4)资金代收：乙方同意并授权丙方按照丙方平台的相关交易规则和本协议的规定，委托其合作的资金托管/存管机构代为收取相应资金，包括但不限于：(a)乙方在借款协议项下应收回的本金和利息，并将该等代收的本金和利息在扣除相关费用后直接转入乙方托管/存管账户；(b)乙方基于债权转让协议应收取的债权回购款。</p>
    <p class="fontIdent">(5)资金提取：乙方可在丙方平台的工作时间内通过丙方平台的相应模块向丙方合作的资金托管/存管机构提出提现要求，资金托管/存管机构将相应的款项汇入乙方提供的银行账户中（根据乙方提供的银行不同，实际汇入时间可能存在差异）。除本条约定外，丙方不接受乙方提出的其他任何提现方式。</p>
    <p class="fontIdent">(6)资金查询：乙方有权在任何时间使用本人的用户名和密码登录丙方平台查询其在托管/存管账户下资金的情况，包括充值、冻结、锁定、代付、代收和提现记录等。乙方应理解，乙方最终收到款项的服务是由其提供的银行账户开户行提供的，需向该银行请求查证。乙方同意，其登录丙方平台查询的任何信息不能够作为相关操作或投融资交易的证据或依据；如该等信息与丙方保存的记录存在任何不一致，应以丙方所提供的记录为准。</p>

    <p class="fontIdentTiele">1.2.2&nbsp;乙方了解并同意，丙方提供上述资金管理服务时可能需要与银行或非银行业的资金托管/存管机构等开展合作。因此，乙方同意：</p>
    <p class="fontIdent">(1)丙方不对银行及资金托管/存管机构进行相应资金划转的时限、准确性、及时性等作出任何承诺或承担任何相关责任，包括但不限于由此产生的利息、货币贬值、银行及资金托管/存管机构执行划转指令出现错误、银行及资金托管/存管机构系统故障、银行及资金托管/存管机构对资金划转的特定限制等导致的损失或责任；</p>
    <p class="fontIdent">(2)就本条规定的资金管理服务，乙方应按照银行及资金托管/存管机构的规定支付相关费用（如有）。就费用支付事项产生的任何争议、纠纷等，均由乙方与银行及（或）资金托管/存管机构自行解决，丙方不承担因此而产生的任何损失或责任。</p>

    <p class="fontIdentTiele">1.2.3&nbsp;如果丙方发现因系统故障、银行或资金托管/存管机构执行指令错误或其他任何原因导致的在资金划转过程中出现的错误，无论是否有利于乙方、丙方或甲方等任何一方，丙方均有权在以电子邮件（以乙方在丙方平台注册时或者注册后变更用户信息时向丙方平台提供的电子邮箱为准）或其他方式通知乙方后立即纠正该错误：</p>
    <p class="fontIdent">(1)如果该等错误导致乙方实际收到的款项少于应获得的金额，则丙方应将乙方实际收到款项与应收到的款项之间的差额转入乙方资金托管/存管账户；</p>
    <p class="fontIdent">(2)如果该等错误导致乙方实际收到的款项多于应获得的金额，则无论错误的性质和原因为何，丙方有权立即予以纠正，乙方应配合丙方进行纠正包括将多转入的款项从乙方资金托管/存管账户中转出，以及要求乙方根据丙方发出的有关纠正错误的通知的具体要求返还多收的款项或进行其他操作。</p>
    <p class="fontIdent">(3)乙方理解并同意，因前述处理错误而多付或少付的款项均不计利息，丙方也不承担因前述处理错误而导致的任何损失或责任，但因丙方具有恶意而导致的处理错误除外。</p>

    <p class="fontIdentTiele">1.2.4&nbsp;乙方同意并确认，其按照本协议发出的任何指示、指令，均为不可撤销、不可逆转的指示或指令，乙方不能以任何理由拒绝付款或要求取消交易。</p>

    <h4>二、权利义务</h4>
    <p class="fontSizeP">
        <strong>2.1 </strong>乙方有义务按照丙方的要求提供真实的个人信息，因乙方提供虚假信息而造成的一切法律后果（包括但不限于民事赔偿、行政处罚等）均由乙方自行承担。</p>
    <p class="fontSizeP">
        <strong>2.2 </strong>乙方有义务按照丙方要求操作平台功能以及查收丙方发出的所有信息（包括但不限于平台站内信、手机短信等），因乙方个人操作不当或疏于查收信息而造成的损失由乙方自行承担。</p>
    <p class="fontSizeP">
        <strong>2.3 </strong>丙方平台有权就为乙方提供的服务收取服务费，服务费的具体标准见相关用户端产品（PC端：https://www.xinjucai.com 和app端:鑫聚财app）上需要收费的地方均有明确的标注。收费标准和规则将由丙方平台不时公开公布的规则及公告确定。</p>
    <p class="fontSizeP">
        <strong>2.4 </strong>乙方在使用丙方提供的平台服务，达成投融资及债权受让交易过程中涉及的其他费用，包括但不限于身份认证费用、充值手续费、提现手续费、其他认证费用除平台规则有明确规定外均应由乙方自己承担，乙方同意丙方可按相关收费方的要求进行相关费用的代收代付。</p>
    <p class="fontSizeP">
        <strong>2.5 </strong>乙方应自行承担在资金出借、债权受让过程产生的相关税收的纳税义务，并根据中国法律的规定自行向其主管税务机关申报、缴纳，丙方不承担任何代扣代缴的义务及责任，乙方违反上述规定导致丙方遭受任何损失应由乙方负责全额赔偿。</p>
    <p class="fontSizeP">
        <strong>2.6 </strong>乙方变更账户信息、通讯地址、电话等相关重要信息，须及时通知丙方。因乙方未及时通知丙方而导致的一切损失，由乙方自行承担或进行赔偿。</p>
    <p class="fontSizeP">
        <strong>2.7 </strong>如果乙方出现出借资金的继承或赠与，必须由主张权利的继承人或受赠人向丙方出示经国家行政或司法机关确认的继承或赠与权利归属证明文件，丙方确认后方予协助进行资产的转移，由此而产生的相应税费，由继承人或受赠人根据中国法律的规定自行向相关主管税务机关申报、缴纳，丙方不承担任何代扣代缴的义务及责任，继承人或受赠人违反上述规定导致丙方遭受的任何损失，由乙方全额赔偿。</p>
    <p class="fontSizeP">
        <strong>2.8 </strong>甲方债权转让成功后，乙方将在其受让债权份额内取代甲方成为新的债权人，享受债权人的权益。借款人应向乙方履行还本付息的义务，乙方自行承担借款人逾期还款或还款不能的风险。甲方授权丙方通过借款协议约定的方式向借款人发出债权转让的通知（通知方式包括但不限于丙方的相关栏目上公示、短信、站内信、电子邮件等方式）。债权转让成功之前已发生但尚未支付的收益归甲方享有；转让成功之后标的债权的所有权、收益权等概由乙方享有。</p>

    <h4>三、债权转让项目</h4>
    <c:forEach items="${list}" var="i" varStatus="x">
        <p class="fontIdentTiele">标的债权:<span id="numberCode">【${i.title} 】</span></p>
        <p class="fontIdentTiele">转让价款:<span class="chineseAmount">【 ${i.cAmount}】</span>（￥<span class="amountNu">【 ${i.parent_amount}】</span>元）</p>
        <p class="fontIdentTiele">标的债权期限:${i.limit_days} 天</p>
        <p class="fontIdentTiele">标的债权利率（年化）:<fmt:formatNumber value="${i.annualized }" groupingUsed="false" type="percent" maxFractionDigits="2"/></p>
        <p class="fontIdentTiele">利息起算日: <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd"/></p>
    </c:forEach>

    <h4>四、债权转让流程</h4>
    <p class="fontSizeP">
        <strong>4.1 </strong>本协议的签署及成立：</p>
    <p class="fontIdentTiele">(1)甲方可以通过打印本协议纸质版本并签字盖章的方式签署本协议。</p>
    <p class="fontIdentTiele">(2)乙方可以按照丙方的服务规则，在显示本协议项下债权转让项目的丙方平台页面上点击相关按钮来确认签署本协议。</p>
    <p class="fontIdentTiele">(3)甲乙双方以本协议约定的方式各自完成协议签署时，本协议自动成立。</p>
    <p class="fontIdentTiele">(4)甲方认可乙方在丙方平台上点击签署本协议电子版的合法性和有效性，对乙方通过此种方式签署本协议表示接受且无任何异议。甲方同意在任何情况下不因本协议签署方式或形式问题而质疑或否决乙方所享有的债权的法律效力，或拒绝向乙方履行应收账款质押担保义务。</p>
    <p class="fontIdentTiele">(5)乙方对于甲方的协议签订方式表示认可且无异议。</p>

    <p class="fontSizeP">
        <strong>4.2 </strong>资金冻结：乙方点击相关按钮即视为其已经向丙方发出不可撤销地授权指令，授权丙方委托其合作的资金托管/存管机构将乙方在该托管/存管机构开立的资金托管/存管账户（以下简称“乙方资金托管/存管账户”）中的等同于乙方拟“债权转让价款”资金冻结。</p>
    <p class="fontSizeP">
        <strong>4.3 </strong>本协议生效：本协议在甲方的债权转让价款全部得到满足且所对应的资金已经全部冻结时立即生效。</p>
    <p class="fontSizeP">
        <strong>4.4 </strong>资金划转：本协议生效的同时，乙方即不可撤销地授权丙方委托其合作的资金托管/存管机构，将其拟对甲方的债权转让价款划转至甲方在丙方合作的资金托管/存管机构上开立的资金托管/存管账户（以下简称“甲方资金托管/存管账户”），划转完毕即视为债权转让成功。</p>
    <p class="fontSizeP">
        <strong>4.5 </strong>资金提取：债权转让协议生效，且债权转让价款已经由资金托管/存管机构划转入甲方资金托管/存管账户后，甲方应自行登录丙方平台发起提现操作，因甲方迟延提现而造成的一切损失或责任由甲方自行承担。甲方的回购利息自债权转让协议生效之日起计算，迟延提现期间的利息正常计算，甲方不得拒绝向乙方支付迟延提现期间产生的利息。</p>

    <h4>五、保证和声明</h4>
    <p class="fontSizeP">
        <strong>5.1 </strong>乙方保证其所用于投资的资金来源合法，乙方是该资金的合法所有人，如果乙方与第三方就资金归属、资金合法性等问题发生争议，由乙方自行负责解决。如乙方未能解决，则放弃享有其所投资款项带来的利息收益，且给甲方或丙方造成损失或不利影响的，乙方应承担违约赔偿责任。</p>
    <p class="fontSizeP">
        <strong>5.2 </strong>丙方不保证乙方的投融资需求一定能够按照乙方意愿得到满足，丙方根据本协议的约定向乙方提供的所有投资咨询服务仅供乙方决定是否受让债权时予以参考，无论乙方通过丙方平台形成的投融资关系是否存在第三方担保，在任何情况下均不应视为丙方及其关联方对借款人偿还能力及本协议的履行作出了任何明示或默示的担保，亦不应视为丙方及其关联方对乙方的本金和/或收益作出了明示或默示的担保或保证。乙方应自行判断相关信息的真实性、准确性、及时性，自主决定是否受让债权，并承担由此而导致的一切损失或责任，丙方将其所知的借款人信息的变更情况或在其能力范围内所了解的借款人信息不真实、不准确的情形通知乙方，在任何情况下均不视为丙方对借款人信息的真实、准确、及时和完整性做出任何保证或承诺。</p>
    <p class="fontSizeP">
        <strong>5.3 </strong>甲方声明本协议项下标的债权系其真实、合法、有效拥有的债权，甲方对该等债权享有完全的所有权，甲方未在该等出让债权上设定抵押权、质权、其他担保物权或任何第三方权利，该等出让债权亦不存在被法院保全、查封等权利限制情况。</p>


    <h4>六、逾期回购</h4>
    <p class="fontSizeP">
        <strong>6.1 </strong>在甲方债权项下的借款人发生逾期前，乙方有权要求甲方随时回购已转让至乙方的债权，甲方同意随时回购该债权。甲方回购后仍可在丙方平台上继续转让，丙方同意为甲方已回购的债权进行多次居间撮合。</p>
    <p class="fontSizeP">
        <strong>6.2 </strong>当甲方债权项下的借款人发生逾期时，甲方向丙方申请或丙方自行撤回尚未转让的债权部分，丙方审核后将该部分债权作下架处理。甲方已通过丙方平台成功转让至乙方的债权，乙方自行承担借款人逾期还本付息的风险。</p>
    <p class="fontSizeP">
        <strong>6.3 </strong>甲方不可撤销地委托丙方在甲方债权项下的借款人发生逾期前按照乙方的回购申请划扣甲方托管/存管账户中的回购款支付给乙方，乙方获得回购款后，即丧失该部分债权，甲方重新获得该部分债权。</p>

    <h4>七、违约责任</h4>
    <p class="fontSizeP">
        <strong>7.1 </strong>一方违反其在本协议中所作的声明和保证或未完全履行其在本协议项下的义务，即视为违约，因此给守约方造成损失的，违约方应予赔偿。</p>
    <p class="fontSizeP">
        <strong>7.2 </strong>违约方因违约应赔偿给守约方的损失，包括但不限于因违约给守约方造成的直接损失，以及守约方在协议履行后可以获得的利益、守约方为防止或减少损失的扩大而支出的合理费用、守约方支付的诉讼费和律师费等间接损失。</p>
    <p class="fontSizeP">
        <strong>7.3 </strong>双方均有过错的，应根据双方实际过错程度，分别承担各自的违约责任。</p>

    <h4>八、保密</h4>
    <p class="fontSizeP">
        <strong>8.1 </strong>本协议任何一方均应对因本协议签署、履行等掌握的他方的尚未公开的各类信息（以下称“保密信息”，包括但不限于个人身份信息、融资项目信息等）予以保密，未经保密信息所有人的同意不得向任何本协议以外的人透露或进行任何非履行居间服务协议或本协议目的的利用。</p>
    <p class="fontSizeP">
        <strong>8.2 </strong>尽管有11.1条的约定，如甲方逾期还款，乙方或丙方有权根据居间服务协议及本协议的约定对甲方的保密信息予以使用或公布。</p>

    <h4>九、本协议的授权</h4>
    <p class="fontSizeP">
        <strong>9.1 </strong>本协议中约定的授权视为授权人对被授权人不可撤销的授权（具体授权内容见附件一），授权人同意被授权人可根据实际需要要求授权人出具书面的授权委托书，否则由授权人承担相应不利的后果。</p>
    <p class="fontSizeP">
        <strong>9.2 </strong>丙方基于甲方和乙方的授权/委托所产生的法律后果由相应委托方承担。</p>

    <h4>十、其他</h4>
    <p class="fontSizeP">
        <strong>10.1 </strong>本协议的任何修改、补充均须以电子文本形式做出，一旦做出，对三方均有效，三方不得以未签署纸质协议或其他理由提出反对意见。</p>

    <p class="fontSizeP">
        <strong>10.2 </strong>甲乙双方使用丙方服务，应当接受丙方公示的规则及各项协议约束。</p>
    <p class="fontSizeP">
        <strong>10.3 </strong>三方均确认，本协议的签订、生效和履行以不违反法律为前提。如果本协议中的任何一条或多条违反适用的法律，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。</p>
    <p class="fontSizeP">
        <strong>10.4 </strong>如果三方在本协议履行过程中发生任何争议，应友好协商解决；如协商不成，则须提交丙方所在地人民法院进行诉讼。</p>
    <p class="fontSizeP">
        <strong>10.5 </strong>本协议附件作为协议不可分割的一部分，与本协议具有同等法律效力。</p>
    <p class="fontSizeP">
        <strong>10.6 </strong>甲乙双方委托丙方保管所有与本协议有关的书面文件或电子信息，保存期限为本协议存续期间及本协议终止之日起5年。</p>

    </br>
    <p>（本页无正文，为《债权转让协议》之签字页）</p>
    </br>
    <p>三方确认签署协议前已充分阅读、 理解并接受本协议的全部内容， 三方同意自愿达成本协议。</p>
    <p class="fontWeigen">甲方（转让人/原债权人）：<b>${trueName}</b></p>
    <p class="fontWeigen">签署日期：<b class="start_time"><fmt:formatDate value="${time}" pattern="yyyy-MM-dd"/></b></p>

    <p class="fontWeigen">乙方（受让人/新债权人）：<b class="trueName">${user.trueName}</b></p>
    <p class="fontWeigen">签署日期：<b class="start_time"><fmt:formatDate value="${time}" pattern="yyyy-MM-dd"/></b></p>

    <p class="fontWeigen">丙方（居间人）：<b>鑫聚财（上海）金融信息服务有限公司</b></p>
    <p class="fontWeigen">签署日期：<b class="start_time"><fmt:formatDate value="${time}" pattern="yyyy-MM-dd"/></b></p>


    <h4 style="margin-top:20px;">附件一：授权委托书</h4>
    <p style="text-indent:2em;">为了履行本协议，保证乙方顺利有效地提供居间服务，甲方/乙方在此不可撤销地授权丙方可行使以下事项：</p>
    <p class="fontSizeP">1.【资金托管】委托资金托管/存管机构为甲方/乙方提供资金托管服务。</p>
    <p class="fontSizeP">2.【资金冻结】按照丙方的相关交易规则和本协议的规定，委托资金托管/存管机构冻结乙方托管/存管账户中相应数额的资金。</p>
    <p class="fontSizeP">3.【资金划扣】按照丙方的相关交易规则和本协议的规定，委托资金托管/存管机构从乙方托管/存管账户中代为划扣乙方应支付的相应数额的资金。</p>
    <p class="fontSizeP">4.【费用划扣】按照居间服务协议和本协议中的相关约定，委托资金托管/存管机构扣划甲方/乙方应支付的各项费用。</p>
    <p class="fontSizeP">5.【债权回购】委托资金托管/存管机构将甲方存入托管/存管账户中的资金按本协议约定向乙方及丙方支付。</p>
    <p class="fontSizeP">6. 相关协议中约定的其他授权。</p>
    <p class="fontSizeP" style="text-indent:2em;">丙方基于居间服务协议及本协议之授权行使授权委托事项时，甲方/乙方不能以任何理由进行干预或要求取消该授权。</p>

    <h4 style="margin-top:20px;">附件二：资费标准及说明</h4>
    <p>按本协议的规定丙方为乙方提供投资居间服务，乙方应向丙方支付投资服务费，具体投资服务费的费率及收费规则如下：</p>
    <p>平台服务费</p>
    <p class="fontSizeP">（1）债权转让产品投资人年化收益率为7.5%，如有变更另行协商。</p>
    <p class="fontSizeP">（2）债权转让按照0.5%收取平台服务费。</p>

    <p class="contract">法定代表人或授权代表（签字或盖章）
        <img class="xinjucai" src="http://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract2.png" />
    </p>
</div>
<script src="../js/zepto.min.js"></script>
<script src="../js/common.js"></script>
<script type="text/javascript">
//    var investID = getUrlParam('investID'); //投资id
//    var token = getUrlParam("token");
//    var appVersion = getUrlParam("appVersion");
//
//    // var investID = 48840;
//    // var appVersion = "5.0.0";
//    // var token = "09c5dc7783b8579fe5fd2bff355c402575820185ff69e6c1ffdd7770af2a1ce7";
//
//    var option = {
//        "appVersion": appVersion,
//        "token": token,
//        "investID": investID
//    };
//    Zepto(function($) {
//
//        if (investID) {
//            $.ajax({
//                url: app.url + "/project/queryCurrentCreditorDetail",
//                type: "get",
//                dataType: "json",
//                data: option,
//                success: function(oData) {
//                    console.log(oData);
//                    if (oData.code == 0) {
//                        alert(oData.msg);
//                        return false;
//                    }
//                    if (oData.code == 1) {
//                        $(".trueName").html(oData.data.trueName);
//                        $("#username").html(oData.data.username);
//                        $("#identityCard").html(oData.data.identityCard);
//                        $(".chineseAmount").html(oData.data.chineseAmount);
//                        $(".amountNu").html(oData.data.amount);
//                        $("#numberCode").html(oData.data.number);
//                        $(".start_time").html(oData.data.time);
//                    }
//
//                }
//            })
//        }
//
//    })
</script>
</body>

</html>
