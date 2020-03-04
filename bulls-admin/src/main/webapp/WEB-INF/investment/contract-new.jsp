<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${basePath}css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${basePath}css/icommon.css"/>
    <title>借款协议</title>
    <style type="text/css">
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
            background-color: #fff;
            color: #666;
        }

        #body.container {
            padding: 15px 15px 50px;
        }

        #body.container .title {
            font-size: 25px;
            text-align: center;
            line-height: 30px;
            margin-bottom: 20px;
        }

        #body p {
            margin: 0 0 10px;
            line-height: 20px;
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
            width: 100%;
        }

        #body table td {
            line-height: 30px;
            border-bottom: 1px solid #ddd;
            border-right: 1px solid #ddd;
            padding-left: 6px;
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
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-admin.png") no-repeat 30px;
            width: 260px;
            height: 165px;
            margin-top: 50px;
            padding-top: 50px;
            float: left;
        }

        #body .contract-common {
            width: 260px;
            height: 165px;
            margin-top: 50px;
            padding-top: 50px;
            margin-left: -75px;
            float: left;
        }

        #body .contract1 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p1.png") no-repeat 30px;
        }

        #body .contract2 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p2.png") no-repeat 30px;
        }

        #body .contract3 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p3.png") no-repeat 30px;
        }

        #body .contract4 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p4.png") no-repeat 30px;
        }

        #body .contract5 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p5.png") no-repeat 30px;
        }

        #body .contract6 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p6.png") no-repeat 30px;
        }

        #body .contract7 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p7.png") no-repeat 30px;
        }

        #body .contract8 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p8.png") no-repeat 30px;
        }

        #body .contract9 {
            background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p9.png") no-repeat 30px;
        }

        <%--#body .contract10 {--%>
        <%--background: url(${sealImg}) no-repeat 30px;--%>
        <%--background: url("https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/contract-p10.png") no-repeat 30px;--%>
        <%--}--%>
    </style>
</head>
<body>
<div id="body" class="container">
    <h2 class="title">借款协议</h2>

    <p>甲方（借款人）：<b>${invest.project.enterprise.name}</b></p>

    <p>证件类型：<b>
        <c:if test="${invest.project.enterprise.type ==0 }">营业执照</c:if>
        <c:if test="${invest.project.enterprise.type ==1 }">身份证号</c:if>
    </b></p>

    <p>证件号码：<b>
    <shiro:lacksPermission name="user:adminPhone">
        ${invest.project.enterprise.no.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1****$2")}
    </shiro:lacksPermission>
    <shiro:hasPermission name="user:adminPhone">
        ${invest.project.enterprise.no }
    </shiro:hasPermission>
    </b></p>

    <p class="margin_bt">住所地：<b>${invest.project.enterprise.background}</b></p>

    <p>乙方（出借人）：<b class="trueName">${user.trueName }</b>平台用户名：<b
            id="username">${user.username }</b></p>

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

    <h4 style="margin-bottom:10px;text-align:left;">鉴于</h4>

    <p>1.甲方为依法成立并有效存续的法人或具有完全民事权利能力和民事行为能力的自然人，存在借款需求，希望通过丙方运营管理的鑫聚财平台（域名https://www.xinjucai.com）进行融资。</p>

    <p>2.乙方为在丙方平台上完成全部注册程序并依法认证通过的注册用户，拟通过丙方的居间撮合，将自有合法资金出借给甲方。</p>

    <p>3.丙方为掌握丰富的融资项目资源信息并拥有专业的信用评价和管理服务的平台，丙方撮合甲方与乙方达成投融资交易，并由丙方提供与投融资交易的履行有关的投融资咨询服务、撮合等服务。</p>

    <p>4.甲方与丙方已签订了《借款人居间服务协议》，就丙方为甲方提供居间服务而产生的双方权利义务做了明确约定；就丙方为乙方提供居间服务而产生的双方权利义务在本协议中约定，乙丙双方不另行签订居间服务协议。</p>

    <p>
        <strong>现,</strong>三方经过友好协商，就甲乙双方通过丙方的居间撮合服务达成融资交易事项达成一致意见并签署本《借款协议》（以下简称“本协议”），以资共同遵守。</p>

    <h4>一、丙方的服务</h4>

    <p class="fontSizeP">
        <strong>1.1 </strong>投资咨询服务</p>

    <p class="fontIdentTiele">1.1.1&nbsp;丙方同意向乙方提供投资咨询服务，乙方同意接受丙方提供的该项服务。双方同意，丙方向乙方提供投资咨询服务应按如下范围和规则进行：</p>

    <p class="fontIdent">
        (1)在适用法律和技术手段允许和合理的范围内，丙方应对在丙方平台上展示的甲方基本信息进行必要的查验，以供乙方在决定是否向甲方出借资金时参考。在乙方与甲方之间债权债务关系尚未完全清偿前，丙方如获知其提供的甲方联络信息发生变更或存在不真实、不准确的情形，应及时以电子邮件（以乙方在丙方平台注册时或者注册后变更用户信息时向丙方平台提供的电子邮箱为准）或其他方式通知乙方。</p>

    <p class="fontIdent">
        (2)丙方可以选择是否通过丙方平台向乙方提供其自行收集或从其他第三方合作机构获得的除甲方联络信息之外的其他甲方信息，如甲方的工作情况、收入情况、家庭情况、信用报告、历史偿债情况等，以供乙方在自行决定是否向甲方出借资金时参考。乙方应自行判断该等信息的真实性、准确性和完整性。</p>

    <p class="fontIdent">
        (3)丙方将根据内部制定的评级规则对甲方及其各笔特定借款需求等进行信用评级，并以适当方式在丙方平台上公示，以供乙方参考。丙方有权根据其进一步获知的甲方相关信息和/或内部评级规则的调整而调整对甲方及各笔特定借款需求的信用评级。</p>

    <p class="fontSizeP">
        <strong>1.2 </strong>资金管理服务</p>

    <p class="fontIdentTiele">1.2.1&nbsp;丙方同意向乙方提供资金管理服务，乙方同意接受丙方提供的该项服务。双方同意，丙方向乙方提供资金管理服务应按如下范围和规则进行：</p>

    <p class="fontIdent">(1)资金保管：乙方应通过丙方平台在丙方合作的资金托管/存管机构以乙方名义开立资金托管/存管账户，账户中托管以下两种情形下的资金：(a)
        乙方为了出借资金或投资的目的，按照丙方平台的相关交易规则和本协议的规定向托管/存管账户转入资金；(b)
        甲方向乙方按期偿还本息及根据借款协议约定偿付的逾期罚息、违约金等。上述资金均在托管/存管账户中乙方的个人账户，独立于丙方的自有资金，丙方将严格根据乙方的指示及授权进行资金的操作。除丙方平台另有规定，上述资金在托管/存管账户托管期间不计利息。</p>

    <p class="fontIdent">(2)资金冻结：乙方同意由丙方按照丙方平台的相关交易规则和本协议的规定冻结乙方托管/存管账户中相应数额的资金，包括但不限于：(a)
        在乙方与甲方签署借款协议后，乙方同意并授权丙方在乙方托管/存管账户中冻结等值于拟出借数额的资金。该项冻结资金在借款协议生效时由托管/存管机构划转至甲方账户，冻结解除；或在甲方的借款金额未全部得到满足而导致融资项目取消时，该冻结资金解冻；
        (b)各类交易中如发生乙方应支付相应费用的，乙方同意并授权丙方在乙方托管账记中冻结等值于拟支付的费用的资金，该项冻结在交易生效支付费用时解除。</p>

    <p class="fontIdent">(3)资金代付：乙方同意并授权丙方按照丙方平台的相关交易规则和本协议的规定，委托其合作的资金托管/存管机构，从乙方托管/存管账户中代为划扣乙方应支付的相应数额的资金，包括但不限于：(a)
        根据乙方与甲方之间的借款协议应向甲方划转的借款本金；(b) 乙方应支付给相关方的相应费用等。/或内部评级规则的调整而调整对甲方及各笔特定借款需求的信用评级。</p>

    <p class="fontIdent">
        (4)资金代收：乙方同意并授权丙方按照丙方平台的相关交易规则和本协议的规定，委托其合作的资金托管/存管机构代为收取相应资金，包括但不限于：(a)乙方在借款协议项下应收回的本金和利息，并将该等代收的本金和利息在扣除相关费用后直接转入乙方托管/存管账户；(b)乙方基于《债权转让协议》应收取的债权受让人支付的债权转让款。</p>

    <p class="fontIdent">
        (5)资金提取：乙方可在丙方平台的工作时间内通过丙方平台的相应模块向丙方合作的资金托管/存管机构提出提现要求，资金托管/存管机构将相应的款项汇入乙方提供的银行账户中（根据乙方提供的银行不同，实际汇入时间可能存在差异）。除本条约定外，丙方不接受乙方提出的其他任何提现方式。</p>

    <p class="fontIdent">
        (6)资金查询：乙方有权在任何时间使用本人的用户名和密码登录丙方平台查询其在托管/存管账户下资金的情况，包括充值、冻结、锁定、代付、代收和提现记录等。乙方应理解，乙方最终收到款项的服务是由其提供的银行账户开户行提供的，需向该银行请求查证。乙方同意，其登录丙方平台查询的任何信息不能够作为相关操作或投融资交易的证据或依据；如该等信息与丙方保存的记录存在任何不一致，应以丙方所提供的记录为准。</p>

    <p class="fontIdentTiele">1.2.2&nbsp;乙方了解并同意，丙方提供上述资金管理服务时可能需要与银行或非银行业的资金托管/存管机构等开展合作。因此，乙方同意：</p>

    <p class="fontIdent">
        (1)丙方不对银行及资金托管/存管机构进行相应资金划转的时限、准确性、及时性等作出任何承诺或承担任何相关责任，包括但不限于由此产生的利息、货币贬值、银行及资金托管/存管机构执行划转指令出现错误、银行及资金托管/存管机构系统故障、银行及资金托管/存管机构对资金划转的特定限制等导致的损失或责任；</p>

    <p class="fontIdent">
        (2)就本条规定的资金管理服务，乙方应按照银行及资金托管/存管机构的规定支付相关费用（如有）。就费用支付事项产生的任何争议、纠纷等，均由乙方与银行及（或）资金托管/存管机构自行解决，丙方不承担因此而产生的任何损失或责任。</p>

    <p class="fontIdentTiele">1.2.3&nbsp;如果丙方发现因系统故障、银行或资金托管/存管机构执行指令错误或其他任何原因导致的在资金划转过程中出现的错误，无论是否有利于乙方、丙方或甲方等任何一方，丙方均有权在以电子邮件（以乙方在丙方平台注册时或者注册后变更用户信息时向丙方平台提供的电子邮箱为准）或其他方式通知乙方后立即纠正该错误：</p>

    <p class="fontIdent">(1)如果该等错误导致乙方实际收到的款项少于应获得的金额，则丙方应将乙方实际收到款项与应收到的款项之间的差额转入乙方资金托管/存管账户；</p>

    <p class="fontIdent">
        (2)如果该等错误导致乙方实际收到的款项多于应获得的金额，则无论错误的性质和原因为何，丙方有权立即予以纠正，乙方应配合丙方进行纠正包括将多转入的款项从乙方资金托管/存管账户中转出，以及要求乙方根据丙方发出的有关纠正错误的通知的具体要求返还多收的款项或进行其他操作。</p>

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
        <strong>2.4 </strong>乙方在使用丙方提供的平台服务，达成投融资及债权转让交易过程中涉及的其他费用，包括但不限于身份认证费用、充值手续费、提现手续费、其他认证费用除平台规则有明确规定外均应由乙方自己承担，乙方同意丙方可按相关收费方的要求进行相关费用的代收代付。
    </p>

    <p class="fontSizeP">
        <strong>2.5 </strong>乙方应自行承担在资金出借、债权转让过程产生的相关税收的纳税义务，并根据中国法律的规定自行向其主管税务机关申报、缴纳，丙方不承担任何代扣代缴的义务及责任，乙方违反上述规定导致丙方遭受任何损失应由乙方负责全额赔偿。
    </p>

    <p class="fontSizeP">
        <strong>2.6 </strong>乙方变更账户信息、通讯地址、电话等相关重要信息，须及时通知丙方。因乙方未及时通知丙方而导致的一切损失，由乙方自行承担或进行赔偿。</p>

    <p class="fontSizeP">
        <strong>2.7 </strong>如果乙方出现出借资金的继承或赠与，必须由主张权利的继承人或受赠人向丙方出示经国家行政或司法机关确认的继承或赠与权利归属证明文件，丙方确认后方予协助进行资产的转移，由此而产生的相应税费，由继承人或受赠人根据中国法律的规定自行向相关主管税务机关申报、缴纳，丙方不承担任何代扣代缴的义务及责任，继承人或受赠人违反上述规定导致丙方遭受的任何损失，由乙方全额赔偿。
    </p>

    <h4>三、借款项目</h4>
    <table cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <td width="36%">项目信息</td>
            <td id="project_name">${invest.project.title }</td>
        </tr>
        <tr>
            <td>借款用途</td>
            <td id="useOfFunds">${invest.project.useOfFunds }</td>
        </tr>
        <tr>
            <td>借款金额</td>
            <td id="investment_amount">${invest.project.totalAmount}元</td>
        </tr>
        <tr>
            <td>借款到期日</td>
            <td id="end_time">
                <c:forEach items="${interest }" var="i" varStatus="s">
                    <c:if test="${i.capitalAmount>0 }"><b><fmt:formatDate value="${i.date }" pattern="yyyy-MM-dd"/></b></c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td>借款利率（年化）</td>
            <td id="annualized"><fmt:formatNumber value="${invest.project.annualized }" type="percent"
                                                  maxFractionDigits="3" groupingUsed="false"/></td>
        </tr>
        <tr>
            <td>还款方式</td>
            <td>按月付息，到期还本</td>
        </tr>
        <tr>
            <td>还款计划</td>
            <td>详见附件一</td>
        </tr>
        <tr>
            <td>利息计算日</td>
            <td class="start_time">协议生效日开始计算利息</td>
        </tr>
        </tbody>
    </table>

    <h4>四、借款流程</h4>

    <p class="fontSizeP">
        <strong>4.1 </strong>本协议的签署及成立：</p>

    <p class="fontIdentTiele">(1)甲方可以通过打印本协议纸质版本并签字盖章的方式签署本协议。</p>

    <p class="fontIdentTiele">(2)乙方可以按照丙方的服务规则，在显示本协议项下借款项目的丙方平台页面上点击相关按钮来确认签署本协议。</p>

    <p class="fontIdentTiele">(3)甲乙双方以本协议约定的方式各自完成协议签署时，本协议自动成立。</p>

    <p class="fontIdentTiele">
        (4)甲方认可乙方在丙方平台上点击签署本协议电子版的合法性和有效性，对乙方通过此种方式签署本协议进而成为其债权人的方式表示接受且无任何异议。甲方同意在任何情况下不因本协议签署方式或形式问题而质疑或否决乙方所享有的债权的法律效力，或拒绝向乙方履行应收账款质押担保义务。</p>

    <p class="fontIdentTiele">(5)乙方对于甲方的协议签订方式表示认可且无异议。</p>

    <p class="fontSizeP">
        <strong>4.2 </strong>出借资金冻结：乙方点击相关按钮即视为其已经向丙方发出不可撤销地授权指令，授权丙方委托其合作的资金托管/存管机构将乙方在该托管/存管机构开立的资金托管/存管账户（以下简称“乙方资金托管/存管账户”）中的等同于乙方拟“出借金额或投资金额”资金冻结。
    </p>

    <p class="fontSizeP">
        <strong>4.3 </strong>本协议生效：本协议在甲方的借款金额全部得到满足且所对应的资金已经全部冻结时立即生效。</p>

    <p class="fontSizeP">
        <strong>4.4 </strong>出借资金划转：本协议生效的同时，乙方即不可撤销地授权丙方委托其合作的资金托管/存管机构，将其拟对甲方出借的资金划转至甲方在丙方合作的资金托管/存管机构上开立的资金托管/存管账户（以下简称“甲方资金托管/存管账户”），划转完毕即视为借款发放成功。
    </p>

    <p class="fontSizeP">
        <strong>4.5 </strong>借款资金提取：借款协议生效，且出借资金已经由资金托管/存管机构划转入甲方资金托管/存管账户后，甲方应自行登录丙方平台发起提现操作，因甲方迟延提现而造成的一切损失或责任由甲方自行承担。甲方的借款利息自借款协议生效之日起计算，迟延提现期间的利息正常计算，甲方不得拒绝向乙方支付迟延提现期间产生的利息。
    </p>

    <h4>五、保证和声明</h4>

    <p class="fontSizeP">
        <strong>5.1 </strong>乙方保证其所用于出借的资金来源合法，乙方是该资金的合法所有人，如果乙方与第三方就资金归属、资金合法性等问题发生争议，由乙方自行负责解决。如乙方未能解决，则放弃享有其所出借款项所带来的利息收益，且给甲方或丙方造成损失或不利影响的，乙方应承担违约赔偿责任。
    </p>

    <p class="fontSizeP">
        <strong>5.2 </strong>丙方不保证乙方的资金出借或投资需求一定能够按照乙方意愿得到满足，丙方根据本协议的约定向乙方提供的所有投资咨询服务仅供乙方决定是否出借资金时予以参考，无论乙方通过丙方平台形成的投融资关系是否存在第三方担保，在任何情况下均不应视为丙方及其关联方对借款人或原始债权人的偿还能力及借款协议的履行作出了任何明示或默示的担保，亦不应视为丙方及其关联方对乙方的本金和/或收益作出了明示或默示的担保或保证。乙方应自行判断相关信息的真实性、准确性、及时性，自主决定是否出借资金，并承担由此而导致的一切损失或责任，丙方将其所知的借款人信息的变更情况或在其能力范围内所了解的借款人信息不真实、不准确的情形通知乙方，在任何情况下均不视为丙方对借款人信息的真实、准确、及时和完整性做出任何保证或承诺。
    </p>

    <h4>六、本息偿还方式</h4>

    <p class="fontSizeP">
        <strong>6.1 </strong>甲方必须按照本协议的约定按时、足额偿还对乙方的本金和利息。乙方有权要求甲方按照借款协议的约定按期还本付息。</p>

    <p class="fontSizeP">
        <strong>6.2 </strong>甲方在每期还款日当日（不迟于当日24：00点）或之前，须向甲方在丙方的合作资金托管/存管机构处开设的资金托管/存管账户中充值，并通过丙方平台进行还款操作，还款金额为还款计划中的应还金额。甲方不可撤消地授权丙方委托其合作的资金托管/存管机构向乙方及丙方进行清偿。资金的清偿顺序为罚金（如有）、提前还款手续费（如有）、逾期管理服务费（如有）、平台服务费、提前还款补偿金（如有）、本金、利息等。每个出借人的利息收益按出借人的出借金额占借款总额的比例计算。
    </p>

    <p class="fontSizeP">
        <strong>6.3 </strong>如果还款日遇到法定假日或公休日，还款日期不进行顺延。</p>

    <h4>七、提前还款</h4>

    <p class="fontSizeP">
        <strong>7.1 </strong>甲方可在借款期间内任何时候通过丙方平台的“提前还款”功能提前偿还剩余借款。</p>

    <p class="fontSizeP">
        <strong>7.2 </strong>若甲方提前偿还全部剩余借款，则甲方应支付所有未付本金（含到期应还及尚未到期），截止至提前还款日的应付未付利息、提前还款手续费、提前还款补偿金、违约金（如有）及其他应付款项（如有）。
    </p>

    <p class="fontSizeP">
        <strong>7.3 </strong>提前还款补偿金的计算与收取：</p>

    <p class="fontSizeP">&nbsp;&nbsp;提前还款补偿金=未到期本金×提前还款补偿金率</p>

    <p class="fontSizeP">&nbsp;&nbsp;提前还款补偿金率=0.5%</p>

    <p class="fontSizeP">&nbsp;&nbsp;支付方式：甲方提前还款时应同时一次性支付提前还款补偿金。</p>

    <p class="fontSizeP">
        <strong>7.4 </strong>如甲方违反本协议约定或丙方的风控规则，乙方有权要求甲方立即提前偿还全部剩余借款。甲方应在收到乙方提前还款通知的3个工作日内将还款资金充值至甲方资金托管/存管账户，并授权丙方委托其合作的资金托管/存管机构按丙方平台的规则及资金清偿顺序向乙方和丙方支付应付款项，以履行其在本协议下的还款责任。
    </p>

    <p class="fontSizeP">
        <strong>7.5 </strong>乙方在此不可撤消的委托丙方代为向甲方提出提前还款要求、代为处理提前还款事宜，即在出现甲方承担提前还款义务的约定事由后，乙方授权丙方代乙方向甲方提出提前履行还款责任，向乙方进行清偿债务的要求，并在甲方同意履行提前还款义务时，代乙方向甲方处理还款事宜。此等情形下，甲方应依照约定履行义务，不得以主体是否适格为由进行抗辩。
    </p>

    <h4>八、逾期还款</h4>

    <p class="fontSizeP">
        <strong>8.1 </strong>在本协议附件一所约定的每期还款日不迟于当日24：00前，甲方未足额支付当期应还款的，则视为逾期。</p>

    <p class="fontSizeP">
        <strong>8.2 </strong>如甲方逾期还款，甲方除应向乙方支付应还本息及向丙方支付相关费用外，还应向乙方支付逾期期间的逾期利息，同时向丙方支付逾期管理服务费、违约金（如有）及其他应付款项（如有）。</p>

    <p class="fontSizeP">
        <strong>8.3 </strong>逾期利息的计算</p>

    <p class="fontSizeP">&nbsp;&nbsp;逾期整月部分：逾期利息=逾期金额*逾期利率（年化）*逾期月数（月）/12</p>

    <p class="fontSizeP">&nbsp;&nbsp;逾期不足1个月的部分：逾期利息=逾期金额*逾期利率（年化）/360*逾期天数。</p>

    <p class="fontSizeP">&nbsp;&nbsp;逾期利率（年化）=借款利率（年化）</p>

    <p class="fontSizeP">
        <strong>8.4 </strong>如甲方逾期还款，乙方有权自行或委托丙方要求甲方履行本协议第九条项下约定的还款担保责任；如甲方逾期还款，且无法充分履行本协议第九条项下约定的还款担保责任的，甲方也可自行寻求或委托丙方按照丙方平台风控规则寻求其他主体代为还款，其他主体在代为还款后获得对甲方的债权。
    </p>

    <p class="fontSizeP">
        <strong>8.5 </strong>如甲方逾期还款，在中国法律允许的范围内，丙方应向乙方提供其所知晓的甲方联络信息，并尽合理的努力采取中国法律所允许的措施协助乙方进行及时的催收和追讨，但丙方不对催收的最终结果向乙方承担责任。丙方为了乙方利益催收和追讨过程中产生的合理成本、费用将优先于乙方可获得的本息或其他资金收益而优先向丙方清偿。
    </p>

    <h4>九、还款担保</h4>

    <p class="fontSizeP">
        <strong>9.1 </strong>甲方系有转租权限的房屋出租人，与房屋承租人签有一定数量的租赁合同，甲方同意以其持有的租赁合同项下收取租金的债权对乙方的出借资金提供应收账款质押的还款担保，担保的范围为10.3条约定的应付款项
    </p>

    <p class="fontSizeP">
        <strong>9.2 </strong>甲方应配合乙方和丙方到中国人民银行征信机构办理质押登记手续。质押登记费用由甲方承担。质押的应收账款清单详见附件二。质权的效力及于应收账款在质押期间内产生的孳息。</p>

    <p class="fontSizeP">
        <strong>9.3 </strong>甲方就应收账款质押事项作出如下陈述与保证：</p>

    <p class="fontIdentTiele">(1)甲方向乙方提供的与应收账款质押有关的文件、资料及信息是真实、准确、完整和有效的；</p>

    <p class="fontIdentTiele">(2)甲方对应收账款享有充分的处分权，不存在任何瑕疵、争议、诉讼（仲裁）或任何未向乙方及丙方披露的第三人权益等情况；</p>

    <p class="fontIdentTiele">(3)甲方已获得出质应收账款所必需的所有授权、批准、同意、审批等手续。</p>

    <p class="fontSizeP">
        <strong>9.4 </strong>甲方就应收账款质押事项承担如下义务：</p>

    <p class="fontIdentTiele">(1)甲方承担因应收账款出质登记、审批、备案、评估、拍卖、变卖而产生的费用；</p>

    <p class="fontIdentTiele">(2)甲方应积极履行与第三方债务人之间的基础交易，以确保应收账款的回收和应收账款的价值；</p>

    <p class="fontIdentTiele">
        (3)甲方应当在应收账款价值减少或有减少可能的情况下，第三方债务人依法宣告破产、撤销、解散的情况下，应收账款权属发生争议或者质权受到或可能受到来自任何第三方的不利影响的情况下，立即书面通知乙方和丙方，并配合乙方或丙方采取有关措施，并按乙方或丙方要求另行提供担保。</p>

    <p class="fontSizeP">
        <strong>9.5 </strong>甲方同意将以包括但不限于事先协议安排的方式，保证所有用于质押的应收账款都由甲方和丙方在第三方机构设立的共管账户代为收取且应收账款的支付义务人应当直接付款至该共管账户。乙方不可撤销地授权丙方代为持有该共管账户。甲方进一步同意，若上述应收账款质押因中国现行或未来法律规定无法被认定为具有质押担保法律效力，甲方仍然有义务将应收账款的回收资金专用于支付各项被担保债务。
    </p>

    <p class="fontSizeP">
        <strong>9.6 </strong>在甲方未能履行全部被担保债务之前，甲方不得以任何方式改变由共管账户代收应收账款的安排，且在未经乙方事先书面同意的情况下，不得擅自就应收账款支付义务人的付款义务进行全部或部分豁免，或推迟支付期限，或作出其他任何不利于乙方或丙方债权实现的安排。
    </p>

    <p class="fontSizeP">
        <strong>9.7 </strong>若甲方未能履行全部被担保债务，且怠于实现其对应收账款支付义务人的到期债权（指未通过诉讼及强制执行的方式实现其到期债权），乙方有权要求甲方将到期债权无偿转让给乙方，并由乙方自行或委托丙方向应收账款支付义务人以包括但不限于诉讼及强制执行的方式进行应收账款催收，债权转让自乙方向甲方发出要求受让债权的书面通知之日发生效力。催收所得款项用于支付甲方在本协议10.3条约定的所有应付款项。若支付完毕后仍有剩余则无偿返还给甲方。若支付完毕后仍不足以清偿甲方的全部被担保债务，则甲方对差额部分仍应承担继续履行的义务。
    </p>

    <p class="fontSizeP">
        <strong>9.8 </strong>应收账款质押的有效期间自借款协议生效之日起至甲方偿还完10.3条约定的所有应付款项时止。</p>

    <p class="fontSizeP">
        <strong>9.9 </strong>本条款效力独立于本协议其它条款及借款协议，本协议其他条款及借款协议的无效不影响本条款的效力。</p>

    <h4>十、违约责任</h4>

    <p class="fontSizeP">
        <strong>10.1 </strong>发生下列任何一项或几项情形的，视为甲方违约：</p>

    <p class="fontIdentTiele">(1)甲方擅自改变本协议第一条约定的借款用途；</p>

    <p class="fontIdentTiele">(2)甲方因任何原因逾期支付任何一期还款的；</p>

    <p class="fontIdentTiele">(3)甲方保证其提供的信息和资料的真实性，不得提供虚假资料或隐瞒重要事实的；</p>

    <p class="fontIdentTiele">(4)甲方信息发生重大变动后五日内未书面通知乙方和丙方，经乙方或丙方询问拒绝回复的；</p>

    <p class="fontIdentTiele">(5)甲方在借款后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为的；</p>

    <p class="fontIdentTiele">(6)甲方的任何财产遭受没收、征用、查封、扣押、冻结等可能影响其履约能力的不利事件，且不能及时提供有效补救措施的；</p>

    <p class="fontIdentTiele">(7)甲方的财务状况出现影响其履约能力的不利变化，且不能及时提供有效补救措施的。</p>

    <p class="fontIdentTiele">(8)其他严重影响本协议履行的情形。</p>

    <p class="fontSizeP">
        <strong>10.2 </strong>若发生本协议第10.1条款所述情形，或根据乙方合理判断甲方可能发生本条第10.1款所述情形，乙方有权自行或委托丙方采取下列任何一项或几项救济措施：</p>

    <p class="fontIdentTiele">(1)立即暂缓、取消发放全部或部分借款；</p>

    <p class="fontIdentTiele">(2)宣布已发放借款全部提前到期，甲方应立即偿还所有应付款；</p>

    <p class="fontIdentTiele">(3)解除或终止本协议；</p>

    <p class="fontIdentTiele">(4)通过法律途径向甲方及/或担保人进行追偿（包括但不限于代为委托律师提起诉讼或仲裁）；</p>

    <p class="fontIdentTiele">(5)委托第三方专业机构进行催收；</p>

    <p class="fontIdentTiele">(6)将该笔债权转让予第三方资产管理公司等机构；</p>

    <p class="fontIdentTiele">(7)可能构成犯罪的，乙方或乙方授权丙方向国家机关报案；</p>

    <p class="fontIdentTiele">(8)采取法律、法规以及本协议约定的其他救济措施。</p>

    <p class="fontSizeP">
        <strong>10.3 </strong>甲方应偿还的所有应付款项包括但不限于：居间服务协议和借款协议项下本金及利息、罚息、平台服务费、逾期管理服务费、违约金、损害赔偿金以及实现债权及担保权的费用等所有甲方应向乙方及丙方支付的费用总额。实现债权及担保权的费用包括但不限于催收费用、诉讼费（或仲裁费）、保全费、公告费、执行费、律师费、差旅费及其它费用。
    </p>

    <p class="fontSizeP">
        <strong>10.4 </strong>本协议中所称全部借款到期日是指，若甲方对任何一期应还款逾期达到下一个还款日或超过31天（以较早日期为准）时，则相对应的借款协议项下借款全部提前到期；若借款逾期未达到下一个还款日但已届最终借款到期日的，仍以最终借款到期日为全部借款到期日。
    </p>

    <p class="fontSizeP">
        <strong>10.5 </strong>甲方须在乙方提出解除或终止本协议要求的三日内，一次性支付截止当日的全部应付未付款项、归属乙方的尚未到期的所有本金、利息、罚息（如有）、违约金（如有）及其他应付款项。</p>

    <p class="fontSizeP">
        <strong>10.6 </strong>乙方保留将甲方违约失信的相关信息在媒体披露的权利。</p>

    <p class="fontSizeP">
        <strong>10.7 </strong>如对甲方追索后实现的债权不足以偿还本协议项下甲方应偿还的所有应付款项，则乙方同意在扣除为实现债权支付的相关费用及丙方和资金托管/存管机构的管理费用后，按照其占该融资项目中所有出借人的应收款项比例收取本金及利息。
    </p>

    <h4>十一、保密</h4>

    <p class="fontSizeP">
        <strong>11.1 </strong>本协议任何一方均应对因本协议签署、履行等掌握的他方的尚未公开的各类信息（以下称“保密信息”，包括但不限于个人身份信息、融资项目信息等）予以保密，未经保密信息所有人的同意不得向任何本协议以外的人透露或进行任何非履行居间服务协议或本协议目的的利用。
    </p>

    <p class="fontSizeP">
        <strong>11.2 </strong>尽管有11.1条的约定，如甲方逾期还款，乙方或丙方有权根据居间服务协议及本协议的约定对甲方的保密信息予以使用或公布。</p>

    <h4>十二、变更通知</h4>

    <p class="fontSizeP">
        <strong>12.1 </strong>自本协议成立之日起至借款全部清偿完毕之日止，若甲方向乙方提供的任何信息发生变更，甲方有义务在信息变更后的5日内通过丙方向乙方提供更新后的信息，并通过丙方向乙方提交相应的证明文件，包括但不限于甲方名称、法定代表人身份证号码、住所、联络人等基本信息，及公司经营情况、财务情况、信用报告、历史偿债情况等甲方信用信息等其他信息。
    </p>

    <p class="fontSizeP">
        <strong>12.2 </strong>若因甲方未能及时提供上述变更后的信息而带来的一切责任和后果由甲方承担。</p>

    <h4>十三、债权转让</h4>

    <p class="fontSizeP">
        <strong>13.1 </strong>甲乙双方同意并确认，乙方可将本协议项下的全部或部分借款债权转让予第三方，但该第三方必须为丙方的注册用户或征得丙方同意的其他第三方。</p>

    <p class="fontSizeP">
        <strong>13.2 </strong>乙方转让其借款债权的，不需要征求甲方的同意，乙方自行或委托丙方在转让完成后通知甲方（通知方式包括但不限于丙方的相关栏目上公示、短信、站内信、电子邮件等方式）即可，甲方对任何符合13.1条件的债权转让行为均无异议。
    </p>

    <p class="fontSizeP">
        <strong>13.3 </strong>乙方根据本协议转让借款债权的，除本协议项下的提供借款的一方变更为债权受让人外，本协议项下其他条款不受影响，且变更内容对甲方仍有约束力，甲方需对债权受让人在剩余借款期限继续履行本协议下其对乙方的还款义务。
    </p>

    <h4>十四、本协议的转让</h4>

    <p class="fontSizeP">未经乙方事先书面（包括但不限于电子邮件等方式）同意，甲方不得将本协议项下的任何权利义务转让给任何第三方。</p>

    <h4>十五、本协议的授权</h4>

    <p class="fontSizeP">
        <strong>15.1 </strong>本协议中约定的授权视为授权人对被授权人不可撤销的授权（具体授权内容见附件三），授权人同意被授权人可根据实际需要要求授权人出具书面的授权委托书，否则由授权人承担相应不利的后果。
    </p>

    <p class="fontSizeP">
        <strong>15.2 </strong>丙方基于甲方和乙方的授权/委托所产生的法律后果由相应委托方承担。</p>

    <h4>十六、其他</h4>

    <p class="fontSizeP">
        <strong>16.1 </strong>甲方发布的相应借款需求未在规定时间内被全部满足并已经适当冻结相应拟出借资金的，自规定期满日24：00起，乙方资金解除冻结。</p>

    <p class="fontSizeP">
        <strong>16.2 </strong>本协议的任何修改、补充均须以电子文本形式做出，一旦做出，对三方均有效，三方不得以未签署纸质协议或其他理由提出反对意见。</p>

    <p class="fontSizeP">
        <strong>16.3 </strong>甲乙双方使用丙方服务，应当接受丙方公示的规则及各项协议约束。</p>

    <p class="fontSizeP">
        <strong>16.4 </strong>三方均确认，本协议的签订、生效和履行以不违反法律为前提。如果本协议中的任何一条或多条违反适用的法律，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。</p>

    <p class="fontSizeP">
        <strong>16.5 </strong>如果三方在本协议履行过程中发生任何争议，应友好协商解决；如协商不成，则须提交本协议签订地人民法院进行诉讼。</p>

    <p class="fontSizeP">
        <strong>16.6 </strong>本协议附件作为协议不可分割的一部分，与本协议具有同等法律效力。</p>

    <p class="fontSizeP">
        <strong>16.7 </strong>甲乙双方委托丙方保管所有与本协议有关的书面文件或电子信息，保存期限为本协议存续期间及本协议终止之日起3年。</p>

    <p class="fontWeigen">甲方（借款人）：<b>${invest.project.enterprise.name}</b></p>

    <p class="fontWeigen">签署日期：<b class="start_time"><fmt:formatDate value="${invest.time }" pattern="yyyy-MM-dd"/></b>
    </p>

    <p class="fontWeigen">乙方（出借人）：<b class="trueName">${user.trueName}</b>
    </p>

    <p class="fontWeigen">签署日期：<b class="start_time"><fmt:formatDate value="${invest.time }" pattern="yyyy-MM-dd"/></b>
    </p>

    <p class="fontWeigen">丙方（居间人）：<b>鑫聚财（上海）金融信息服务有限公司</b></p>

    <p class="fontWeigen">签署日期：<b class="start_time"><fmt:formatDate value="${invest.time }" pattern="yyyy-MM-dd"/></b>
    </p>

    <h4 style="margin-top:20px;">附件一：还款计划</h4>
    <table style="text-align:center;" cellpadding="0" cellspacing="0">
        <tbody id="dividend_date">
        <tr>
            <td>期数</td>
            <td>应还金额</td>
            <td>应还本金（元）</td>
            <td>应还利息（元）</td>
            <td>平台服务费（元）</td>
        </tr>
        <c:forEach items="${interest }" var="i">
            <tr>
                <td><fmt:formatDate value="${i.date }" pattern="yyyy-MM-dd"/></td>
                <td>${i.interestAmount+i.capitalAmount }</td>
                <td>${i.capitalAmount }</td>
                <td>${i.interestAmount }</td>
                <td>0</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>注：每期的还款日可以登录丙方的相关栏目查询。还款计划有调整的，以丙方实际公布调整的为准，甲乙双方应及时登陆丙方账户查询最新调整变化及丙方公布的各项规则及公告。</p>

    <h4 style="margin-top:20px;">附件二：质押应收账款清单</h4>
    <table style="text-align:center;" cellpadding="0" cellspacing="0">
        <tbody id="accountTable">
        <tr>
            <td>交易合同名称</td>
            <td>交易合同编号</td>
            <td>交易合同签署日期</td>
            <td>应收账款基础交易</td>
            <td>应收账款金额</td>
            <td>应收账款到期日</td>
        </tr>
        <c:forEach items="${account }" var="i">
            <tr>
                <td>${i.name }</td>
                <td>${i.orderNo }</td>
                <td><fmt:formatDate value="${i.signDate }" pattern="yyyy-MM-dd"/></td>
                <td>${i.basicAmount }</td>
                <td>${i.amount }</td>
                <td><fmt:formatDate value="${i.endTime }" pattern="yyyy-MM-dd"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h4 style="margin-top:20px;">附件三：授权委托书</h4>

    <p style="text-indent:2em;">
        为了履行本协议，保证乙方顺利有效地提供居间服务，使得借款人的借款需求尽可能及时得到满足，投资人的投资行为能够顺利有效地开展，特甲方/乙方在此不可撤销地授权丙方可行使以下事项：</p>

    <p>1.【资金托管】委托资金托管/存管机构为甲方/乙方提供资金托管服务。</p>

    <p>2.【资金冻结】按照丙方的相关交易规则和本协议的规定，委托资金托管/存管机构冻结乙方托管/存管账户中相应数额的资金。</p>

    <p>3.【资金划扣】按照丙方的相关交易规则和本协议的规定，委托资金托管/存管机构从乙方托管/存管账户中代为划扣乙方应出借或支付的相应数额的资金。</p>

    <p>4.【费用划扣】按照居间服务协议和本协议中的相关约定，委托资金托管/存管机构扣划甲方/乙方应支付的各项费用。</p>

    <p>5.【债务清偿】委托资金托管/存管机构将甲方存入托管/存管账户中的资金按本协议约定的清偿顺序向乙方及丙方进行清偿。</p>

    <p>6.【提前还款】如甲方违反本协议约定或丙方的风控规则，丙方可要求甲方立即提前偿还全部剩余借款。</p>

    <p>7.【共管账户】所有质押的甲方应收账款都由甲方和丙方在第三方机构设立的共管账户代为收取，丙方可按照居间服务协议和本协议中的相关约定对该共管账户中的资金进行划扣。</p>

    <p>8.【还款追偿】若甲方未按时足额履行还款或代还款义务，丙方可以本协议或借款协议约定的方式进行应收账款的催收。</p>

    <p>9.【电子存证】授权丙方代为委托第三方电子签名服务商生成甲方/乙方电子签章，并授权丙方使用该电子签章在线签署电子协议。</p>
    
    <p>10.相关协议中约定的其他授权。</p>

    <p style="text-indent:2em;">丙方基于居间服务协议及本协议之授权行使授权委托事项时，甲方/乙方不能以任何理由进行干预或要求取消该授权。</p>

    <!-- <h4 style="margin-top:20px;">附件四：资费标准及说明</h4>
    <p></p> -->

    <p class="contract">法定代表人或授权代表（签字或盖章）</p>
    <c:if test="${invest.project.id==1 || invest.project.id==6}">
        <p class="contract1 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==2 || invest.project.id==3}">
        <p class="contract2 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==7 || invest.project.id==8}">
        <p class="contract3 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==9 || invest.project.id==22}">
        <p class="contract4 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==10 || invest.project.id==11}">
        <p class="contract5 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==13 || invest.project.id==14}">
        <p class="contract6 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==15 || invest.project.id==16}">
        <p class="contract7 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==17 || invest.project.id==18}">
        <p class="contract8 contract-common"></p>
    </c:if>
    <c:if test="${invest.project.id==19 || invest.project.id==21}">
        <p class="contract9 contract-common"></p>
    </c:if>
    <p class="contract10 contract-common"></p>
</div>
</body>
</html>